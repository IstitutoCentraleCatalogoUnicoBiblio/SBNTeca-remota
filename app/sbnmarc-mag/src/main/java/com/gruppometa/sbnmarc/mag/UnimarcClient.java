package com.gruppometa.sbnmarc.mag;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.MarcXmlWriter;
import org.marc4j.converter.impl.AnselToUnicode;
import org.marc4j.marc.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;

public class UnimarcClient {

	protected String opacUrlUnimarc = "https://opac.sbn.it/c/opac/unimarc/export?id=";
	protected String opacUrlMarc21 = "https://opac.sbn.it/c/opac/marc21/export?id=";

	public String getOpacUrlUnimarc() {
		return opacUrlUnimarc;
	}

	public void setOpacUrlUnimarc(String opacUrlUnimarc) {
		this.opacUrlUnimarc = opacUrlUnimarc;
	}

	public String getOpacUrlMarc21() {
		return opacUrlMarc21;
	}

	public void setOpacUrlMarc21(String opacUrlMarc21) {
		this.opacUrlMarc21 = opacUrlMarc21;
	}

	protected String solrUrl;

	public String getSolrUrl() {
		return solrUrl;
	}

	public void setSolrUrl(String solrUrl) {
		this.solrUrl = solrUrl;
	}

	public String getUnimarcBinaryFieldName() {
		return unimarcBinaryFieldName;
	}

	public void setUnimarcBinaryFieldName(String unimarcBinaryFieldName) {
		this.unimarcBinaryFieldName = unimarcBinaryFieldName;
	}

	protected String unimarcBinaryFieldName="";
	
	
	protected static final Logger logger = LoggerFactory.getLogger(UnimarcClient.class);
	
	public String getResponse(String filename) throws MagException {
		InputStream input;
		try {
			input = new FileInputStream(filename);
	        return getResponse(input, null);
		} catch (FileNotFoundException e) {
			logger.error("",e);
		}
		return null;
	}
	public String getResponseFromSolr(String bid) throws Exception{
		return getResponseFromSolr(bid, null);
	}

	public String getResponseFromSolr(String bid, String secondTransformer2) throws Exception{
		String base64unimarc = null;
		SolrQuery query = new SolrQuery();
		query.setFields(unimarcBinaryFieldName);
		query.setQuery("id:"+bid);
		try {
			HttpSolrClient 	server =  new HttpSolrClient.Builder(solrUrl).build();
			server.setParser(new XMLResponseParser());
			QueryResponse resp = server.query(query);
			if(resp.getResults().getNumFound()>0){
				if(resp.getResults().get(0).getFieldValues(unimarcBinaryFieldName)!=null){
					base64unimarc = resp.getResults().get(0).getFieldValues(unimarcBinaryFieldName).iterator().next().toString();
					byte[] data = Base64.getDecoder().decode(base64unimarc);
					ByteArrayInputStream bufInput =	new ByteArrayInputStream(data);
					return getResponse(bufInput, secondTransformer2);
				}
				else
					logger.debug("No "+unimarcBinaryFieldName+ " for " + bid);
			}
			server.close();
		} catch (Exception e) {
			logger.error("",e);
			logger.error("base64: "+base64unimarc);
			throw e;
		}
		return null;
	}
	
	public String getResponse(InputStream inputStream, String secondTransformer) throws MagException {
		StringWriter stringWriter = new StringWriter();

		int count = 0;
		try{
			@SuppressWarnings("unused")
			//String stylesheetUrl = "http://www.loc.gov/standards/mods/v3/MARC21slim2MODS3.xsl";
	        InputStream inputXsl = UnimarcClient.class.getResourceAsStream(
	        		"/marcxml2sbnmarc.xsl"
			);
			boolean isMarc = (secondTransformer!=null && secondTransformer.equals("marc"));
			Source stylesheet = new StreamSource(inputXsl);
	        Result result = new StreamResult(stringWriter);
	        MarcReader reader = new MarcStreamReader(inputStream
                    ,"UTF-8" // va forzato l'encoding UTF-8 perchè manca il BOM nell'array di bytes da Solr
                );
	        MarcXmlWriter writer =
					isMarc?new MarcXmlWriter(result)
							:new MarcXmlWriter(result, stylesheet);
	        // Problema di codifità è già tutto unicode? (31-08-2016): non va convertito
			//writer.setConverter(new AnselToUnicode());
			while (reader.hasNext()) {
	            Record record = (Record) reader.next();
	            writer.write(record);
	            count++;
	            break;
	        }
	        
	        if(writer!=null){
	        	try{
	        		writer.close();
	        	}
	        	catch(NullPointerException e){
	        	}        
	        }
		}
		catch(Exception e){
			logger.error("",e);
		}
		if(count==0)
			throw new MagException("No record found in Marc data.");
		if(secondTransformer!=null){
			// TODO trasformazione sbnmarc to mag p.e.
			return stringWriter.toString();
		}
		else
			return stringWriter.toString();
	}
	public String getOpacSbn2Mag(MagTransformer magTransformer,
							   String bid) throws MagException {
		return getOpacSbn2X(magTransformer, bid, true, false);
	}

