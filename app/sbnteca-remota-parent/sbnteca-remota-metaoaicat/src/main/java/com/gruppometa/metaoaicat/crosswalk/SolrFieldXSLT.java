package com.gruppometa.metaoaicat.crosswalk;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.xml.namespace.NamespaceContext;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.solr.common.SolrDocument;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.QName;
import org.dom4j.io.DocumentSource;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import com.gruppometa.metaoaicat.SolrOAISetsCatalog;

import ORG.oclc.oai.server.crosswalk.Crosswalk;
import ORG.oclc.oai.server.verb.CannotDisseminateFormatException;
import ORG.oclc.oai.server.verb.OAIInternalServerError;

public class SolrFieldXSLT extends Crosswalk  implements NeededFields{
	protected Logger logger = LoggerFactory.getLogger(SolrFieldXSLT.class);
	protected Templates templates = null;
	protected String xmlField = null;
	protected String viewUrl = "";
	protected String previewUrl = null;
	protected String iiifBaseUrl = null;
	protected boolean isCheckResult = false;
	protected String idField = "id";

	/**
	 * The constructor assigns the schemaLocation associated with this
	 * crosswalk. Since the crosswalk is trivial in this case, no properties are
	 * utilized.
	 * 
	 * @param properties
	 *            properties that are needed to configure the crosswalk.
	 */
	public SolrFieldXSLT(Properties properties) throws OAIInternalServerError {
		super(properties.getProperty("SolrFieldXSLT.schemaLocation"));
		makeInit(properties,"SolrFieldXSLT");
	}
	public SolrFieldXSLT(Properties properties, String name) throws OAIInternalServerError {
		super(properties.getProperty(name+".schemaLocation"));
		makeInit(properties,name);
	}

	public List<String> getNeededFields(){
		List<String> fields = new ArrayList<String>();
		if(xmlField!=null)
			fields.add(xmlField);
		return fields;
	}

