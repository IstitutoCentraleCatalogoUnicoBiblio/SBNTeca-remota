package com.gruppometa.metaoaicat.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class IccdUtils {
	protected static Logger logger = LoggerFactory.getLogger(IccdUtils.class);
	
	public static HashMap<String, String> getValuesAsMap(String value){
		ArrayList<Object> list = new ArrayList<Object>();
		list.add(value);
		return getValuesAsMap(list,null);
	}
	public static HashMap<String, String> getValuesAsMap(Collection<Object> collection, String separator){
		if(collection==null)
			return null; 
		HashMap<String, String> values =  new HashMap<String, String>();
		for (Iterator iterator = collection.iterator(); iterator.hasNext();) {
			String str = (String) iterator.next();
			String[] vs = str.split(";");
			String k = null;
			for (int i = 0; i < vs.length; i++) {
				String[] pair = vs[i].split("=",2);
				if(pair.length>1){
					k = getKey( pair[0] );
					if(values.get(k)==null)
						values.put(k , unquoted( pair[1]));
					else
						values.put(k , values.get(k)+(separator!=null?separator:"; ")+ unquoted( pair[1]));
				}
				// appendi all'ultimo
				if(pair.length==1 && i>=2 && values.get(k)!=null){
					values.put(k , values.get(k)+(";")+ pair[0]);
				}
			}		
		}
		return values;
	}
	public static List<String> getValuesAsList(String value){
		if(value==null)
			return null;
		List<String> values =  new ArrayList<String>();
			String[] vs = value.split(";");
			String k = null;
			for (int i = 0; i < vs.length; i++) {
				String[] pair = vs[i].split("=",2);
				if(pair.length>1){
					values.add(unquoted(pair[1]));
				}
				// appendi all'ultimo
				if(pair.length==1 ){
					values.add( pair[0]);
				}
			}		
		return values;
	}

	public static String getKey(String key){
		if(key==null)
			return null;
		return key.toLowerCase().replace(String.valueOf((char) 160), " ").trim();		
	}
	public static String unquoted(String value){
		if(value==null)
			return null;
		if(value.startsWith("\"") && value.endsWith("\""))
			return value.substring(1,value.length()-1);
		else
			return value;
	}
	public static Node createNode(Node node, String value){
		DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
		fac.setNamespaceAware(true);		
		try {
			org.w3c.dom.Document doc2 = fac.newDocumentBuilder().newDocument();
			org.w3c.dom.Element concept = doc2.createElementNS(node.getNamespaceURI(),node.getNodeName());
			NamedNodeMap map = node.getAttributes();
			for (int i = 0; map!=null && i < map.getLength(); i++) {
				Node attr = map.item(i);
				concept.setAttributeNS(attr.getNamespaceURI(), attr.getNodeName(), attr.getNodeValue());
			}
			concept.setTextContent(value);
			return concept;
		}
		catch(Exception e){
			logger.error("",e);
		}
		return null;
	}
}
