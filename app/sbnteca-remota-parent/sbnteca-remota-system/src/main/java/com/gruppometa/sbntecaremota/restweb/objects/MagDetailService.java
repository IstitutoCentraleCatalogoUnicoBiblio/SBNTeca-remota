package com.gruppometa.sbntecaremota.restweb.objects;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.gruppometa.data.mets.Mets;
import com.gruppometa.data.mods.Mods;
import com.gruppometa.mets2mag.MetsConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gruppometa.sbntecaremota.objects.DataResourceDelivery;
import com.gruppometa.sbntecaremota.objects.Mag;
import com.gruppometa.sbntecaremota.objects.json.JsonMagDetail;
import com.gruppometa.sbntecaremota.objects.json.JsonMagDetailField;
import com.gruppometa.sbntecaremota.objects.json.JsonMagDetailImgTarget;
import com.gruppometa.sbntecaremota.objects.json.JsonMagDetailPiece;
import com.gruppometa.sbntecaremota.objects.json.JsonMagDetailResource;
import com.gruppometa.sbntecaremota.objects.json.JsonMagDetailStru;
import com.gruppometa.sbntecaremota.objects.json.JsonMagDetailStruElement;
import com.gruppometa.sbntecaremota.objects.json.JsonMagPresentation;
import com.gruppometa.sbntecaremota.objects.json.JsonMagPresentationAudioGroup;
import com.gruppometa.sbntecaremota.objects.json.JsonMagPresentationDisItem;
import com.gruppometa.sbntecaremota.objects.json.JsonMagPresentationHoldings;
import com.gruppometa.sbntecaremota.objects.json.JsonMagPresentationImgGroup;
import com.gruppometa.sbntecaremota.objects.json.JsonMagPresentationImgTarget;
import com.gruppometa.sbntecaremota.objects.json.JsonMagPresentationPiece;
import com.gruppometa.sbntecaremota.objects.json.JsonMagPresentationResource;
import com.gruppometa.sbntecaremota.objects.json.JsonMagPresentationResourceType;
import com.gruppometa.sbntecaremota.objects.json.JsonMagPresentationStru;
import com.gruppometa.sbntecaremota.objects.json.JsonMagPresentationUsageResourceList;
import com.gruppometa.sbntecaremota.objects.json.JsonMagPresentationVideoGroup;
import com.gruppometa.sbntecaremota.retrieve.MagResourceDelivery;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import com.gruppometa.sbntecaremota.util.ThumbnailCreator;
import com.gruppometa.sbntecaremota.util.UtilSolr;
import com.gruppometa.sbntecaremota.util.UtilXML;

/**
 * Classe che si occupa di fornire i dati legati alle scheda di dettaglio dei MAG
 *
 *
 */
public class MagDetailService {

	// tipi risorsa
	private static final List<String> RESOURCE_TYPES = Arrays.asList("img", "audio", "video", "ocr", "doc");
	
	@Autowired
	private ThumbnailCreator thumbCreator;

	@Autowired
	private MagResourceDelivery delivery;

	// logger
	private static Logger logger = LoggerFactory.getLogger(MagDetailService.class);

