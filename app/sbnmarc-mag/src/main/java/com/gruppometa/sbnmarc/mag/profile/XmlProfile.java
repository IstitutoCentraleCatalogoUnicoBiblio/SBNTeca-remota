package com.gruppometa.sbnmarc.mag.profile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.gruppometa.sbnmarc.mag.mapping.MappingDefinition;
import com.gruppometa.sbnmarc.mag.object.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XmlProfile extends SbnProfile {
	protected String type;
	protected static Log logger = LogFactory.getLog(XmlProfile.class);
	protected String xmlFile = null;
	protected String filterId = null;
	protected String filterNature = null;
	protected String mapVersion = "1.0";

	public String getFilterNature() {
		return filterNature;
	}

	public void setFilterNature(String filterNature) {
		this.filterNature = filterNature;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getMapVersion(){
		return mapVersion;
	}
	public String getFilterId() {
		return filterId;
	}

	public void setFilterId(String filterId) {
		this.filterId = filterId;
	}


	String className = "/defaultProfile.xml";

	public XmlProfile(){
		
	}
			
	public XmlProfile(String className) {
		this.className = className;
	}
	
	/* (non-Javadoc)
	 * @see com.gruppometa.unimarc.profile.SbnProfile#addField(com.gruppometa.unimarc.object.OutItem, java.lang.String, java.lang.String)
	 */
	@Override
	protected Field addField(OutItem desc, String name, String qualifier) {
		String key = name.toLowerCase()+(qualifier!=null?( ":"+qualifier.toLowerCase()):"");
		if(fieldmaps.get(key)!=null
				){
			if(!fieldmaps.get(key).equals("delete")){
				String[] names = fieldmaps.get(name.toLowerCase()+":"+qualifier.toLowerCase()).split(":"); 
				return super.addField(desc, names[0], (names.length>1? names[1]:null));
			}
			else
				return null;
		}
		return super.addField(desc, name, qualifier);
	}
	
	
	/* (non-Javadoc)
	 * @see com.gruppometa.unimarc.profile.SbnProfile#makeNode(com.gruppometa.unimarc.object.OutItem, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void makeNode(OutItem desc, String destinationNode, String data,
			String qualifier) throws IllegalArgumentException,
			SecurityException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		if(qualifier==null)
			qualifier = "";
		if(fieldmaps.get(destinationNode.toLowerCase()+":"+qualifier.toLowerCase())!=null){
			String[] names = fieldmaps.get(destinationNode.toLowerCase()+":"+qualifier.toLowerCase()).split(":");
			if(!names[0].equals("delete"))
				super.makeNode(desc, names[0], data,(names.length>1? names[1]:null));
		}
		else
			super.makeNode(desc, destinationNode, data, qualifier);
	}


	protected String sep =":";
	protected HashMap<String,String> fieldmaps = new HashMap<String, String>();
	protected HashMap<String,String> fieldsHash = new HashMap<String, String>();

	
	
	@Override
	public boolean isFinished() {
		return getFilterId()!=null && hasAdded;
	}
	/* (non-Javadoc)
	 * @see com.gruppometa.unimarc.profile.SbnProfile#init()
	 */
	@Override
	public void init() {
		normalizer.setLanguageFieldName("lingua");
		fieldsHash.clear();
		hasAdded = false;
		try {
			DocumentBuilder builder;
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			// factory.setXIncludeAware(true); // non supportato
			// factory.setNamespaceAware( true );
			builder = factory.newDocumentBuilder();
            InputStream inputStream;
            String prefix = "file://";
            if(className.startsWith(prefix))
                inputStream = new FileInputStream(new File(className.substring(prefix.length())));
            else
                inputStream = XmlProfile.class.getResourceAsStream(className);
			Document doc = builder.parse(inputStream);
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodes = (NodeList) xPath.evaluate("/mappings/mapping", doc, XPathConstants.NODESET);
			List<MappingDefinition> mappings = new ArrayList<MappingDefinition>();
			makeXmlDefNode(nodes,mappings);
			NodeList nodes2 = (NodeList) xPath.evaluate("/mappings/include", doc, XPathConstants.NODESET);
			for (int i = 0; nodes2!=null && i < nodes2.getLength(); i++) {
				if(nodes2.item(i).getAttributes().getNamedItem("href")!=null){
					String xml = nodes2.item(i).getAttributes().getNamedItem("href").getNodeValue();
					Document doc2 = builder.parse(XmlProfile.class.getResourceAsStream(xml));
					NodeList nodes3 = (NodeList) xPath.evaluate("/mappings/mapping", doc2, XPathConstants.NODESET);
					makeXmlDefNode(nodes3,mappings);
				}
			}

			defs = (MappingDefinition[])mappings.toArray(new MappingDefinition[mappings.size()]);
			normalizer.initMaps(defs);
			
			nodes = (NodeList) xPath.evaluate("//version", doc, XPathConstants.NODESET);
			for (int i = 0; nodes!=null && i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				mapVersion = node.getTextContent();
			}
			nodes = (NodeList) xPath.evaluate("//fieldmap", doc, XPathConstants.NODESET);
			for (int i = 0; nodes!=null && i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				NamedNodeMap map = node.getAttributes();
				fieldmaps.put(map.getNamedItem("name").getNodeValue().toLowerCase(), map.getNamedItem("value").getNodeValue());
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
	}

	protected void makeXmlDefNode(NodeList nodes,List<MappingDefinition> mappings) throws TransformerException, XPathExpressionException {
		for (int i = 0; nodes!=null && i < nodes.getLength(); i++) {
			MappingDefinition def = makeMapDefinition(nodes.item(i),-1);
			if(def==null)
				continue;
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList subnodes = (NodeList) xPath.evaluate("mapping", nodes.item(i), XPathConstants.NODESET);
			List<MappingDefinition> subDefs = new ArrayList<MappingDefinition>();
			Map<String,MappingDefinition> subMap = new HashMap<String, MappingDefinition>();
			for (int j = 0; subnodes!=null && j < subnodes.getLength(); j++) {
				MappingDefinition defsub = makeMapDefinition(subnodes.item(j),j);
				if(def.isMultiple() || subMap.containsKey(defsub.getDestination())){
					defsub.setMultiple(true);
					if(subMap.containsKey(defsub.getDestination()))
						subMap.get(defsub.getDestination()).setMultiple(true);
				}
				subDefs.add(defsub);
				subMap.put(defsub.getDestination(), defsub);
			}
			if(subDefs.size()>0)
				def.setSubDefs(subDefs);
			mappings.add(def);
			fieldsHash.put(def.getDestination(), def.getMarcField());
		}
	}
	protected double parseDouble(String value){
		if(value.length()==0)
			return -1;
		if(value.contains("."))
			return Double.parseDouble(value);
		return Integer.parseInt(value);
	}

	protected MappingDefinition makeMapDefinition(Node node, int pos){
		NamedNodeMap map = node.getAttributes();
		if(map.getNamedItem("condType")!=null && type!=null){
			List<String> v = Arrays.asList(map.getNamedItem("condType").getNodeValue().split("\\|"));
			if(v.size()==1 && v.get(0).startsWith("!")){
				if(v.get(0).substring(1).equals(type))
					return null;
			}
			else if(!v.contains(type))
				return null;
		}
		//new MappingDefinition("210", new String[] { "a","e"}, "Publisher","PlaceString",false,true),
		String marcField = "";
		if(map.getNamedItem("marcField")!=null)
			marcField = map.getNamedItem("marcField").getNodeValue();
		String marcSections[] = ALL;
		String group = null; 
		if(map.getNamedItem("group")!=null && map.getNamedItem("group").getNodeValue().trim().length()>0)
			group = map.getNamedItem("group").getNodeValue();
		if(map.getNamedItem("marcSection")!=null)
			marcSections = map.getNamedItem("marcSection").getNodeValue().split("\\|");
		if(map.getNamedItem("destination")==null){
			String mess =  "No destination node for "+marcField;
			logger.error(mess);
			System.out.println(mess);
			return null;
		}
		String destination = map.getNamedItem("destination").getNodeValue();
		String qualifier = (map.getNamedItem("qualifier")!=null?map.getNamedItem("qualifier").getNodeValue():null);
		int posInit = map.getNamedItem("posInit")!=null?Integer.parseInt(map.getNamedItem("posInit").getNodeValue()):-1;
		int posEnd = map.getNamedItem("posEnd")!=null?Integer.parseInt(map.getNamedItem("posEnd").getNodeValue()):-1;
		double vistaEtichette = map.getNamedItem("vistaEtichette")!=null?
				parseDouble(map.getNamedItem("vistaEtichette").getNodeValue())
				:-1;
		double vistaIsbd = map.getNamedItem("vistaIsbd")!=null?parseDouble(map.getNamedItem("vistaIsbd").getNodeValue()):-1;
		boolean caseSensetive = false;
		boolean ifFirst = false;
		MappingDefinition def = new MappingDefinition(marcField, marcSections, destination, qualifier, caseSensetive, ifFirst);
        if(map.getNamedItem("ind")!=null)
            def.setInd(map.getNamedItem("ind").getNodeValue());
		if(map.getNamedItem("listType")!=null)
			def.setListType(map.getNamedItem("listType").getNodeValue());
		if(map.getNamedItem("condMarcSection")!=null)
			def.setCondMarcSection(map.getNamedItem("condMarcSection").getNodeValue());
		if(map.getNamedItem("condValue")!=null)
			def.setCondValue(map.getNamedItem("condValue").getNodeValue());
		if(map.getNamedItem("condValue2")!=null)
			def.setCondValue2(map.getNamedItem("condValue2").getNodeValue());
		if(map.getNamedItem("parent")!=null)
			def.setParent(map.getNamedItem("parent").getNodeValue());
		if(map.getNamedItem("parentLabel")!=null)
			def.setParentLabel(map.getNamedItem("parentLabel").getNodeValue());
		if(map.getNamedItem("type")!=null)
			def.setType(map.getNamedItem("type").getNodeValue());
		if(map.getNamedItem("excludeInFe")!=null &&  map.getNamedItem("excludeInFe").getNodeValue().equalsIgnoreCase("true"))
			def.setExcludeInFe(true);
		if(map.getNamedItem("cutZeros")!=null &&  map.getNamedItem("cutZeros").getNodeValue().equalsIgnoreCase("true"))
			def.setCutZeros(true);
		if(map.getNamedItem("docAttribute")!=null &&  map.getNamedItem("docAttribute").getNodeValue().equalsIgnoreCase("true"))
			def.setDocAttribute(true);
		if(map.getNamedItem("excludeFromSearchField")!=null &&  map.getNamedItem("excludeFromSearchField").getNodeValue().equalsIgnoreCase("true"))
			def.setExcludeFromSearchField(true);
		if(map.getNamedItem("hideLabel")!=null &&  map.getNamedItem("hideLabel").getNodeValue().equalsIgnoreCase("true"))
			def.setHideLabel(true);
		if(map.getNamedItem("rewrite")!=null &&  map.getNamedItem("rewrite").getNodeValue().equalsIgnoreCase("true"))
			def.setRewrite(true);
		if(map.getNamedItem("sortField")!=null &&  map.getNamedItem("sortField").getNodeValue().equalsIgnoreCase("true"))
			def.setSortField(true);
		if(map.getNamedItem("sortFieldName")!=null)
			def.setSortFieldName(map.getNamedItem("sortFieldName").getNodeValue());
		if(map.getNamedItem("rangeEnd")!=null)
			def.setRangeEnd(map.getNamedItem("rangeEnd").getNodeValue());
		if(map.getNamedItem("label")!=null)
			def.setLabel(map.getNamedItem("label").getNodeValue());
		if(map.getNamedItem("feLabel")!=null)
			def.setFeLabel(map.getNamedItem("feLabel").getNodeValue());
		if(map.getNamedItem("facetLabel")!=null)
			def.setFacetLabel(map.getNamedItem("facetLabel").getNodeValue());
		if(map.getNamedItem("searchType")!=null)
			def.setSearchType(map.getNamedItem("searchType").getNodeValue());
		if(map.getNamedItem("copyTo")!=null)
			def.setCopyTo(map.getNamedItem("copyTo").getNodeValue());
		if(map.getNamedItem("join")!=null)
			def.setJoin(map.getNamedItem("join").getNodeValue());
		if(map.getNamedItem("solrFieldname")!=null)
			def.setSolrFieldname(map.getNamedItem("solrFieldname").getNodeValue());
		if(map.getNamedItem("vocabulary")!=null)
			def.setVocabulary(map.getNamedItem("vocabulary").getNodeValue());
		if(map.getNamedItem("group2")!=null)
			def.setGroup2(map.getNamedItem("group2").getNodeValue());
		if(map.getNamedItem("separator")!=null)
			def.setSeparator(map.getNamedItem("separator").getNodeValue());
		if(map.getNamedItem("facet")!=null)
			def.setFacet(map.getNamedItem("facet").getNodeValue());
		if(map.getNamedItem("facetOrder")!=null )
			def.setFacetOrder(parseDouble(map.getNamedItem("facetOrder").getNodeValue()));
		if(map.getNamedItem("vistaShort")!=null )
			def.setVistaShort(parseDouble(map.getNamedItem("vistaShort").getNodeValue()));
		if(map.getNamedItem("boost")!=null )
			def.setBoost(parseDouble(map.getNamedItem("boost").getNodeValue()));
		if(map.getNamedItem("inverse")!=null)
			def.setInverse(map.getNamedItem("inverse").getNodeValue());
		if(map.getNamedItem("facets")!=null && map.getNamedItem("facets").getNodeValue().equalsIgnoreCase("true"))
			def.setFacets(true);
		if(map.getNamedItem("searchField")!=null && map.getNamedItem("searchField").getNodeValue().equalsIgnoreCase("true"))
			def.setSearchField(true);
		if(map.getNamedItem("startsWith")!=null && map.getNamedItem("startsWith").getNodeValue().equalsIgnoreCase("true"))
			def.setStartsWith(true);
		if(map.getNamedItem("fulltext")!=null && map.getNamedItem("fulltext").getNodeValue().equalsIgnoreCase("false"))
			def.setFulltext(false);
		if(map.getNamedItem("is4Fe")!=null && map.getNamedItem("is4Fe").getNodeValue().equalsIgnoreCase("true"))
			def.setIs4Fe(true);
		if(map.getNamedItem("multiple")!=null && map.getNamedItem("multiple").getNodeValue().equalsIgnoreCase("true"))
			def.setMultiple(true);
		if(map.getNamedItem("handler")!=null)
			def.setHandler(map.getNamedItem("handler").getNodeValue());
		if(map.getNamedItem("labelGroup")!=null)
			def.setLabelGroup(map.getNamedItem("labelGroup").getNodeValue());
		if(map.getNamedItem("forceHtml")!=null && map.getNamedItem("forceHtml").getNodeValue().equalsIgnoreCase("true"))
			def.setForceHtml(true);
		if(map.getNamedItem("feExclusions")!=null){
			def.setFeExclusions(Arrays.asList(map.getNamedItem("feExclusions").getNodeValue().split("\\|")));
		}
		def.setPosInit(posInit);
		def.setPosEnd(posEnd);
		def.setVistaEtichette(vistaEtichette);		
		def.setOrder(pos);
		def.setVistaIsbd(vistaIsbd);
		def.setGroup(group);
		return def;
	}
	public String getMarcFieldFromDestination(String destination){
		if(fieldsHash!=null){
			String ret =  fieldsHash.get(destination);
			for(String key: fieldsHash.keySet()){
				if(!key.equals(destination) && fieldsHash.get(key).equals(ret))
					return ret+"_"+destination;
			}
			return ret;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.gruppometa.unimarc.profile.SbnProfile#makeSpecialTwo(com.gruppometa.unimarc.object.OutItem, org.marc4j.marc.Record)
	 */
	@Override
	public void makeSpecialTwo(OutItem desc, Record record)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		/**
		 *  Richiesta 10-12-2014:
		 *  In “libri antichi” creare una condizione per cui se il campo unimarc 200 
		 *  ha un valore solo numerico (1, 2, 3 …), comporre il campo “Titolo” della Teca 
		 *  unendo il valore del campo unimarc 200 solo numerico separato da <due punti – spazio> 
		 *  con il valore del campo unimarc 461 (es.:  1: Commedie in versi dell'abate 
		 *  Pietro Chiari bresciano poeta di S.A. serenissima il sig. duca di Modana. Tomo primo [-decimo ed ultimo])
		 */
		
	}

	protected boolean hasAdded;
	
	/* (non-Javadoc)
	 * @see com.gruppometa.unimarc.profile.SbnProfile#makeSpecialOne(com.gruppometa.unimarc.object.OutItem, org.marc4j.marc.Record)
	 */
	@Override
	public void makeSpecialOne(OutItem desc, Record record)
			throws IllegalArgumentException, SecurityException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		//logger.debug("ID: "+desc.getAbout());
		hasAdded = true;
		@SuppressWarnings("unchecked")
		List<DataField> datas = record.getDataFields();
		//boolean isPartOf = record.getLeader().toString().charAt(9)=='W';
		//HashMap<String, String> fieldsDone = new HashMap<String, String>();
		for (Iterator<DataField> iterator = datas.iterator(); iterator
				.hasNext();) {
			DataField dataField = (DataField) iterator.next();				
			if(dataField.getTag().equals("950")){
				@SuppressWarnings("unchecked")
				List<Subfield> subs = dataField.getSubfields('e');
				@SuppressWarnings("unchecked")
				List<Subfield> subsA = dataField.getSubfields('a');
				if(subs!=null && subsA!=null && subsA.size()>0 && isValidLocalizzazione(subsA.get(0).getData())){
					for (Subfield sub : subs) {
						String data = sub.getData();
						if(data.startsWith(" CR   ")|| data.length()>16){
							String val = data.substring(6,16);
							makeNode(desc, "Inventario", val);
							//logger.debug("Added inventario: '"+val+"'");
						}
					}
				}
				else if(subs!=null && subsA!=null && subsA.size()>0 && !isValidLocalizzazione(subsA.get(0).getData())){
					removeDataField(dataField);
				}
			}
			else if(isDataField(dataField.getTag())){
				Subfield sub = dataField.getSubfield('a');
				if(sub!=null){
					String val = sub.getData().substring(9,13);
					String range = "[";
					String range2 = "";
					String rangeForData = "";
					boolean useRange = false;
					boolean useRange2 = true;
					boolean useDataRangeForData = true;
					if(isValidDate(val)){
						if(!useDataRangeForData)
							makeNode(desc, "Data", val);
						makeNode(desc, "Data_inizio", val);
						range +=val;
						rangeForData +=val;
						range2 +=val;
					}
					int posChar = 8; // era 7, bug vedi specifica unimarc. MFADEV-825
					if(sub.getData().charAt(posChar)=='b' || sub.getData().charAt(posChar)=='g'
						|| sub.getData().charAt(posChar)=='f'
						){									
						val = sub.getData().substring(13,17);
						if(isValidDate(val)){
							if(!useDataRangeForData)
								makeNode(desc, "Data", val);
							range +=" TO "+val+"]";
							range2 += " - "+ val;
							rangeForData += " - "+ val;
							makeNode(desc, "Data_fine", val);
						}
						else{
							range2+= " - ";
							range+=" TO 9999]";
							makeNode(desc, "Data_fine", "9999");
						}
					}
					else{
						range2+= " - "+ val;
						range+=" TO "+val+"]";
						/**
						 * secondo valore
						 */
						val = sub.getData().substring(13,17);
						if(isValidDate(val)){
							makeNode(desc, "Data_inizio", val);
						}
						if(val.equals("9999")){
							makeNode(desc, "Data_fine", val);
						}

					}
					if(useRange)
						makeNode(desc, "Data_range", range);
					if(useRange2)
						makeNode(desc, "Data_range", range2);
					if(useDataRangeForData)
						makeNode(desc, "Data", rangeForData);
				}
			}
		}
		removeDataFields(record);
		//super.makeSpecialOne(desc, record);
	}
	
	protected boolean isDataField(String dataField){
		return dataField!=null && dataField.equals("100");
	}
	protected void removeDataFields(Record record) {
	}
	protected void removeDataField(DataField dataField) {
	}

	@Override
	protected String getValueSeparator(String fieldname, String code, String data, String[] subFieldsCodes) {
		/**
		 * per BNCR
		 */
		if(fieldname.equals("215")){
			if(code.equals("c"))
				return " : ";
			if(code.equals("d"))
				return " ; ";			
		}
		if(fieldname.equals("012")){
			if(code.equals("9"))
				return " - ";						
		}
		return super.getValueSeparator(fieldname, code, data, subFieldsCodes);
	}

	@Override
	public boolean scarta(OutItem desc) {
		if(getFilterNature()!=null && desc.getFieldArray("Livello bibliografico")!=null
				&& desc.getFieldArray("Livello bibliografico").size()>0
				&& !desc.getFieldArray("Livello bibliografico").get(0).getTextValue().equals(getFilterNature()))
			return true;
		if(getFilterId()!=null && !desc.getAbout().equals(getFilterId()))
			return true;
		return false;
		//return desc.getAbout().indexOf("\\MSM\\")!=-1 || desc.getAbout().indexOf("\\MUS\\")!=-1;
	}

}