	public String getOpacSbn2Marcxml(MagTransformer magTransformer,
								 String bid) throws MagException {
		return getOpacSbn2X(magTransformer, bid, false, true);
	}

	protected  String getOpacSbn2X(MagTransformer magTransformer,
			 String bid, boolean toMag, boolean toMarcXml) throws MagException {
		String ret = null;
		try {
			String bid2 = bid.startsWith("IT\\ICCU\\")?bid.substring(8):bid;
			bid2 = bid2.replaceAll("\\\\","");
			bid2 = URLEncoder.encode(bid2,"UTF-8");
			String url = getOpacUrlUnimarc()+bid2;
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0";
			conn.setRequestProperty("User-Agent",userAgent);
			int respondeCode = conn.getResponseCode();
			if(respondeCode==HttpURLConnection.HTTP_MOVED_PERM){
				conn = (HttpURLConnection) new URL(conn.getHeaderField("Location")).openConnection();
				conn.setRequestProperty("User-Agent",userAgent);
			}
			InputStream bidInputStream = conn.getInputStream();
			ret = getResponse(bidInputStream, (toMarcXml?"marc":null) );
		} catch (Exception e) {
			return "<error>"+ StringEscapeUtils.escapeXml11(e.getMessage())+"</error>";
		}
		if(ret!=null && ret.trim().length()>0) {
			if(!toMag)
				return ret;
			try {
				return magTransformer.transform(ret);
			} catch (Exception e) {
				logger.error("",e);
				throw new  MagException(e.getMessage());
			}
		}
		else{
			return null;
		}
	}

	public String getOpacSbn2Mods(String bid){
		String ret = null;
		try {
			String bid2 = bid.startsWith("IT\\ICCU\\")?bid.substring(8):bid;
			bid2 = bid2.replaceAll("\\\\","");
			bid2 = URLEncoder.encode(bid2,"UTF-8");
			String url = getOpacUrlMarc21()+bid2;
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
			String userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:47.0) Gecko/20100101 Firefox/47.0";
			conn.setRequestProperty("User-Agent",userAgent);
			int respondeCode = conn.getResponseCode();
			if(respondeCode==HttpURLConnection.HTTP_MOVED_PERM){
				conn = (HttpURLConnection) new URL(conn.getHeaderField("Location")).openConnection();
				conn.setRequestProperty("User-Agent",userAgent);
			}
			InputStream bidInputStream = conn.getInputStream();
			//ret = getResponse(bidInputStream, "marc");
 	      	Source stylesheet = new StreamSource(UnimarcClient.class.getResourceAsStream("/MARC21slim2MODS3-7-withUtils.xsl"));

		   StringWriter stringWriter = new StringWriter();
		   Result result = new StreamResult(stringWriter);

			MarcReader reader = new MarcStreamReader(bidInputStream
					,"UTF-8" // va forzato l'encoding UTF-8 perchè manca il BOM nell'array di bytes da Solr
			);
		   MarcXmlWriter writer = new MarcXmlWriter(result, stylesheet);
		   //writer.setConverter(new AnselToUnicode());
		   while (reader.hasNext()) {
			   Record record = (Record) reader.next();
			   writer.write(record);
		   }
		   try {
			   writer.close();
		   }
		   catch (Exception e){
				// writer is null
		   }
			return stringWriter.toString();
		} catch (Exception e) {
			return "<error>"+ StringEscapeUtils.escapeXml11(e.getMessage())+"</error>";
		}
	}

}