	public String getMetsDetail(String magID) throws FileNotFoundException {
		Mag mag = UtilSolr.selectDocumentById(magID);
		String add = "";
		if(mag == null){
			String path = UtilSolr.getPathFromId(magID);
			add = " Trovato path: "+path;
			mag = UtilSolr.selectDocumentByPath(path);
		}
		if(mag == null)
			throw new FileNotFoundException("MAG richiesto non trovato nei risultati di ricerca"+add);
		try {
			MetsConvertor metsConvertor = new MetsConvertor();
			String metsString = mag.getMetsExternal()==null?mag.getMetsOriginal():
					mag.getMetsExternal();
			Mets mets =  metsConvertor.readMetsFromString(metsString);
			if(mets==null)
				return "No data";
			return metsConvertor.convertMets2Json(mets);
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	public String getMetsDetailWithGenImp(String magID) throws FileNotFoundException {
		Mag mag = UtilSolr.selectDocumentById(magID);

		if(mag == null)
			throw new FileNotFoundException("MAG richiesto non trovato nei risultati di ricerca");
		try {
			/**
			 * configurazione
			 */
			JacksonXmlModule module = new JacksonXmlModule();
			module.setDefaultUseWrapper(false);
			ObjectMapper mapper = new XmlMapper(module);
			JaxbAnnotationModule jaxbAnnotationModule = new JaxbAnnotationModule();
			mapper.registerModule(jaxbAnnotationModule);
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			//mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			//mapper.disable(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS);

			ObjectMapper mapperJson = new ObjectMapper();
			mapperJson.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			mapperJson.setSerializationInclusion(JsonInclude.Include.NON_NULL);

			/**
			 * creazione output
			 */
			Mets mets =  mapper.readValue(mag.getMetsExternal(), Mets.class);
			JsonNode jsonNode = mapper.convertValue(mets, JsonNode.class);

			/**
			 * iniezione parte generale dal MAG
			 */
			JsonMagPresentation jsonMagPresentation = getMagDetail(magID);
			JsonNode jsonNodeMag = mapper.convertValue(jsonMagPresentation, JsonNode.class);
			((ObjectNode)jsonNode).putIfAbsent("importazione", jsonNodeMag.get("importazione"));
			((ObjectNode)jsonNode).putIfAbsent("gen", jsonNodeMag.get("gen"));
			return mapperJson.writeValueAsString(jsonNode);

		} catch (JsonProcessingException e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * Restituisce i metadati del MAG da visualizzare nella scheda di dettaglio
	 * 
	 * SCHEDA DETTAGLIO MAG
	 * 
	 * @param magID ID del MAG
	 * @return JsonMagPresentation oggetto riepilogo metadati MAG
	 * @throws FileNotFoundException
	 */
	public JsonMagPresentation getMagDetail(String magID) throws FileNotFoundException {
		Mag mag = UtilSolr.selectDocumentById(magID);
		
		if(mag == null)
			throw new FileNotFoundException("MAG richiesto non trovato nei risultati di ricerca");
		
		Document doc = UtilXML.convertStringToDocumentXML(mag.getMagInternal());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		JsonMagPresentation jsonDetail = new JsonMagPresentation();
		jsonDetail.getImp().setProject(mag.getProject());
		jsonDetail.getImp().setPublicFlag(mag.getPublishFlag() + "");
		jsonDetail.getImp().setOaiIdentifier(mag.getOaiIdentifier());
		jsonDetail.getImp().setLastModified(formatter.format(mag.getTimestamp()));

		
		Element genNode = (Element) doc.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "gen").item(0);
		
		if(genNode.hasAttribute("creation"))
			jsonDetail.getGen().setCreation(genNode.getAttribute("creation"));
		
		if(genNode.hasAttribute("last_update"))
			jsonDetail.getGen().setLastUpdate(genNode.getAttribute("last_update"));
		
		NodeList genChildren = genNode.getChildNodes();
		
		for(int i = 0; i < genChildren.getLength(); i++) {
			Node node = genChildren.item(i);
			
			if(node.getNodeType() == Document.ELEMENT_NODE) {
				Element elem = (Element) node;
				
				if("collection".equals(elem.getLocalName()))
					jsonDetail.getGen().setCollection(elem.getTextContent());
				
				else if("agency".equals(elem.getLocalName()))
					jsonDetail.getGen().setAgency(elem.getTextContent());
				
				else if("stprog".equals(elem.getLocalName()))
					jsonDetail.getGen().setStprog(elem.getTextContent());
				
				else if("access_rights".equals(elem.getLocalName()))
					jsonDetail.getGen().setAccessRights(elem.getTextContent());
				
				else if("completeness".equals(elem.getLocalName()))
					jsonDetail.getGen().setCompleteness(elem.getTextContent());
				
				else if("img_group".equals(elem.getLocalName())) {
					JsonMagPresentationImgGroup imggroup = new JsonMagPresentationImgGroup();
					imggroup.setId(elem.getAttribute("ID"));
					NodeList groupChildren = elem.getChildNodes();
					
					for(int j = 0; j < groupChildren.getLength(); j++) {
						Node childNode = groupChildren.item(j);
						
						if(childNode.getNodeType() == Document.ELEMENT_NODE) {
							if("image_metrics".equals(childNode.getLocalName())) {
								NodeList metricsChildren = childNode.getChildNodes();
								
								for(int k = 0; k < metricsChildren.getLength(); k++) {
									Node metricsChild = metricsChildren.item(k);
									
									if(metricsChild.getNodeType() == Document.ELEMENT_NODE) {
										if("samplingfrequencyunit".equals(metricsChild.getLocalName()))
											imggroup.setSamplingFrequencyUnit(metricsChild.getTextContent());
										
										else if("samplingfrequencyplane".equals(metricsChild.getLocalName()))
											imggroup.setSamplingFrequencyPlane(metricsChild.getTextContent());
										
										else if("xsamplingfrequency".equals(metricsChild.getLocalName()))
											imggroup.setXSamplingFrequency(metricsChild.getTextContent());
										
										else if("ysamplingfrequency".equals(metricsChild.getLocalName()))
											imggroup.setYSamplingFrequency(metricsChild.getTextContent());
										
										else if("photometricinterpretation".equals(metricsChild.getLocalName()))
											imggroup.setPhotometricInterpretation(metricsChild.getTextContent());
										
										else if("bitpersample".equals(metricsChild.getLocalName()))
											imggroup.setBitPerSample(metricsChild.getTextContent());
									}
								}
							}
							
							else if("format".equals(childNode.getLocalName())) {
								NodeList formatChildren = childNode.getChildNodes();
								
								for(int k = 0; k < formatChildren.getLength(); k++) {
									Node formatChild = formatChildren.item(k);
									
									if(formatChild.getNodeType() == Document.ELEMENT_NODE) {
										if("name".equals(formatChild.getLocalName()))
											imggroup.setFormatName(formatChild.getTextContent());
										
										else if("mime".equals(formatChild.getLocalName()))
											imggroup.setFormatMime(formatChild.getTextContent());
										
										else if("compression".equals(formatChild.getLocalName()))
											imggroup.setFormatCompression(formatChild.getTextContent());
									}
								}
							}
							
							else if("ppi".equals(childNode.getLocalName()))
								imggroup.setPpi(childNode.getTextContent());
							
							else if("dpi".equals(childNode.getLocalName()))
								imggroup.setDpi(childNode.getTextContent());
							
							else if("scanning".equals(childNode.getLocalName())) {
								NodeList scanningChildren = childNode.getChildNodes();
								
								for(int k = 0; k < scanningChildren.getLength(); k++) {
									Node scanningChild = scanningChildren.item(k);
									
									if(scanningChild.getNodeType() == Document.ELEMENT_NODE) {
										if("sourcetype".equals(scanningChild.getLocalName()))
											imggroup.setSourceType(scanningChild.getTextContent());
										
										else if("scanningagency".equals(scanningChild.getLocalName()))
											imggroup.setScanningAgency(scanningChild.getTextContent());
										
										else if("devicesource".equals(scanningChild.getLocalName()))
											imggroup.setDeviceSource(scanningChild.getTextContent());

										else if("scanningsystem".equals(scanningChild.getLocalName())) {
											NodeList systemChildren = scanningChild.getChildNodes();
											
											for(int l = 0; l < systemChildren.getLength(); l++) {
												Node systemChild = systemChildren.item(l);
												
												if(systemChild.getNodeType() == Document.ELEMENT_NODE) {
													if("scanner_manufacturer".equals(systemChild.getLocalName()))
														imggroup.setScannerManufacturer(systemChild.getTextContent());
													
													else if("scanner_model".equals(systemChild.getLocalName()))
														imggroup.setScannerModel(systemChild.getTextContent());
													
													else if("capture_software".equals(systemChild.getLocalName()))
														imggroup.setCaptureSoftware(systemChild.getTextContent());
												}
											}
										}
									}
								}
							}
						}
					}
					
					jsonDetail.getGen().getImgGroups().add(imggroup);
				}
				
				else if("audio_group".equals(elem.getLocalName())) {
					JsonMagPresentationAudioGroup audiogroup = new JsonMagPresentationAudioGroup();
					audiogroup.setId(elem.getAttribute("ID"));
					NodeList groupChildren = elem.getChildNodes();
					
					for(int j = 0; j < groupChildren.getLength(); j++) {
						Node childNode = groupChildren.item(j);
						
						if(childNode.getNodeType() == Document.ELEMENT_NODE) {
							NodeList dataNodes = childNode.getChildNodes();
							
							for(int k = 0; k < dataNodes.getLength(); k++) {
								Node dataNode = dataNodes.item(k);
								
								if(dataNode.getNodeType() == Document.ELEMENT_NODE) {
									if("samplingfrequency".equals(dataNode.getLocalName()))
										audiogroup.setSamplingFrequency(dataNode.getTextContent());
									
									else if("bitpersample".equals(dataNode.getLocalName()))
										audiogroup.setBitPerSample(dataNode.getTextContent());
									
									else if("bitrate".equals(dataNode.getLocalName()))
										audiogroup.setBitRate(dataNode.getTextContent());
									
									else if("name".equals(dataNode.getLocalName()))
										audiogroup.setFormatName(dataNode.getTextContent());
									
									else if("mime".equals(dataNode.getLocalName()))
										audiogroup.setFormatMime(dataNode.getTextContent());
									
									else if("compression".equals(dataNode.getLocalName()))
										audiogroup.setFormatCompression(dataNode.getTextContent());
									
									else if("channel_configuration".equals(dataNode.getLocalName()))
										audiogroup.setChannelConfiguration(dataNode.getTextContent());
								}
							}
						}
					}
					
					jsonDetail.getGen().getAudioGroups().add(audiogroup);
				}
				
				else if("video_group".equals(elem.getLocalName())) {
					JsonMagPresentationVideoGroup videogroup = new JsonMagPresentationVideoGroup();
					videogroup.setId(elem.getAttribute("ID"));
					NodeList groupChildren = elem.getChildNodes();
					
					for(int j = 0; j < groupChildren.getLength(); j++) {
						Node childNode = groupChildren.item(j);
						
						if(childNode.getNodeType() == Document.ELEMENT_NODE) {
							NodeList dataNodes = childNode.getChildNodes();
							
							for(int k = 0; k < dataNodes.getLength(); k++) {
								Node dataNode = dataNodes.item(k);
								
								if(dataNode.getNodeType() == Document.ELEMENT_NODE) {
									if("videosize".equals(dataNode.getLocalName()))
										videogroup.setVideoSize(dataNode.getTextContent());
									
									else if("aspectratio".equals(dataNode.getLocalName()))
										videogroup.setAspectRatio(dataNode.getTextContent());
									
									else if("framerate".equals(dataNode.getLocalName()))
										videogroup.setFrameRate(dataNode.getTextContent());
									
									else if("name".equals(dataNode.getLocalName()))
										videogroup.setFormatName(dataNode.getTextContent());
									
									else if("mime".equals(dataNode.getLocalName()))
										videogroup.setFormatMime(dataNode.getTextContent());
									
									else if("videoformat".equals(dataNode.getLocalName()))
										videogroup.setFormatVideo(dataNode.getTextContent());
									
									else if("encode".equals(dataNode.getLocalName()))
										videogroup.setFormatEncode(dataNode.getTextContent());
									
									else if("streamtype".equals(dataNode.getLocalName()))
										videogroup.setFormatStreamType(dataNode.getTextContent());
									
									else if("codec".equals(dataNode.getLocalName()))
										videogroup.setFormatCodec(dataNode.getTextContent());
								}
							}
						}
					}
					
					jsonDetail.getGen().getVideoGroups().add(videogroup);
				}
			}
		}
		
		
		
		Element bibNode = (Element) doc.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "bib").item(0);
		jsonDetail.getBib().setLevel(bibNode.getAttribute("level"));
		NodeList bibChildren = bibNode.getChildNodes();
		
		for(int i = 0; i < bibChildren.getLength(); i++) {
			Node node = bibChildren.item(i);
			
			if(node.getNodeType() == Document.ELEMENT_NODE) {
				Element elem = (Element) node;
				
				if("identifier".equals(elem.getLocalName()))
					jsonDetail.getBib().getIdentifiers().add(elem.getTextContent());
				
				else if("identifier".equals(elem.getLocalName()))
					jsonDetail.getBib().getIdentifiers().add(elem.getTextContent());
				
				else if("title".equals(elem.getLocalName()))
					jsonDetail.getBib().getTitles().add(elem.getTextContent());
				
				else if("creator".equals(elem.getLocalName()))
					jsonDetail.getBib().getCreators().add(elem.getTextContent());
				
				else if("publisher".equals(elem.getLocalName()))
					jsonDetail.getBib().getPublishers().add(elem.getTextContent());
				
				else if("subject".equals(elem.getLocalName()))
					jsonDetail.getBib().getSubjects().add(elem.getTextContent());
				
				else if("description".equals(elem.getLocalName()))
					jsonDetail.getBib().getDescriptions().add(elem.getTextContent());
				
				else if("contributor".equals(elem.getLocalName()))
					jsonDetail.getBib().getContributors().add(elem.getTextContent());
				
				else if("date".equals(elem.getLocalName()))
					jsonDetail.getBib().getDates().add(elem.getTextContent());
				
				else if("type".equals(elem.getLocalName()))
					jsonDetail.getBib().getTypes().add(elem.getTextContent());
				
				else if("format".equals(elem.getLocalName()))
					jsonDetail.getBib().getFormats().add(elem.getTextContent());
				
				else if("source".equals(elem.getLocalName()))
					jsonDetail.getBib().getSources().add(elem.getTextContent());
				
				else if("language".equals(elem.getLocalName()))
					jsonDetail.getBib().getLanguages().add(elem.getTextContent());
				
				else if("relation".equals(elem.getLocalName()))
					jsonDetail.getBib().getRelations().add(elem.getTextContent());
				
				else if("coverage".equals(elem.getLocalName()))
					jsonDetail.getBib().getCoverages().add(elem.getTextContent());
				
				else if("rights".equals(elem.getLocalName()))
						jsonDetail.getBib().getRights().add(elem.getTextContent());
				
				else if("local_bib".equals(elem.getLocalName())) {
					NodeList localBibChildren = elem.getChildNodes();
					
					for(int j = 0; j < localBibChildren.getLength(); j++) {
						Node localNode = localBibChildren.item(j);
						
						if(localNode.getNodeType() == Node.ELEMENT_NODE) {
							if("geo_coord".equals(localNode.getLocalName()))
								jsonDetail.getBib().getGeoCoord().add(localNode.getTextContent());
							
							else if("not_date".equals(localNode.getLocalName()))
								jsonDetail.getBib().getNotDates().add(localNode.getTextContent());
						}
					}
				}
				
				else if("holdings".equals(elem.getLocalName())) {
					JsonMagPresentationHoldings holding = new JsonMagPresentationHoldings();
					
					if(elem.hasAttribute("ID"))
						holding.setId(elem.getAttribute("ID"));
					
					NodeList holdingChildren = elem.getChildNodes();
					
					for(int j = 0; j < holdingChildren.getLength(); j++) {
						Node childNode = holdingChildren.item(j);
						
						if(childNode.getNodeType() == Node.ELEMENT_NODE) {
							if("library".equals(childNode.getLocalName()))
								holding.setLibrary(childNode.getTextContent());
							
							else if("inventory_number".equals(childNode.getLocalName()))
								holding.setInventoryNumber(childNode.getTextContent());
							
							else if("shelfmark".equals(childNode.getLocalName()))
								holding.getShelfmarks().add(childNode.getTextContent());
						}
					}
					
					jsonDetail.getBib().getHoldings().add(holding);
				}
				
				else if("piece".equals(elem.getLocalName())) {
					JsonMagPresentationPiece piece = new JsonMagPresentationPiece();
					NodeList pieceChildren = elem.getChildNodes();
					
					for(int j = 0; j < pieceChildren.getLength(); j++) {
						Node childNode = pieceChildren.item(j);
						
						if(childNode.getNodeType() == Node.ELEMENT_NODE) {
							if("issue".equals(childNode.getLocalName()))
								piece.setIssue(childNode.getTextContent());
							
							else if("year".equals(childNode.getLocalName()))
								piece.setYear(childNode.getTextContent());
							
							else if("stpiece_per".equals(childNode.getLocalName()))
								piece.setStpiecePer(childNode.getTextContent());
							
							else if("part_number".equals(childNode.getLocalName()))
								piece.setPartNumber(childNode.getTextContent());
							
							else if("part_name".equals(childNode.getLocalName()))
								piece.setPartName(childNode.getTextContent());
							
							else if("stpiece_vol".equals(childNode.getLocalName()))
								piece.setStpieceVol(childNode.getTextContent());
						}
					}
					
					jsonDetail.getBib().setPiece(piece);
				}
			}
		}
		
		
		
		NodeList struNodeList = doc.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "stru");
		
		for(int i = 0; i < struNodeList.getLength(); i++) {
			Element struNode = (Element) struNodeList.item(i);
			
			if(!"stru".equals(struNode.getParentNode().getLocalName())) {
				JsonMagPresentationStru stru = new JsonMagPresentationStru();
				stru.setType(JsonMagPresentationStru.TYPE_STRU);
				this.mapStru(struNode, stru);
				jsonDetail.getStru().add(stru);
			}
		}
		
		
		
		for(String resourceType : RESOURCE_TYPES) {
			NodeList resourceNodeList = doc.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", resourceType);
			
			if(resourceNodeList.getLength() > 0) {
				JsonMagPresentationResourceType jsonResourceType = new JsonMagPresentationResourceType();
				jsonResourceType.setType(resourceType);
				Map<String, List<JsonMagPresentationResource>> resourceUsageMap = new HashMap<String, List<JsonMagPresentationResource>>();
				
				if("ocr".equals(resourceType) || "doc".equals(resourceType)) {
					for(int i = 0; i < resourceNodeList.getLength(); i++) {
						Element resourceNode = (Element) resourceNodeList.item(i);
						JsonMagPresentationResource jsonResource = new JsonMagPresentationResource();
						
						jsonResource.setNomenclature(resourceNode.getElementsByTagNameNS(
								"http://www.iccu.sbn.it/metaAG1.pdf", "nomenclature").item(0).getTextContent());
						
						jsonResource.setSequenceNumber(resourceNode.getElementsByTagNameNS(
								"http://www.iccu.sbn.it/metaAG1.pdf", "sequence_number").item(0).getTextContent());
						
						NodeList usageNodeList = resourceNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "usage");
						
						// assenza usage, default 3
						if(usageNodeList.getLength() == 0) {
							if(!resourceUsageMap.containsKey("3"))
								resourceUsageMap.put("3", new ArrayList<JsonMagPresentationResource>());
							
							resourceUsageMap.get("3").add(jsonResource);
						}
						
						// presenza usage, standard
						else {
							for(int j = 0; j < usageNodeList.getLength(); j++) {
								String usage = usageNodeList.item(j).getTextContent().trim();
								
								if(!"a".equals(usage) && !"b".equals(usage)) {
									if(!resourceUsageMap.containsKey(usage))
										resourceUsageMap.put(usage, new ArrayList<JsonMagPresentationResource>());
									
									resourceUsageMap.get(usage).add(jsonResource);
								}
							}
						}
					}
				}
				
				else {
					if("audio".equals(resourceType) || "video".equals(resourceType)) {
						for(int i = 0; i < resourceNodeList.getLength(); i++) {
							Element resourceNode = (Element) resourceNodeList.item(i);
							
							String nomenclature = resourceNode.getElementsByTagNameNS(
									"http://www.iccu.sbn.it/metaAG1.pdf", "nomenclature").item(0).getTextContent();
							
							NodeList proxiesNodeList = resourceNode.getElementsByTagNameNS(
									"http://www.iccu.sbn.it/metaAG1.pdf", "proxies");
							
							String sequenceNumber = resourceNode.getElementsByTagNameNS(
									"http://www.iccu.sbn.it/metaAG1.pdf", "sequence_number").item(0).getTextContent();
							
							
							for(int j = 0; j < proxiesNodeList.getLength(); j++) {
								Element proxyNode = (Element) proxiesNodeList.item(j);
								JsonMagPresentationResource jsonResource = new JsonMagPresentationResource();
								jsonResource.setNomenclature(nomenclature);
								jsonResource.setSequenceNumber(sequenceNumber);
								NodeList usageNodeList = proxyNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "usage");
								
								// assenza usage, default 3
								if(usageNodeList.getLength() == 0) {
									if(!resourceUsageMap.containsKey("3"))
										resourceUsageMap.put("3", new ArrayList<JsonMagPresentationResource>());
									
									resourceUsageMap.get("3").add(jsonResource);
								}
								
								// presenza usage, standard
								else {
									for(int k = 0; k < usageNodeList.getLength(); k++) {
										String usage = usageNodeList.item(k).getTextContent().trim();
										
										if(!"a".equals(usage) && !"b".equals(usage)) {
											if(!resourceUsageMap.containsKey(usage))
												resourceUsageMap.put(usage, new ArrayList<JsonMagPresentationResource>());
											
											resourceUsageMap.get(usage).add(jsonResource);
										}
									}
								}
							}
						}
					}
					
					else {
						for(int i = 0; i < resourceNodeList.getLength(); i++) {
							List<Element> imgNodeList = new ArrayList<Element>();
							Element resourceNode = (Element) resourceNodeList.item(i);
							imgNodeList.add(resourceNode);
							
							String nomenclature = resourceNode.getElementsByTagNameNS(
									"http://www.iccu.sbn.it/metaAG1.pdf", "nomenclature").item(0).getTextContent();
							
							String sequenceNumber = resourceNode.getElementsByTagNameNS(
									"http://www.iccu.sbn.it/metaAG1.pdf", "sequence_number").item(0).getTextContent();
							
							NodeList altimgNodeList = resourceNode.getElementsByTagNameNS(
									"http://www.iccu.sbn.it/metaAG1.pdf", "altimg");
							
							for(int j = 0; j < altimgNodeList.getLength(); j++)
								imgNodeList.add((Element) altimgNodeList.item(j));
							
							
							for(Element imgNode : imgNodeList) {
								JsonMagPresentationResource jsonResource = new JsonMagPresentationResource();
								jsonResource.setNomenclature(nomenclature);
								jsonResource.setSequenceNumber(sequenceNumber);
								
								List<Element> usageNodeList = UtilXML.searchInResource(imgNode, 
										"http://www.iccu.sbn.it/metaAG1.pdf", "usage");

								// assenza usage, default 3
								if(usageNodeList.isEmpty()) {
									if(!resourceUsageMap.containsKey("3"))
										resourceUsageMap.put("3", new ArrayList<JsonMagPresentationResource>());
									
									resourceUsageMap.get("3").add(jsonResource);
								}
								
								// presenza usage, standard
								else {
									for(Element usageNode : usageNodeList) {
										String usage = usageNode.getTextContent().trim();
										
										if(!"a".equals(usage) && !"b".equals(usage)) {
											if(!resourceUsageMap.containsKey(usage))
												resourceUsageMap.put(usage, new ArrayList<JsonMagPresentationResource>());
											
											resourceUsageMap.get(usage).add(jsonResource);
										}
									}
								}
							}
						}
					}
				}
				
				List<String> usageList = new ArrayList<String>(resourceUsageMap.keySet());
				Collections.sort(usageList);
				
				for(String usage : usageList) {
					JsonMagPresentationUsageResourceList jsonUsageList = new JsonMagPresentationUsageResourceList();
					jsonUsageList.setUsage(usage);
					jsonUsageList.setExport(mag.getUsageE().contains(usage));
					jsonUsageList.setResources(resourceUsageMap.get(usage));
					jsonResourceType.getUsages().add(jsonUsageList);
				}
				
				jsonDetail.getResources().add(jsonResourceType);
			}
		}
		
