package com.gruppometa.sbntecaremota.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gruppometa.sbntecaremota.objects.Mag;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorAudioGroup;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorBib;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorContent;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorDis;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorDisItem;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorGen;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorHoldings;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorImgGroup;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorImgTarget;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorPiece;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorResource;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorStru;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorStruElement;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorTranscriptionChain;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorTranscriptionData;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorTranscriptionSummary;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorUsageResource;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorVideoGroup;

public class EditorUtility {
	
	// logger
	private static Logger logger = LoggerFactory.getLogger(EditorUtility.class);
	
	/**
	 * Crea l'oggetto JSON che riassume la sezione GEN
	 * 
	 * @param document
	 * @return JsonMagEditorGen sezione GEN in formato JSON
	 */
	public static JsonMagEditorGen createGenJson(Document document) {
		NodeList genNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "gen");
		JsonMagEditorGen genObj = new JsonMagEditorGen();
		
		if(genNodeList.getLength() > 0) {
			Element genNode = (Element) genNodeList.item(0);
			NodeList genChildren = genNode.getChildNodes();
			
			for(int i = 0; i < genChildren.getLength(); i++) {
				Node node = genChildren.item(i);
				
				if(node.getNodeType() == Document.ELEMENT_NODE) {
					Element elem = (Element) node;
					
					if("collection".equals(elem.getLocalName()))
						genObj.setCollection(elem.getTextContent());
					
					else if("agency".equals(elem.getLocalName()))
						genObj.setAgency(elem.getTextContent());
					
					else if("stprog".equals(elem.getLocalName()))
						genObj.setStprog(elem.getTextContent());
					
					else if("access_rights".equals(elem.getLocalName()))
						genObj.setAccessRights(elem.getTextContent());
					
					else if("completeness".equals(elem.getLocalName()))
						genObj.setCompleteness(elem.getTextContent());
					
					else if("img_group".equals(elem.getLocalName()))
						genObj.getImgGroups().add(readImgGroup(elem));
					
					else if("audio_group".equals(elem.getLocalName()))
						genObj.getAudioGroups().add(readAudioGroup(elem));
					
					else if("video_group".equals(elem.getLocalName()))
						genObj.getVideoGroups().add(readVideoGroup(elem));
				}
			}
		}
		
