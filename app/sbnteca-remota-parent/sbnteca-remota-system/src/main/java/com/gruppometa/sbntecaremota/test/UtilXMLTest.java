package com.gruppometa.sbntecaremota.test;

import java.io.File;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Ignore;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.gruppometa.sbntecaremota.util.UtilXML;

public class UtilXMLTest {

  @Ignore
  public void getContentNodeTEst(){
	  Document doc=UtilXML.openMag("/home/tomcat/Scrivania/mag16.xml", "file");
	  //UtilXML.getContentNode(doc,"(//*[local-name()='img']/*[local-name()='sequence_number'])[3]");
  }

  /*
  @Test
	public void retriveAttributeValueTest(){
		ValidatorMag validatorMag = new ValidatorMag();
		Document doc=UtilXML.openXML("/home/tomcat/Scrivania/mag16.xml");
		/*[local-name()='Header']/*[local-name()='TestInfo']/@testID*/
		/*(//*[local-name()='"+fileType+"']/*[local-name()='file'][@xlink:href])["+i+"]";*/
		/*String expression="(//*[local-name()='img']/*[local-name()='file']/@xlink:href)[1]";
		UtilXML.retrieveAttributeValue(doc, expression, "");
		
		
	}*/
    
  /*
  @Ignore
  public void selectUsagesTest(){
		//ok funziona l'indice indica quale elemento usage prende all'interno di tutto il file xml e non si riferisce al nodo img per poi ricavare gli usage interni
		//String expression = "(//*[local-name()='img']/*[local-name()='usage'])[6]"; 
		String expression="(//*[local-name()='img'][3]/*[local-name()='usage'])";
		String pathfilemag="E:\\workspace\\mag17.xml";
		Mag mag = new Mag();
		mag.setPath(pathfilemag);
		mag.setNumberImg(3);
	    //UtilXML.selectUsages(mag);
  }*/
  
  @Ignore
  public void selectFieldFileFromXMLTest(){
	  String pathfilemag="E:\\workspace\\mag17.xml";	  
	  //UtilXML.selectFieldFileFromXML(pathfilemag, "img",3);
		
  }
  
  @Ignore
  public void deleteNodeTest(){
	  Document doc= UtilXML.openMag("/home/tomcat/workspace/AnalizzatoreMag/esempiMag/mag3a.xml", "file");
	  Node node=doc.getElementsByTagName("mag:img").item(0);
	  Element el=(Element)node;
	  Node nodeChild=el.getElementsByTagName("mag:altimg").item(0);
	  Node newImg=null,magmetadigit=null;
	  newImg=nodeChild.cloneNode(true);
	  magmetadigit=doc.getElementsByTagName("mag:metadigit").item(0);
	  System.out.println(newImg.getNodeName());
	  doc.renameNode(newImg, newImg.getNamespaceURI(), "mag:img");
	  magmetadigit.appendChild(doc.importNode(newImg, true));	  	 
	 UtilXML.removeNode(nodeChild);
	  try{
	  scriviFileXML(doc);
	  }
	  catch(TransformerException e){
		  System.out.println(e.getMessage());
	  }
	  
  }
  
  
  private boolean scriviFileXML(Document doc) throws TransformerException{
		 String pathDir="",pathMag="";
		 //if (listNode.size()>0){
		  pathMag="/home/tomcat/Scrivania/test.xml";
		  TransformerFactory transformerFactory = TransformerFactory.newInstance();
		  Transformer transformer = transformerFactory.newTransformer();
		  DOMSource source = new DOMSource(doc);			
		  //StreamResult result = new StreamResult(new File(fileOutput));
		 StreamResult result = new StreamResult(new File(pathMag).getAbsolutePath());
		 transformer.transform(source, result);
		 return true;
		
  }
  
}
