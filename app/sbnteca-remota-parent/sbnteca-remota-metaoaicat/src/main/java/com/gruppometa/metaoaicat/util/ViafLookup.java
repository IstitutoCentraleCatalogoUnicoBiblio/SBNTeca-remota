package com.gruppometa.metaoaicat.util;

import java.net.URLEncoder;
import java.text.Normalizer;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.gruppometa.culturaitalia.admin.objects.ObjectFactory;
import com.gruppometa.culturaitalia.admin.objects.UriLookup;

public class ViafLookup {
	protected static Logger logger = LoggerFactory.getLogger(ViafLookup.class);
	// Esempio:
	// http://viaf.org/viaf/search?query=local.personalNames+all+%22cardano%22&
	//        httpAccept=application/xml&sortKeys=holdingscount&&maximumRecords=10
	
	// http://viaf.org/viaf/terms# VIAFCluster/mainHeadings/data*/text => VIAFCluster/Document/@about
	
	// select  //VIAFCluster/mainHeadings/data/text[text()='Cardano']
	
	// check if only one VIAFCLuster found
	
	public String getAbout(String type,String name, String birthyear, String deathyear){
		try {
			if(type==null)
				type ="personalNames";
			name = normalize(name);
			String key = "viaf/"+type+"/#"+name+"#"+birthyear+"#"+deathyear;
			List<Object> lookups = ObjectFactory.getList("UriLookup where key = '"+
					key.replaceAll("'", "''")+"'");
			if(lookups.size()>0){
				return ((UriLookup)lookups.get(0)).getValue();
			}
			String uri = "http://viaf.org/viaf/search?query=local."+type+"+all+%22"+
					URLEncoder.encode(name,"UTF-8")+"%22&" +
					"httpAccept=application/xml&sortKeys=holdingscount&&maximumRecords=10";
			DocumentBuilderFactory domFactory = 
			DocumentBuilderFactory.newInstance();
			domFactory.setNamespaceAware(true); 			
			DocumentBuilder builder = domFactory.newDocumentBuilder();				  
			Document doc = builder.parse(uri);
			XPath xpath = XPathFactory.newInstance().newXPath();
			xpath.setNamespaceContext(new NamespaceContext(){

				public String getNamespaceURI(String prefix) {
					if(prefix.equals("viaf"))
						return "http://viaf.org/viaf/terms#";
					else
						return null;
				}

				public String getPrefix(String uri) {
					if(uri.equals("http://viaf.org/viaf/terms#"))
						return "viaf";
					else
						return null;
				}

				public Iterator getPrefixes(String arg0) {
					return null;
				}});
			XPathExpression expr = xpath.compile("//viaf:VIAFCluster/viaf:mainHeadings/viaf:data/viaf:text");
			XPathExpression idExpr = xpath.compile("../../../viaf:Document/@about");
			NodeList nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
			String id = null;
			//logger.debug("Nodes:"+nodes.getLength());
			for (int i = 0; nodes!=null && i < nodes.getLength(); i++) {
				String content = nodes.item(i).getTextContent();
				//logger.debug(content.charAt(3)+"="+name.charAt(3));
				if(checkContext(content,name, birthyear, deathyear)){
					//logger.debug("Found");
					Node about = (Node)idExpr.evaluate(nodes.item(i), XPathConstants.NODE);
					if(about!=null){
						if(id==null)
							id = about.getNodeValue();
						else{
							if(!id.equalsIgnoreCase(about.getNodeValue())){
								logger.debug("Found more than one entry for '"+name+"'");
								id=null;
								break;
							}
						}
					}
				}
			}
			UriLookup lookup = new UriLookup();
			lookup.setKey(key);
			lookup.setValue(id);
			ObjectFactory.saveObject(lookup);			
			return id;
		} catch (Exception e) {
			logger.error("",e);
		}
		return null;
	}

	private String normalize(String name) {
		if(name==null)
			return null;
		else
			return name.replaceAll(",[^-\\s]",", ")
					.replace(" : ", " ")
					.trim();
	}

	public static String unAccent(String s) {
	      //
	      // JDK1.5
	      //   use sun.text.Normalizer.normalize(s, Normalizer.DECOMP, 0);
	      //
	      String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
	      Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	      return pattern.matcher(temp).replaceAll("");
	  }
	
	protected boolean checkContext(String content, String name, String birthyear,
			String deathyear) {		
		 return content!=null && 
				 (unAccent(content).trim().equalsIgnoreCase(unAccent(name))
						 || (birthyear!=null && deathyear==null && unAccent(content).trim().equalsIgnoreCase(unAccent(name)+" "+birthyear+"-"))
						 || (birthyear!=null && deathyear!=null && unAccent(content).trim().equalsIgnoreCase(unAccent(name)+" "+birthyear+"-"+deathyear))
						 );
	}
}