	protected void makeInit(Properties properties, String name)
			throws TransformerFactoryConfigurationError, OAIInternalServerError {
		String crosswalkName = name;
		try {
			if(properties.getProperty("SolrOAICatalog.idField")!=null)
				idField = properties.getProperty("SolrOAICatalog.idField");
			String xsltName = properties.getProperty(crosswalkName+".xsltName");
			xmlField = properties.getProperty(crosswalkName+".xmlField");
			viewUrl = properties.getProperty(crosswalkName+".viewUrl");
			previewUrl = properties.getProperty(crosswalkName+".previewUrl");
			iiifBaseUrl = properties.getProperty(crosswalkName+".iiifBaseUrl");
			isCheckResult = properties.getProperty(crosswalkName+".checkResult")!=null
					&& properties.getProperty(crosswalkName+".checkResult").equals("true");
			if (xmlField == null)
				throw new OAIInternalServerError(
						crosswalkName+".xmlField property is needed.");
			TransformerFactory tFactory = TransformerFactory.newInstance();			
			InputStream stream = SolrFieldXSLT.class.getResourceAsStream("/"
					+ xsltName);
			if (stream == null)
				throw new OAIInternalServerError("XSLT " + xsltName + " not found for "+crosswalkName+".");
			if (xsltName != null) {
				StreamSource xslSource = new StreamSource(stream);
				this.templates = tFactory.newTemplates(xslSource);
			} else {
				// this.transformer = tFactory.newTransformer();
				// this.transformer.setOutputProperty(
				// OutputKeys.OMIT_XML_DECLARATION, "yes");
				// this.transformer.setOutputProperty(
				// OutputKeys.INDENT, "yes");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new OAIInternalServerError(e.getMessage());
		}
	}

	
	/**
	 * Can this nativeItem be represented in DC format?
	 * 
	 * @param nativeItem
	 *            a record in native format
	 * @return true if DC format is possible, false otherwise.
	 */
	public boolean isAvailableFor(Object nativeItem) {
		return nativeItem instanceof SolrDocument;
	}

	/**
	 * Perform the actual crosswalk.
	 * 
	 * @param nativeItem
	 *            the native "item". In this case, it is already formatted as an
	 *            OAI <record> element, with the possible exception that
	 *            multiple metadataFormats are present in the <metadata>
	 *            element.
	 * @return a String containing the FileMap to be stored within the
	 *         <metadata> element.
	 * @exception CannotDisseminateFormatException
	 *                nativeItem doesn't support this format.
	 */
	public String createMetadata(Object nativeItem)
			throws CannotDisseminateFormatException {

		SolrDocument doc = (SolrDocument) nativeItem;
		try {
			String xmlRec = (String) doc.getFieldValue(xmlField);
			if(xmlRec==null)
				throw new CannotDisseminateFormatException("XML field not found.");
			xmlRec = SolrFieldXSLT.filterCharacters(xmlRec);
			if(!xmlRec.startsWith("<?xml version"))
				xmlRec = "<?xml version=\"1.0\"?>\n"+xmlRec;
			StringReader stringReader = new StringReader(xmlRec);
			Source streamSource = null;
			Collection<Object> contants = ((SolrDocument)nativeItem).getFieldValues("constant");
			if(contants!=null && contants.size()>0){
				SAXReader saxReader = new SAXReader();
				try {
					Document docRec = saxReader.read(stringReader);
					for (@SuppressWarnings("rawtypes")
					Iterator iterator = contants.iterator(); iterator
							.hasNext();) {
						Object object = (Object) iterator.next();
						String[] vals = ((String)object).split(SolrOAISetsCatalog.FIELD_SEPARATOR_REGEX,2);
						addNode(doc,docRec, vals[0], vals[1]);
					}
					streamSource = new DocumentSource(docRec);
				} catch (Exception e) {
					logger.error("createMetadata", e);
					throw new CannotDisseminateFormatException(e.getMessage());
				}
			}
			else
				streamSource = new StreamSource(stringReader);
			
			StringWriter stringWriter = new StringWriter();
			Transformer transformer = null;
			if (templates == null)
				transformer = TransformerFactory.newInstance().newTransformer();
			else
				transformer = templates.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");			
			if(previewUrl!=null)
				transformer.setParameter("previewUrl", previewUrl);
			if(viewUrl!=null)
				transformer.setParameter("baseUrl", viewUrl);
			if(iiifBaseUrl!=null)
				transformer.setParameter("iiifBaseUrl", iiifBaseUrl);
			String recordType = SolrOAISetsCatalog.getEdmType(doc, "OAIHandler");
			if(recordType!=null)
				transformer.setParameter("recordType", recordType);
			if(doc.getFirstValue("descSourceLevel2")!=null){
				transformer.setParameter("descSourceLevel2", (String)doc.getFirstValue("descSourceLevel2"));
			}
			if(doc.getFirstValue("descSource")!=null){
				transformer.setParameter("descSource", (String)doc.getFirstValue("descSource"));
			}
			transformer.setParameter("id", URLEncoder.encode((String)((SolrDocument)nativeItem).getFieldValue(idField),"UTF-8"));
			transformer.setParameter("idNative", ((SolrDocument)nativeItem).getFieldValue(idField));
			/**
			 * check per Edm Sound
			 */
			if(isCheckResult){
				DOMResult domResult = new DOMResult();
				transformer.transform(streamSource,
						domResult
						//new StreamResult(stringWriter)
				);
				checkDomResult(domResult);
				Transformer transformer2 = TransformerFactory.newInstance().newTransformer();
				transformer2.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer2.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");			
				transformer2.transform(new DOMSource(domResult.getNode()), new StreamResult(stringWriter));
			}
			else{
				transformer.transform(streamSource,new StreamResult(stringWriter));
			}
			// non serve: transformer.reset();
			stringWriter.close();
			return stringWriter.toString();
		} catch (TransformerException e) {
			logger.error("createMetadata", e);
			throw new CannotDisseminateFormatException(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error("createMetadata", e);
			throw new CannotDisseminateFormatException(e.getMessage());
		} catch (IOException e) {
			logger.error("createMetadata", e);
			throw new CannotDisseminateFormatException(e.getMessage());
		}
	}

	private void checkDomResult(DOMResult domResult) {
		HashMap<String, Node> map = new HashMap<String,Node>();
		checkNode(domResult.getNode(), map);
		List<Node> nodes = new ArrayList<Node>();
		for (String  key : map.keySet()) {
			//logger.debug("key:"+key);
			for (String  key2 : map.keySet()) {
				// pardi da cancellare
				if(key2.contains("data.europeana.eu")
						&&  key2.startsWith(key) && key2.length()>=key.length()
						&& map.get(key)!=map.get(key2)){
					nodes.add(map.get(key));
					logger.debug("del:"+key);
					
				}
				// doppioni
				else if(key.equals(key2) && map.get(key)!=map.get(key2)){
					nodes.add(map.get(key));
					logger.debug("doppione:"+key);
				}
			}
		}
		for (Node node : nodes) {
			if(node.getParentNode()!=null){
				node.getParentNode().removeChild(node);
			}
		}
		XPathFactory xPathfactory = XPathFactory.newInstance();
		XPath xpath = xPathfactory.newXPath();
		xpath.setNamespaceContext(new NamespaceContext(){

			public String getNamespaceURI(String prefix) {
				if(prefix.equals("edm"))
					return "http://www.europeana.eu/schemas/edm/";
				else
					return null;
			}

			public String getPrefix(String uri) {
				if(uri.equals("http://www.europeana.eu/schemas/edm/"))
					return "edm";
				else
					return null;
			}

			@SuppressWarnings("rawtypes")
			public Iterator getPrefixes(String arg0) {
				return null;
			}});
		XPathExpression expr;
		try {
			expr = xpath.compile("//edm:ProvidedCHO");
			Node providedCHO = (Node) expr.evaluate(domResult.getNode(),  XPathConstants.NODE);
			if(providedCHO==null){
				logger.error("No ProvidedCHO node found");
			}
			else{
				List<String> sortKeys = new ArrayList<String>();
				HashMap<String,Node> nodesMap = new HashMap<String,Node>();
				for (int i = providedCHO.getChildNodes().getLength()-1; i >=0; i--) {
					Node child = providedCHO.removeChild(providedCHO.getChildNodes().item(i));
					String key = getKey(child,i);
					sortKeys.add(key);
					nodesMap.put(key, child);
				}
				Collections.sort(sortKeys);
				for (Iterator<String> iterator = sortKeys.iterator(); iterator.hasNext();) {
					String key = (String) iterator.next();
					providedCHO.appendChild(nodesMap.get(key));
				}
			}
		} catch (XPathExpressionException e) {
			logger.error("",e);
		}
	}
	
	protected String getKey(Node child,int i) {
		String prefix = child.getPrefix();
		if(prefix.equals("edm"))
			prefix = "eadm";
		String name = child.getLocalName();
		if(name.equals("duration"))
			name = "zduration";
		return prefix+"::"+name+"::"+(i<10?("0"+i):i);
	}
	private Node checkNode(Node node, HashMap<String, Node> map) {
		if(node.getLocalName()!=null && node.getLocalName().equals("ProvidedCHO")){
			map.put("start", node);
			for (int i = node.getChildNodes().getLength()-1; i>=0;i--){
				Node c;
				if((c=checkNode(node.getChildNodes().item(i), map))!=null){
					logger.debug("del:"+c);
					node.removeChild(c);
				}
			}
		}
		if(!map.isEmpty()){
			String key = "";
			key = node.getNodeName()+"::"+
				((node.getAttributes()!=null && node.getAttributes().getNamedItem("rdf:resource")!=null)?
						node.getAttributes().getNamedItem("rdf:resource").getNodeValue():"")+""+
				node.getTextContent();
			if(map.get(key)!=null)
				return node;
			map.put(key,node);
		}
		for (int i = node.getChildNodes().getLength()-1; i>=0;i--){
			if(node.getChildNodes().item(i).getNodeType()!=Node.TEXT_NODE)
				checkNode(node.getChildNodes().item(i), map);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected void addNode(SolrDocument doc, Document docRec, String nodeName, String value){
		String[] tokens = nodeName.split(" ");
		String[] vals = tokens[0].split(":",2);
		Element el = null;
		if(value.startsWith("$")){
			if(doc.getFieldValue(value.substring(1))!=null) {
				Object valueObject = doc.getFieldValue(value.substring(1));
				if(valueObject instanceof ArrayList)
					value = ((ArrayList<String>) valueObject).get(0);
				else
					value = (String) valueObject;
			}
		}
		if(vals.length==2){
			el = docRec.getRootElement().addElement(
				new QName(vals[1],new Namespace(vals[0], getNameSpaceFromPrefix(vals[0]))));
			el.addText(value);
		}
		else{
			el = docRec.getRootElement().addElement(nodeName);
			el.addText(value);
		}
		for (int i = 1; i < tokens.length; i++) {
			String[] parts = tokens[i].split("=");
			String[] attr = parts[0].split(":");
			if(parts.length==2 && attr.length==2)
				el.addAttribute(new QName(attr[1], new Namespace(attr[0], getNameSpaceFromPrefix(attr[0]))), stripApici( parts[1]));
		}
	}
	
	private String stripApici(String string) {
		return string.replace("\"", "");
	}

	private String getNameSpaceFromPrefix(String string) {
		if(string.equals("oai"))
				return "http://www.openarchives.org/OAI/2.0/";
		if(string.equals("dcterms"))
			return  "http://purl.org/dc/terms/";
		if(string.equals("pico"))
			return "http://purl.org/pico/1.0/";
		if(string.equals("dc"))
			return "http://purl.org/dc/elements/1.1/";
		if(string.equals("xsi"))
			return "http://www.w3.org/2001/XMLSchema-instance";
		if(string.equals("edm"))
			return "http://www.europeana.eu/schemas/edm/";
		if(string.equals("rdf"))
			return "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
		if(string.equals("metaindice"))
			return "http://www.internetculturale.it/metaindice";
		return null;
	}

	public static String filterCharacters(String xmlRec) {
		char c1 =  156;
		char c2 =  152;		 
		if(xmlRec==null)
			return null;
		else {
			return xmlRec.replace(c2, '[')
					.replace(c1, ']');
			
		}
	}
}