		NodeList disItemNodeList = doc.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "dis_item");
		
		for(int i = 0; i < disItemNodeList.getLength(); i++) {
			Node disItemNode = disItemNodeList.item(i);
			NodeList disItemChildren = disItemNode.getChildNodes();
			JsonMagPresentationDisItem disItemObj = new JsonMagPresentationDisItem();
			
			for(int j = 0; j < disItemChildren.getLength(); j++) {
				Node child = disItemChildren.item(j);
				
				if(child.getNodeType() == Document.ELEMENT_NODE) {
					Element itemChildElem = (Element) child;
					
					if("file".equals(itemChildElem.getLocalName()))
						disItemObj.setFile(itemChildElem.getAttributeNS("http://www.w3.org/TR/xlink", "href"));
					
					else if("preview".equals(itemChildElem.getLocalName()))
						disItemObj.setPreview(itemChildElem.getTextContent());
					
					else if("available".equals(itemChildElem.getLocalName()))
						disItemObj.setAvailable(itemChildElem.getTextContent());
				}
			}
			
			jsonDetail.getDis().getDisItems().add(disItemObj);
		}
		
	    return jsonDetail;
	}

	/**
	 * Metodo ricorsivo di mappatura delle sezioni stru
	 * 
	 * @param struElement tag stru XML
	 * @param stru oggetto stru per memorizzazione metadati
	 */
	public void mapStru(Element struElement, JsonMagPresentationStru stru) {
		NodeList struChildrenNodeList = struElement.getChildNodes();
		
		for(int i = 0; i < struChildrenNodeList.getLength(); i++) {
			Node struChild = struChildrenNodeList.item(i);
			
			if(struChild.getNodeType() == Document.ELEMENT_NODE) {
				Element childElement = (Element) struChild;
				
				if("sequence_number".equals(childElement.getLocalName()))
					stru.setSequenceNumber(childElement.getTextContent().trim());
				
				else if("nomenclature".equals(childElement.getLocalName()))
					stru.setNomenclature(childElement.getTextContent().trim());
				
				else if("identifier".equals(childElement.getLocalName()))
					stru.setIdentifier(childElement.getTextContent().trim());

				else if("resource".equals(childElement.getLocalName()))
					stru.setResourceType(childElement.getTextContent().trim());
				
				else if("file".equals(childElement.getLocalName()))
					stru.setFile(childElement.getAttributeNS("http://www.w3.org/TR/xlink", "href"));
				
				else if("start".equals(childElement.getLocalName())) {
					NamedNodeMap attrs = childElement.getAttributes();
					
					for(int j = 0; j < attrs.getLength(); j++) {
						Attr attr = (Attr) attrs.item(j);
						
						if("sequence_number".equals(attr.getLocalName()))
							stru.setStart(attr.getValue());
					}
				}
				
				else if("stop".equals(childElement.getLocalName())) {
					NamedNodeMap attrs = childElement.getAttributes();
					
					for(int j = 0; j < attrs.getLength(); j++) {
						Attr attr = (Attr) attrs.item(j);
						
						if("sequence_number".equals(attr.getLocalName()))
							stru.setStop(attr.getValue());
					}
				}
				
				else if("piece".equals(childElement.getLocalName())) {
					JsonMagPresentationPiece piece = new JsonMagPresentationPiece();
					NodeList pieceChildren = childElement.getChildNodes();
					
					for(int j = 0; j < pieceChildren.getLength(); j++) {
						Node childNode = pieceChildren.item(j);
						
						if(childNode.getNodeType() == Node.ELEMENT_NODE) {
							if("issue".equals(childNode.getLocalName()))
								piece.setIssue(childNode.getTextContent());
							
							else if("year".equals(childNode.getLocalName()))
								piece.setYear(childNode.getTextContent());
							
							else if("stpiece_per".equals(childNode.getLocalName()))
								piece.setStpiecePer(childNode.getTextContent());
							
							else if("part_number".equals(childNode.getLocalName()))
								piece.setPartNumber(childNode.getTextContent());
							
							else if("part_name".equals(childNode.getLocalName()))
								piece.setPartName(childNode.getTextContent());
							
							else if("stpiece_vol".equals(childNode.getLocalName()))
								piece.setStpieceVol(childNode.getTextContent());
						}
					}
					
					stru.setPiece(piece);
				}
				
				else if("stru".equals(childElement.getLocalName())) {
					JsonMagPresentationStru childObj = new JsonMagPresentationStru();
					childObj.setType(JsonMagPresentationStru.TYPE_STRU);
					this.mapStru(childElement, childObj);
					stru.getChildren().add(childObj);
				}
				
				else if("element".equals(childElement.getLocalName())) {
					JsonMagPresentationStru childObj = new JsonMagPresentationStru();
					childObj.setType(JsonMagPresentationStru.TYPE_ELEMENT);
					this.mapStru(childElement, childObj);
					stru.getChildren().add(childObj);
				}
			}
		}
	}
	
	/**
	 * Restituisce la risorsa digitale da visualizzare
	 * 
	 * VISUALIZZAZIONE OGGETTO SIGITALE SCHEDA DI DETTAGLIO (IMMAGINE ORIGINALE/STREAMING AUDIO-VIDEO/DOCUMENTI)
	 * 
	 * @param magID ID del MAG
	 * @param type tipo di risorsa (img, audio, video, ocr, doc)
	 * @param sequenceNumber numero di sequenza
	 * @param usage usage
	 * @return file per il download
	 * @throws FileNotFoundException
	 */
	public DataResourceDelivery getDigitalResource(String magID, String type, 
			String sequenceNumber, String usage) throws IOException {
		
		Mag mag = UtilSolr.selectDocumentById(magID);
		
		if(mag == null)
			throw new FileNotFoundException("MAG richiesto non trovato nei risultati di ricerca");
		
		
		// ricerca nel documento
		Document doc = UtilXML.convertStringToDocumentXML(mag.getMagInternal());
		Element resourceNode = UtilXML.getResourceNode(doc, type, sequenceNumber, usage);
		
		if(resourceNode == null) {
			throw new FileNotFoundException("Risorsa di tipo " + type + " con numero di sequenza"
					+ " " + sequenceNumber + " e usage " + usage + " non trovata");
		}
		
		// ricerca il path della risorsa
		List<Element> pathNode = UtilXML.searchInResource(resourceNode, "http://www.iccu.sbn.it/metaAG1.pdf", "file");
		
		if(pathNode.isEmpty())
			throw new FileNotFoundException("Path della risorsa digitale non disponibile");
		
		String path = pathNode.get(0).getAttributeNS("http://www.w3.org/TR/xlink", "href");

		URLConnection connection = new URL(makePath(ContentStatic.
				getProperties().getProperty("UrlServlet"), path)).openConnection();
		
		String disposition = connection.getHeaderField("Content-Disposition");
		if(disposition==null)
			logger.error("No Content-Disposition for "+ContentStatic.getProperties().getProperty("UrlServlet") + "/" + path);
		int idx = disposition.indexOf("=");
		String fileName = idx <= 0 ? "" : disposition.substring(idx + 1).replace("\"", "");
		
		// dati risorsa
		DataResourceDelivery resource = new DataResourceDelivery();
		resource.setContentType(connection.getContentType());
		resource.setLength(connection.getContentLengthLong());
		resource.setResourceType(type);
		resource.setLastModified(new Date(connection.getLastModified()));
		resource.setStream(connection.getInputStream());
		resource.setResourceName(fileName);
		return resource;
	}

	private String makePath(String urlServlet, String path) {
		if(!urlServlet.endsWith("/"))
			urlServlet = urlServlet+"/";
		if(path.startsWith("/"))
			return urlServlet+path.substring(1);
		return urlServlet+path;
	}

	/**
	 * Restituisce l'immagine di preview della risorsa digitale da visualizzare
	 * nella scheda di dettaglio
	 * 
	 * PREVIEW OGGETTI DIGITALI PER SCHEDA DI DETTAGLIO (GALLERY)
	 * 
	 * @param magID ID del MAG
	 * @param type tipo di risorsa (img, audi, video, ocr, doc)
	 * @param sequenceNumber numero di sequenza
	 * @param usage usage
	 * @param width larghezza massima immagine di preview
	 * @param height altezza massima immagine di preview
	 * @param context contesto servlet (per le immagini di rpeview di default)
	 * @return stream di download dell'immagine di preview nella grandezza specificata
	 * @throws FileNotFoundException
	 */
	public InputStream getImagePreviewDigitalResource(String magID, String type, 
			String sequenceNumber, String usage, int width, int height) 
					throws IOException {
		
		Mag mag = UtilSolr.selectDocumentById(magID);
		
		if(mag == null)
			throw new FileNotFoundException("MAG richiesto non trovato nei risultati di ricerca");
		
		// ricerca nel documento
		Document doc = UtilXML.convertStringToDocumentXML(mag.getMagInternal());
		Element resourceNode = UtilXML.getResourceNode(doc, type, sequenceNumber, usage);
		
		if(resourceNode == null) {
			throw new FileNotFoundException("Risorsa di tipo " + type + " con numero di sequenza"
					+ " " + sequenceNumber + " e usage " + usage + " non trovata");
		}
		
		// ricerca il path della risorsa
		List<Element> pathNode = UtilXML.searchInResource(resourceNode, "http://www.iccu.sbn.it/metaAG1.pdf", "file");
		
		if(pathNode.isEmpty())
			throw new FileNotFoundException("Path del file non disponibile");
		
		String path = pathNode.get(0).getAttributeNS("http://www.w3.org/TR/xlink", "href");
		String urlResource = ContentStatic.getProperties().getProperty("UrlServlet") + "/" + path;
		return thumbCreator.getThumb(urlResource, type, width, height,null);
	}
	
	/**
	 * Restituisce i metadati della risorsa digitale specificata dai parametri
	 * 
	 * METADATI OGGETTI DIGITALI SCHEDA DI DETTAGLIO (POPUP)
	 * 
	 * @param magID ID del MAG
	 * @param type tipo di risorsa (img, audio, video, ocr, doc)
	 * @param sequenceNumber numero di sequenza
	 * @param usage usage
	 * @return JsonMagPresentationResource oggetto metadati risorsa digitale
	 * @throws FileNotFoundException
	 */
	public JsonMagPresentationResource getResourceDetail(String magID, String type, 
			String sequenceNumber, String usage) throws FileNotFoundException {
		
		JsonMagPresentationResource jsonResource = new JsonMagPresentationResource();
		Mag mag = UtilSolr.selectDocumentById(magID);
		
		if(mag == null)
			throw new FileNotFoundException("MAG richiesto non trovato nei risultati di ricerca");
		
		// ricerca nel documento
		Document doc = UtilXML.convertStringToDocumentXML(mag.getMagInternal());
		Element parentResource = UtilXML.getResourceNode(doc, type, sequenceNumber, usage);
		Element parentOriginal = parentResource;
		
		if(parentResource == null) {
			throw new FileNotFoundException("Risorsa di tipo " + type + " con numero di sequenza"
					+ " " + sequenceNumber + " e usage " + usage + " non trovata");
		}
		
		// calcolo riferimento originale
		if("altimg".equals(parentResource.getLocalName()) || "proxies".equals(parentResource.getLocalName()))
			parentOriginal = (Element) parentResource.getParentNode();
		
		
		if(parentResource.hasAttribute("holdingsID"))
			jsonResource.setHoldingsID(parentResource.getAttribute("holdingsID"));
		
		else if(parentOriginal.hasAttribute("holdingsID"))
			jsonResource.setHoldingsID(parentOriginal.getAttribute("holdingsID"));
		
		// risorse di tipo ocr e doc
		if("ocr".equals(parentOriginal.getLocalName()) || "doc".equals(parentOriginal.getLocalName())) {
			NodeList children = parentOriginal.getChildNodes();
			
			for(int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				
				if(child.getNodeType() == Document.ELEMENT_NODE) {
					Element elem = (Element) child;
					
					// nodo semplice
					if(!"format".equals(elem.getLocalName())) {
						String tag = elem.getLocalName();
						
						if("usage".equals(tag))
							jsonResource.getUsages().add(elem.getTextContent());
						
						else if("sequence_number".equals(tag))
							jsonResource.setSequenceNumber(elem.getTextContent());
						
						else if("nomenclature".equals(tag))
							jsonResource.setNomenclature(elem.getTextContent());
						
						else if("file".equals(tag))
							jsonResource.setFile(elem.getAttributeNS("http://www.w3.org/TR/xlink", "href"));
						
						else if("source".equals(tag))
							jsonResource.setSource(elem.getAttributeNS("http://www.w3.org/TR/xlink", "href"));
						
						else if("md5".equals(tag))
							jsonResource.setMd5(elem.getTextContent());
						
						else if("filesize".equals(tag))
							jsonResource.setFileSize(elem.getTextContent());
						
						else if("note".equals(tag))
							jsonResource.setNote(elem.getTextContent());
						
						else if("datetimecreated".equals(tag))
							jsonResource.setDatetimeCreated(elem.getTextContent());
						
						else if("software_ocr".equals(tag))
							jsonResource.setSoftwareOcr(elem.getTextContent());
					}
					
					// nodo di tipo format
					else {
						NodeList formatChildren = elem.getChildNodes();
						
						for(int j = 0; j < formatChildren.getLength(); j++) {
							Node formatChild = formatChildren.item(j);
							
							if(formatChild.getNodeType() == Document.ELEMENT_NODE) {
								String formatTag = formatChild.getLocalName();
								
								if("name".equals(formatTag))
									jsonResource.setFormatName(formatChild.getTextContent());
								
								else if("mime".equals(formatTag))
									jsonResource.setFormatMime(formatChild.getTextContent());
								
								else if("compression".equals(formatTag))
									jsonResource.setFormatCompression(formatChild.getTextContent());
							}
						}
					}
				}
			}
		}
		
		else if("audio".equals(parentOriginal.getLocalName()) || "video".equals(parentOriginal.getLocalName())) {
			if(parentResource.hasAttribute("audiogroupID"))
				jsonResource.setGroupID(parentResource.getAttribute("audiogroupID"));
			
			else if(parentOriginal.hasAttribute("audiogroupID"))
				jsonResource.setGroupID(parentOriginal.getAttribute("audiogroupID"));
			
			if(parentResource.hasAttribute("videogroupID"))
				jsonResource.setGroupID(parentResource.getAttribute("videogroupID"));
			
			else if(parentOriginal.hasAttribute("videogroupID"))
				jsonResource.setGroupID(parentOriginal.getAttribute("videogroupID"));
			
			NodeList parentOriginalChildren = parentOriginal.getChildNodes();
			
			for(int i = 0; i < parentOriginalChildren.getLength(); i++) {
				Node originalChild = parentOriginalChildren.item(i);
				
				if(originalChild.getNodeType() == Document.ELEMENT_NODE) {
					String originalTag = originalChild.getLocalName();
					
					if("sequence_number".equals(originalTag))
						jsonResource.setSequenceNumber(originalChild.getTextContent());
					
					else if("nomenclature".equals(originalTag))
						jsonResource.setNomenclature(originalChild.getTextContent());
					
					else if("note".equals(originalTag))
						jsonResource.setNote(originalChild.getTextContent());
				}
			}
			
			NodeList parentResourceChildren = parentResource.getChildNodes();
						
			for(int i = 0; i < parentResourceChildren.getLength(); i++) {
				Node resourceChild = parentResourceChildren.item(i);
							
				if(resourceChild.getNodeType() == Document.ELEMENT_NODE && 
						!"transcription".equals(resourceChild.getLastChild()) && 
						!"digitisation".equals(resourceChild.getLastChild())) {
								
					Element proxiesElem = (Element) resourceChild;
					String tag = proxiesElem.getLocalName();
					
					if("usage".equals(tag))
						jsonResource.getUsages().add(proxiesElem.getTextContent());
					
					else if("file".equals(tag))
						jsonResource.setFile(proxiesElem.getAttributeNS("http://www.w3.org/TR/xlink", "href"));
					
					else if("md5".equals(tag))
						jsonResource.setMd5(proxiesElem.getTextContent());
					
					else if("filesize".equals(tag))
						jsonResource.setFileSize(proxiesElem.getTextContent());
					
					else if("datetimecreated".equals(tag))
						jsonResource.setDatetimeCreated(proxiesElem.getTextContent());
					
					else if("format".equals(tag)) {
						NodeList formatChildren = proxiesElem.getChildNodes();
						
						for(int j = 0; j < formatChildren.getLength(); j++) {
							Node formatChild = formatChildren.item(j);
							
							if(formatChild.getNodeType() == Document.ELEMENT_NODE) {
								String formatTag = formatChild.getLocalName();
								
								if("name".equals(formatTag))
									jsonResource.setFormatName(formatChild.getTextContent());
								
								else if("mime".equals(formatTag))
									jsonResource.setFormatMime(formatChild.getTextContent());
								
								else if("compression".equals(formatTag))
									jsonResource.setFormatCompression(formatChild.getTextContent());
								
								else if("channel_configuration".equals(formatTag))
									jsonResource.setChannelConfiguration(formatChild.getTextContent());
								
								else if("videoformat".equals(formatTag))
									jsonResource.setFormatVideo(formatChild.getTextContent());
								
								else if("encode".equals(formatTag))
									jsonResource.setFormatEncode(formatChild.getTextContent());
								
								else if("streamtype".equals(formatTag))
									jsonResource.setFormatStreamType(formatChild.getTextContent());
								
								else if("codec".equals(formatTag))
									jsonResource.setFormatCodec(formatChild.getTextContent());
							}
						}
					}
					
					else if(proxiesElem.getLocalName().endsWith("_dimensions")) {
						NodeList mediaChildren = proxiesElem.getChildNodes();
						
						for(int j = 0; j < mediaChildren.getLength(); j++) {
							Node mediaChild = mediaChildren.item(j);
							
							if(mediaChild.getNodeType() == Document.ELEMENT_NODE)
								jsonResource.setDuration(mediaChild.getTextContent());
						}
					}
					
					else if(proxiesElem.getLocalName().endsWith("_metrics")) {
						NodeList mediaChildren = proxiesElem.getChildNodes();
						
						for(int j = 0; j < mediaChildren.getLength(); j++) {
							Node mediaChild = mediaChildren.item(j);
							String mediaTag = mediaChild.getLocalName();
							
							if(mediaChild.getNodeType() == Document.ELEMENT_NODE) {
								if("videosize".equals(mediaTag))
									jsonResource.setVideoSize(mediaChild.getTextContent());
								
								else if("aspectratio".equals(mediaTag))
									jsonResource.setAspectRatio(mediaChild.getTextContent());
								
								else if("framerate".equals(mediaTag))
									jsonResource.setFrameRate(mediaChild.getTextContent());
								
								else if("samplingfrequency".equals(mediaTag))
									jsonResource.setSamplingFrequency(mediaChild.getTextContent());
								
								else if("bitpersample".equals(mediaTag))
									jsonResource.setBitPerSample(mediaChild.getTextContent());
								
								else if("bitrate".equals(mediaTag))
									jsonResource.setBitRate(mediaChild.getTextContent());
							}
						}
					}
				}
			}
		}
		
		// immagini
		else {
			if(parentResource.hasAttribute("imggroupID"))
				jsonResource.setGroupID(parentResource.getAttribute("imggroupID"));
			
			else if(parentOriginal.hasAttribute("imggroupID"))
				jsonResource.setGroupID(parentOriginal.getAttribute("imggroupID"));
			
			NodeList originalChildren = parentOriginal.getChildNodes();
			
			for(int i = 0; i < originalChildren.getLength(); i++) {
				Node originalChild = originalChildren.item(i);
				
				if(originalChild.getNodeType() == Document.ELEMENT_NODE) {
					String originalTag = originalChild.getLocalName();
					
					if("sequence_number".equals(originalTag))
						jsonResource.setSequenceNumber(originalChild.getTextContent());
					
					else if("nomenclature".equals(originalTag))
						jsonResource.setNomenclature(originalChild.getTextContent());
					
					else if("note".equals(originalTag))
						jsonResource.setNote(originalChild.getTextContent());
					
					else if("side".equals(originalTag))
						jsonResource.setImageSide(originalChild.getTextContent());
					
					else if("scale".equals(originalTag))
						jsonResource.setImageScale(originalChild.getTextContent());
					
					else if("target".equals(originalTag)) {
						JsonMagPresentationImgTarget target = new JsonMagPresentationImgTarget();
						NodeList targetChildren = originalChild.getChildNodes();
						
						for(int j = 0; j < targetChildren.getLength(); j++) {
							Node targetChild = targetChildren.item(j);
							
							if(targetChild.getNodeType() == Document.ELEMENT_NODE) {
								String targetTag = targetChild.getLocalName();
								
								if("targetType".equals(targetTag))
									target.setTargetType(targetChild.getTextContent());
									
								else if("targetID".equals(targetTag))
									target.setTargetID(targetChild.getTextContent());
									
								else if("imageData".equals(targetTag))
									target.setImageData(targetChild.getTextContent());
										
								else if("performanceData".equals(targetTag))
									target.setPerformanceData(targetChild.getTextContent());
											
								else if("profiles".equals(targetTag))
									target.setProfiles(targetChild.getTextContent());
							}
						}
						
						jsonResource.getTargets().add(target);
					}
				}
			}
			
			NodeList imgChildren = parentResource.getChildNodes();
			
			for(int i = 0; i < imgChildren.getLength(); i++) {
				Node imgChild = imgChildren.item(i);
				
				if(imgChild.getNodeType() == Document.ELEMENT_NODE) {
					Element imgElem = (Element) imgChild;
					String imgTag = imgElem.getLocalName();
					
					if("usage".equals(imgTag))
						jsonResource.getUsages().add(imgElem.getTextContent());
					
					else if("file".equals(imgTag))
						jsonResource.setFile(imgElem.getAttributeNS("http://www.w3.org/TR/xlink", "href"));
					
					else if("md5".equals(imgTag))
						jsonResource.setMd5(imgElem.getTextContent());
					
					else if("filesize".equals(imgTag))
						jsonResource.setFileSize(imgElem.getTextContent());
					
					else if("datetimecreated".equals(imgTag))
						jsonResource.setDatetimeCreated(imgElem.getTextContent());
					
					else if(imgElem.getLocalName().endsWith("_dimensions")) {
						NodeList mediaChildren = imgElem.getChildNodes();
						
						for(int j = 0; j < mediaChildren.getLength(); j++) {
							Node mediaChild = mediaChildren.item(j);
							String mediaTag = mediaChild.getLocalName();
							
							if(mediaChild.getNodeType() == Document.ELEMENT_NODE) {
								if("imagewidth".equals(mediaTag))
									jsonResource.setImageWidth(mediaChild.getTextContent());
								
								else if("imagelength".equals(mediaTag))
									jsonResource.setImageHeight(mediaChild.getTextContent());
								
								else if("source_xdimension".equals(mediaTag))
									jsonResource.setImageX(mediaChild.getTextContent());
								
								else if("source_ydimension".equals(mediaTag))
									jsonResource.setImageY(mediaChild.getTextContent());
							}
						}
					}
					
					else if("image_metrics".equals(imgTag)) {
						NodeList metricsChildren = imgElem.getChildNodes();
						
						for(int k = 0; k < metricsChildren.getLength(); k++) {
							Node metricsChild = metricsChildren.item(k);
							
							if(metricsChild.getNodeType() == Document.ELEMENT_NODE) {
								if("samplingfrequencyunit".equals(metricsChild.getLocalName()))
									jsonResource.setSamplingFrequencyUnit(metricsChild.getTextContent());
								
								else if("samplingfrequencyplane".equals(metricsChild.getLocalName()))
									jsonResource.setSamplingFrequencyPlane(metricsChild.getTextContent());
								
								else if("xsamplingfrequency".equals(metricsChild.getLocalName()))
									jsonResource.setXSamplingFrequency(metricsChild.getTextContent());
								
								else if("ysamplingfrequency".equals(metricsChild.getLocalName()))
									jsonResource.setYSamplingFrequency(metricsChild.getTextContent());
								
								else if("photometricinterpretation".equals(metricsChild.getLocalName()))
									jsonResource.setPhotometricInterpretation(metricsChild.getTextContent());
								
								else if("bitpersample".equals(metricsChild.getLocalName()))
									jsonResource.setBitPerSample(metricsChild.getTextContent());
							}
						}
					}
					
					else if("format".equals(imgTag)) {
						NodeList formatChildren = imgElem.getChildNodes();
						
						for(int k = 0; k < formatChildren.getLength(); k++) {
							Node formatChild = formatChildren.item(k);
							
							if(formatChild.getNodeType() == Document.ELEMENT_NODE) {
								if("name".equals(formatChild.getLocalName()))
									jsonResource.setFormatName(formatChild.getTextContent());
								
								else if("mime".equals(formatChild.getLocalName()))
									jsonResource.setFormatMime(formatChild.getTextContent());
								
								else if("compression".equals(formatChild.getLocalName()))
									jsonResource.setFormatCompression(formatChild.getTextContent());
							}
						}
					}
					
					else if("ppi".equals(imgTag))
						jsonResource.setPpi(imgElem.getTextContent());
					
					else if("dpi".equals(imgTag))
						jsonResource.setDpi(imgElem.getTextContent());
					
					else if("scanning".equals(imgTag)) {
						NodeList scanningChildren = imgElem.getChildNodes();
						
						for(int k = 0; k < scanningChildren.getLength(); k++) {
							Node scanningChild = scanningChildren.item(k);
							
							if(scanningChild.getNodeType() == Document.ELEMENT_NODE) {
								if("sourcetype".equals(scanningChild.getLocalName()))
									jsonResource.setSourceType(scanningChild.getTextContent());
								
								else if("scanningagency".equals(scanningChild.getLocalName()))
									jsonResource.setScanningAgency(scanningChild.getTextContent());
								
								else if("devicesource".equals(scanningChild.getLocalName()))
									jsonResource.setDeviceSource(scanningChild.getTextContent());

								else if("scanningsystem".equals(scanningChild.getLocalName())) {
									NodeList systemChildren = scanningChild.getChildNodes();
									
									for(int l = 0; l < systemChildren.getLength(); l++) {
										Node systemChild = systemChildren.item(l);
										
										if(systemChild.getNodeType() == Document.ELEMENT_NODE) {
											if("scanner_manufacturer".equals(systemChild.getLocalName()))
												jsonResource.setScannerManufacturer(systemChild.getTextContent());
											
											else if("scanner_model".equals(systemChild.getLocalName()))
												jsonResource.setScannerModel(systemChild.getTextContent());
											
											else if("capture_software".equals(systemChild.getLocalName()))
												jsonResource.setCaptureSoftware(systemChild.getTextContent());
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return jsonResource;
	}

	/**
	 * Restituisce i metadati del MAG da visualizzare nella scheda di dettaglio
	 * 
	 * SCHEDA DI DETTAGLIO VIEWER CHERUBINI
	 * 
	 * @param magID ID del MAG
	 * @return JsonMagPresentation oggetto riepilogo metadati MAG
	 * @throws FileNotFoundException
	 */
	public JsonMagDetail getServiceMagDetail(String magID) throws FileNotFoundException {
		Mag mag = UtilSolr.selectDocumentById(magID);
		
		if(mag == null)
			throw new FileNotFoundException("MAG richiesto non trovato nei risultati di ricerca");
		
		Document doc = UtilXML.convertStringToDocumentXML(mag.getMagInternal());
		// SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		JsonMagDetail jsonDetail = new JsonMagDetail();
		/*
		jsonDetail.getImp().setProject(mag.getProject());
		jsonDetail.getImp().setPublicFlag(mag.getPublishFlag() + "");
		jsonDetail.getImp().setLastModified(formatter.format(mag.getTimestamp()));
		*/
		Element genNode = (Element) doc.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "gen").item(0);
		
		if(genNode.hasAttribute("creation"))
			jsonDetail.getMetadata().add(this.createMetadataJsonSingleValueField("creation", "Data creazione", genNode.getAttribute("creation")));
		
		if(genNode.hasAttribute("last_update"))
			jsonDetail.getMetadata().add(this.createMetadataJsonSingleValueField("last_update", "Data ultima modifica", genNode.getAttribute("last_update")));
		
		NodeList genChildren = genNode.getChildNodes();
		
		for(int i = 0; i < genChildren.getLength(); i++) {
			Node node = genChildren.item(i);
			
			if(node.getNodeType() == Document.ELEMENT_NODE) {
				Element elem = (Element) node;
				
				if("collection".equals(elem.getLocalName()))
					jsonDetail.getMetadata().add(this.createMetadataJsonSingleValueField("collection", "Collezione", elem.getTextContent()));
				
				else if("agency".equals(elem.getLocalName()))
					jsonDetail.getMetadata().add(this.createMetadataJsonSingleValueField("agency", "Agenzia", elem.getTextContent()));
				
				else if("stprog".equals(elem.getLocalName()))
					jsonDetail.getMetadata().add(this.createMetadataJsonSingleValueField("stprog", "Progetto di digitalizzazione", elem.getTextContent()));
				
				else if("access_rights".equals(elem.getLocalName())) {
					String accessRights = "";
					
					if("1".equals(elem.getTextContent()))
						accessRights = "Si";
					
					else if("0".equals(elem.getTextContent()))
						accessRights = "No";
					
					jsonDetail.getMetadata().add(this.createMetadataJsonSingleValueField("access_rights", "Condizioni di accesso", accessRights));
				}
				
				else if("completeness".equals(elem.getLocalName())) {
					String completeness = "";
					
					if("1".equals(elem.getTextContent()))
						completeness = "No";
					
					else if("0".equals(elem.getTextContent()))
						completeness = "Si";
					
					jsonDetail.getMetadata().add(this.createMetadataJsonSingleValueField("completeness", "Completezza", completeness));
				}
				/*
				else if("img_group".equals(elem.getLocalName())) {
					JsonMagDetailImgGroup imggroup = new JsonMagDetailImgGroup();
					imggroup.setId(elem.getAttribute("ID"));
					NodeList groupChildren = elem.getChildNodes();
					
					for(int j = 0; j < groupChildren.getLength(); j++) {
						Node childNode = groupChildren.item(j);
						
						if(childNode.getNodeType() == Document.ELEMENT_NODE) {
							if("image_metrics".equals(childNode.getLocalName())) {
								NodeList metricsChildren = childNode.getChildNodes();
								
								for(int k = 0; k < metricsChildren.getLength(); k++) {
									Node metricsChild = metricsChildren.item(k);
									
									if(metricsChild.getNodeType() == Document.ELEMENT_NODE) {
										if("samplingfrequencyunit".equals(metricsChild.getLocalName()))
											imggroup.setSamplingFrequencyUnit(metricsChild.getTextContent());
										
										else if("samplingfrequencyplane".equals(metricsChild.getLocalName()))
											imggroup.setSamplingFrequencyPlane(metricsChild.getTextContent());
										
										else if("xsamplingfrequency".equals(metricsChild.getLocalName()))
											imggroup.setXSamplingFrequency(metricsChild.getTextContent());
										
										else if("ysamplingfrequency".equals(metricsChild.getLocalName()))
											imggroup.setYSamplingFrequency(metricsChild.getTextContent());
										
										else if("photometricinterpretation".equals(metricsChild.getLocalName()))
											imggroup.setPhotometricInterpretation(metricsChild.getTextContent());
										
										else if("bitpersample".equals(metricsChild.getLocalName()))
											imggroup.setBitPerSample(metricsChild.getTextContent());
									}
								}
							}
							
							else if("format".equals(childNode.getLocalName())) {
								NodeList formatChildren = childNode.getChildNodes();
								
								for(int k = 0; k < formatChildren.getLength(); k++) {
									Node formatChild = formatChildren.item(k);
									
									if(formatChild.getNodeType() == Document.ELEMENT_NODE) {
										if("name".equals(formatChild.getLocalName()))
											imggroup.setFormatName(formatChild.getTextContent());
										
										else if("mime".equals(formatChild.getLocalName()))
											imggroup.setFormatMime(formatChild.getTextContent());
										
										else if("compression".equals(formatChild.getLocalName()))
											imggroup.setFormatCompression(formatChild.getTextContent());
									}
								}
							}
							
							else if("ppi".equals(childNode.getLocalName()))
								imggroup.setPpi(childNode.getTextContent());
							
							else if("dpi".equals(childNode.getLocalName()))
								imggroup.setDpi(childNode.getTextContent());
							
							else if("scanning".equals(childNode.getLocalName())) {
								NodeList scanningChildren = childNode.getChildNodes();
								
								for(int k = 0; k < scanningChildren.getLength(); k++) {
									Node scanningChild = scanningChildren.item(k);
									
									if(scanningChild.getNodeType() == Document.ELEMENT_NODE) {
										if("sourcetype".equals(scanningChild.getLocalName()))
											imggroup.setSourceType(scanningChild.getTextContent());
										
										else if("scanningagency".equals(scanningChild.getLocalName()))
											imggroup.setScanningAgency(scanningChild.getTextContent());
										
										else if("devicesource".equals(scanningChild.getLocalName()))
											imggroup.setDeviceSource(scanningChild.getTextContent());

										else if("scanningsystem".equals(scanningChild.getLocalName())) {
											NodeList systemChildren = scanningChild.getChildNodes();
											
											for(int l = 0; l < systemChildren.getLength(); l++) {
												Node systemChild = systemChildren.item(l);
												
												if(systemChild.getNodeType() == Document.ELEMENT_NODE) {
													if("scanner_manufacturer".equals(systemChild.getLocalName()))
														imggroup.setScannerManufacturer(systemChild.getTextContent());
													
													else if("scanner_model".equals(systemChild.getLocalName()))
														imggroup.setScannerModel(systemChild.getTextContent());
													
													else if("capture_software".equals(systemChild.getLocalName()))
														imggroup.setCaptureSoftware(systemChild.getTextContent());
												}
											}
										}
									}
								}
							}
						}
					}
					
					jsonDetail.getGen().getImgGroups().add(imggroup);
				}
				
				else if("audio_group".equals(elem.getLocalName())) {
					JsonMagDetailAudioGroup audiogroup = new JsonMagDetailAudioGroup();
					audiogroup.setId(elem.getAttribute("ID"));
					NodeList groupChildren = elem.getChildNodes();
					
					for(int j = 0; j < groupChildren.getLength(); j++) {
						Node childNode = groupChildren.item(j);
						
						if(childNode.getNodeType() == Document.ELEMENT_NODE) {
							NodeList dataNodes = childNode.getChildNodes();
							
							for(int k = 0; k < dataNodes.getLength(); k++) {
								Node dataNode = dataNodes.item(k);
								
								if(dataNode.getNodeType() == Document.ELEMENT_NODE) {
									if("samplingfrequency".equals(dataNode.getLocalName()))
										audiogroup.setSamplingFrequency(dataNode.getTextContent());
									
									else if("bitpersample".equals(dataNode.getLocalName()))
										audiogroup.setBitPerSample(dataNode.getTextContent());
									
									else if("bitrate".equals(dataNode.getLocalName()))
										audiogroup.setBitRate(dataNode.getTextContent());
									
									else if("name".equals(dataNode.getLocalName()))
										audiogroup.setFormatName(dataNode.getTextContent());
									
									else if("mime".equals(dataNode.getLocalName()))
										audiogroup.setFormatMime(dataNode.getTextContent());
									
									else if("compression".equals(dataNode.getLocalName()))
										audiogroup.setFormatCompression(dataNode.getTextContent());
									
									else if("channel_configuration".equals(dataNode.getLocalName()))
										audiogroup.setChannelConfiguration(dataNode.getTextContent());
								}
							}
						}
					}
					
					jsonDetail.getGen().getAudioGroups().add(audiogroup);
				}
				
				else if("video_group".equals(elem.getLocalName())) {
					JsonMagDetailVideoGroup videogroup = new JsonMagDetailVideoGroup();
					videogroup.setId(elem.getAttribute("ID"));
					NodeList groupChildren = elem.getChildNodes();
					
					for(int j = 0; j < groupChildren.getLength(); j++) {
						Node childNode = groupChildren.item(j);
						
						if(childNode.getNodeType() == Document.ELEMENT_NODE) {
							NodeList dataNodes = childNode.getChildNodes();
							
							for(int k = 0; k < dataNodes.getLength(); k++) {
								Node dataNode = dataNodes.item(k);
								
								if(dataNode.getNodeType() == Document.ELEMENT_NODE) {
									if("videosize".equals(dataNode.getLocalName()))
										videogroup.setVideoSize(dataNode.getTextContent());
									
									else if("aspectratio".equals(dataNode.getLocalName()))
										videogroup.setAspectRatio(dataNode.getTextContent());
									
									else if("framerate".equals(dataNode.getLocalName()))
										videogroup.setFrameRate(dataNode.getTextContent());
									
									else if("name".equals(dataNode.getLocalName()))
										videogroup.setFormatName(dataNode.getTextContent());
									
									else if("mime".equals(dataNode.getLocalName()))
										videogroup.setFormatMime(dataNode.getTextContent());
									
									else if("videoformat".equals(dataNode.getLocalName()))
										videogroup.setFormatVideo(dataNode.getTextContent());
									
									else if("encode".equals(dataNode.getLocalName()))
										videogroup.setFormatEncode(dataNode.getTextContent());
									
									else if("streamtype".equals(dataNode.getLocalName()))
										videogroup.setFormatStreamType(dataNode.getTextContent());
									
									else if("codec".equals(dataNode.getLocalName()))
										videogroup.setFormatCodec(dataNode.getTextContent());
								}
							}
						}
					}
					
					jsonDetail.getGen().getVideoGroups().add(videogroup);
				}
				*/
			}
		}
		
		
		
		Element bibNode = (Element) doc.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "bib").item(0);
		NodeList bibChildren = bibNode.getChildNodes();
		
		Map<String, String> levelLabels = new HashMap<String, String>();
		levelLabels.put("m", "monografia");
		levelLabels.put("a", "spoglio");
		levelLabels.put("s", "seriale");
		levelLabels.put("c", "raccolta prodotta dall'istituzione");
		levelLabels.put("f", "unità archivistica");
		levelLabels.put("d", "unità documentaria");
		
		String level = bibNode.getAttribute("level");
		
		jsonDetail.getMetadata().add(this.createMetadataJsonSingleValueField("level", "Livello", 
				levelLabels.containsKey(level) ? levelLabels.get(level) : ""));
		
		Map<String, JsonMagDetailField> fieldMap = new HashMap<String, JsonMagDetailField>();
		
		for(int i = 0; i < bibChildren.getLength(); i++) {
			Node node = bibChildren.item(i);
			
			if(node.getNodeType() == Document.ELEMENT_NODE) {
				Element elem = (Element) node;
				
				if("identifier".equals(elem.getLocalName())) {
					if(!fieldMap.containsKey("identifier")) {
						JsonMagDetailField field = new JsonMagDetailField();
						field.setName("identifier");
						field.setLabel("Identificatori");
						fieldMap.put("identifier", field);
					}
					
					fieldMap.get("identifier").getValues().add(elem.getTextContent());
				}
				
				else if("title".equals(elem.getLocalName())) {
					if(!fieldMap.containsKey("title")) {
						JsonMagDetailField field = new JsonMagDetailField();
						field.setName("title");
						field.setLabel("Titoli");
						fieldMap.put("title", field);
					}
					
					fieldMap.get("title").getValues().add(elem.getTextContent());
				}
				
				else if("creator".equals(elem.getLocalName())) {
					if(!fieldMap.containsKey("creator")) {
						JsonMagDetailField field = new JsonMagDetailField();
						field.setName("creator");
						field.setLabel("Autori");
						fieldMap.put("creator", field);
					}
					
					fieldMap.get("creator").getValues().add(elem.getTextContent());
				}
				
				else if("publisher".equals(elem.getLocalName())) {
					if(!fieldMap.containsKey("publisher")) {
						JsonMagDetailField field = new JsonMagDetailField();
						field.setName("publisher");
						field.setLabel("Editori");
						fieldMap.put("publisher", field);
					}
					
					fieldMap.get("publisher").getValues().add(elem.getTextContent());
				}
				
				else if("subject".equals(elem.getLocalName())) {
					if(!fieldMap.containsKey("subject")) {
						JsonMagDetailField field = new JsonMagDetailField();
						field.setName("subject");
						field.setLabel("Soggetti");
						fieldMap.put("subject", field);
					}
					
					fieldMap.get("subject").getValues().add(elem.getTextContent());
				}
				
				else if("description".equals(elem.getLocalName())) {
					if(!fieldMap.containsKey("description")) {
						JsonMagDetailField field = new JsonMagDetailField();
						field.setName("description");
						field.setLabel("Descrizioni");
						fieldMap.put("description", field);
					}
					
					fieldMap.get("description").getValues().add(elem.getTextContent());
				}
				
				else if("contributor".equals(elem.getLocalName())) {
					if(!fieldMap.containsKey("contributor")) {
						JsonMagDetailField field = new JsonMagDetailField();
						field.setName("contributor");
						field.setLabel("Contributori");
						fieldMap.put("contributor", field);
					}
					
					fieldMap.get("contributor").getValues().add(elem.getTextContent());
				}
				
				else if("date".equals(elem.getLocalName())) {
					if(!fieldMap.containsKey("date")) {
						JsonMagDetailField field = new JsonMagDetailField();
						field.setName("date");
						field.setLabel("Date");
						fieldMap.put("date", field);
					}
					
					fieldMap.get("date").getValues().add(elem.getTextContent());
				}
				
				else if("type".equals(elem.getLocalName())) {
					if(!fieldMap.containsKey("type")) {
						JsonMagDetailField field = new JsonMagDetailField();
						field.setName("type");
						field.setLabel("Tipi");
						fieldMap.put("type", field);
					}
					
					fieldMap.get("type").getValues().add(elem.getTextContent());
				}
				
				else if("format".equals(elem.getLocalName())) {
					if(!fieldMap.containsKey("format")) {
						JsonMagDetailField field = new JsonMagDetailField();
						field.setName("format");
						field.setLabel("Sottotipi");
						fieldMap.put("format", field);
					}
					
					fieldMap.get("format").getValues().add(elem.getTextContent());
				}
				
				else if("source".equals(elem.getLocalName())) {
					if(!fieldMap.containsKey("source")) {
						JsonMagDetailField field = new JsonMagDetailField();
						field.setName("source");
						field.setLabel("Fonti");
						fieldMap.put("source", field);
					}
					
					fieldMap.get("source").getValues().add(elem.getTextContent());
				}
				
				else if("language".equals(elem.getLocalName())) {
					if(!fieldMap.containsKey("language")) {
						JsonMagDetailField field = new JsonMagDetailField();
						field.setName("language");
						field.setLabel("Lingue");
						fieldMap.put("language", field);
					}
					
					fieldMap.get("language").getValues().add(elem.getTextContent());
				}
				
				else if("relation".equals(elem.getLocalName())) {
					if(!fieldMap.containsKey("relation")) {
						JsonMagDetailField field = new JsonMagDetailField();
						field.setName("relation");
						field.setLabel("Relazioni");
						fieldMap.put("relation", field);
					}
					
					fieldMap.get("relation").getValues().add(elem.getTextContent());
				}
				
				else if("coverage".equals(elem.getLocalName())) {
					if(!fieldMap.containsKey("coverage")) {
						JsonMagDetailField field = new JsonMagDetailField();
						field.setName("coverage");
						field.setLabel("Estensioni");
						fieldMap.put("coverage", field);
					}
					
					fieldMap.get("coverage").getValues().add(elem.getTextContent());
				}
				
				else if("rights".equals(elem.getLocalName())) {
					if(!fieldMap.containsKey("rights")) {
						JsonMagDetailField field = new JsonMagDetailField();
						field.setName("rights");
						field.setLabel("Diritti");
						fieldMap.put("rights", field);
					}
					
					fieldMap.get("rights").getValues().add(elem.getTextContent());
				}
				
				else if("local_bib".equals(elem.getLocalName())) {
					NodeList localBibChildren = elem.getChildNodes();
					
					for(int j = 0; j < localBibChildren.getLength(); j++) {
						Node localNode = localBibChildren.item(j);
						
						if(localNode.getNodeType() == Node.ELEMENT_NODE) {
							if("geo_coord".equals(localNode.getLocalName())) {
								if(!fieldMap.containsKey("geo_coord")) {
									JsonMagDetailField field = new JsonMagDetailField();
									field.setName("geo_coord");
									field.setLabel("Coordinate");
									fieldMap.put("geo_coord", field);
								}
								
								fieldMap.get("geo_coord").getValues().add(elem.getTextContent());
							}
							
							else if("not_date".equals(localNode.getLocalName())) {
								if(!fieldMap.containsKey("not_date")) {
									JsonMagDetailField field = new JsonMagDetailField();
									field.setName("not_date");
									field.setLabel("Date di notifica");
									fieldMap.put("not_date", field);
								}
								
								fieldMap.get("not_date").getValues().add(elem.getTextContent());
							}
						}
					}
				}
				
				else if("holdings".equals(elem.getLocalName())) {
					if(!fieldMap.containsKey("holdings")) {
						JsonMagDetailField field = new JsonMagDetailField();
						field.setName("holdings");
						field.setLabel("Localizzazioni");
						fieldMap.put("holdings", field);
					}
					
					JsonMagDetailField holdingsField = new JsonMagDetailField();
					holdingsField.setName("holdings");
					holdingsField.setLabel("Localizzazione");
					
					if(elem.hasAttribute("ID"))
						holdingsField.getValues().add(this.createMetadataJsonSingleValueField("ID", "ID", elem.getAttribute("ID")));
					
					JsonMagDetailField shelfmarkField = new JsonMagDetailField();
					shelfmarkField.setName("shelfmark");
					shelfmarkField.setLabel("Collocazioni");
					
					NodeList holdingChildren = elem.getChildNodes();
					
					for(int j = 0; j < holdingChildren.getLength(); j++) {
						Node childNode = holdingChildren.item(j);
						
						if(childNode.getNodeType() == Node.ELEMENT_NODE) {
							if("library".equals(childNode.getLocalName()))
								holdingsField.getValues().add(this.createMetadataJsonSingleValueField("library", "Istituzione", childNode.getTextContent()));
							
							else if("inventory_number".equals(childNode.getLocalName()))
								holdingsField.getValues().add(this.createMetadataJsonSingleValueField("inventory_number", "Numero d'inventario", childNode.getTextContent()));
							
							else if("shelfmark".equals(childNode.getLocalName()))
								shelfmarkField.getValues().add(childNode.getTextContent());
						}
					}
					
					if(!shelfmarkField.getValues().isEmpty())
						holdingsField.getValues().add(shelfmarkField);
					
					fieldMap.get("holdings").getValues().add(holdingsField);
				}
				
				else if("piece".equals(elem.getLocalName())) {
					JsonMagDetailField pieceField = new JsonMagDetailField();
					pieceField.setName("piece");
					pieceField.setLabel("s".equals(bibNode.getAttribute("level")) ? "Seriale" : "Unità componente");
					NodeList pieceChildren = elem.getChildNodes();
					
					for(int j = 0; j < pieceChildren.getLength(); j++) {
						Node childNode = pieceChildren.item(j);
						
						if(childNode.getNodeType() == Node.ELEMENT_NODE) {
							if("issue".equals(childNode.getLocalName())) {
								pieceField.getValues().add(this.createMetadataJsonSingleValueField("issue", 
										"Estremi fasc.", childNode.getTextContent()));
							}
							
							else if("year".equals(childNode.getLocalName())) {
								pieceField.getValues().add(this.createMetadataJsonSingleValueField("year", 
										"Anno", childNode.getTextContent()));
							}
							
							else if("stpiece_per".equals(childNode.getLocalName())) {
								pieceField.getValues().add(this.createMetadataJsonSingleValueField("stpiece_per", 
										"Riferimento fasc.", childNode.getTextContent()));
							}
							
							else if("part_number".equals(childNode.getLocalName())) {
								pieceField.getValues().add(this.createMetadataJsonSingleValueField("part_number", 
										"Numero parte", childNode.getTextContent()));
							}
							
							else if("part_name".equals(childNode.getLocalName())) {
								pieceField.getValues().add(this.createMetadataJsonSingleValueField("part_name", 
										"Titolo parte", childNode.getTextContent()));
							}
							
							else if("stpiece_vol".equals(childNode.getLocalName())) {
								pieceField.getValues().add(this.createMetadataJsonSingleValueField("issue", 
										"Riferimento parte", childNode.getTextContent()));
							}
						}
					}
					
					jsonDetail.getMetadata().add(pieceField);
				}
			}
		}
		
		jsonDetail.getMetadata().addAll(fieldMap.values());
		Collections.sort(jsonDetail.getMetadata(), new Comparator<JsonMagDetailField>() {
			
			// field sort
			private List<String> order = Arrays.asList("identifier", "title", "piece", "creator", 
					"contributor", "collection", "type", "subject", "date", 
					"description", "format", "publisher", "level", "language", 
					"source", "relation", "coverage", "rights", "holdings", 
					"geo_coord", "not_date", "agency", "stprog", "access_rights", 
					"completeness", "creation", "last_update", "img_group", 
					"audio_group", "video_group");
			

			@Override
			public int compare(JsonMagDetailField f1, JsonMagDetailField f2) {
				return Integer.compare(order.indexOf(f1.getName()), order.indexOf(f2.getName()));
			}
		});
		
		/*
		NodeList struNodeList = doc.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "stru");
		List<JsonMagDetailStru> struArray = new ArrayList<JsonMagDetailStru>();
		
		for(int i = 0; i < struNodeList.getLength(); i++) {
			Element struNode = (Element) struNodeList.item(i);
			
			if(!"stru".equals(struNode.getParentNode().getLocalName())) {
				JsonMagDetailStru stru = new JsonMagDetailStru();
				mapStru(struNode, stru);
				struArray.add(stru);
			}
		}
		*/
		
		for(String resourceType : RESOURCE_TYPES) {
			NodeList resourceNodeList = doc.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", resourceType);
			
			if(resourceNodeList.getLength() > 0) {
				if("ocr".equals(resourceType) || "doc".equals(resourceType)) {
					for(int i = 0; i < resourceNodeList.getLength(); i++) {
						Element resourceNode = (Element) resourceNodeList.item(i);
						JsonMagDetailResource jsonResource = new JsonMagDetailResource();
						
						jsonResource.setNomenclature(resourceNode.getElementsByTagNameNS(
								"http://www.iccu.sbn.it/metaAG1.pdf", "nomenclature").item(0).getTextContent());
						
						jsonResource.setSequenceNumber(resourceNode.getElementsByTagNameNS(
								"http://www.iccu.sbn.it/metaAG1.pdf", "sequence_number").item(0).getTextContent());
						

						NodeList fileNodeList = resourceNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "file");
						
						if(fileNodeList != null && fileNodeList.getLength() > 0) {
							Element fileNode = (Element) fileNodeList.item(0);
							
							String originalRelativePath = fileNode.getAttributeNS("http://www.w3.org/TR/xlink", "href");
							jsonResource.setOriginal(originalRelativePath);
							
							String deliveryID = originalRelativePath.replaceAll("digitalObject/", "").
									replaceAll("/original", "");
							
							jsonResource.setFormatMime(delivery.getContentType(deliveryID));
							jsonResource.setPreview(originalRelativePath + "?mode=preview");
						}
						
						NodeList usageNodeList = resourceNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "usage");
						
						// assenza usage, default 3
						if(usageNodeList.getLength() == 0) {
							jsonResource.setData("rest/detailResourceFE/" + resourceType + "/"
									+ jsonResource.getSequenceNumber() + "/3?id=" + magID);
						}
						
						// presenza usage, standard
						else {
							for(int j = 0; j < usageNodeList.getLength(); j++) {
								String usage = usageNodeList.item(j).getTextContent().trim();
								
								if(!"a".equals(usage) && !"b".equals(usage)) {
									jsonResource.setData("rest/detailResourceFE/" + resourceType + "/"
											+ jsonResource.getSequenceNumber() + "/" + usage + "?id=" + magID);
								}
							}
						}

						if("ocr".equals(resourceType))
							jsonDetail.getOcr().add(jsonResource);
						
						else if("doc".equals(resourceType))
							jsonDetail.getDoc().add(jsonResource);
					}
				}
				
				else {
					if("audio".equals(resourceType) || "video".equals(resourceType)) {
						for(int i = 0; i < resourceNodeList.getLength(); i++) {
							Element resourceNode = (Element) resourceNodeList.item(i);
							
							String nomenclature = resourceNode.getElementsByTagNameNS(
									"http://www.iccu.sbn.it/metaAG1.pdf", "nomenclature").item(0).getTextContent();
							
							NodeList proxiesNodeList = resourceNode.getElementsByTagNameNS(
									"http://www.iccu.sbn.it/metaAG1.pdf", "proxies");
							
							String sequenceNumber = resourceNode.getElementsByTagNameNS(
									"http://www.iccu.sbn.it/metaAG1.pdf", "sequence_number").item(0).getTextContent();
							
							
							JsonMagDetailResource jsonResource = new JsonMagDetailResource();
							jsonResource.setNomenclature(nomenclature);
							jsonResource.setSequenceNumber(sequenceNumber);
							
							
							for(int j = 0; j < proxiesNodeList.getLength(); j++) {
								Element proxyNode = (Element) proxiesNodeList.item(j);
								NodeList usageNodeList = proxyNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "usage");
								
								// presenza usage, standard
								if(usageNodeList != null && usageNodeList.getLength() > 0) {
									for(int k = 0; k < usageNodeList.getLength(); k++) {
										String usage = usageNodeList.item(k).getTextContent().trim();
										
										if(!"a".equals(usage) && !"b".equals(usage)) {
											if(j == 0) {
												jsonResource.setData("rest/detailResourceFE/" + resourceType + "/"
														+ jsonResource.getSequenceNumber() + "/" + usage + "?id=" + magID);
											}
										}
									}
								}
								
								if(j == 0) {
									NodeList fileNodeList = proxyNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "file");
									
									if(fileNodeList != null && fileNodeList.getLength() > 0) {
										Element fileNode = (Element) fileNodeList.item(0);
										String originalRelativePath = fileNode.getAttributeNS("http://www.w3.org/TR/xlink", "href");
										jsonResource.setOriginal(originalRelativePath);
										
										String deliveryID = originalRelativePath.replaceAll("digitalObject/", "").
												replaceAll("/original", "");
										
										jsonResource.setFormatMime(delivery.getContentType(deliveryID));
									}
								}
								
								if(j == proxiesNodeList.getLength() - 1) {
									NodeList fileNodeList = proxyNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "file");
									
									if(fileNodeList != null && fileNodeList.getLength() > 0) {
										Element fileNode = (Element) fileNodeList.item(0);
										jsonResource.setPreview(fileNode.getAttributeNS("http://www.w3.org/TR/xlink", "href") + "?mode=preview");
									}
								}
							}
							
							if("audio".equals(resourceType))
								jsonDetail.getAudio().add(jsonResource);
							
							else if("video".equals(resourceType))
								jsonDetail.getVideo().add(jsonResource);
						}
					}
					
					else {
						for(int i = 0; i < resourceNodeList.getLength(); i++) {
							List<Element> imgNodeList = new ArrayList<Element>();
							Element resourceNode = (Element) resourceNodeList.item(i);
							imgNodeList.add(resourceNode);
							
							String nomenclature = resourceNode.getElementsByTagNameNS(
									"http://www.iccu.sbn.it/metaAG1.pdf", "nomenclature").item(0).getTextContent();
							
							String sequenceNumber = resourceNode.getElementsByTagNameNS(
									"http://www.iccu.sbn.it/metaAG1.pdf", "sequence_number").item(0).getTextContent();
							
							NodeList altimgNodeList = resourceNode.getElementsByTagNameNS(
									"http://www.iccu.sbn.it/metaAG1.pdf", "altimg");
							
							JsonMagDetailResource jsonResource = new JsonMagDetailResource();
							jsonResource.setNomenclature(nomenclature);
							jsonResource.setSequenceNumber(sequenceNumber);
							
							for(int j = 0; j < altimgNodeList.getLength(); j++)
								imgNodeList.add((Element) altimgNodeList.item(j));
							
							for(int j = 0; j < imgNodeList.size(); j++) {
								Element imgNode = imgNodeList.get(j);
								
								List<Element> usageNodeList = UtilXML.searchInResource(imgNode, 
										"http://www.iccu.sbn.it/metaAG1.pdf", "usage");

								// presenza usage, standard
								if(usageNodeList != null && !usageNodeList.isEmpty()) {
									for(Element usageNode : usageNodeList) {
										String usage = usageNode.getTextContent().trim();
										
										if(!"a".equals(usage) && !"b".equals(usage)) {
											if(j == 0) {
												jsonResource.setData("rest/detailResourceFE/" + resourceType + "/"
													+ jsonResource.getSequenceNumber() + "/" + usage + "?id=" + magID);
											}
										}
									}
								}

								if(j == 0) {
									NodeList imgChildren = imgNode.getChildNodes();
									
									for(int k = 0; k < imgChildren.getLength(); k++) {
										Node imgChild = imgChildren.item(k);
										
										if(imgChild.getNodeType() == Document.ELEMENT_NODE) {
											Element imgChildElem = (Element) imgChild;
											
											if("file".equals(imgChildElem.getLocalName())) {
												String originalRelativePath = imgChildElem.getAttributeNS("http://www.w3.org/TR/xlink", "href");
												jsonResource.setOriginal(originalRelativePath);
												
												String deliveryID = originalRelativePath.replaceAll("digitalObject/", "").
														replaceAll("/original", "");
												
												jsonResource.setFormatMime(delivery.getContentType(deliveryID));
												break;
											}
										}
									}
								}
								
								if(j == imgNodeList.size() - 1) {
									NodeList imgChildren = imgNode.getChildNodes();
									
									for(int k = 0; k < imgChildren.getLength(); k++) {
										Node imgChild = imgChildren.item(k);
										
										if(imgChild.getNodeType() == Document.ELEMENT_NODE) {
											Element imgChildElem = (Element) imgChild;
											
											if("file".equals(imgChildElem.getLocalName())) {
												jsonResource.setPreview(imgChildElem.getAttributeNS("http://www.w3.org/TR/xlink", "href") + "?mode=preview");
												break;
											}
										}
									}
								}
							}

							jsonDetail.getImg().add(jsonResource);
						}
					}
				}
			}
		}
		
	    return jsonDetail;
	}

	/**
	 * Metodo ricorsivo di mappatura delle sezioni stru
	 * 
	 * @param struElement tag stru XML
	 * @param stru oggetto stru per memorizzazione metadati
	 *//*
	private static void mapStru(Element struElement, JsonMagDetailStru stru) {
		NodeList struChildrenNodeList = struElement.getChildNodes();
		
		for(int i = 0; i < struChildrenNodeList.getLength(); i++) {
			Node struChild = struChildrenNodeList.item(i);
			
			if(struChild.getNodeType() == Document.ELEMENT_NODE) {
				Element childElement = (Element) struChild;
				
				if("sequence_number".equals(childElement.getLocalName()))
					stru.setSequenceNumber(childElement.getTextContent().trim());
				
				else if("nomenclature".equals(childElement.getLocalName()))
					stru.setNomenclature(childElement.getTextContent().trim());
				
				else if("start".equals(childElement.getLocalName())) {
					NamedNodeMap attrs = childElement.getAttributes();
					
					for(int j = 0; j < attrs.getLength(); j++) {
						Attr attr = (Attr) attrs.item(j);
						
						if("sequence_number".equals(attr.getLocalName()))
							stru.setStart(attr.getValue());
					}
				}
				
				else if("stop".equals(childElement.getLocalName())) {
					NamedNodeMap attrs = childElement.getAttributes();
					
					for(int j = 0; j < attrs.getLength(); j++) {
						Attr attr = (Attr) attrs.item(j);
						
						if("sequence_number".equals(attr.getLocalName()))
							stru.setStop(attr.getValue());
					}
				}
				
				else if("element".equals(childElement.getLocalName())) {
					JsonMagDetailStruElement childObj = new JsonMagDetailStruElement();
					mapElement(childElement, childObj);
					stru.getElement().add(childObj);
				}
				
				else if("stru".equals(childElement.getLocalName())) {
					JsonMagDetailStru childObj = new JsonMagDetailStru();
					mapStru(childElement, childObj);
					stru.getStru().add(childObj);
				}
			}
		}
	}*/

	/**
	 * Metodo ricorsivo di mappatura delle sezioni element
	 * 
	 * @param struElement tag stru XML
	 * @param element oggetto element per mappatura
	 */
	private static void mapElement(Element struElement, JsonMagDetailStruElement element) {
		NodeList struChildrenNodeList = struElement.getChildNodes();
		
		for(int i = 0; i < struChildrenNodeList.getLength(); i++) {
			Node struChild = struChildrenNodeList.item(i);
			
			if(struChild.getNodeType() == Document.ELEMENT_NODE) {
				Element childElement = (Element) struChild;
				
				if("nomenclature".equals(childElement.getLocalName()))
					element.setNomenclature(childElement.getTextContent().trim());
				
				else if("identifier".equals(childElement.getLocalName()))
					element.setIdentifier(childElement.getTextContent().trim());
			
				else if("resource".equals(childElement.getLocalName()))
					element.setResourceType(childElement.getTextContent().trim());
				
				else if("file".equals(childElement.getLocalName()))
					element.setFile(childElement.getAttributeNS("http://www.w3.org/TR/xlink", "href"));
				
				else if("start".equals(childElement.getLocalName())) {
					NamedNodeMap attrs = childElement.getAttributes();
					
					for(int j = 0; j < attrs.getLength(); j++) {
						Attr attr = (Attr) attrs.item(j);
						
						if("sequence_number".equals(attr.getLocalName()))
							element.setStart(attr.getValue());
					}
				}
				
				else if("stop".equals(childElement.getLocalName())) {
					NamedNodeMap attrs = childElement.getAttributes();
					
					for(int j = 0; j < attrs.getLength(); j++) {
						Attr attr = (Attr) attrs.item(j);
						
						if("sequence_number".equals(attr.getLocalName()))
							element.setStop(attr.getValue());
					}
				}
				
				else if("piece".equals(childElement.getLocalName())) {
					JsonMagDetailPiece piece = new JsonMagDetailPiece();
					NodeList pieceChildren = childElement.getChildNodes();
					
					for(int j = 0; j < pieceChildren.getLength(); j++) {
						Node childNode = pieceChildren.item(j);
						
						if(childNode.getNodeType() == Node.ELEMENT_NODE) {
							if("issue".equals(childNode.getLocalName()))
								piece.setIssue(childNode.getTextContent());
							
							else if("year".equals(childNode.getLocalName()))
								piece.setYear(childNode.getTextContent());
							
							else if("stpiece_per".equals(childNode.getLocalName()))
								piece.setStpiecePer(childNode.getTextContent());
							
							else if("part_number".equals(childNode.getLocalName()))
								piece.setPartNumber(childNode.getTextContent());
							
							else if("part_name".equals(childNode.getLocalName()))
								piece.setPartName(childNode.getTextContent());
							
							else if("stpiece_vol".equals(childNode.getLocalName()))
								piece.setStpieceVol(childNode.getTextContent());
						}
					}
					
					element.setPiece(piece);
				}
			}
		}
	}

	/**
	 * Restituisce i metadati della risorsa digitale specificata dai parametri
	 * 
	 * METADATI OGGETTI DIGITALI VIEWER CHERUBINI
	 * 
	 * @param magID ID del MAG
	 * @param type tipo di risorsa (img, audio, video, ocr, doc)
	 * @param sequenceNumber numero di sequenza
	 * @param usage usage
	 * @return JsonMagPresentationResource oggetto metadati risorsa digitale
	 * @throws FileNotFoundException
	 */
	public JsonMagDetailResource getServiceResourceDetail(String magID, String type, 
			String sequenceNumber, String usage) throws FileNotFoundException {
			
		JsonMagDetailResource jsonResource = new JsonMagDetailResource();
		Mag mag = UtilSolr.selectDocumentById(magID);
		
		if(mag == null)
			throw new FileNotFoundException("MAG richiesto non trovato nei risultati di ricerca");
		
		// ricerca nel documento
		Document doc = UtilXML.convertStringToDocumentXML(mag.getMagInternal());
		Element parentResource = UtilXML.getResourceNode(doc, type, sequenceNumber, usage);
		Element parentOriginal = parentResource;
		
		if(parentResource == null) {
			throw new FileNotFoundException("Risorsa di tipo " + type + " con numero di sequenza"
					+ " " + sequenceNumber + " e usage " + usage + " non trovata");
		}
		
		// calcolo riferimento originale
		if("altimg".equals(parentResource.getLocalName()) || "proxies".equals(parentResource.getLocalName()))
			parentOriginal = (Element) parentResource.getParentNode();
		
		
		if(parentResource.hasAttribute("holdingsID"))
			jsonResource.setHoldingsID(parentResource.getAttribute("holdingsID"));
		
		else if(parentOriginal.hasAttribute("holdingsID"))
			jsonResource.setHoldingsID(parentOriginal.getAttribute("holdingsID"));
		
		// risorse di tipo ocr e doc
		if("ocr".equals(parentOriginal.getLocalName()) || "doc".equals(parentOriginal.getLocalName())) {
			NodeList children = parentOriginal.getChildNodes();
			
			for(int i = 0; i < children.getLength(); i++) {
				Node child = children.item(i);
				
				if(child.getNodeType() == Document.ELEMENT_NODE) {
					Element elem = (Element) child;
					
					// nodo semplice
					if(!"format".equals(elem.getLocalName())) {
						String tag = elem.getLocalName();
						
						if("usage".equals(tag))
							jsonResource.getUsages().add(elem.getTextContent());
						
						else if("sequence_number".equals(tag))
							jsonResource.setSequenceNumber(elem.getTextContent());
						
						else if("nomenclature".equals(tag))
							jsonResource.setNomenclature(elem.getTextContent());
						
						else if("file".equals(tag))
							jsonResource.setFile(elem.getAttributeNS("http://www.w3.org/TR/xlink", "href"));
						
						else if("source".equals(tag))
							jsonResource.setSource(elem.getAttributeNS("http://www.w3.org/TR/xlink", "href"));
						
						else if("md5".equals(tag))
							jsonResource.setMd5(elem.getTextContent());
						
						else if("filesize".equals(tag))
							jsonResource.setFileSize(elem.getTextContent());
						
						else if("note".equals(tag))
							jsonResource.setNote(elem.getTextContent());
						
						else if("datetimecreated".equals(tag))
							jsonResource.setDatetimeCreated(elem.getTextContent());
						
						else if("software_ocr".equals(tag))
							jsonResource.setSoftwareOcr(elem.getTextContent());
					}
					
					// nodo di tipo format
					else {
						NodeList formatChildren = elem.getChildNodes();
						
						for(int j = 0; j < formatChildren.getLength(); j++) {
							Node formatChild = formatChildren.item(j);
							
							if(formatChild.getNodeType() == Document.ELEMENT_NODE) {
								String formatTag = formatChild.getLocalName();
								
								if("name".equals(formatTag))
									jsonResource.setFormatName(formatChild.getTextContent());
								
								else if("mime".equals(formatTag))
									jsonResource.setFormatMime(formatChild.getTextContent());
								
								else if("compression".equals(formatTag))
									jsonResource.setFormatCompression(formatChild.getTextContent());
							}
						}
					}
				}
			}
		}
		
		else if("audio".equals(parentOriginal.getLocalName()) || "video".equals(parentOriginal.getLocalName())) {
			if(parentResource.hasAttribute("audiogroupID"))
				jsonResource.setGroupID(parentResource.getAttribute("audiogroupID"));
			
			else if(parentOriginal.hasAttribute("audiogroupID"))
				jsonResource.setGroupID(parentOriginal.getAttribute("audiogroupID"));
			
			if(parentResource.hasAttribute("videogroupID"))
				jsonResource.setGroupID(parentResource.getAttribute("videogroupID"));
			
			else if(parentOriginal.hasAttribute("videogroupID"))
				jsonResource.setGroupID(parentOriginal.getAttribute("videogroupID"));
			
			
			if(jsonResource.getGroupID() != null && !jsonResource.getGroupID().isEmpty()) {
				NodeList groupNodeList = doc.getElementsByTagNameNS(
						"http://www.iccu.sbn.it/metaAG1.pdf", type + "_group");
				
				if(groupNodeList != null) {
					for(int i = 0; i < groupNodeList.getLength(); i++) {
						Element groupNode = (Element) groupNodeList.item(i);
						
						if(groupNode.hasAttribute("ID") && jsonResource.getGroupID().endsWith(groupNode.getAttribute("ID"))) {
							NodeList groupChildren = groupNode.getChildNodes();
							
							for(int j = 0; j < groupChildren.getLength(); j++) {
								Node childNode = groupChildren.item(j);
								
								if(childNode.getNodeType() == Document.ELEMENT_NODE) {
									NodeList dataNodes = childNode.getChildNodes();
									
									for(int k = 0; k < dataNodes.getLength(); k++) {
										Node dataNode = dataNodes.item(k);
										
										if(dataNode.getNodeType() == Document.ELEMENT_NODE) {
											if("samplingfrequency".equals(dataNode.getLocalName()))
												jsonResource.setSamplingFrequency(dataNode.getTextContent());
											
											else if("bitpersample".equals(dataNode.getLocalName()))
												jsonResource.setBitPerSample(dataNode.getTextContent());
											
											else if("bitrate".equals(dataNode.getLocalName()))
												jsonResource.setBitRate(dataNode.getTextContent());
											
											else if("name".equals(dataNode.getLocalName()))
												jsonResource.setFormatName(dataNode.getTextContent());
											
											else if("mime".equals(dataNode.getLocalName()))
												jsonResource.setFormatMime(dataNode.getTextContent());
											
											else if("compression".equals(dataNode.getLocalName()))
												jsonResource.setFormatCompression(dataNode.getTextContent());
											
											else if("channel_configuration".equals(dataNode.getLocalName()))
												jsonResource.setChannelConfiguration(dataNode.getTextContent());
											
											else if("videosize".equals(dataNode.getLocalName()))
												jsonResource.setVideoSize(dataNode.getTextContent());
											
											else if("aspectratio".equals(dataNode.getLocalName()))
												jsonResource.setAspectRatio(dataNode.getTextContent());
											
											else if("framerate".equals(dataNode.getLocalName()))
												jsonResource.setFrameRate(dataNode.getTextContent());
											
											else if("name".equals(dataNode.getLocalName()))
												jsonResource.setFormatName(dataNode.getTextContent());
											
											else if("mime".equals(dataNode.getLocalName()))
												jsonResource.setFormatMime(dataNode.getTextContent());
											
											else if("videoformat".equals(dataNode.getLocalName()))
												jsonResource.setFormatVideo(dataNode.getTextContent());
											
											else if("encode".equals(dataNode.getLocalName()))
												jsonResource.setFormatEncode(dataNode.getTextContent());
											
											else if("streamtype".equals(dataNode.getLocalName()))
												jsonResource.setFormatStreamType(dataNode.getTextContent());
											
											else if("codec".equals(dataNode.getLocalName()))
												jsonResource.setFormatCodec(dataNode.getTextContent());
										}
									}
								}
							}
						}
					}
				}
			}
			
			NodeList parentOriginalChildren = parentOriginal.getChildNodes();
			
			for(int i = 0; i < parentOriginalChildren.getLength(); i++) {
				Node originalChild = parentOriginalChildren.item(i);
				
				if(originalChild.getNodeType() == Document.ELEMENT_NODE) {
					String originalTag = originalChild.getLocalName();
					
					if("sequence_number".equals(originalTag))
						jsonResource.setSequenceNumber(originalChild.getTextContent());
					
					else if("nomenclature".equals(originalTag))
						jsonResource.setNomenclature(originalChild.getTextContent());
					
					else if("note".equals(originalTag))
						jsonResource.setNote(originalChild.getTextContent());
				}
			}
			
			NodeList parentResourceChildren = parentResource.getChildNodes();
						
			for(int i = 0; i < parentResourceChildren.getLength(); i++) {
				Node resourceChild = parentResourceChildren.item(i);
							
				if(resourceChild.getNodeType() == Document.ELEMENT_NODE && 
						!"transcription".equals(resourceChild.getLastChild()) && 
						!"digitisation".equals(resourceChild.getLastChild())) {
								
					Element proxiesElem = (Element) resourceChild;
					String tag = proxiesElem.getLocalName();
					
					if("usage".equals(tag))
						jsonResource.getUsages().add(proxiesElem.getTextContent());
					
					else if("file".equals(tag))
						jsonResource.setFile(proxiesElem.getAttributeNS("http://www.w3.org/TR/xlink", "href"));
					
					else if("md5".equals(tag))
						jsonResource.setMd5(proxiesElem.getTextContent());
					
					else if("filesize".equals(tag))
						jsonResource.setFileSize(proxiesElem.getTextContent());
					
					else if("datetimecreated".equals(tag))
						jsonResource.setDatetimeCreated(proxiesElem.getTextContent());
					
					else if("format".equals(tag)) {
						NodeList formatChildren = proxiesElem.getChildNodes();
						
						for(int j = 0; j < formatChildren.getLength(); j++) {
							Node formatChild = formatChildren.item(j);
							
							if(formatChild.getNodeType() == Document.ELEMENT_NODE) {
								String formatTag = formatChild.getLocalName();
								
								if("name".equals(formatTag))
									jsonResource.setFormatName(formatChild.getTextContent());
								
								else if("mime".equals(formatTag))
									jsonResource.setFormatMime(formatChild.getTextContent());
								
								else if("compression".equals(formatTag))
									jsonResource.setFormatCompression(formatChild.getTextContent());
								
								else if("channel_configuration".equals(formatTag))
									jsonResource.setChannelConfiguration(formatChild.getTextContent());
								
								else if("videoformat".equals(formatTag))
									jsonResource.setFormatVideo(formatChild.getTextContent());
								
								else if("encode".equals(formatTag))
									jsonResource.setFormatEncode(formatChild.getTextContent());
								
								else if("streamtype".equals(formatTag))
									jsonResource.setFormatStreamType(formatChild.getTextContent());
								
								else if("codec".equals(formatTag))
									jsonResource.setFormatCodec(formatChild.getTextContent());
							}
						}
					}
					
					else if(proxiesElem.getLocalName().endsWith("_dimensions")) {
						NodeList mediaChildren = proxiesElem.getChildNodes();
						
						for(int j = 0; j < mediaChildren.getLength(); j++) {
							Node mediaChild = mediaChildren.item(j);
							
							if(mediaChild.getNodeType() == Document.ELEMENT_NODE)
								jsonResource.setDuration(mediaChild.getTextContent());
						}
					}
					
					else if(proxiesElem.getLocalName().endsWith("_metrics")) {
						NodeList mediaChildren = proxiesElem.getChildNodes();
						
						for(int j = 0; j < mediaChildren.getLength(); j++) {
							Node mediaChild = mediaChildren.item(j);
							String mediaTag = mediaChild.getLocalName();
							
							if(mediaChild.getNodeType() == Document.ELEMENT_NODE) {
								if("videosize".equals(mediaTag))
									jsonResource.setVideoSize(mediaChild.getTextContent());
								
								else if("aspectratio".equals(mediaTag))
									jsonResource.setAspectRatio(mediaChild.getTextContent());
								
								else if("framerate".equals(mediaTag))
									jsonResource.setFrameRate(mediaChild.getTextContent());
								
								else if("samplingfrequency".equals(mediaTag))
									jsonResource.setSamplingFrequency(mediaChild.getTextContent());
								
								else if("bitpersample".equals(mediaTag))
									jsonResource.setBitPerSample(mediaChild.getTextContent());
								
								else if("bitrate".equals(mediaTag))
									jsonResource.setBitRate(mediaChild.getTextContent());
							}
						}
					}
				}
			}
		}
		
		// immagini
		else {
			if(parentResource.hasAttribute("imggroupID"))
				jsonResource.setGroupID(parentResource.getAttribute("imggroupID"));
			
			else if(parentOriginal.hasAttribute("imggroupID"))
				jsonResource.setGroupID(parentOriginal.getAttribute("imggroupID"));
			
			if(jsonResource.getGroupID() != null && !jsonResource.getGroupID().isEmpty()) {
				NodeList groupNodeList = doc.getElementsByTagNameNS(
						"http://www.iccu.sbn.it/metaAG1.pdf", "img_group");
				
				if(groupNodeList != null) {
					for(int i = 0; i < groupNodeList.getLength(); i++) {
						Element groupNode = (Element) groupNodeList.item(i);
						
						if(groupNode.hasAttribute("ID") && jsonResource.getGroupID().endsWith(groupNode.getAttribute("ID"))) {
							NodeList groupChildren = groupNode.getChildNodes();
							
							for(int j = 0; j < groupChildren.getLength(); j++) {
								Node childNode = groupChildren.item(j);
								
								if(childNode.getNodeType() == Document.ELEMENT_NODE) {
									if("image_metrics".equals(childNode.getLocalName())) {
										NodeList metricsChildren = childNode.getChildNodes();
										
										for(int k = 0; k < metricsChildren.getLength(); k++) {
											Node metricsChild = metricsChildren.item(k);
											
											if(metricsChild.getNodeType() == Document.ELEMENT_NODE) {
												if("samplingfrequencyunit".equals(metricsChild.getLocalName()))
													jsonResource.setSamplingFrequencyUnit(metricsChild.getTextContent());
												
												else if("samplingfrequencyplane".equals(metricsChild.getLocalName()))
													jsonResource.setSamplingFrequencyPlane(metricsChild.getTextContent());
												
												else if("xsamplingfrequency".equals(metricsChild.getLocalName()))
													jsonResource.setXSamplingFrequency(metricsChild.getTextContent());
												
												else if("ysamplingfrequency".equals(metricsChild.getLocalName()))
													jsonResource.setYSamplingFrequency(metricsChild.getTextContent());
												
												else if("photometricinterpretation".equals(metricsChild.getLocalName()))
													jsonResource.setPhotometricInterpretation(metricsChild.getTextContent());
												
												else if("bitpersample".equals(metricsChild.getLocalName()))
													jsonResource.setBitPerSample(metricsChild.getTextContent());
											}
										}
									}
									
									else if("format".equals(childNode.getLocalName())) {
										NodeList formatChildren = childNode.getChildNodes();
										
										for(int k = 0; k < formatChildren.getLength(); k++) {
											Node formatChild = formatChildren.item(k);
											
											if(formatChild.getNodeType() == Document.ELEMENT_NODE) {
												if("name".equals(formatChild.getLocalName()))
													jsonResource.setFormatName(formatChild.getTextContent());
												
												else if("mime".equals(formatChild.getLocalName()))
													jsonResource.setFormatMime(formatChild.getTextContent());
												
												else if("compression".equals(formatChild.getLocalName()))
													jsonResource.setFormatCompression(formatChild.getTextContent());
											}
										}
									}
									
									else if("ppi".equals(childNode.getLocalName()))
										jsonResource.setPpi(childNode.getTextContent());
									
									else if("dpi".equals(childNode.getLocalName()))
										jsonResource.setDpi(childNode.getTextContent());
									
									else if("scanning".equals(childNode.getLocalName())) {
										NodeList scanningChildren = childNode.getChildNodes();
										
										for(int k = 0; k < scanningChildren.getLength(); k++) {
											Node scanningChild = scanningChildren.item(k);
											
											if(scanningChild.getNodeType() == Document.ELEMENT_NODE) {
												if("sourcetype".equals(scanningChild.getLocalName()))
													jsonResource.setSourceType(scanningChild.getTextContent());
												
												else if("scanningagency".equals(scanningChild.getLocalName()))
													jsonResource.setScanningAgency(scanningChild.getTextContent());
												
												else if("devicesource".equals(scanningChild.getLocalName()))
													jsonResource.setDeviceSource(scanningChild.getTextContent());

												else if("scanningsystem".equals(scanningChild.getLocalName())) {
													NodeList systemChildren = scanningChild.getChildNodes();
													
													for(int l = 0; l < systemChildren.getLength(); l++) {
														Node systemChild = systemChildren.item(l);
														
														if(systemChild.getNodeType() == Document.ELEMENT_NODE) {
															if("scanner_manufacturer".equals(systemChild.getLocalName()))
																jsonResource.setScannerManufacturer(systemChild.getTextContent());
															
															else if("scanner_model".equals(systemChild.getLocalName()))
																jsonResource.setScannerModel(systemChild.getTextContent());
															
															else if("capture_software".equals(systemChild.getLocalName()))
																jsonResource.setCaptureSoftware(systemChild.getTextContent());
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
			
			NodeList originalChildren = parentOriginal.getChildNodes();
			
			for(int i = 0; i < originalChildren.getLength(); i++) {
				Node originalChild = originalChildren.item(i);
				
				if(originalChild.getNodeType() == Document.ELEMENT_NODE) {
					String originalTag = originalChild.getLocalName();
					
					if("sequence_number".equals(originalTag))
						jsonResource.setSequenceNumber(originalChild.getTextContent());
					
					else if("nomenclature".equals(originalTag))
						jsonResource.setNomenclature(originalChild.getTextContent());
					
					else if("note".equals(originalTag))
						jsonResource.setNote(originalChild.getTextContent());
					
					else if("side".equals(originalTag))
						jsonResource.setImageSide(originalChild.getTextContent());
					
					else if("scale".equals(originalTag))
						jsonResource.setImageScale(originalChild.getTextContent());
					
					else if("target".equals(originalTag)) {
						JsonMagDetailImgTarget target = new JsonMagDetailImgTarget();
						NodeList targetChildren = originalChild.getChildNodes();
						
						for(int j = 0; j < targetChildren.getLength(); j++) {
							Node targetChild = targetChildren.item(j);
							
							if(targetChild.getNodeType() == Document.ELEMENT_NODE) {
								String targetTag = targetChild.getLocalName();
								
								if("targetType".equals(targetTag))
									target.setTargetType(targetChild.getTextContent());
									
								else if("targetID".equals(targetTag))
									target.setTargetID(targetChild.getTextContent());
									
								else if("imageData".equals(targetTag))
									target.setImageData(targetChild.getTextContent());
										
								else if("performanceData".equals(targetTag))
									target.setPerformanceData(targetChild.getTextContent());
											
								else if("profiles".equals(targetTag))
									target.setProfiles(targetChild.getTextContent());
							}
						}
						
						jsonResource.getTargets().add(target);
					}
				}
			}
			
			NodeList imgChildren = parentResource.getChildNodes();
			
			for(int i = 0; i < imgChildren.getLength(); i++) {
				Node imgChild = imgChildren.item(i);
				
				if(imgChild.getNodeType() == Document.ELEMENT_NODE) {
					Element imgElem = (Element) imgChild;
					String imgTag = imgElem.getLocalName();
					
					if("usage".equals(imgTag))
						jsonResource.getUsages().add(imgElem.getTextContent());
					
					else if("file".equals(imgTag))
						jsonResource.setFile(imgElem.getAttributeNS("http://www.w3.org/TR/xlink", "href"));
					
					else if("md5".equals(imgTag))
						jsonResource.setMd5(imgElem.getTextContent());
					
					else if("filesize".equals(imgTag))
						jsonResource.setFileSize(imgElem.getTextContent());
					
					else if("datetimecreated".equals(imgTag))
						jsonResource.setDatetimeCreated(imgElem.getTextContent());
					
					else if(imgElem.getLocalName().endsWith("_dimensions")) {
						NodeList mediaChildren = imgElem.getChildNodes();
						
						for(int j = 0; j < mediaChildren.getLength(); j++) {
							Node mediaChild = mediaChildren.item(j);
							String mediaTag = mediaChild.getLocalName();
							
							if(mediaChild.getNodeType() == Document.ELEMENT_NODE) {
								if("imagewidth".equals(mediaTag))
									jsonResource.setImageWidth(mediaChild.getTextContent());
								
								else if("imagelength".equals(mediaTag))
									jsonResource.setImageHeight(mediaChild.getTextContent());
								
								else if("source_xdimension".equals(mediaTag))
									jsonResource.setImageX(mediaChild.getTextContent());
								
								else if("source_ydimension".equals(mediaTag))
									jsonResource.setImageY(mediaChild.getTextContent());
							}
						}
					}
					
					else if("image_metrics".equals(imgTag)) {
						NodeList metricsChildren = imgElem.getChildNodes();
						
						for(int k = 0; k < metricsChildren.getLength(); k++) {
							Node metricsChild = metricsChildren.item(k);
							
							if(metricsChild.getNodeType() == Document.ELEMENT_NODE) {
								if("samplingfrequencyunit".equals(metricsChild.getLocalName()))
									jsonResource.setSamplingFrequencyUnit(metricsChild.getTextContent());
								
								else if("samplingfrequencyplane".equals(metricsChild.getLocalName()))
									jsonResource.setSamplingFrequencyPlane(metricsChild.getTextContent());
								
								else if("xsamplingfrequency".equals(metricsChild.getLocalName()))
									jsonResource.setXSamplingFrequency(metricsChild.getTextContent());
								
								else if("ysamplingfrequency".equals(metricsChild.getLocalName()))
									jsonResource.setYSamplingFrequency(metricsChild.getTextContent());
								
								else if("photometricinterpretation".equals(metricsChild.getLocalName()))
									jsonResource.setPhotometricInterpretation(metricsChild.getTextContent());
								
								else if("bitpersample".equals(metricsChild.getLocalName()))
									jsonResource.setBitPerSample(metricsChild.getTextContent());
							}
						}
					}
					
					else if("format".equals(imgTag)) {
						NodeList formatChildren = imgElem.getChildNodes();
						
						for(int k = 0; k < formatChildren.getLength(); k++) {
							Node formatChild = formatChildren.item(k);
							
							if(formatChild.getNodeType() == Document.ELEMENT_NODE) {
								if("name".equals(formatChild.getLocalName()))
									jsonResource.setFormatName(formatChild.getTextContent());
								
								else if("mime".equals(formatChild.getLocalName()))
									jsonResource.setFormatMime(formatChild.getTextContent());
								
								else if("compression".equals(formatChild.getLocalName()))
									jsonResource.setFormatCompression(formatChild.getTextContent());
							}
						}
					}
					
					else if("ppi".equals(imgTag))
						jsonResource.setPpi(imgElem.getTextContent());
					
					else if("dpi".equals(imgTag))
						jsonResource.setDpi(imgElem.getTextContent());
					
					else if("scanning".equals(imgTag)) {
						NodeList scanningChildren = imgElem.getChildNodes();
						
						for(int k = 0; k < scanningChildren.getLength(); k++) {
							Node scanningChild = scanningChildren.item(k);
							
							if(scanningChild.getNodeType() == Document.ELEMENT_NODE) {
								if("sourcetype".equals(scanningChild.getLocalName()))
									jsonResource.setSourceType(scanningChild.getTextContent());
								
								else if("scanningagency".equals(scanningChild.getLocalName()))
									jsonResource.setScanningAgency(scanningChild.getTextContent());
								
								else if("devicesource".equals(scanningChild.getLocalName()))
									jsonResource.setDeviceSource(scanningChild.getTextContent());

								else if("scanningsystem".equals(scanningChild.getLocalName())) {
									NodeList systemChildren = scanningChild.getChildNodes();
									
									for(int l = 0; l < systemChildren.getLength(); l++) {
										Node systemChild = systemChildren.item(l);
										
										if(systemChild.getNodeType() == Document.ELEMENT_NODE) {
											if("scanner_manufacturer".equals(systemChild.getLocalName()))
												jsonResource.setScannerManufacturer(systemChild.getTextContent());
											
											else if("scanner_model".equals(systemChild.getLocalName()))
												jsonResource.setScannerModel(systemChild.getTextContent());
											
											else if("capture_software".equals(systemChild.getLocalName()))
												jsonResource.setCaptureSoftware(systemChild.getTextContent());
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return jsonResource;
	}
	
	/**
	 * Crea oggetto JSON campo singolo valore
	 * 
	 * @param name nome reale campo
	 * @param label label campo
	 * @param value valore campo
	 * @return JsonMagDetailField oggetto JSON
	 */
	private JsonMagDetailField createMetadataJsonSingleValueField(String name, String label, Object value) {
		JsonMagDetailField object = new JsonMagDetailField();
		object.setName(name);
		object.setLabel(label);
		object.setValue(value);
		return object;
	}

}