		return genObj;
	}
	
	/**
	 * Lettura nodi gruppo immagine
	 * 
	 * @param imgGroupNode nodo img_group
	 * @return JsonMagEditorImgGroup oggetto JSON gruppo immagine
	 */
	public static JsonMagEditorImgGroup readImgGroup(Element imgGroupNode) {
		JsonMagEditorImgGroup imggroup = new JsonMagEditorImgGroup();
		imggroup.setId(imgGroupNode.getAttribute("ID"));
		NodeList groupChildren = imgGroupNode.getChildNodes();
		
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
		
		return imggroup;
	}

	/**
	 * Lettura nodi gruppo audio
	 * 
	 * @param imgGroupNode nodo audio_group
	 * @return JsonMagEditorAudioGroup oggetto JSON gruppo audio
	 */
	public static JsonMagEditorAudioGroup readAudioGroup(Element audioGroupNode) {
		JsonMagEditorAudioGroup audiogroup = new JsonMagEditorAudioGroup();
		audiogroup.setId(audioGroupNode.getAttribute("ID"));
		NodeList groupChildren = audioGroupNode.getChildNodes();
		
		for(int j = 0; j < groupChildren.getLength(); j++) {
			Node childNode = groupChildren.item(j);
			
			if(childNode.getNodeType() == Document.ELEMENT_NODE) {
				if("transcription".equals(childNode.getLocalName()))
					readAudioGroupTranscriptionObject((Element) childNode, audiogroup);
				
				else {
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
		}
		
		return audiogroup;
	}
	
	/**
	 * Lettura nodi gruppo video
	 * 
	 * @param imgGroupNode nodo video_group
	 * @return JsonMagEditorVideoGroup oggetto JSON gruppo video
	 */
	public static JsonMagEditorVideoGroup readVideoGroup(Element videoGroupNode) {
		JsonMagEditorVideoGroup videogroup = new JsonMagEditorVideoGroup();
		videogroup.setId(videoGroupNode.getAttribute("ID"));
		NodeList groupChildren = videoGroupNode.getChildNodes();
		
		for(int j = 0; j < groupChildren.getLength(); j++) {
			Node childNode = groupChildren.item(j);
			
			if(childNode.getNodeType() == Document.ELEMENT_NODE) {
				if("digitisation".equals(childNode.getLocalName()))
					readVideoGroupTranscriptionObject((Element) childNode, videogroup);
				
				else {
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
		}
		
		return videogroup;
	}
	
	/**
	 * Crea l'oggetto JSON che riassume la sezione BIB
	 * 
	 * @param document
	 * @return JsonMagEditorGen sezione BIB in formato JSON
	 */
	public static JsonMagEditorBib createBibJson(Document document) {
		NodeList bibNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "bib");
		JsonMagEditorBib bibObj = new JsonMagEditorBib();
		
		if(bibNodeList.getLength() > 0) {
			Element bibNode = (Element) bibNodeList.item(0);
			bibObj.setLevel(bibNode.getAttribute("level"));
			NodeList bibChildren = bibNode.getChildNodes();
			
			for(int i = 0; i < bibChildren.getLength(); i++) {
				Node node = bibChildren.item(i);
				
				if(node.getNodeType() == Document.ELEMENT_NODE) {
					Element elem = (Element) node;
					
					if("identifier".equals(elem.getLocalName())) {
						Map<String, String> obj = new HashMap<String, String>();
						obj.put(elem.getLocalName(), elem.getTextContent());
						bibObj.getIdentifiers().add(obj);
					}
					
					else if("title".equals(elem.getLocalName())) {
						Map<String, String> obj = new HashMap<String, String>();
						obj.put(elem.getLocalName(), elem.getTextContent());
						bibObj.getTitles().add(obj);
					}
					
					else if("creator".equals(elem.getLocalName())) {
						Map<String, String> obj = new HashMap<String, String>();
						obj.put(elem.getLocalName(), elem.getTextContent());
						bibObj.getCreators().add(obj);
					}
					
					else if("publisher".equals(elem.getLocalName())) {
						Map<String, String> obj = new HashMap<String, String>();
						obj.put(elem.getLocalName(), elem.getTextContent());
						bibObj.getPublishers().add(obj);
					}
					
					else if("subject".equals(elem.getLocalName())) {
						Map<String, String> obj = new HashMap<String, String>();
						obj.put(elem.getLocalName(), elem.getTextContent());
						bibObj.getSubjects().add(obj);
					}
					
					else if("description".equals(elem.getLocalName())) {
						Map<String, String> obj = new HashMap<String, String>();
						obj.put(elem.getLocalName(), elem.getTextContent());
						bibObj.getDescriptions().add(obj);
					}
					
					else if("contributor".equals(elem.getLocalName())) {
						Map<String, String> obj = new HashMap<String, String>();
						obj.put(elem.getLocalName(), elem.getTextContent());
						bibObj.getContributors().add(obj);
					}
					
					else if("date".equals(elem.getLocalName())) {
						Map<String, String> obj = new HashMap<String, String>();
						obj.put(elem.getLocalName(), elem.getTextContent());
						bibObj.getDates().add(obj);
					}
					
					else if("type".equals(elem.getLocalName())) {
						Map<String, String> obj = new HashMap<String, String>();
						obj.put(elem.getLocalName(), elem.getTextContent());
						bibObj.getTypes().add(obj);
					}
					
					else if("format".equals(elem.getLocalName())) {
						Map<String, String> obj = new HashMap<String, String>();
						obj.put(elem.getLocalName(), elem.getTextContent());
						bibObj.getFormats().add(obj);
					}
					
					else if("source".equals(elem.getLocalName())) {
						Map<String, String> obj = new HashMap<String, String>();
						obj.put(elem.getLocalName(), elem.getTextContent());
						bibObj.getSources().add(obj);
					}
					
					else if("language".equals(elem.getLocalName())) {
						Map<String, String> obj = new HashMap<String, String>();
						obj.put(elem.getLocalName(), elem.getTextContent());
						bibObj.getLanguages().add(obj);
					}
					
					else if("relation".equals(elem.getLocalName())) {
						Map<String, String> obj = new HashMap<String, String>();
						obj.put(elem.getLocalName(), elem.getTextContent());
						bibObj.getRelations().add(obj);
					}
					
					else if("coverage".equals(elem.getLocalName())) {
						Map<String, String> obj = new HashMap<String, String>();
						obj.put(elem.getLocalName(), elem.getTextContent());
						bibObj.getCoverages().add(obj);
					}
					
					else if("rights".equals(elem.getLocalName())) {
						Map<String, String> obj = new HashMap<String, String>();
						obj.put("right", elem.getTextContent());
						bibObj.getRights().add(obj);
					}
					
					else if("local_bib".equals(elem.getLocalName())) {
						NodeList localBibChildren = elem.getChildNodes();
						
						for(int j = 0; j < localBibChildren.getLength(); j++) {
							Node localNode = localBibChildren.item(j);
							
							if(localNode.getNodeType() == Node.ELEMENT_NODE) {
								if("geo_coord".equals(localNode.getLocalName())) {
									Map<String, String> obj = new HashMap<String, String>();
									obj.put(localNode.getLocalName(), localNode.getTextContent());
									bibObj.getGeoCoord().add(obj);
								}
								
								else if("not_date".equals(localNode.getLocalName())) {
									Map<String, String> obj = new HashMap<String, String>();
									obj.put(localNode.getLocalName(), localNode.getTextContent());
									bibObj.getNotDates().add(obj);
								}
							}
						}
					}
					
					else if("holdings".equals(elem.getLocalName())) {
						JsonMagEditorHoldings holding = new JsonMagEditorHoldings();
						
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
								
								else if("shelfmark".equals(childNode.getLocalName())) {
									Element shelfmarkNode = (Element) childNode;
									Map<String, String> obj = new HashMap<String, String>();
									obj.put(childNode.getLocalName(), shelfmarkNode.getTextContent());
									
									if(shelfmarkNode.hasAttribute("type")) {
										String shelfmarkType = shelfmarkNode.getAttribute("type");
										
										if(!shelfmarkType.isEmpty())
											obj.put("type", shelfmarkType);
									}
									
									holding.getShelfmarks().add(obj);
								}
							}
						}
						
						bibObj.getHoldings().add(holding);
					}
					
					else if("piece".equals(elem.getLocalName())) {
						JsonMagEditorPiece piece = new JsonMagEditorPiece();
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
						
						bibObj.setPiece(Arrays.asList(piece));
					}
				}
			}
		}
		
		return bibObj;
	}
	
	/**
	 * Crea l'oggetto JSON che riassume la sezione STRU
	 * 
	 * @param document
	 * @return JsonMagEditorGen sezione STRU in formato JSON
	 */
	public static List<JsonMagEditorStru> createStruJson(Document document) {
		NodeList struNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "stru");
		List<JsonMagEditorStru> struArray = new ArrayList<JsonMagEditorStru>();
		
		for(int i = 0; i < struNodeList.getLength(); i++) {
			Element struNode = (Element) struNodeList.item(i);
			
			if(!"stru".equals(struNode.getParentNode().getLocalName())) {
				JsonMagEditorStru stru = new JsonMagEditorStru();
				mapStru(struNode, stru);
				struArray.add(stru);
			}
		}
		
		return struArray;
	}
	
	/**
	 * Restituisce le risorse digitali di tipo IMG, con file a risoluzione più bassa
	 * 
	 * @param originalDocument documento
	 * @param type tipo di risorsa (img, audio, video, ocr, doc)
	 * @param content oggetto json contenuto risposta
	 */
	public static void createDigitalResourcesSection(Document originalDocument, 
			String type, JsonMagEditorContent content) {

		if(Arrays.asList("img", "audio", "video").contains(type)) {
			NodeList groupNodeList = originalDocument.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", type + "_group");
			
			for(int i = 0; i < groupNodeList.getLength(); i++) {
				Element groupNode = (Element) groupNodeList.item(i);
				
				if("img".equals(type)) {
					content.getImgGroups().add(groupNode.getAttribute("ID"));
					
					content.getImgGroupMap().put(groupNode.getAttribute("ID"), 
							EditorUtility.readImgGroup(groupNode));
				}
				
				else if("audio".equals(type)) {
					content.getAudioGroups().add(groupNode.getAttribute("ID"));

					content.getAudioGroupMap().put(groupNode.getAttribute("ID"), 
							EditorUtility.readAudioGroup(groupNode));
				}
				
				else {
					content.getVideoGroups().add(groupNode.getAttribute("ID"));

					content.getVideoGroupMap().put(groupNode.getAttribute("ID"), 
							EditorUtility.readVideoGroup(groupNode));
				}
			}
		}
		
		NodeList resourceNodeList = originalDocument.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", type);
		boolean warning = false;
		
		for(int i = 0; i < resourceNodeList.getLength(); i++) {
			JsonMagEditorResource jsonRes = new JsonMagEditorResource();
			Element resourceNode = (Element) resourceNodeList.item(i);
			
			if(resourceNode.hasAttribute("holdingsID"))
				jsonRes.setHoldingsID(resourceNode.getAttribute("holdingsID"));
			
			NodeList resourceChildren = resourceNode.getChildNodes();
			
			// prendi metadati generali
			for(int j = 0; j < resourceChildren.getLength(); j++) {
				Node child = resourceChildren.item(j);
				
				if(child.getNodeType() == Document.ELEMENT_NODE) {
					Element resourceChild = (Element) child;
					
					if("sequence_number".equals(resourceChild.getLocalName())) {
						jsonRes.setSequenceNumber(resourceChild.getTextContent());
						jsonRes.setId(resourceChild.getTextContent());
					}
					
					else if("nomenclature".equals(resourceChild.getLocalName()))
						jsonRes.setNomenclature(resourceChild.getTextContent());

					else if("side".equals(resourceChild.getLocalName()))
						jsonRes.setImageSide(resourceChild.getTextContent());
					
					else if("scale".equals(resourceChild.getLocalName()))
						jsonRes.setImageScale(resourceChild.getTextContent());
					
					else if("note".equals(resourceChild.getLocalName()))
						jsonRes.setNote(resourceChild.getTextContent());
					
					else if("target".equals(resourceChild.getLocalName())) {
						JsonMagEditorImgTarget target = new JsonMagEditorImgTarget();
						NodeList targetChildren = resourceChild.getChildNodes();
						
						for(int k = 0; k < targetChildren.getLength(); k++) {
							Node targetChild = targetChildren.item(k);
							
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
						
						jsonRes.getTargets().add(target);
					}
				}
			}
			
			// prendi nodo del file a risoluzione più bassa
			NodeList fileNodeList = resourceNode.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "file");
			
			for(int j = 0; j < fileNodeList.getLength(); j++) {
				Element fileNode = (Element) fileNodeList.item(j);
				String href = fileNode.getAttributeNS("http://www.w3.org/TR/xlink", "href");
				
				if(href.isEmpty())
					warning = true;
				
				Node parentNode = fileNode.getParentNode();
				NodeList parentChildren = parentNode.getChildNodes();
				int maxUsage = 0;
				
				for(int k = 0; k < parentChildren.getLength(); k++) {
					Node child = parentChildren.item(k);
					
					if(child.getNodeType() == Document.ELEMENT_NODE && "usage".equals(child.getLocalName())) {
						if(StringUtils.isNumeric(child.getTextContent())) {
							try {
								int usageNum = Integer.parseInt(child.getTextContent());
								URL url = new URL(ContentStatic.getProperties().getProperty("UrlServlet") + "/" + href);
								HttpURLConnection conn = (HttpURLConnection) url.openConnection();
								
								if(usageNum > maxUsage && conn.getResponseCode() == 200) {
									maxUsage = usageNum;
									jsonRes.setFile(href + "?mode=preview");
								}
								
							} catch (IOException e) {
								logger.warn(e.getMessage(), e);
							}
							
						}
					}
				}
			}
			
			if("img".equals(type))
				content.getImg().add(jsonRes);
			
			else if("audio".equals(type))
				content.getAudio().add(jsonRes);
			
			else if("video".equals(type))
				content.getVideo().add(jsonRes);
			
			else if("ocr".equals(type))
				content.getOcr().add(jsonRes);
			
			else if("doc".equals(type))
				content.getDoc().add(jsonRes);
		}
		
		if(warning) {
			content.getWarnings().add("Il MAG contiene oggetti digitali di cui "
					+ "non sono disponibili tutti gli usage indicati");
		}
	}
	
	/**
	 * Crea l'oggetto JSON che riassume la sezione DIS
	 * 
	 * @param document
	 * @return JsonMagEditorGen sezione DIS in formato JSON
	 */
	public static JsonMagEditorDis createDisJson(Document document) {
		NodeList disItemNodeList = document.getElementsByTagNameNS("http://www.iccu.sbn.it/metaAG1.pdf", "dis_item");
		JsonMagEditorDis disObj = new JsonMagEditorDis();
		
		for(int i = 0; i < disItemNodeList.getLength(); i++) {
			Node disItemNode = disItemNodeList.item(i);
			NodeList disItemChildren = disItemNode.getChildNodes();
			JsonMagEditorDisItem disItemObj = new JsonMagEditorDisItem();
			
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
			
			disObj.getDisItems().add(disItemObj);
		}
		
		return disObj;
	}
	
	/**
	 *  Crea l'oggetto JSON che riassume la l'oggetto digitale definito dal nodo (sezione OCR/DOC)
	 * 
	 * @param resourceNode nodo della risorsa digitale
	 * @return Map<Integer, JsonMagEditorUsageResource> mappa delle versioni (usage) con i metadati dipendenti
	 */
	public static Map<Integer, JsonMagEditorUsageResource> createDocJson(Element resourceNode) {
		Map<Integer, JsonMagEditorUsageResource> versions = new HashMap<Integer, JsonMagEditorUsageResource>();
		JsonMagEditorUsageResource jsonResource = new JsonMagEditorUsageResource();
		NodeList children = resourceNode.getChildNodes();
		URL url = null;
	
		for(int i = 0; i < children.getLength(); i++) {
			Node child = children.item(i);
			
			if(child.getNodeType() == Document.ELEMENT_NODE) {
				Element elem = (Element) child;

				// nodo semplice
				if(!"format".equals(elem.getLocalName())) {
					String tag = elem.getLocalName();
					
					if("usage".equals(tag)) {
						String usageString = elem.getTextContent();
						jsonResource.getUsages().add(usageString);
						
						if(!StringUtils.isNumeric(usageString))
							jsonResource.getUsagesAB().add(usageString);
					}
					
					else if("file".equals(tag)) {
						String href = elem.getAttributeNS("http://www.w3.org/TR/xlink", "href");
						
						if(!href.isEmpty()) {
							jsonResource.setId(href.replace("/original", "").replace("digitalObject/", ""));
							jsonResource.setFile(href);
							
							try {
								url = new URL(ContentStatic.getProperties().getProperty("UrlServlet") + "/" + href);
								
							} catch (MalformedURLException e) {
								logger.warn(e.getMessage(), e);
								url = null;
							}
						}
					}
					
					else if("source".equals(tag))
						jsonResource.setSource(elem.getAttributeNS("http://www.w3.org/TR/xlink", "href"));
					
					else if("md5".equals(tag))
						jsonResource.setMd5(elem.getTextContent());
					
					else if("filesize".equals(tag))
						jsonResource.setFileSize(elem.getTextContent());
					
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

		if(url != null) {
			try {
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();

				if(conn.getResponseCode() == 200)
					versions.put(0, jsonResource);
				
			} catch (IOException e) {
				logger.warn(e.getMessage(), e);
			}
		}
		
		return versions;
	}

	/**
	 *  Crea l'oggetto JSON che riassume la l'oggetto digitale definito dal nodo proxies (AUDIO/VIDEO)
	 * 
	 * @param resourceNode nodo della risorsa digitale
	 * @return Map<Integer, JsonMagEditorUsageResource> mappa delle versioni (usage) con i metadati dipendenti
	 */
	public static Map<Integer, JsonMagEditorUsageResource> createProxiesJson(Element proxiesNode) {
		Map<Integer, JsonMagEditorUsageResource> resourceMap = new HashMap<Integer, JsonMagEditorUsageResource>();
		JsonMagEditorUsageResource jsonResource = new JsonMagEditorUsageResource();
		int usage = 0;
		Element parentOriginal = (Element) proxiesNode.getParentNode();
		URL url = null;
		
		if(proxiesNode.hasAttribute("audiogroupID"))
			jsonResource.setGroupID(proxiesNode.getAttribute("audiogroupID"));
		
		else if(parentOriginal.hasAttribute("audiogroupID"))
			jsonResource.setGroupID(parentOriginal.getAttribute("audiogroupID"));
		
		if(proxiesNode.hasAttribute("videogroupID"))
			jsonResource.setGroupID(proxiesNode.getAttribute("videogroupID"));
		
		else if(parentOriginal.hasAttribute("videogroupID"))
			jsonResource.setGroupID(parentOriginal.getAttribute("videogroupID"));
		
		
		NodeList parentResourceChildren = proxiesNode.getChildNodes();
					
		for(int i = 0; i < parentResourceChildren.getLength(); i++) {
			Node resourceChild = parentResourceChildren.item(i);
						
			if(resourceChild.getNodeType() == Document.ELEMENT_NODE) {
				Element proxiesElem = (Element) resourceChild;
				String tag = proxiesElem.getLocalName();
				
				if("transcription".equals(tag) || "digitisation".equals(tag))
					readResourceTranscriptionObject(proxiesElem, jsonResource);
				
				else if("usage".equals(tag)) {
					String usageString = proxiesElem.getTextContent();
					
					if(StringUtils.isNumeric(usageString)) {
						jsonResource.getUsages().add(usageString);
						usage = Integer.parseInt(usageString);
					}
					
					else
						jsonResource.getUsagesAB().add(usageString);
				}
				
				else if("file".equals(tag)) {
					String href = proxiesElem.getAttributeNS("http://www.w3.org/TR/xlink", "href");
					
					if(!href.isEmpty()) {
						jsonResource.setId(href.replace("/original", "").replace("digitalObject/", ""));
						jsonResource.setFile(href);
	
						try {
							url = new URL(ContentStatic.getProperties().getProperty("UrlServlet") + "/" + href);
							
						} catch (MalformedURLException e) {
							logger.warn(e.getMessage(), e);
							url = null;
						}
					}
				}
				
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

		if(url != null) {
			try {
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();

				if(conn.getResponseCode() == 200)
					resourceMap.put(usage, jsonResource);
				
			} catch (IOException e) {
				logger.warn(e.getMessage(), e);
			}
		}
		
		return resourceMap;
	}

	/**
	 * Crea l'oggetto JSON che riassume la l'oggetto digitale definito dal nodo IMG/ALTIMG
	 * 
	 * @param imgNode nodo img o altimg
	 * @return Map<Integer, JsonMagEditorUsageResource> metadati mappati su chiave usage
	 */
	public static Map<Integer, JsonMagEditorUsageResource> createImgJson(Element imgNode) {
		Map<Integer, JsonMagEditorUsageResource> resourceMap = new HashMap<Integer, JsonMagEditorUsageResource>();
		JsonMagEditorUsageResource jsonResource = new JsonMagEditorUsageResource();
		int usage = 0;
		Element parentOriginal = (Element) imgNode.getParentNode();
		URL url = null;
		
		if(imgNode.hasAttribute("imggroupID"))
			jsonResource.setGroupID(imgNode.getAttribute("imggroupID"));
		
		else if(parentOriginal.hasAttribute("imggroupID"))
			jsonResource.setGroupID(parentOriginal.getAttribute("imggroupID"));
		
		NodeList imgChildren = imgNode.getChildNodes();
		
		for(int i = 0; i < imgChildren.getLength(); i++) {
			Node imgChild = imgChildren.item(i);
			
			if(imgChild.getNodeType() == Document.ELEMENT_NODE) {
				Element imgElem = (Element) imgChild;
				String imgTag = imgElem.getLocalName();
				
				if("usage".equals(imgTag)) {
					String usageString = imgElem.getTextContent();
					
					if(StringUtils.isNumeric(usageString)) {
						jsonResource.getUsages().add(usageString);
						usage = Integer.parseInt(usageString);
					}
					
					else
						jsonResource.getUsagesAB().add(usageString);
				}
				
				else if("file".equals(imgTag)) {
					String href = imgElem.getAttributeNS("http://www.w3.org/TR/xlink", "href");
					
					if(!href.isEmpty()) {
						jsonResource.setId(href.replace("/original", "").replace("digitalObject/", ""));
						jsonResource.setFile(href);
						
						try {
							url = new URL(ContentStatic.getProperties().getProperty("UrlServlet") + "/" + href);
							
						} catch (MalformedURLException e) {
							logger.warn(e.getMessage(), e);
							url = null;
						}
					}
				}
				
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

		if(url != null) {
			try {
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();

				if(conn.getResponseCode() == 200)
					resourceMap.put(usage, jsonResource);
				
			} catch (IOException e) {
				logger.warn(e.getMessage(), e);
			}
		}
		
		return resourceMap;
	}
	
	/**
	 * Crea nodo XML sezione GEN
	 * 
	 * @param document documento XML
	 * @param mag oggetto di indicizzazione
	 * @param genObject sezione GEN in formato JSON
	 * @return Element nodo sezione GEN
	 */
	public static Element createGenXml(Document document, Mag mag, JsonMagEditorGen genObject) {
		Element genNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "gen");
		
		if(genObject.getStprog() != null && !genObject.getStprog().isEmpty()) {
			mag.setStprog(genObject.getStprog());
			
			genNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "stprog", genObject.getStprog()));
		}
		
		if(genObject.getCollection() != null && !genObject.getCollection().isEmpty()) {
			mag.setCollection(genObject.getCollection());
			
			genNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "collection", genObject.getCollection()));
		}
		
		if(genObject.getAgency() != null && !genObject.getAgency().isEmpty()) {
			mag.setAgency(genObject.getAgency());
			
			genNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "agency", genObject.getAgency()));
		}
		
		if(genObject.getAccessRights() != null && !genObject.getAccessRights().isEmpty()) {
			mag.setAccessRights(Integer.parseInt(genObject.getAccessRights()));
			
			genNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "access_rights", genObject.getAccessRights()));
		}
		
		if(genObject.getCompleteness() != null && !genObject.getCompleteness().isEmpty()) {
			mag.setCompleteness(Integer.parseInt(genObject.getCompleteness()));
			
			genNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "completeness", genObject.getCompleteness()));
		}
			
		
		
		for(JsonMagEditorImgGroup imgGroupObject : genObject.getImgGroups()) {
			Element imgGroupNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "img_group");
			
			if(imgGroupObject.getId() != null && !imgGroupObject.getId().isEmpty())
				imgGroupNode.setAttribute("ID", imgGroupObject.getId());
			
			Node imageMetricsNode = createImageMetricsNode(document, imgGroupObject.getSamplingFrequencyUnit(), 
					imgGroupObject.getSamplingFrequencyPlane(), imgGroupObject.getXSamplingFrequency(), 
					imgGroupObject.getYSamplingFrequency(), imgGroupObject.getPhotometricInterpretation(), 
					imgGroupObject.getBitPerSample());
			
			if(imageMetricsNode != null)
				imgGroupNode.appendChild(imageMetricsNode);
			
			
			if(imgGroupObject.getPpi() != null && !imgGroupObject.getPpi().isEmpty()) {
				imgGroupNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "ppi", imgGroupObject.getPpi()));
			}
			
			if(imgGroupObject.getDpi() != null && !imgGroupObject.getDpi().isEmpty()) {
				imgGroupNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "dpi", imgGroupObject.getDpi()));
			}
			
			Node formatNode = createImageFormatNode(document, imgGroupObject.getFormatName(), 
					imgGroupObject.getFormatMime(), imgGroupObject.getFormatCompression());
			
			if(formatNode != null)
				imgGroupNode.appendChild(formatNode);
			
			Node scanningNode = createImageScanningNode(document, imgGroupObject.getSourceType(), 
					imgGroupObject.getScanningAgency(), imgGroupObject.getDeviceSource(), 
					imgGroupObject.getScannerManufacturer(), imgGroupObject.getScannerModel(), 
					imgGroupObject.getCaptureSoftware());
			
			if(scanningNode != null)
				imgGroupNode.appendChild(scanningNode);
			
			genNode.appendChild(imgGroupNode);
		}

		
		
		for(JsonMagEditorAudioGroup audioGroupObject : genObject.getAudioGroups()) {
			Element audioGroupNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "audio_group");
			
			if(audioGroupObject.getId() != null && !audioGroupObject.getId().isEmpty())
				audioGroupNode.setAttribute("ID", audioGroupObject.getId());
			
			Node audioMetricsNode = createAudioMetrics(document, audioGroupObject.getSamplingFrequency(), 
					audioGroupObject.getBitPerSample(), audioGroupObject.getBitRate());
			
			if(audioMetricsNode != null)
				audioGroupNode.appendChild(audioMetricsNode);
			
			Node formatNode = createAudioFormatNode(document, audioGroupObject.getFormatName(), 
					audioGroupObject.getFormatMime(), audioGroupObject.getFormatCompression(), 
					audioGroupObject.getChannelConfiguration());
			
			if(formatNode != null)
				audioGroupNode.appendChild(formatNode);
			
			Element transcriptionNode = createAudioGroupTranscriptionNode("transcription", audioGroupObject, document);
			
			if(transcriptionNode != null)
				audioGroupNode.appendChild(transcriptionNode);
				
			genNode.appendChild(audioGroupNode);
		}

		
		
		for(JsonMagEditorVideoGroup videoGroupObject : genObject.getVideoGroups()) {
			Element videoGroupNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "video_group");
			
			if(videoGroupObject.getId() != null && !videoGroupObject.getId().isEmpty())
				videoGroupNode.setAttribute("ID", videoGroupObject.getId());
			
			Node videoMetricsNode = createVideoMetricsNode(document, videoGroupObject.getVideoSize(), 
					videoGroupObject.getAspectRatio(), videoGroupObject.getFrameRate());
			
			if(videoMetricsNode != null)
				videoGroupNode.appendChild(videoMetricsNode);
			
			Node formatNode = createVideoFormatNode(document, videoGroupObject.getFormatName(), 
					videoGroupObject.getFormatMime(), videoGroupObject.getFormatVideo(), videoGroupObject.getFormatEncode(), 
					videoGroupObject.getFormatStreamType(), videoGroupObject.getFormatCodec());
			
			if(formatNode != null)
				videoGroupNode.appendChild(formatNode);

			Element digitisationNode = createVideoGroupTranscriptionNode("digitisation", videoGroupObject, document);
			
			if(digitisationNode != null)
				videoGroupNode.appendChild(digitisationNode);
			
			genNode.appendChild(videoGroupNode);
		}
		
		return genNode;
	}
	
	/**
	 * Crea nodo XML sezione BIB
	 * 
	 * @param document documento XML
	 * @param mag oggetto di indicizzazione
	 * @param bibObject sezione BIB in formato JSON
	 * @return Element nodo sezione BIB
	 */
	public static Element createBibXml(Document document, Mag mag, JsonMagEditorBib bibObject) {
		Element bibNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "bib");
		
		if(bibObject.getLevel() != null && !bibObject.getLevel().isEmpty()) {
			mag.setLevel(bibObject.getLevel());
			bibNode.setAttribute("level", bibObject.getLevel());
		}
		
		mag.setIdentifiers(new ArrayList<String>());
		
		if(bibObject.getIdentifiers() != null) {
			for(Map<String, String> obj : bibObject.getIdentifiers()) {
				String identifier = obj.get("identifier");
				
				if(identifier != null && !identifier.isEmpty()) {
					bibNode.appendChild(createSimpleTextElement(document, 
							"http://purl.org/dc/elements/1.1/", "identifier", identifier));
					
					mag.getIdentifiers().add(identifier);
				}
			}
		}
		
		mag.setTitles(new ArrayList<String>());

		if(bibObject.getTitles() != null) {
			for(Map<String, String> obj : bibObject.getTitles()) {
				String title = obj.get("title");
				
				if(title != null && !title.isEmpty()) {
					bibNode.appendChild(createSimpleTextElement(document, 
							"http://purl.org/dc/elements/1.1/", "title", title));
					
					mag.getTitles().add(title);
				}
			}
		}
		
		mag.setCreators(new ArrayList<String>());
		
		if(bibObject.getCreators() != null) {
			for(Map<String, String> obj : bibObject.getCreators()) {
				String creator = obj.get("creator");
				
				if(creator != null && !creator.isEmpty()) {
					bibNode.appendChild(createSimpleTextElement(document, 
							"http://purl.org/dc/elements/1.1/", "creator", creator));
					
					mag.getCreators().add(creator);
				}
			}
		}
		
		mag.setPublishers(new ArrayList<String>());

		if(bibObject.getPublishers() != null) {
			for(Map<String, String> obj : bibObject.getPublishers()) {
				String publisher = obj.get("publisher");
				
				if(publisher != null && !publisher.isEmpty()) {
					bibNode.appendChild(createSimpleTextElement(document, 
							"http://purl.org/dc/elements/1.1/", "publisher", publisher));
					
					mag.getPublishers().add(publisher);
				}
			}
		}
		
		mag.setSubjects(new ArrayList<String>());
		
		if(bibObject.getSubjects() != null) {
			for(Map<String, String> obj : bibObject.getSubjects()) {
				String subject = obj.get("subject");
				
				if(subject != null && !subject.isEmpty()) {
					bibNode.appendChild(createSimpleTextElement(document, 
							"http://purl.org/dc/elements/1.1/", "subject", subject));
					
					mag.getSubjects().add(subject);
				}
			}
		}
		
		mag.setDescriptions(new ArrayList<String>());

		if(bibObject.getDescriptions() != null) {
			for(Map<String, String> obj : bibObject.getDescriptions()) {
				String description = obj.get("description");
				
				if(description != null && !description.isEmpty()) {
					bibNode.appendChild(createSimpleTextElement(document, 
							"http://purl.org/dc/elements/1.1/", "description", description));
					
					mag.getDescriptions().add(description);
				}
			}
		}
		
		mag.setContributors(new ArrayList<String>());
		
		if(bibObject.getContributors() != null) {
			for(Map<String, String> obj : bibObject.getContributors()) {
				String contributor = obj.get("contributor");
				
				if(contributor != null && !contributor.isEmpty()) {
					bibNode.appendChild(createSimpleTextElement(document, 
							"http://purl.org/dc/elements/1.1/", "contributor", contributor));
					
					mag.getContributors().add(contributor);
				}
			}
		}
		
		mag.setDates(new ArrayList<String>());
		
		if(bibObject.getDates() != null) {
			for(Map<String, String> obj : bibObject.getDates()) {
				String date = obj.get("date");
				
				if(date != null && !date.isEmpty()) {
					bibNode.appendChild(createSimpleTextElement(document, 
							"http://purl.org/dc/elements/1.1/", "date", date));
					
					mag.getDates().add(date);
				}
			}
		}
		
		mag.setTypes(new ArrayList<String>());
		
		if(bibObject.getTypes() != null) {
			for(Map<String, String> obj : bibObject.getTypes()) {
				String type = obj.get("type");
				
				if(type != null && !type.isEmpty()) {
					bibNode.appendChild(createSimpleTextElement(document, 
							"http://purl.org/dc/elements/1.1/", "type", type));
					
					mag.getTypes().add(type);
				}
			}
		}
		
		mag.setFormats(new ArrayList<String>());

		if(bibObject.getFormats() != null) {
			for(Map<String, String> obj : bibObject.getFormats()) {
				String format = obj.get("format");
				
				if(format != null && !format.isEmpty()) {
					bibNode.appendChild(createSimpleTextElement(document, 
							"http://purl.org/dc/elements/1.1/", "format", format));
					
					mag.getFormats().add(format);
				}
			}
		}
		
		mag.setSources(new ArrayList<String>());

		if(bibObject.getSources() != null) {
			for(Map<String, String> obj : bibObject.getSources()) {
				String source = obj.get("source");
				
				if(source != null && !source.isEmpty()) {
					bibNode.appendChild(createSimpleTextElement(document, 
							"http://purl.org/dc/elements/1.1/", "source", source));
					
					mag.getSources().add(source);
				}
			}
		}
		
		mag.setLanguages(new ArrayList<String>());

		if(bibObject.getLanguages() != null) {
			for(Map<String, String> obj : bibObject.getLanguages()) {
				String language = obj.get("language");
				
				if(language != null && !language.isEmpty()) {
					bibNode.appendChild(createSimpleTextElement(document, 
							"http://purl.org/dc/elements/1.1/", "language", language));
					
					mag.getLanguages().add(language);
				}
			}
		}
		
		mag.setRelations(new ArrayList<String>());

		if(bibObject.getRelations() != null) {
			for(Map<String, String> obj : bibObject.getRelations()) {
				String relation = obj.get("relation");
				
				if(relation != null && !relation.isEmpty()) {
					bibNode.appendChild(createSimpleTextElement(document, 
							"http://purl.org/dc/elements/1.1/", "relation", relation));
					
					mag.getRelations().add(relation);
				}
			}
		}
		
		mag.setCoverages(new ArrayList<String>());

		if(bibObject.getCoverages() != null) {
			for(Map<String, String> obj : bibObject.getCoverages()) {
				String coverage = obj.get("coverage");
				
				if(coverage != null && !coverage.isEmpty()) {
					bibNode.appendChild(createSimpleTextElement(document, 
							"http://purl.org/dc/elements/1.1/", "coverage", coverage));
					
					mag.getCoverages().add(coverage);
				}
			}
		}
		
		mag.setRights(new ArrayList<String>());

		if(bibObject.getRights() != null) {
			for(Map<String, String> obj : bibObject.getRights()) {
				String rights = obj.get("right");
				
				if(rights != null && !rights.isEmpty()) {
					bibNode.appendChild(createSimpleTextElement(document, 
							"http://purl.org/dc/elements/1.1/", "rights", rights));
					
					mag.getRights().add(rights);
				}
			}
		}
		
		mag.setLibraries(new ArrayList<String>());
		mag.setInventoryNumbers(new ArrayList<String>());
		mag.setShelfmarks(new ArrayList<String>());
		
		if(bibObject.getHoldings() != null) {
			for(JsonMagEditorHoldings holdingsObject : bibObject.getHoldings()) {
				Element holdingsNode = createSimpleElement(document, null, "holdings");
				
				if(holdingsObject.getId() != null && !holdingsObject.getId().isEmpty())
					holdingsNode.setAttribute("ID", holdingsObject.getId());
				
				if(holdingsObject.getLibrary() != null && !holdingsObject.getLibrary().isEmpty()) {
					holdingsNode.appendChild(createSimpleTextElement(document, null, 
							"library", holdingsObject.getLibrary()));
					
					mag.getLibraries().add(holdingsObject.getLibrary());
				}
				
				if(holdingsObject.getInventoryNumber() != null) {
					if(holdingsObject.getInventoryNumber().isEmpty())
						holdingsNode.appendChild(createSimpleElement(document, null, "inventory_number"));
					
					else {
						holdingsNode.appendChild(createSimpleTextElement(document, null, 
								"inventory_number", holdingsObject.getInventoryNumber()));
						
						mag.getInventoryNumbers().add(holdingsObject.getInventoryNumber());
					}
				}
				
				if(holdingsObject.getShelfmarks() != null) {
					for(Map<String, String> obj : holdingsObject.getShelfmarks()) {
						String shelfmark = obj.get("shelfmark");
						String shelfmarkType = obj.get("type");
						
						if(shelfmark != null && !shelfmark.isEmpty()) {
							Element shelfmarkNode = createSimpleElement(document,null, "shelfmark");
							shelfmarkNode.setTextContent(shelfmark);
							
							if(shelfmarkType != null && !shelfmarkType.isEmpty())
								shelfmarkNode.setAttribute("type", shelfmarkType);
							
							holdingsNode.appendChild(shelfmarkNode);							
							mag.getShelfmarks().add(shelfmark);
						}
					}
				}
				
				bibNode.appendChild(holdingsNode);
			}
		}
		
		mag.setGeoCoord(new ArrayList<String>());
		mag.setNotDate(new ArrayList<String>());
		
		if(!bibObject.getGeoCoord().isEmpty() || !bibObject.getNotDates().isEmpty()) {
			Element localNode = createSimpleElement(document, null, "local_bib");
			
			if(bibObject.getGeoCoord() != null) {
				for(Map<String, String> obj : bibObject.getGeoCoord()) {
					String geo = obj.get("geo_coord");
					
					if(geo != null && !geo.isEmpty()) {
						localNode.appendChild(createSimpleTextElement(document, 
								null, "geo_coord", geo));
						
						mag.getGeoCoord().add(geo);
					}
				}
			}
			
			if(bibObject.getNotDates() != null) {
				for(Map<String, String> obj : bibObject.getNotDates()) {
					String notDate = obj.get("not_date");
					
					if(notDate != null && !notDate.isEmpty()) {
						localNode.appendChild(createSimpleTextElement(document, 
								null, "not_date", notDate));
						
						mag.getNotDate().add(notDate);
					}
				}
			}
			
			bibNode.appendChild(localNode);
		}
		
		if(!bibObject.getPiece().isEmpty()) {
			JsonMagEditorPiece pieceObj = bibObject.getPiece().get(0);
			
			Element pieceNode = createSimpleElement(document, null, "piece");
			
			if(pieceObj != null && !pieceObj.getYear().isEmpty()) {
				mag.setYear(pieceObj.getYear());
				
				pieceNode.appendChild(createSimpleTextElement(document, null, 
						"year", pieceObj.getYear()));
			}
			
			if(pieceObj.getIssue() != null && !pieceObj.getIssue().isEmpty()) {
				mag.setIssue(pieceObj.getIssue());
				
				pieceNode.appendChild(createSimpleTextElement(document, null, 
						"issue", pieceObj.getIssue()));
			}
			
			if(pieceObj.getStpiecePer() != null && !pieceObj.getStpiecePer().isEmpty()) {
				mag.setStpiecePer(pieceObj.getStpiecePer());
				
				pieceNode.appendChild(createSimpleTextElement(document, null, 
						"stpiece_per", pieceObj.getStpiecePer()));
			}
			
			if(pieceObj.getPartNumber() != null && !pieceObj.getPartNumber().isEmpty()) {
				mag.setPartNumber(pieceObj.getPartNumber());
				
				pieceNode.appendChild(createSimpleTextElement(document, null, 
						"part_number", pieceObj.getPartNumber()));
			}
			
			if(pieceObj.getPartName() != null && !pieceObj.getPartName().isEmpty()) {
				mag.setPartName(pieceObj.getPartName());
				
				pieceNode.appendChild(createSimpleTextElement(document, null, 
						"part_name", pieceObj.getPartName()));
			}
			
			if(pieceObj.getStpieceVol() != null && !pieceObj.getStpieceVol().isEmpty()) {
				mag.setStpieceVol(pieceObj.getStpieceVol());
				
				pieceNode.appendChild(createSimpleTextElement(document, null, 
						"stpiece_vol", pieceObj.getStpieceVol()));
			}
			
			bibNode.appendChild(pieceNode);
		}
		
		return bibNode;
	}

	/**
	 * Crea nodi XML sezione STRU
	 * 
	 * @param document documento XML
	 * @param mag oggetto di indicizzazione
	 * @param struObjects sezione STRU in formato JSON
	 * @return Element nodo sezione STRU
	 */
	public static List<Element> createStruXml(Document document, Mag mag, List<JsonMagEditorStru> struObjects) {
		List<Element> newElements = new ArrayList<Element>();
		populateStru(document, document.getDocumentElement(), struObjects, newElements, mag);
		return newElements;
	}

	/**
	 * Metodo ricorsivo di mappatura delle sezioni stru
	 * 
	 * @param struElement tag stru XML
	 * @param stru oggetto stru per memorizzazione metadati
	 */
	private static void mapStru(Element struElement, JsonMagEditorStru stru) {
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
					JsonMagEditorStruElement childObj = new JsonMagEditorStruElement();
					mapElement(childElement, childObj);
					stru.getElement().add(childObj);
				}
				
				else if("stru".equals(childElement.getLocalName())) {
					JsonMagEditorStru childObj = new JsonMagEditorStru();
					mapStru(childElement, childObj);
					stru.getStru().add(childObj);
				}
			}
		}
	}

	/**
	 * Crea nodo XML sezione IMG
	 * 
	 * @param document documento XML
	 * @param imgObj oggetto IMG in formato JSON
	 * @return Element nodo IMG
	 */
	public static Element createImgXml(Document document, JsonMagEditorResource imgObj) {
		Element imgNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "img");
		List<Integer> usageList = new ArrayList<Integer>(imgObj.getVersions().keySet());
		Collections.sort(usageList);
		
		JsonMagEditorUsageResource mainResource = imgObj.getVersions().isEmpty() ? null : 
			imgObj.getVersions().get(usageList.get(0));
		
		List<JsonMagEditorUsageResource> altimgResources = new ArrayList<JsonMagEditorUsageResource>();
		
		for(int i = 1; i < usageList.size(); i++)
			altimgResources.add(imgObj.getVersions().get(usageList.get(i)));
		
		if(mainResource != null && mainResource.getGroupID() != null && !mainResource.getGroupID().isEmpty())
			imgNode.setAttribute("imggroupID", mainResource.getGroupID());
		
		if(imgObj.getHoldingsID() != null && !imgObj.getHoldingsID().isEmpty())
			imgNode.setAttribute("holdingsID", imgObj.getHoldingsID());
		
		if(imgObj.getSequenceNumber() != null && !imgObj.getSequenceNumber().isEmpty()) {
			imgNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "sequence_number", 
					imgObj.getSequenceNumber()));
		}
		
		if(imgObj.getNomenclature() != null && !imgObj.getNomenclature().isEmpty()) {
			imgNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "nomenclature", 
					imgObj.getNomenclature()));
		}
		
		if(mainResource != null) {
			for(String usage : mainResource.getUsages()) {
				if(usage != null && !usage.isEmpty()) {
					imgNode.appendChild(createSimpleTextElement(document, 
							"http://www.iccu.sbn.it/metaAG1.pdf", "usage", usage));
				}
			}
			
			for(String usageAB : mainResource.getUsagesAB()) {
				if(usageAB != null && !usageAB.isEmpty()) {
					imgNode.appendChild(createSimpleTextElement(document, 
							"http://www.iccu.sbn.it/metaAG1.pdf", "usage", usageAB));
				}
			}
		}
		
		if(imgObj.getImageSide() != null && !imgObj.getImageSide().isEmpty()) {
			imgNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "side", imgObj.getImageSide()));
		}

		if(imgObj.getImageScale() != null && !imgObj.getImageScale().isEmpty()) {
			imgNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "scale", imgObj.getImageScale()));
		}
		
		if(mainResource != null && mainResource.getFile() != null && !mainResource.getFile().isEmpty()) {
			Element fileNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "file");
			fileNode.setAttribute("Location", "URI");
			fileNode.setAttributeNS("http://www.w3.org/TR/xlink", document.getDocumentElement().lookupPrefix("http://www.w3.org/TR/xlink") +":href",
					mainResource.getFile());
			//logger.info("Set href to "+mainResource.getFile());
			imgNode.appendChild(fileNode);
		}
		
		if(mainResource != null && mainResource.getMd5() != null && !mainResource.getMd5().isEmpty()) {
			imgNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "md5", mainResource.getMd5()));
		}

		if(mainResource != null && mainResource.getFileSize() != null && !mainResource.getFileSize().isEmpty()) {
			imgNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "filesize", mainResource.getFileSize()));
		}
		
		if(mainResource != null) {
			imgNode.appendChild(createImageDimensions(document, mainResource.getImageHeight(), 
					mainResource.getImageWidth(), mainResource.getImageX(), mainResource.getImageY()));
			
			if(mainResource.getGroupID() == null || mainResource.getGroupID().isEmpty()) {
				Node imageMetricsNode = createImageMetricsNode(document, mainResource.getSamplingFrequencyUnit(), 
						mainResource.getSamplingFrequencyPlane(), mainResource.getXSamplingFrequency(), 
						mainResource.getYSamplingFrequency(), mainResource.getPhotometricInterpretation(), 
						mainResource.getBitPerSample());
				
				if(imageMetricsNode != null)
					imgNode.appendChild(imageMetricsNode);
				
				if(mainResource.getPpi() != null && !mainResource.getPpi().isEmpty()) {
					imgNode.appendChild(createSimpleTextElement(document, 
							"http://www.iccu.sbn.it/metaAG1.pdf", "ppi", mainResource.getPpi()));
				}
				
				if(mainResource.getDpi() != null && !mainResource.getDpi().isEmpty()) {
					imgNode.appendChild(createSimpleTextElement(document, 
							"http://www.iccu.sbn.it/metaAG1.pdf", "dpi", mainResource.getDpi()));
				}
				
				Node formatNode = createImageFormatNode(document, mainResource.getFormatName(), 
						mainResource.getFormatMime(), mainResource.getFormatCompression());
				
				if(formatNode != null)
					imgNode.appendChild(formatNode);
				
	
				Node scanningNode = createImageScanningNode(document, mainResource.getSourceType(), 
						mainResource.getScanningAgency(), mainResource.getDeviceSource(), 
						mainResource.getScannerManufacturer(), mainResource.getScannerModel(), mainResource.getCaptureSoftware());
				
				if(scanningNode != null)
					imgNode.appendChild(scanningNode);
			}
			
			if(mainResource.getDatetimeCreated() != null && !mainResource.getDatetimeCreated().isEmpty()) {
				imgNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "datetimecreated", mainResource.getDatetimeCreated()));
			}
		}
		
		for(JsonMagEditorImgTarget target : imgObj.getTargets()) {
			Element targetNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "target");
			
			if(target.getTargetType() != null && !target.getTargetType().isEmpty()) {
				targetNode.appendChild(createSimpleTextElement(document, 
						"http://www.niso.org/pdfs/DataDict.pdf", "targetType", target.getTargetType()));
			}
			
			if(target.getTargetID() != null && !target.getTargetID().isEmpty()) {
				targetNode.appendChild(createSimpleTextElement(document, 
						"http://www.niso.org/pdfs/DataDict.pdf", "targetID", target.getTargetID()));
			}
			
			if(target.getImageData() != null && !target.getImageData().isEmpty()) {
				targetNode.appendChild(createSimpleTextElement(document, 
						"http://www.niso.org/pdfs/DataDict.pdf", "imageData", target.getImageData()));
			}

			if(target.getPerformanceData() != null && !target.getPerformanceData().isEmpty()) {
				targetNode.appendChild(createSimpleTextElement(document, 
						"http://www.niso.org/pdfs/DataDict.pdf", "performanceData", target.getPerformanceData()));
			}

			if(target.getProfiles() != null && !target.getProfiles().isEmpty()) {
				targetNode.appendChild(createSimpleTextElement(document, 
						"http://www.niso.org/pdfs/DataDict.pdf", "profiles", target.getProfiles()));
			}
			
			imgNode.appendChild(targetNode);
		}
		
		if(!altimgResources.isEmpty()) {
			for(int i = 0; i < altimgResources.size(); i++) {
				Element altimgNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "altimg");
				JsonMagEditorUsageResource altimgResource = altimgResources.get(i);
				
				if(altimgResource.getGroupID() != null && !altimgResource.getGroupID().isEmpty())
					altimgNode.setAttribute("imggroupID", altimgResource.getGroupID());

				for(String usage : altimgResource.getUsages()) {
					if(usage != null && !usage.isEmpty()) {
						altimgNode.appendChild(createSimpleTextElement(document, 
								"http://www.iccu.sbn.it/metaAG1.pdf", "usage", usage));
					}
				}

				for(String usageAB : altimgResource.getUsagesAB()) {
					if(usageAB != null && !usageAB.isEmpty()) {
						altimgNode.appendChild(createSimpleTextElement(document, 
								"http://www.iccu.sbn.it/metaAG1.pdf", "usage", usageAB));
					}
				}

				if(altimgResource.getFile() != null && !altimgResource.getFile().isEmpty()) {
					Element fileNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "file");
					fileNode.setAttribute("Location", "URI");
					fileNode.setAttributeNS("http://www.w3.org/TR/xlink", document.getDocumentElement().lookupPrefix("http://www.w3.org/TR/xlink") +":href", altimgResource.getFile());
					altimgNode.appendChild(fileNode);
				}
				
				if(altimgResource.getMd5() != null && !altimgResource.getMd5().isEmpty()) {
					altimgNode.appendChild(createSimpleTextElement(document, 
							"http://www.iccu.sbn.it/metaAG1.pdf", "md5", altimgResource.getMd5()));
				}

				if(altimgResource.getFileSize() != null && !altimgResource.getFileSize().isEmpty()) {
					altimgNode.appendChild(createSimpleTextElement(document, 
							"http://www.iccu.sbn.it/metaAG1.pdf", "filesize", altimgResource.getFileSize()));
				}
				
				altimgNode.appendChild(createImageDimensions(document, altimgResource.getImageHeight(), 
						altimgResource.getImageWidth(), altimgResource.getImageX(), altimgResource.getImageY()));
				

				if(altimgResource.getGroupID() == null || altimgResource.getGroupID().isEmpty()) {
					Node altimageMetricsNode = createImageMetricsNode(document, altimgResource.getSamplingFrequencyUnit(), 
							altimgResource.getSamplingFrequencyPlane(), altimgResource.getXSamplingFrequency(), 
							altimgResource.getYSamplingFrequency(), altimgResource.getPhotometricInterpretation(), 
							altimgResource.getBitPerSample());
					
					if(altimageMetricsNode != null)
						altimgNode.appendChild(altimageMetricsNode);
					
					if(altimgResource.getPpi() != null && !altimgResource.getPpi().isEmpty()) {
						altimgNode.appendChild(createSimpleTextElement(document, 
								"http://www.iccu.sbn.it/metaAG1.pdf", "ppi", altimgResource.getPpi()));
					}
					
					if(altimgResource.getDpi() != null && !altimgResource.getDpi().isEmpty()) {
						altimgNode.appendChild(createSimpleTextElement(document, 
								"http://www.iccu.sbn.it/metaAG1.pdf", "dpi", altimgResource.getDpi()));
					}
					
					Node altimgFormatNode = createImageFormatNode(document, altimgResource.getFormatName(), 
							altimgResource.getFormatMime(), altimgResource.getFormatCompression());
					
					if(altimgFormatNode != null)
						altimgNode.appendChild(altimgFormatNode);
					
	
					Node altimgScanningNode = createImageScanningNode(document, altimgResource.getSourceType(), 
							altimgResource.getScanningAgency(), altimgResource.getDeviceSource(), 
							altimgResource.getScannerManufacturer(), altimgResource.getScannerModel(), altimgResource.getCaptureSoftware());
					
					if(altimgScanningNode != null)
						altimgNode.appendChild(altimgScanningNode);
				}
				
				
				if(altimgResource.getDatetimeCreated() != null && !altimgResource.getDatetimeCreated().isEmpty()) {
					altimgNode.appendChild(createSimpleTextElement(document, 
							"http://www.iccu.sbn.it/metaAG1.pdf", "datetimecreated", altimgResource.getDatetimeCreated()));
				}
				
				imgNode.appendChild(altimgNode);
			}
		}
		
		if(imgObj.getNote() != null && !imgObj.getNote().isEmpty()) {
			imgNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "note", imgObj.getNote()));
		}
		
		return imgNode;
	}
	
	/**
	 * Crea nodo XML sezione AUDIO
	 * 
	 * @param document documento XML
	 * @param audioObj oggetto AUDIO in formato JSON
	 * @return Element nodo AUDIO
	 */
	public static Element createAudioXml(Document document, JsonMagEditorResource audioObj) {
		List<Integer> usageList = new ArrayList<Integer>(audioObj.getVersions().keySet());
		Collections.sort(usageList);
		List<JsonMagEditorUsageResource> proxiesResources = new ArrayList<JsonMagEditorUsageResource>();
		
		for(int i = 0; i < usageList.size(); i++)
			proxiesResources.add(audioObj.getVersions().get(usageList.get(i)));
		
		Element audioNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "audio");
		
		if(audioObj.getHoldingsID() != null && !audioObj.getHoldingsID().isEmpty())
			audioNode.setAttribute("holdingsID", audioObj.getHoldingsID());
		
		if(audioObj.getSequenceNumber() != null && !audioObj.getSequenceNumber().isEmpty()) {
			audioNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "sequence_number", audioObj.getSequenceNumber()));
		}
		
		if(audioObj.getNomenclature() != null && !audioObj.getNomenclature().isEmpty()) {
			audioNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "nomenclature", audioObj.getNomenclature()));
		}
		
		for(JsonMagEditorUsageResource r : proxiesResources) {
			Element proxiesNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "proxies");
			
			if(r.getGroupID() != null && !r.getGroupID().isEmpty())
				proxiesNode.setAttribute("audiogroupID", r.getGroupID());
			
			for(String usage : r.getUsages()) {
				if(usage != null && !usage.isEmpty()) {
					proxiesNode.appendChild(createSimpleTextElement(document, 
							"http://www.iccu.sbn.it/metaAG1.pdf", "usage", usage));
				}
			}
			
			for(String usageAB : r.getUsagesAB()) {
				if(usageAB != null && !usageAB.isEmpty()) {
					proxiesNode.appendChild(createSimpleTextElement(document, 
							"http://www.iccu.sbn.it/metaAG1.pdf", "usage", usageAB));
				}
			}

			if(r.getFile() != null && !r.getFile().isEmpty()) {
				Element fileNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "file");
				fileNode.setAttribute("Location", "URI");
				fileNode.setAttributeNS("http://www.w3.org/TR/xlink", document.getDocumentElement().lookupPrefix("http://www.w3.org/TR/xlink") +":href", r.getFile());
				proxiesNode.appendChild(fileNode);
			}
			
			if(r.getMd5() != null && !r.getMd5().isEmpty()) {
				proxiesNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "md5", r.getMd5()));
			}

			if(r.getFileSize() != null && !r.getFileSize().isEmpty()) {
				proxiesNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "filesize", r.getFileSize()));
			}
			
			if(r.getDuration() != null && !r.getDuration().isEmpty()) {
				Element audioDimNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "audio_dimensions");
				
				audioDimNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "duration", r.getDuration()));
				
				proxiesNode.appendChild(audioDimNode);
			}
			
			if(r.getGroupID() == null || r.getGroupID().isEmpty()) {
				Node audioMetricsNode = createAudioMetrics(document, r.getSamplingFrequency(), 
						r.getBitPerSample(), r.getBitRate());
				
				if(audioMetricsNode != null)
					proxiesNode.appendChild(audioMetricsNode);
	
				Node formatNode = createAudioFormatNode(document, r.getFormatName(), 
						r.getFormatMime(), r.getFormatCompression(), r.getChannelConfiguration());
				
				if(formatNode != null)
					proxiesNode.appendChild(formatNode);
				
				Element transcriptionNode = createResourceTranscriptionNode("transcription", r, document);
				
				if(transcriptionNode != null)
					proxiesNode.appendChild(transcriptionNode);
			}

			if(r.getDatetimeCreated() != null && !r.getDatetimeCreated().isEmpty()) {
				proxiesNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "datetimecreated", r.getDatetimeCreated()));
			}
			
			audioNode.appendChild(proxiesNode);
		}
		
		if(audioObj.getNote() != null && !audioObj.getNote().isEmpty()) {
			audioNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "note", audioObj.getNote()));
		}
		
		return audioNode;
	}

	/**
	 * Crea nodo XML sezione VIDEO
	 * 
	 * @param document documento XML
	 * @param videoObj oggetto VIDEO in formato JSON
	 * @return Element nodo VIDEO
	 */
	public static Element createVideoXml(Document document, JsonMagEditorResource videoObj) {
		List<Integer> usageList = new ArrayList<Integer>(videoObj.getVersions().keySet());
		Collections.sort(usageList);
		
		List<JsonMagEditorUsageResource> proxiesResources = new ArrayList<JsonMagEditorUsageResource>();
		
		for(int i = 0; i < usageList.size(); i++)
			proxiesResources.add(videoObj.getVersions().get(usageList.get(i)));
		
		Element videoNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "video");
		
		if(videoObj.getHoldingsID() != null && !videoObj.getHoldingsID().isEmpty())
			videoNode.setAttribute("holdingsID", videoObj.getHoldingsID());
		
		if(videoObj.getSequenceNumber() != null && !videoObj.getSequenceNumber().isEmpty()) {
			videoNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "sequence_number", videoObj.getSequenceNumber()));
		}
		
		if(videoObj.getNomenclature() != null && !videoObj.getNomenclature().isEmpty()) {
			videoNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "nomenclature", videoObj.getNomenclature()));
		}
		
		for(JsonMagEditorUsageResource r : proxiesResources) {
			Element proxiesNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "proxies");

			if(r.getGroupID() != null && !r.getGroupID().isEmpty())
				proxiesNode.setAttribute("videogroupID", r.getGroupID());
			
			for(String usage : r.getUsages()) {
				if(usage != null && !usage.isEmpty()) {
					proxiesNode.appendChild(createSimpleTextElement(document, 
							"http://www.iccu.sbn.it/metaAG1.pdf", "usage", usage));
				}
			}
			
			for(String usageAB : r.getUsagesAB()) {
				if(usageAB != null && !usageAB.isEmpty()) {
					proxiesNode.appendChild(createSimpleTextElement(document, 
							"http://www.iccu.sbn.it/metaAG1.pdf", "usage", usageAB));
				}
			}

			if(r.getFile() != null && !r.getFile().isEmpty()) {
				Element fileNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "file");
				fileNode.setAttribute("Location", "URI");
				fileNode.setAttributeNS("http://www.w3.org/TR/xlink", document.getDocumentElement().lookupPrefix("http://www.w3.org/TR/xlink") +":href", r.getFile());
				proxiesNode.appendChild(fileNode);
			}
			
			if(r.getMd5() != null && !r.getMd5().isEmpty()) {
				proxiesNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "md5", r.getMd5()));
			}

			if(r.getFileSize() != null && !r.getFileSize().isEmpty()) {
				proxiesNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "filesize", r.getFileSize()));
			}
			
			if(r.getDuration() != null && !r.getDuration().isEmpty()) {
				Element videoDimNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "video_dimensions");
				
				videoDimNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "duration", r.getDuration()));
				
				proxiesNode.appendChild(videoDimNode);
			}
			
			if(r.getGroupID() == null || r.getGroupID().isEmpty()) {
				Node videoMetricsNode = createVideoMetricsNode(document, r.getVideoSize(), 
						r.getAspectRatio(), r.getFrameRate());
				
				if(videoMetricsNode != null)
					proxiesNode.appendChild(videoMetricsNode);
	
				Node formatNode = createVideoFormatNode(document, r.getFormatName(), 
						r.getFormatMime(), r.getFormatVideo(), r.getFormatEncode(), 
						r.getFormatStreamType(), r.getFormatCodec());
				
				if(formatNode != null)
					proxiesNode.appendChild(formatNode);
				
				Element digitisationNode = createResourceTranscriptionNode("digitisation", r, document);
				
				if(digitisationNode != null)
					proxiesNode.appendChild(digitisationNode);
			}
			
			if(r.getDatetimeCreated() != null && !r.getDatetimeCreated().isEmpty()) {
				proxiesNode.appendChild(createSimpleTextElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", 
						"datetimecreated", r.getDatetimeCreated()));
			}
			
			videoNode.appendChild(proxiesNode);
		}
		
		if(videoObj.getNote() != null) {
			videoNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "note", videoObj.getNote()));
		}
		
		return videoNode;
	}

	/**
	 * Crea nodo XML sezione OCR
	 * 
	 * @param document documento XML
	 * @param ocrObj oggetto OCR in formato JSON
	 * @return Element nodo OCR
	 */
	public static Element createOcrXml(Document document, JsonMagEditorResource ocrObj) {
		JsonMagEditorUsageResource firstResource = ocrObj.getVersions().isEmpty() ? 
				null : new ArrayList<JsonMagEditorUsageResource>(ocrObj.getVersions().values()).get(0);
		
		Element ocrNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "ocr");
		
		if(ocrObj.getHoldingsID() != null && !ocrObj.getHoldingsID().isEmpty())
			ocrNode.setAttribute("holdingsID", ocrObj.getHoldingsID());
		
		if(ocrObj.getSequenceNumber() != null && !ocrObj.getSequenceNumber().isEmpty()) {
			ocrNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "sequence_number", ocrObj.getSequenceNumber()));
		}
		
		if(ocrObj.getNomenclature() != null && !ocrObj.getNomenclature().isEmpty()) {
			ocrNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "nomenclature", ocrObj.getNomenclature()));
		}
		
		if(firstResource != null) {
			ocrNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "usage", "3"));
			
			for(String usageAB : firstResource.getUsagesAB()) {
				if(usageAB != null && !usageAB.isEmpty()) {
					ocrNode.appendChild(createSimpleTextElement(document, 
							"http://www.iccu.sbn.it/metaAG1.pdf", "usage", usageAB));
				}
			}

			if(firstResource.getFile() != null && !firstResource.getFile().isEmpty()) {
				Element fileNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "file");
				fileNode.setAttribute("Location", "URI");
				fileNode.setAttributeNS("http://www.w3.org/TR/xlink", document.getDocumentElement().lookupPrefix("http://www.w3.org/TR/xlink") +":href", firstResource.getFile());
				ocrNode.appendChild(fileNode);
			}
			
			if(firstResource.getMd5() != null && !firstResource.getMd5().isEmpty()) {
				ocrNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "md5", firstResource.getMd5()));
			}

			if(firstResource.getSource() != null && !firstResource.getSource().isEmpty()) {
				Element sourceNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "source");
				sourceNode.setAttribute("Location", "URI");
				sourceNode.setAttributeNS("http://www.w3.org/TR/xlink", document.getDocumentElement().lookupPrefix("http://www.w3.org/TR/xlink") +":href", firstResource.getSource());
				ocrNode.appendChild(sourceNode);
			}

			if(firstResource.getFileSize() != null && !firstResource.getFileSize().isEmpty()) {
				ocrNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "filesize", firstResource.getFileSize()));
			}

			Node formatNode = createDocFormatNode(document, firstResource.getFormatName(), 
					firstResource.getFormatMime(), firstResource.getFormatCompression());
			
			if(formatNode != null)
				ocrNode.appendChild(formatNode);
			
			if(firstResource.getSoftwareOcr() != null && !firstResource.getSoftwareOcr().isEmpty()) {
				ocrNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "software_ocr", firstResource.getSoftwareOcr()));
			}

			if(firstResource.getDatetimeCreated() != null && !firstResource.getDatetimeCreated().isEmpty()) {
				ocrNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "datetimecreated", firstResource.getDatetimeCreated()));
			}
		}
		
		if(ocrObj.getNote() != null && !ocrObj.getNote().isEmpty()) {
			ocrNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "note", ocrObj.getNote()));
		}
		
		return ocrNode;
	}

	/**
	 * Crea nodo XML sezione DOC
	 * 
	 * @param document documento XML
	 * @param docObj oggetto DOC in formato JSON
	 * @return Element nodo DOC
	 */
	public static Element createDocXml(Document document, JsonMagEditorResource docObj) {
		JsonMagEditorUsageResource firstResource = docObj.getVersions().isEmpty() ? 
				null : new ArrayList<JsonMagEditorUsageResource>(docObj.getVersions().values()).get(0);
		
		Element docNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "doc");
		
		if(docObj.getHoldingsID() != null && !docObj.getHoldingsID().isEmpty())
			docNode.setAttribute("holdingsID", docObj.getHoldingsID());
		
		if(docObj.getSequenceNumber() != null && !docObj.getSequenceNumber().isEmpty()) {
			docNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "sequence_number", docObj.getSequenceNumber()));
		}
		
		if(docObj.getNomenclature() != null && !docObj.getNomenclature().isEmpty()) {
			docNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "nomenclature", docObj.getNomenclature()));
		}
		
		if(firstResource != null) {
			docNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "usage", "3"));
			
			for(String usageAB : firstResource.getUsagesAB()) {
				if(usageAB != null && !usageAB.isEmpty()) {
					docNode.appendChild(createSimpleTextElement(document, 
							"http://www.iccu.sbn.it/metaAG1.pdf", "usage", usageAB));
				}
			}

			if(firstResource.getFile() != null && !firstResource.getFile().isEmpty()) {
				Element fileNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "file");
				fileNode.setAttribute("Location", "URI");
				fileNode.setAttributeNS("http://www.w3.org/TR/xlink", document.getDocumentElement().lookupPrefix("http://www.w3.org/TR/xlink") +":href", firstResource.getFile());
				docNode.appendChild(fileNode);
			}
			
			if(firstResource.getMd5() != null && !firstResource.getMd5().isEmpty()) {
				docNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "md5", firstResource.getMd5()));
			}

			if(firstResource.getFileSize() != null && !firstResource.getFileSize().isEmpty()) {
				docNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "filesize", firstResource.getFileSize()));
			}

			Node formatNode = createDocFormatNode(document, firstResource.getFormatName(), 
					firstResource.getFormatMime(), firstResource.getFormatCompression());
			
			if(formatNode != null)
				docNode.appendChild(formatNode);

			if(firstResource.getDatetimeCreated() != null && !firstResource.getDatetimeCreated().isEmpty()) {
				docNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "datetimecreated", firstResource.getDatetimeCreated()));
			}
		}
		
		if(docObj.getNote() != null && !docObj.getNote().isEmpty()) {
			docNode.appendChild(createSimpleTextElement(document, 
					"http://www.iccu.sbn.it/metaAG1.pdf", "note", docObj.getNote()));
		}
		
		return docNode;
	}

	/**
	 * Crea nodo XML sezione DIS
	 * 
	 * @param document documento XML
	 * @param disObject oggetto DIS in formato JSON
	 * @return Element nodo DIS
	 */
	public static Element createDisXml(Document document, JsonMagEditorDis disObject) {
		Element disElement = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "dis");
		
		if(disObject.getDisItems() != null) {
			for(JsonMagEditorDisItem disItemObj : disObject.getDisItems()) {
				Element disItemElement = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "dis_item");
				
				if(disItemObj.getFile() != null) {
					Element fileNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "file");
					fileNode.setAttribute("Location", "URI");
					fileNode.setAttributeNS("http://www.w3.org/TR/xlink", document.getDocumentElement().lookupPrefix("http://www.w3.org/TR/xlink") +":href", disItemObj.getFile());
					disItemElement.appendChild(fileNode);
				}
				
				if(disItemObj.getPreview() != null) {
					disItemElement.appendChild(createSimpleTextElement(document, 
							"http://www.iccu.sbn.it/metaAG1.pdf", "preview", disItemObj.getPreview()));
				}

				if(disItemObj.getAvailable() != null) {
					disItemElement.appendChild(createSimpleTextElement(document, 
							"http://www.iccu.sbn.it/metaAG1.pdf", "available", disItemObj.getAvailable()));
				}
				
				disElement.appendChild(disItemElement);
			}
		}
		
		return disElement;
	}

	/**
	 * Metodo ricorsivo di mappatura delle sezioni element
	 * 
	 * @param struElement tag stru XML
	 * @param element oggetto element per mappatura
	 */
	private static void mapElement(Element struElement, JsonMagEditorStruElement element) {
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
					JsonMagEditorPiece piece = new JsonMagEditorPiece();
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
					
					element.setPiece(Arrays.asList(piece));
				}
			}
		}
	}

	/**
	 * Legge la trascrizione (o la digitalizzazione) per un oggetto digitale
	 * 
	 * @param transcriptionNode nodo di trascrizione (o digitalizzazione)
	 * @param resourceObj oggetto risorsa digitale
	 */
	private static void readResourceTranscriptionObject(Element transcriptionNode, 
			JsonMagEditorUsageResource resourceObj) {
		
		NodeList transcriptionChildren = transcriptionNode.getChildNodes();
		
		for(int i = 0; i < transcriptionChildren.getLength(); i++) {
			Node child = transcriptionChildren.item(i);
			
			if(child.getNodeType() == Document.ELEMENT_NODE) {
				if("sourcetype".equals(child.getLocalName()))
					resourceObj.setSourceType(child.getTextContent());
				
				else if("transcriptionagency".equals(child.getLocalName()))
					resourceObj.setTranscriptionAgency(child.getTextContent());

				else if("transcriptiondate".equals(child.getLocalName()))
					resourceObj.setTranscriptionDate(child.getTextContent());

				else if("devicesource".equals(child.getLocalName()))
					resourceObj.setDeviceSource(child.getTextContent());
				
				else if("transcriptionchain".equals(child.getLocalName()))
					resourceObj.getChains().add(getTranscriptionChainObject((Element) child));

				else if("transcriptionsummary".equals(child.getLocalName()))
					resourceObj.getSummaries().add(getTranscriptionSummaryObject((Element) child));

				else if("transcriptiondata".equals(child.getLocalName()))
					resourceObj.getData().add(getTranscriptionDataObject((Element) child));
			}
		}
	}

	/**
	 * Legge la trascrizione (o la digitalizzazione) per un gruppo AUDIO
	 * 
	 * @param transcriptionNode nodo di trascrizione (o digitalizzazione)
	 * @param audioGroupObj oggetto gruppo AUDIO
	 */
	private static void readAudioGroupTranscriptionObject(Element transcriptionNode, 
			JsonMagEditorAudioGroup audioGroupObj) {
		
		NodeList transcriptionChildren = transcriptionNode.getChildNodes();
		
		for(int i = 0; i < transcriptionChildren.getLength(); i++) {
			Node child = transcriptionChildren.item(i);
			
			if(child.getNodeType() == Document.ELEMENT_NODE) {
				if("sourcetype".equals(child.getLocalName()))
					audioGroupObj.setSourceType(child.getTextContent());
				
				else if("transcriptionagency".equals(child.getLocalName()))
					audioGroupObj.setTranscriptionAgency(child.getTextContent());

				else if("transcriptiondate".equals(child.getLocalName()))
					audioGroupObj.setTranscriptionDate(child.getTextContent());

				else if("devicesource".equals(child.getLocalName()))
					audioGroupObj.setDeviceSource(child.getTextContent());
				
				else if("transcriptionchain".equals(child.getLocalName()))
					audioGroupObj.getChains().add(getTranscriptionChainObject((Element) child));

				else if("transcriptionsummary".equals(child.getLocalName()))
					audioGroupObj.getSummaries().add(getTranscriptionSummaryObject((Element) child));

				else if("transcriptiondata".equals(child.getLocalName()))
					audioGroupObj.getData().add(getTranscriptionDataObject((Element) child));
			}
		}
	}

	/**
	 * Legge la trascrizione (o la digitalizzazione) per un gruppo AUDIO
	 * 
	 * @param transcriptionNode nodo di trascrizione (o digitalizzazione)
	 * @param videoGroupObj oggetto gruppo VIDEO
	 */
	private static void readVideoGroupTranscriptionObject(Element transcriptionNode, 
			JsonMagEditorVideoGroup videoGroupObj) {
		
		NodeList transcriptionChildren = transcriptionNode.getChildNodes();
		
		for(int i = 0; i < transcriptionChildren.getLength(); i++) {
			Node child = transcriptionChildren.item(i);
			
			if(child.getNodeType() == Document.ELEMENT_NODE) {
				if("sourcetype".equals(child.getLocalName()))
					videoGroupObj.setSourceType(child.getTextContent());
				
				else if("transcriptionagency".equals(child.getLocalName()))
					videoGroupObj.setTranscriptionAgency(child.getTextContent());

				else if("transcriptiondate".equals(child.getLocalName()))
					videoGroupObj.setTranscriptionDate(child.getTextContent());

				else if("devicesource".equals(child.getLocalName()))
					videoGroupObj.setDeviceSource(child.getTextContent());
				
				else if("transcriptionchain".equals(child.getLocalName()))
					videoGroupObj.getChains().add(getTranscriptionChainObject((Element) child));

				else if("transcriptionsummary".equals(child.getLocalName()))
					videoGroupObj.getSummaries().add(getTranscriptionSummaryObject((Element) child));

				else if("transcriptiondata".equals(child.getLocalName()))
					videoGroupObj.getData().add(getTranscriptionDataObject((Element) child));
			}
		}
	}

	/**
	 * Ricostruisce l'oggetto JSON del transcription chain
	 * 
	 * @param chainNode nodeo del transcription chain
	 * @return JsonMagEditorTranscriptionChain oggetto JSON
	 */
	private static JsonMagEditorTranscriptionChain getTranscriptionChainObject(Element chainNode) {
		JsonMagEditorTranscriptionChain chain = new JsonMagEditorTranscriptionChain();
		NodeList chainChildren = chainNode.getChildNodes();
		
		for(int i = 0; i < chainChildren.getLength(); i++) {
			Node child = chainChildren.item(i);
			
			if(child.getNodeType() == Document.ELEMENT_NODE) {
				if("device_description".equals(child.getLocalName())) {
					Element deviceDescriptionNode = (Element) child;
					
					if(deviceDescriptionNode.hasAttribute("Type"))
						chain.setType(deviceDescriptionNode.getAttribute("Type"));
					
					if(deviceDescriptionNode.hasAttribute("Unique_identifier"))
						chain.setUniqueIdentifier(deviceDescriptionNode.getAttribute("Unique_identifier"));
					
					if(deviceDescriptionNode.hasAttribute("Comments"))
						chain.setComments(deviceDescriptionNode.getAttribute("Comments"));
				}
				
				else if("device_model".equals(child.getLocalName())) {
					Element deviceModelNode = (Element) child;
					
					if(deviceModelNode.hasAttribute("Model"))
						chain.setModel(deviceModelNode.getAttribute("Model"));
					
					if(deviceModelNode.hasAttribute("Serial_Number"))
						chain.setSerialNumber(deviceModelNode.getAttribute("Serial_Number"));
				}
				
				else if("device_manufacturer".equals(child.getLocalName()))
					chain.setDeviceManufacturer(child.getTextContent());
				
				else if("capture_software".equals(child.getLocalName()))
					chain.setCaptureSoftware(child.getTextContent());

				else if("device_settings".equals(child.getLocalName()))
					chain.setDeviceSettings(child.getTextContent());
			}
		}
		
		return chain;
	}
	
	/**
	 * Ricostruisce l'oggetto JSON del transcription summary
	 * 
	 * @param chainNode nodeo del transcription summary
	 * @return JsonMagEditorTranscriptionSummary oggetto JSON
	 */
	private static JsonMagEditorTranscriptionSummary getTranscriptionSummaryObject(Element summaryNode) {
		JsonMagEditorTranscriptionSummary summary = new JsonMagEditorTranscriptionSummary();
		NodeList summaryChildren = summaryNode.getChildNodes();
		
		for(int i = 0; i < summaryChildren.getLength(); i++) {
			Node child = summaryChildren.item(i);
			
			if(child.getNodeType() == Document.ELEMENT_NODE) {
				if("grouping".equals(child.getLocalName()))
					summary.setGrouping(child.getTextContent());
				
				else if("data_description".equals(child.getLocalName()))
					summary.setDataDescription(child.getTextContent());
				
				else if("data_unit".equals(child.getLocalName()))
					summary.setDataUnit(child.getTextContent());
				
				else if("data_value".equals(child.getLocalName()))
					summary.setDataValue(child.getTextContent());
				
				else if("transcriptionsummary".equals(child.getLocalName()))
					summary.getSummaries().add(getTranscriptionSummaryObject((Element) child));
			}
		}
		
		return summary;
	}

	/**
	 * Ricostruisce l'oggetto JSON del transcription data
	 * 
	 * @param chainNode nodeo del transcription data
	 * @return JsonMagEditorTranscriptionData oggetto JSON
	 */
	private static JsonMagEditorTranscriptionData getTranscriptionDataObject(Element dataNode) {
		JsonMagEditorTranscriptionData data = new JsonMagEditorTranscriptionData();
		NodeList dataChildren = dataNode.getChildNodes();
		
		for(int i = 0; i < dataChildren.getLength(); i++) {
			Node child = dataChildren.item(i);
			
			if(child.getNodeType() == Document.ELEMENT_NODE) {
				if("grouping".equals(child.getLocalName()))
					data.setGrouping(child.getTextContent());
				
				else if("data_description".equals(child.getLocalName()))
					data.setDataDescription(child.getTextContent());
				
				else if("data_unit".equals(child.getLocalName()))
					data.setDataUnit(child.getTextContent());
				
				else if("data_value".equals(child.getLocalName()))
					data.setDataValue(child.getTextContent());
				
				else if("transcriptionsummary".equals(child.getLocalName()))
					data.getData().add(getTranscriptionDataObject((Element) child));
				
				else if("interval".equals(child.getLocalName())) {
					Element intervalNode = (Element) child;
					
					if(intervalNode.hasAttribute("start"))
						data.setIntervalStart(intervalNode.getAttribute("start"));
					
					if(intervalNode.hasAttribute("stop"))
						data.setIntervalStop(intervalNode.getAttribute("stop"));
				}
			}
		}
		
		return data;
	}

	/**
	 * Crea il nodo format per IMG per il documento scelto
	 * 
	 * @param document documento MAG
	 * @param name estensione formato
	 * @param mime mime formato
	 * @param compression compressione formato
	 * @return Node nodo creato
	 */
	private static Node createImageFormatNode(Document document, String name, String mime, String compression) {
		boolean hasName = name != null && !name.isEmpty();
		boolean hasMime = mime != null && !mime.isEmpty();
		boolean hasCompression = compression != null && !compression.isEmpty();
		
		if(hasName || hasMime || hasCompression) {
			Element formatNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "format");
			
			if(hasName)
				formatNode.appendChild(createSimpleTextElement(document, "http://www.niso.org/pdfs/DataDict.pdf", "name", name));
			
			if(hasMime)
				formatNode.appendChild(createSimpleTextElement(document, "http://www.niso.org/pdfs/DataDict.pdf", "mime", mime));
			
			if(hasCompression)
				formatNode.appendChild(createSimpleTextElement(document, "http://www.niso.org/pdfs/DataDict.pdf", "compression", compression));
			
			return formatNode;
		}
		
		return null;
	}
	
	/**
	 * Crea il nodo scanning per il documento scelto
	 * 
	 * @param document documento MAG
	 * @param sourceType tipo sorgente
	 * @param scanningAgency agenzia
	 * @param deviceSource dispositivo
	 * @param scannerManufacturer produttore scanner
	 * @param scannerModel madello scanner
	 * @param captureSoftware software di cattura
	 * @return Node nodo creato
	 */
	private static Node createImageScanningNode(Document document, String sourceType, String scanningAgency, 
			String deviceSource, String scannerManufacturer, String scannerModel, String captureSoftware) {
		
		boolean hasSourceType = sourceType != null && !sourceType.isEmpty();
		boolean hasScanningAgency = scanningAgency != null && !scanningAgency.isEmpty();
		boolean hasDeviceSource = deviceSource != null && !deviceSource.isEmpty();
		boolean hasScannerManufacturer = scannerManufacturer != null && !scannerManufacturer.isEmpty();
		boolean hasScannerModel = scannerModel != null && !scannerModel.isEmpty();
		boolean hasCaptureSoftware = captureSoftware != null && !captureSoftware.isEmpty();
		
		if(hasSourceType || hasScanningAgency || hasDeviceSource || hasScannerManufacturer || hasScannerModel || hasCaptureSoftware) {
			Element scanningNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "scanning");
			
			if(hasSourceType)
				scanningNode.appendChild(createSimpleTextElement(document, "http://www.niso.org/pdfs/DataDict.pdf", "sourcetype", sourceType));

			if(hasScanningAgency)
				scanningNode.appendChild(createSimpleTextElement(document, "http://www.niso.org/pdfs/DataDict.pdf", "scanningagency", scanningAgency));

			if(hasDeviceSource)
				scanningNode.appendChild(createSimpleTextElement(document, "http://www.niso.org/pdfs/DataDict.pdf", "devicesource", deviceSource));
			
			
			if(hasScannerManufacturer || hasScannerModel || hasCaptureSoftware) {
				Element scanningSystemNode = createSimpleElement(document, "http://www.niso.org/pdfs/DataDict.pdf", "scanningsystem");
				
				if(hasScannerManufacturer) {
					scanningSystemNode.appendChild(createSimpleTextElement(document, 
							"http://www.niso.org/pdfs/DataDict.pdf", "scanner_manufacturer", 
							scannerManufacturer));
				}
				
				if(hasScannerModel) {
					scanningSystemNode.appendChild(createSimpleTextElement(document,
							"http://www.niso.org/pdfs/DataDict.pdf", "scanner_model", 
							scannerModel));
				}
				
				if(hasCaptureSoftware) {
					scanningSystemNode.appendChild(createSimpleTextElement(document, 
							"http://www.niso.org/pdfs/DataDict.pdf", "capture_software", 
							captureSoftware));
				}
				
				scanningNode.appendChild(scanningSystemNode);
			}
			
			return scanningNode;
		}
		
		return null;
	}
	
	/**
	 * Crea il nodo image_dimensions per il documento scelto
	 * 
	 * @param document documento MAG
	 * @param height altezza immagine
	 * @param width larghezza immagine
	 * @param x posizione x
	 * @param y posizione y
	 * @return Node nodo creato
	 */
	private static Node createImageDimensions(Document document, String height, String width, String x, String y) {
		Element imageDimNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "image_dimensions");
		
		if(height != null && !height.isEmpty()) {
			imageDimNode.appendChild(createSimpleTextElement(document, 
					"http://www.niso.org/pdfs/DataDict.pdf", "imagelength", height));
		}

		if(width != null && !width.isEmpty()) {
			imageDimNode.appendChild(createSimpleTextElement(document, 
					"http://www.niso.org/pdfs/DataDict.pdf", "imagewidth", width));
		}

		if(x != null && !x.isEmpty()) {
			imageDimNode.appendChild(createSimpleTextElement(document, 
					"http://www.niso.org/pdfs/DataDict.pdf", "source_xdimension", x));
		}

		if(y != null && !y.isEmpty()) {
			imageDimNode.appendChild(createSimpleTextElement(document, 
					"http://www.niso.org/pdfs/DataDict.pdf", "source_ydimension", y));
		}
		
		return imageDimNode;
	}
	
	/**
	 * Crea il nodo image_metrics per il documento scelto
	 * 
	 * @param document documento MAG
	 * @param samplingFrequencyUnit unità della frequenza di campionamento
	 * @param samplingFrequencyPlane piano della frequenza di campionamento
	 * @param xSamplingFrequency frequenza di campionamento x
	 * @param ySamplingFrequency frequenza di campionamento y
	 * @param photometricInterpretation interpretazione fotometrica (modello colori)
	 * @param bitPerSample bit per campione
	 * @return Node nodo creato
	 */
	private static Node createImageMetricsNode(Document document, String samplingFrequencyUnit, String samplingFrequencyPlane,
			String xSamplingFrequency, String ySamplingFrequency, String photometricInterpretation, String bitPerSample) {
		
		boolean hasSamplingFrequencyUnit = samplingFrequencyUnit != null && !samplingFrequencyUnit.isEmpty();
		boolean hasSamplingFrequencyPlane = samplingFrequencyPlane != null && !samplingFrequencyPlane.isEmpty();
		boolean hasXSamplingFrequency = xSamplingFrequency != null && !xSamplingFrequency.isEmpty();
		boolean hasYSamplingFrequency = ySamplingFrequency != null && !ySamplingFrequency.isEmpty();
		boolean hasPhotometricInterpretation = photometricInterpretation != null && !photometricInterpretation.isEmpty();
		boolean hasBitPerSample = bitPerSample != null && !bitPerSample.isEmpty();
		
		if(hasSamplingFrequencyUnit || hasSamplingFrequencyPlane || hasXSamplingFrequency || 
				hasYSamplingFrequency || hasPhotometricInterpretation || hasBitPerSample) {
			
			Element imageMetricsNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "image_metrics");
			
			if(hasSamplingFrequencyUnit) {
				imageMetricsNode.appendChild(createSimpleTextElement(document, 
						"http://www.niso.org/pdfs/DataDict.pdf", "samplingfrequencyunit", samplingFrequencyUnit));
			}

			if(hasSamplingFrequencyPlane) {
				imageMetricsNode.appendChild(createSimpleTextElement(document, 
						"http://www.niso.org/pdfs/DataDict.pdf", "samplingfrequencyplane", samplingFrequencyPlane));
			}

			if(hasXSamplingFrequency) {
				imageMetricsNode.appendChild(createSimpleTextElement(document, 
						"http://www.niso.org/pdfs/DataDict.pdf", "xsamplingfrequency", xSamplingFrequency));
			}

			if(hasYSamplingFrequency) {
				imageMetricsNode.appendChild(createSimpleTextElement(document, 
						"http://www.niso.org/pdfs/DataDict.pdf", "ysamplingfrequency", ySamplingFrequency));
			}

			if(hasPhotometricInterpretation) {
				imageMetricsNode.appendChild(createSimpleTextElement(document, 
						"http://www.niso.org/pdfs/DataDict.pdf", "photometricinterpretation", photometricInterpretation));
			}

			if(hasBitPerSample) {
				imageMetricsNode.appendChild(createSimpleTextElement(document, 
						"http://www.niso.org/pdfs/DataDict.pdf", "bitpersample", bitPerSample));
			}
			
			return imageMetricsNode;
		}
		
		return null;
	}
	
	/**
	 * Crea il nodo audio_metrics per il documento scelto
	 * 
	 * @param document documento MAG
	 * @param samplingFrequency frquenza campionamento
	 * @param bitPerSample bit per campionamento
	 * @param bitRate bitrate
	 * @return Node nodo creato
	 */
	private static Node createAudioMetrics(Document document, String samplingFrequency, String bitPerSample, String bitRate) {
		boolean hasSamplingFrequency = samplingFrequency != null && !samplingFrequency.isEmpty();
		boolean hasBitPerSample = bitPerSample != null && !bitPerSample.isEmpty();
		boolean hasBitRate = bitRate != null && !bitRate.isEmpty();
		
		if(hasSamplingFrequency || hasBitPerSample || hasBitRate) {
			Element audioMetricsNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "audio_metrics");
			
			if(hasSamplingFrequency) {
				audioMetricsNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "samplingfrequency", samplingFrequency));
			}
	
			if(hasBitPerSample) {
				audioMetricsNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "bitpersample", bitPerSample));
			}
	
			if(hasBitRate) {
				audioMetricsNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "bitrate", bitRate));
			}
			
			return audioMetricsNode;
		}
		
		return null;
	}
	
	/**
	 * Crea il nodo format per AUDIO per il documento scelto
	 * 
	 * @param document documento MAG
	 * @param name estensione formato
	 * @param mime mime formato
	 * @param compression compressione formato
	 * @param channelConfiguration canale di configurazione
	 * @return Node nodo creato
	 */
	private static Node createAudioFormatNode(Document document, String name, String mime, String compression,
			String channelConfiguration) {
		
		boolean hasName = name != null && !name.isEmpty();
		boolean hasMime = mime != null && !mime.isEmpty();
		boolean hasCompression = compression != null && !compression.isEmpty();
		boolean hasChannelConfiguration = channelConfiguration != null && !channelConfiguration.isEmpty();
		
		if(hasName || hasMime || hasCompression || hasChannelConfiguration) {
			Element formatNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "format");
			
			if(hasName) {
				formatNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "name", name));
			}
			
			if(hasMime) {
				formatNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "mime", mime));
			}
			
			if(hasCompression) {
				formatNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "compression", compression));
			}
	
			if(hasChannelConfiguration) {
				formatNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "channel_configuration", channelConfiguration));
			}
			
			return formatNode;
		}
		
		return null;
	}
	
	/**
	 * Crea il nodo vieo_metrics per il documento scelto
	 * 
	 * @param document documento MAG
	 * @param videoSize dimensioni video
	 * @param aspectRatio rapporto video
	 * @param frameRate frame rate
	 * @return
	 */
	private static Node createVideoMetricsNode(Document document, String videoSize, String aspectRatio, String frameRate) {
		boolean hasVideoSize = videoSize != null && !videoSize.isEmpty();
		boolean hasAspectRatio = aspectRatio != null && !aspectRatio.isEmpty();
		boolean hasFrameRate = frameRate != null && !frameRate.isEmpty();
		
		if(hasVideoSize || hasAspectRatio || hasFrameRate) {
			Element videoMetricsNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "video_metrics");
			
			if(hasVideoSize) {
				videoMetricsNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "videosize", videoSize));
			}
	
			if(hasAspectRatio) {
				videoMetricsNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "aspectratio", aspectRatio));
			}
	
			if(hasFrameRate) {
				videoMetricsNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "framerate", frameRate));
			}
			
			return videoMetricsNode;
		}
		
		return null;
	}
	
	/**
	 * Crea il nodo format per risorse digitali di tipo OCR/DOC per il documento scelto
	 * 
	 * @param document documento MAG
	 * @param name estensione formato
	 * @param mime mime formato
	 * @param compression compressione formato
	 * @return Node nodo creato
	 */
	private static Node createDocFormatNode(Document document, String name, String mime, String compression) {
		return createImageFormatNode(document, name, mime, compression);
	}
	
	/**
	 * Crea il nodo format per VIDEO per il documento scelto
	 * 
	 * @param document documento MAG
	 * @param name estensione formato
	 * @param mime mime formato
	 * @param videoFormat formato video
	 * @param encode codifica
	 * @param streamType tipo di stream
	 * @param codec codec
	 * @return Node nodo creato
	 */
	private static Node createVideoFormatNode(Document document, String name, String mime, String videoFormat,
			String encode, String streamType, String codec) {
		
		boolean hasName = name != null && !name.isEmpty();
		boolean hasMime = mime != null && !mime.isEmpty();
		boolean hasVideoformat = videoFormat != null & !videoFormat.isEmpty();
		boolean hasEncode = encode != null && !encode.isEmpty();
		boolean hasStreamType = streamType != null && !streamType.isEmpty();
		boolean hasCodec = codec != null && !codec.isEmpty();

		if(hasName || hasMime || hasVideoformat || hasEncode || hasStreamType || hasCodec) {
			Element formatNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "format");
			
			if(hasName) {
				formatNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "name", name));
			}
			
			if(hasMime) {
				formatNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "mime", mime));
			}
			
			if(hasVideoformat) {
				formatNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "videoformat", videoFormat));
			}
	
			if(hasEncode) {
				formatNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "encode", encode));
			}
	
			if(hasStreamType) {
				formatNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "streamtype", streamType));
			}
	
			if(hasCodec) {
				formatNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "codec", codec));
			}
			
			return formatNode;
		}
		
		return null;
	}
	
	/**
	 * Popola gli elementi XML per la sezione STRU del documento scelto
	 * 
	 * @param document documento MAG
	 * @param parent nodo padre a cui aggiungere i figli di tipo STRU
	 * @param struObjects lista oggetti JSON contenente i metadati per la sezione STRU del ramo di indice scelto
	 * @param elements elementi di livello 0 nella gerarchia (nodi figli di metadigit) 
	 * @param mag oggetto di indicizzazione
	 */
	private static void populateStru(Document document, Element parent, List<JsonMagEditorStru> struObjects, 
			List<Element> elements, Mag mag) {
		
		if(struObjects != null) {
			mag.setStruNomenclatures(new ArrayList<String>());
			mag.setElementNomenclatures(new ArrayList<String>());
			
			for(JsonMagEditorStru struObj : struObjects) {
				Element struNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "stru");
				
				if(struObj.getStart() != null && !struObj.getStart().isEmpty())
					struNode.setAttribute("start", struObj.getStart());

				if(struObj.getStop() != null && !struObj.getStop().isEmpty())
					struNode.setAttribute("stop", struObj.getStop());
				
				if(struObj.getSequenceNumber() != null && !struObj.getSequenceNumber().isEmpty())
					struNode.appendChild(createSimpleTextElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "sequence_number", struObj.getSequenceNumber()));
				
				if(struObj.getNomenclature() != null && !struObj.getNomenclature().isEmpty()) {
					struNode.appendChild(createSimpleTextElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", 
							"nomenclature", struObj.getNomenclature()));
					
					mag.getStruNomenclatures().add(struObj.getNomenclature());
				}
				
				if(struObj.getElement() != null) {
					for(JsonMagEditorStruElement elementObj : struObj.getElement()) {
						Element elementNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "element");
						
						if(elementObj.getNomenclature() != null && !elementObj.getNomenclature().isEmpty()) {
							elementNode.appendChild(createSimpleTextElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", 
									"nomenclature", elementObj.getNomenclature()));
							
							mag.getElementNomenclatures().add(elementObj.getNomenclature());
						}
						
						if(elementObj.getFile() != null && !elementObj.getFile().isEmpty()) {
							Element fileNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "file");
							fileNode.setAttributeNS("http://www.w3.org/TR/xlink", document.getDocumentElement().lookupPrefix("http://www.w3.org/TR/xlink") +":href", elementObj.getFile());
							elementNode.appendChild(fileNode);
						}
						
						if(elementObj.getIdentifier() != null && !elementObj.getIdentifier().isEmpty())
							elementNode.appendChild(createSimpleTextElement(document, "http://purl.org/dc/elements/1.1/", "identifier", elementObj.getIdentifier()));
						
						if(elementObj.getPiece() != null && !elementObj.getPiece().isEmpty()) {
							Element pieceNode = createSimpleElement(document, null, "piece");
							JsonMagEditorPiece pieceObj = elementObj.getPiece().get(0);
							
							if(pieceObj.getYear() != null && !pieceObj.getYear().isEmpty()) {
								pieceNode.appendChild(createSimpleTextElement(document, null, 
										"year", pieceObj.getYear()));
							}
							
							if(pieceObj.getIssue() != null && !pieceObj.getIssue().isEmpty()) {
								pieceNode.appendChild(createSimpleTextElement(document, null, 
										"issue", pieceObj.getIssue()));
							}
							
							if(pieceObj.getStpiecePer() != null && !pieceObj.getStpiecePer().isEmpty()) {
								pieceNode.appendChild(createSimpleTextElement(document, null, 
										"stpiece_per", pieceObj.getStpiecePer()));
							}
							
							if(pieceObj.getPartNumber() != null && !pieceObj.getPartNumber().isEmpty()) {
								pieceNode.appendChild(createSimpleTextElement(document, null, 
										"part_number", pieceObj.getPartNumber()));
							}
							
							if(pieceObj.getPartName() != null && !pieceObj.getPartName().isEmpty()) {
								pieceNode.appendChild(createSimpleTextElement(document, null, 
										"part_name", pieceObj.getPartName()));
							}
							
							if(pieceObj.getStpieceVol() != null && !pieceObj.getStpieceVol().isEmpty()) {
								pieceNode.appendChild(createSimpleTextElement(document, null, 
										"stpiece_vol", pieceObj.getStpieceVol()));
							}
							
							elementNode.appendChild(pieceNode);
						}
					
						if(elementObj.getResourceType() != null && !elementObj.getResourceType().isEmpty())
							elementNode.appendChild(createSimpleTextElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "resource", elementObj.getResourceType()));
						
						if(elementObj.getStart() != null && !elementObj.getStart().isEmpty()) {
							Element startNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "start");
							startNode.setAttribute("sequence_number", elementObj.getStart());
							elementNode.appendChild(startNode);
						}
						
						if(elementObj.getStop() != null && !elementObj.getStop().isEmpty()) {
							Element stopNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "stop");
							stopNode.setAttribute("sequence_number", elementObj.getStop());
							elementNode.appendChild(stopNode);
						}
						
						struNode.appendChild(elementNode);
					}
				}
				
				populateStru(document, struNode, struObj.getStru(), elements, mag);
				
				if(!"metadigit".equals(parent.getLocalName()))
					parent.appendChild(struNode);
				
				else
					elements.add(struNode);
			}
		}
	}
	
	/**
	 * Crea un semplice nodo con testo
	 * 
	 * @param document documento di appartenenza del nuovo nodo
	 * @param namespace namespace del nodo da creare
	 * @param tagName nodo da creare
	 * @param text testo del nodo
	 * @return Element nodo
	 */
	private static Element createSimpleTextElement(Document document, String namespace, String tagName, String text) {
		Element node = null;
		
		if(namespace == null)
			node = document.createElement(tagName);
		
		else {
			node = document.createElementNS(namespace, tagName);
			if(haveToSetPrefix(document,node,namespace))
				node.setPrefix(document.getDocumentElement().lookupPrefix(namespace));
		}
		
		node.setTextContent(text);
		return node;
	}
	

	protected static boolean haveToSetPrefix(Document document, Element node, String namespace){
		return (!(document.getDocumentElement().getNamespaceURI().equals(namespace) &&
				((document.getDocumentElement().getPrefix()==null && node.getPrefix()==null)
						|| (document.getDocumentElement().getPrefix().equals(node.getPrefix()))
				)	));

	}
	/**
	 * Crea un semplice nodo
	 * 
	 * @param document documento di appartenenza del nuovo nodo
	 * @param namespace namespace del nodo da creare
	 * @param tagName nodo da creare
	 * @return Element nodo
	 */
	private static Element createSimpleElement(Document document, String namespace, String tagName) {
		Element node = null;
		
		if(namespace == null)
			node = document.createElement(tagName);
		
		else {
			node = document.createElementNS(namespace, tagName);
			if(haveToSetPrefix(document,node,namespace))
				node.setPrefix(document.getDocumentElement().lookupPrefix(namespace));
		}
		
		return node;
	}

	/**
	 * Costruisce il nodo transcription (o digitisation) a partire dall'oggetto JSON gruppo audio
	 * 
	 * @param name nome del nodo (transcription/digitisation)
	 * @param audioGroupObject oggetto JSON gruppo audio
	 * @param document documento MAG
	 * @return Element nodo transcription (o digitisation)
	 */
	private static Element createAudioGroupTranscriptionNode(String name, 
			JsonMagEditorAudioGroup audioGroupObject, Document document) {
		
		boolean hasSourceType = audioGroupObject.getSourceType() != null && 
				!audioGroupObject.getSourceType().isEmpty();
		
		boolean hasTranscriptionAgency = audioGroupObject.getTranscriptionAgency() != null && 
				!audioGroupObject.getTranscriptionAgency().isEmpty();
		
		boolean hasTranscriptionDate = audioGroupObject.getTranscriptionDate() != null && 
				!audioGroupObject.getTranscriptionDate().isEmpty();
		
		boolean hasDeviceSource = audioGroupObject.getDeviceSource() != null && 
				!audioGroupObject.getDeviceSource().isEmpty();
		
		boolean hasChains = audioGroupObject.getChains() != null && !audioGroupObject.
				getChains().isEmpty();
		
		boolean hasSummaries = audioGroupObject.getSummaries() != null && !audioGroupObject.
				getSummaries().isEmpty();
		
		boolean hasData = audioGroupObject.getData() != null && !audioGroupObject.
				getData().isEmpty();
		
		
		if(hasSourceType || hasTranscriptionAgency || hasTranscriptionDate || hasDeviceSource || 
				hasChains || hasSummaries || hasData) {
			
			Element transcriptionNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", name);

			if(hasSourceType) {
				transcriptionNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "sourcetype", 
						audioGroupObject.getSourceType()));
			}
			
			if(hasTranscriptionAgency) {
				transcriptionNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "transcriptionagency", 
						audioGroupObject.getTranscriptionAgency()));
			}
			
			if(hasTranscriptionDate) {
				transcriptionNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "transcriptiondate", 
						audioGroupObject.getTranscriptionDate()));
			}
			
			if(hasDeviceSource) {
				transcriptionNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "devicesource", 
						audioGroupObject.getDeviceSource()));
			}

			for(JsonMagEditorTranscriptionChain chainObject : audioGroupObject.getChains())
				transcriptionNode.appendChild(createTranscriptionChainNode(chainObject, document));

			for(JsonMagEditorTranscriptionSummary summaryObject : audioGroupObject.getSummaries())
				transcriptionNode.appendChild(createTranscriptionSummaryNode(summaryObject, document));

			for(JsonMagEditorTranscriptionData dataObject : audioGroupObject.getData())
				transcriptionNode.appendChild(createTranscriptionDataNode(dataObject, document));
			
			return transcriptionNode;
		}
		
		return null;
	}

	/**
	 * Costruisce il nodo transcription (o digitisation) a partire dall'oggetto JSON gruppo video
	 * 
	 * @param name nome del nodo (transcription/digitisation)
	 * @param videoGroupObject oggetto JSON gruppo video
	 * @param document documento MAG
	 * @return Element nodo transcription (o digitisation)
	 */
	private static Element createVideoGroupTranscriptionNode(String name, 
			JsonMagEditorVideoGroup videoGroupObject, Document document) {
		
		boolean hasSourceType = videoGroupObject.getSourceType() != null && 
				!videoGroupObject.getSourceType().isEmpty();
		
		boolean hasTranscriptionAgency = videoGroupObject.getTranscriptionAgency() != null && 
				!videoGroupObject.getTranscriptionAgency().isEmpty();
		
		boolean hasTranscriptionDate = videoGroupObject.getTranscriptionDate() != null && 
				!videoGroupObject.getTranscriptionDate().isEmpty();
		
		boolean hasDeviceSource = videoGroupObject.getDeviceSource() != null && 
				!videoGroupObject.getDeviceSource().isEmpty();
		
		boolean hasChains = videoGroupObject.getChains() != null && !videoGroupObject.
				getChains().isEmpty();
		
		boolean hasSummaries = videoGroupObject.getSummaries() != null && !videoGroupObject.
				getSummaries().isEmpty();
		
		boolean hasData = videoGroupObject.getData() != null && !videoGroupObject.
				getData().isEmpty();
		
		
		if(hasSourceType || hasTranscriptionAgency || hasTranscriptionDate || hasDeviceSource || 
				hasChains || hasSummaries || hasData) {
			
			Element transcriptionNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", name);

			if(hasSourceType) {
				transcriptionNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "sourcetype", 
						videoGroupObject.getSourceType()));
			}
			
			if(hasTranscriptionAgency) {
				transcriptionNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "transcriptionagency", 
						videoGroupObject.getTranscriptionAgency()));
			}
			
			if(hasTranscriptionDate) {
				transcriptionNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "transcriptiondate", 
						videoGroupObject.getTranscriptionDate()));
			}
			
			if(hasDeviceSource) {
				transcriptionNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "devicesource", 
						videoGroupObject.getDeviceSource()));
			}

			for(JsonMagEditorTranscriptionChain chainObject : videoGroupObject.getChains())
				transcriptionNode.appendChild(createTranscriptionChainNode(chainObject, document));

			for(JsonMagEditorTranscriptionSummary summaryObject : videoGroupObject.getSummaries())
				transcriptionNode.appendChild(createTranscriptionSummaryNode(summaryObject, document));

			for(JsonMagEditorTranscriptionData dataObject : videoGroupObject.getData())
				transcriptionNode.appendChild(createTranscriptionDataNode(dataObject, document));
			
			return transcriptionNode;
		}
		
		return null;
	}

	/**
	 * Costruisce il nodo transcription (o digitisation) a partire dall'oggetto JSON risorsa digitale
	 * 
	 * @param name nome del nodo (transcription/digitisation)
	 * @param resourceObject oggetto JSON risorsa digitale
	 * @param document documento MAG
	 * @return Element nodo transcription (o digitisation)
	 */
	private static Element createResourceTranscriptionNode(String name, 
			JsonMagEditorUsageResource resourceObject, Document document) {
		
		boolean hasSourceType = resourceObject.getSourceType() != null && 
				!resourceObject.getSourceType().isEmpty();
		
		boolean hasTranscriptionAgency = resourceObject.getTranscriptionAgency() != null && 
				!resourceObject.getTranscriptionAgency().isEmpty();
		
		boolean hasTranscriptionDate = resourceObject.getTranscriptionDate() != null && 
				!resourceObject.getTranscriptionDate().isEmpty();
		
		boolean hasDeviceSource = resourceObject.getDeviceSource() != null && 
				!resourceObject.getDeviceSource().isEmpty();
		
		boolean hasChains = resourceObject.getChains() != null && !resourceObject.
				getChains().isEmpty();
		
		boolean hasSummaries = resourceObject.getSummaries() != null && !resourceObject.
				getSummaries().isEmpty();
		
		boolean hasData = resourceObject.getData() != null && !resourceObject.
				getData().isEmpty();
		
		
		if(hasSourceType || hasTranscriptionAgency || hasTranscriptionDate || hasDeviceSource || 
				hasChains || hasSummaries || hasData) {
			
			Element transcriptionNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", name);

			if(hasSourceType) {
				transcriptionNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "sourcetype", 
						resourceObject.getSourceType()));
			}
			
			if(hasTranscriptionAgency) {
				transcriptionNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "transcriptionagency", 
						resourceObject.getTranscriptionAgency()));
			}
			
			if(hasTranscriptionDate) {
				transcriptionNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "transcriptiondate", 
						resourceObject.getTranscriptionDate()));
			}
			
			if(hasDeviceSource) {
				transcriptionNode.appendChild(createSimpleTextElement(document, 
						"http://www.iccu.sbn.it/metaAG1.pdf", "devicesource", 
						resourceObject.getDeviceSource()));
			}

			for(JsonMagEditorTranscriptionChain chainObject : resourceObject.getChains())
				transcriptionNode.appendChild(createTranscriptionChainNode(chainObject, document));

			for(JsonMagEditorTranscriptionSummary summaryObject : resourceObject.getSummaries())
				transcriptionNode.appendChild(createTranscriptionSummaryNode(summaryObject, document));

			for(JsonMagEditorTranscriptionData dataObject : resourceObject.getData())
				transcriptionNode.appendChild(createTranscriptionDataNode(dataObject, document));
			
			return transcriptionNode;
		}
		
		return null;
	}

	/**
	 * Costruisce il nodo transcriptionchain a partire dall'oggetto JSON
	 * 
	 * @param chainObject oggetto JSON
	 * @param document documento MAG
	 * @return Element nodo transcriptionchain
	 */
	private static Element createTranscriptionChainNode(JsonMagEditorTranscriptionChain chainObject, Document document) {
		Element chainNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "transcriptionchain");
		boolean hasType = chainObject.getType() != null && !chainObject.getType().isEmpty();
		boolean hasUniqueIdentifier = chainObject.getUniqueIdentifier() != null && !chainObject.getUniqueIdentifier().isEmpty();
		boolean hasComments = chainObject.getComments() != null && !chainObject.getComments().isEmpty();
		
		if(hasType || hasUniqueIdentifier || hasComments) {
			Element descriptionNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "device_description");
			
			if(hasType)
				descriptionNode.setAttribute("Type", chainObject.getType());
			
			if(hasUniqueIdentifier)
				descriptionNode.setAttribute("Unique_identifier", chainObject.getUniqueIdentifier());
			
			if(hasComments)
				descriptionNode.setAttribute("Comments", chainObject.getComments());
			
			chainNode.appendChild(descriptionNode);
		}
		
		if(chainObject.getDeviceManufacturer() != null && !chainObject.getDeviceManufacturer().isEmpty()) {
			chainNode.appendChild(createSimpleTextElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", 
					"device_manufacturer", chainObject.getDeviceManufacturer()));
		}
		
		boolean hasModel = chainObject.getModel() != null && !chainObject.getModel().isEmpty();
		boolean hasSerialNumber = chainObject.getSerialNumber() != null && !chainObject.getSerialNumber().isEmpty();
		
		if(hasModel || hasSerialNumber) {
			Element modelNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "device_model");
			
			if(hasModel)
				modelNode.setAttribute("Model", chainObject.getModel());
			
			if(hasSerialNumber)
				modelNode.setAttribute("Serial_Number", chainObject.getSerialNumber());
			
			chainNode.appendChild(modelNode);
		}

		if(chainObject.getCaptureSoftware() != null && !chainObject.getCaptureSoftware().isEmpty()) {
			chainNode.appendChild(createSimpleTextElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", 
					"capture_software", chainObject.getCaptureSoftware()));
		}
		
		if(chainObject.getDeviceSettings() != null && !chainObject.getDeviceSettings().isEmpty()) {
			chainNode.appendChild(createSimpleTextElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", 
					"device_settings", chainObject.getDeviceSettings()));
		}
		
		return chainNode;
	}
	
	/**
	 * Costruisce il nodo transcriptionsummary a partire dall'oggetto JSON
	 * 
	 * @param summaryObject oggetto JSON
	 * @param document documento MAG
	 * @return Element nodo transcriptionsummary
	 */
	private static Element createTranscriptionSummaryNode(JsonMagEditorTranscriptionSummary summaryObject, Document document) {
		Element summaryNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "transcriptionsummary");
		
		if(summaryObject.getGrouping() != null && !summaryObject.getGrouping().isEmpty()) {
			summaryNode.appendChild(createSimpleTextElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", 
					"grouping", summaryObject.getGrouping()));
		}
		
		for(JsonMagEditorTranscriptionSummary summaryChild : summaryObject.getSummaries())
			summaryNode.appendChild(createTranscriptionSummaryNode(summaryChild, document));
		
		if(summaryObject.getDataDescription() != null && !summaryObject.getDataDescription().isEmpty()) {
			summaryNode.appendChild(createSimpleTextElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", 
					"data_description", summaryObject.getDataDescription()));
		}

		if(summaryObject.getDataUnit() != null && !summaryObject.getDataUnit().isEmpty()) {
			summaryNode.appendChild(createSimpleTextElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", 
					"data_unit", summaryObject.getDataUnit()));
		}
		
		if(summaryObject.getDataValue() != null && !summaryObject.getDataValue().isEmpty()) {
			summaryNode.appendChild(createSimpleTextElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", 
					"data_value", summaryObject.getDataValue()));
		}
		
		return summaryNode;
	}
	
	/**
	 * Costruisce il nodo transcriptiondata a partire dall'oggetto JSON
	 * 
	 * @param dataObject oggetto JSON
	 * @param document documento MAG
	 * @return Element nodo transcriptiondata
	 */
	private static Element createTranscriptionDataNode(JsonMagEditorTranscriptionData dataObject, Document document) {
		Element dataNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "transcriptiondata");
		
		if(dataObject.getGrouping() != null && !dataObject.getGrouping().isEmpty()) {
			dataNode.appendChild(createSimpleTextElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", 
					"grouping", dataObject.getGrouping()));
		}
		
		for(JsonMagEditorTranscriptionData dataChild : dataObject.getData())
			dataNode.appendChild(createTranscriptionDataNode(dataChild, document));
		
		if(dataObject.getDataDescription() != null && !dataObject.getDataDescription().isEmpty()) {
			dataNode.appendChild(createSimpleTextElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", 
					"data_description", dataObject.getDataDescription()));
		}

		if(dataObject.getDataUnit() != null && !dataObject.getDataUnit().isEmpty()) {
			dataNode.appendChild(createSimpleTextElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", 
					"data_unit", dataObject.getDataUnit()));
		}
		
		boolean hasStart = dataObject.getIntervalStart() != null && !dataObject.getIntervalStart().isEmpty();
		boolean hasStop = dataObject.getIntervalStop() != null && !dataObject.getIntervalStop().isEmpty();
		
		if(hasStart || hasStop) {
			Element intervalNode = createSimpleElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", "interval");
			
			if(hasStart)
				intervalNode.setAttribute("start", dataObject.getIntervalStart());
			
			if(hasStop)
				intervalNode.setAttribute("stop", dataObject.getIntervalStop());
			
			dataNode.appendChild(intervalNode);
		}
		
		if(dataObject.getDataValue() != null && !dataObject.getDataValue().isEmpty()) {
			dataNode.appendChild(createSimpleTextElement(document, "http://www.iccu.sbn.it/metaAG1.pdf", 
					"data_value", dataObject.getDataValue()));
		}
		
		return dataNode;
	}
	
	/**
	 * Aggiorna i prefissi namespace aggiungendo namespace di default o 
	 * eventualmente modificando quello esistente
	 * 
	 * @param document documento da aggiornare
	 */
	public static void updateNamespaces(Document document) {
		if(document == null)
			return;
		
		Element metadigit = document.getDocumentElement();
		
		if(metadigit == null)
			return;
		
		boolean hasDefaultNamespace = metadigit.
				hasAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns");
		
		// se non c'è viene aggiunto
		if(!hasDefaultNamespace) {
			metadigit.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, 
					"xmlns", "http://www.iccu.sbn.it/metaAG1.pdf");
		}
		
		// se c'è
		else {
			String defaultNamespace = metadigit.
					getAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, "xmlns");
			
			// se corrisponde a quello giusto non fare nulla
			if(defaultNamespace.equals("http://www.iccu.sbn.it/metaAG1.pdf"))
				return;
			
			metadigit.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, 
					"xmlns", "http://www.iccu.sbn.it/metaAG1.pdf");
			
			// cerca il prefix relativo al vecchio default
			String prefix = metadigit.lookupPrefix(defaultNamespace);
			
			// se non c'è ne crea uno automatico
			if(prefix == null) {
				prefix = "def";
				
				metadigit.setAttributeNS(XMLConstants.XMLNS_ATTRIBUTE_NS_URI, 
						"xmlns:" + prefix, defaultNamespace);
			}
			
			// aggiorna i nodi senza prefix aggiungendo il prefix
			NodeList allElements = document.getElementsByTagName("*");
			
			for(int i = 0; i < allElements.getLength(); i++) {
				Element elem = (Element) allElements.item(i);
				
				if(elem.getPrefix() == null || elem.getPrefix().isEmpty())
					elem.setPrefix(prefix);
			}
		}
	}
	
	/**
	 * Verifica i sequence number per eventuali spostamenti con buchi
	 * 
	 * @param resources risorse JSON
	 *//*
	public static void checkSequenceNumbers(List<JsonMagEditorResource> resources) {
		int maxOldSequenceNumber = Integer.MIN_VALUE;
		int maxNewSequenceNumber = Integer.MIN_VALUE;
		
		for(JsonMagEditorResource res : resources) {
			int oldSequenceNumber = 0;
			
			if(!res.getId().startsWith("new"))
				oldSequenceNumber = Integer.parseInt(res.getId());
			
			else
				oldSequenceNumber = Integer.parseInt(res.getSequenceNumber());
			
			if(maxOldSequenceNumber < oldSequenceNumber)
				maxOldSequenceNumber = oldSequenceNumber;
			
			int newSequenceNumber = Integer.parseInt(res.getSequenceNumber());
			
			if(maxNewSequenceNumber < newSequenceNumber)
				maxNewSequenceNumber = newSequenceNumber;
		}
		
		if(maxOldSequenceNumber == maxNewSequenceNumber)
			return;
		
		int diff = maxOldSequenceNumber - maxNewSequenceNumber;
		int size = resources.size();
		
		for(int i = 0; i < size; i++) {
			JsonMagEditorResource res = resources.get(i);
			int oldSequenceNumber = Integer.parseInt(res.getSequenceNumber());
			res.setSequenceNumber(new Integer(oldSequenceNumber + diff).toString());
		}
	}*/

}
