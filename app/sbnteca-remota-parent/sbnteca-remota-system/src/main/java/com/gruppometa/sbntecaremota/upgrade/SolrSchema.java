package com.gruppometa.sbntecaremota.upgrade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author ingonew
 *
 */
public class SolrSchema {
	protected static Logger logger = LoggerFactory.getLogger(SolrSchema.class);
	protected Document doc = null;
	protected HashMap<String, String> fieldNames = new HashMap<String, String>();
	public SolrSchema(String schemaName){
		try {
			DocumentBuilder builder =  DocumentBuilderFactory.newInstance().newDocumentBuilder();
			doc = builder.parse(SolrSchema.class.getResourceAsStream("/"+schemaName));
			fieldNames = getAllFieldNames();
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	public List<String> getFieldNames(boolean facetsOnly){
		XPath path = XPathFactory.newInstance().newXPath();
		ArrayList<String> values = new ArrayList<String>();
		try {
			XPathExpression xesp= path.compile("//field");
			NodeList set = (NodeList) xesp.evaluate( doc , XPathConstants.NODESET);
			for (int i = 0; i < set.getLength(); i++) {
				Node n = set.item(i);
				if(!facetsOnly || n.getAttributes().getNamedItem("type").getNodeValue().contains("string"))
					values.add(n.getAttributes().getNamedItem("name").getNodeValue());
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		return values;
	}
	
	public HashMap<String,String> getAllFieldNames(){
		XPath path = XPathFactory.newInstance().newXPath();
		HashMap<String,String>  values = new HashMap<String,String>();
		try {
			XPathExpression xesp= path.compile("//field");
			NodeList set = (NodeList) xesp.evaluate( doc , XPathConstants.NODESET);
			for (int i = 0; i < set.getLength(); i++) {
				Node n = set.item(i);
					values.put(n.getAttributes().getNamedItem("name").getNodeValue(),
							n.getAttributes().getNamedItem("name").getNodeValue());
			}
		} catch (Exception e) {
			logger.error("",e);
		}
		return values;
	}
	
	public  boolean containsSchemaField(String name){
		return fieldNames.get(name)!=null;
		/*
		 * slow?
		XPath path = XPathFactory.newInstance().newXPath();
		try {
			XPathExpression xesp= path.compile("//field[@name='"+name+"']");
			NodeList set = (NodeList) xesp.evaluate( doc , XPathConstants.NODESET);
			if(set.getLength()>0)
				return true;
		} catch (Exception e) {
			logger.error("",e);
		}
		return false;
		*/
	}
	
	public  String getAttribute(String name, String attributeName){
		XPath path = XPathFactory.newInstance().newXPath();
		try {
			XPathExpression xesp= path.compile("//plugin[@name='"+name+"']");
			NodeList set = (NodeList) xesp.evaluate( doc , XPathConstants.NODESET);
			if(set.getLength()>0 && set.item(0).getAttributes().getNamedItem(attributeName)!=null)
				return set.item(0).getAttributes().getNamedItem(attributeName).getNodeValue();
		} catch (Exception e) {
			logger.error("",e);
		}
		return null;
	}
	public  String getTitle(){
		return getString("/plugins/title");
	}

	public  String getLogo(){
		return getString("/plugins/logo");
	}
	
	public  String getCss(){
		return getString("/plugins/css");	
	}

	private String getString(String xpath) {
		XPath path = XPathFactory.newInstance().newXPath();
		try {
			XPathExpression xesp= path.compile(xpath);
			Element node = (Element) xesp.evaluate( doc , XPathConstants.NODE);
			if(node!=null)
				return node.getTextContent();
		} catch (Exception e) {
			logger.error("",e);
		}
		return "Senza titolo";
	}

	public String[] getCopyFields() {
		ArrayList<String> fields = new ArrayList<String>();
		XPath path = XPathFactory.newInstance().newXPath();
		try {
			XPathExpression xesp= path.compile("//copyField");
			NodeList set = (NodeList) xesp.evaluate( doc , XPathConstants.NODESET);
			for (int i = 0; i < set.getLength(); i++) {
				String v =  set.item(i).getAttributes().getNamedItem("dest").getNodeValue();
				if(!fields.contains(v))
					fields.add(v);
			}
		} catch (Exception e) {
			logger.error("",e);
		}		
		return fields.toArray(new String[fields.size()]);
	}
}
