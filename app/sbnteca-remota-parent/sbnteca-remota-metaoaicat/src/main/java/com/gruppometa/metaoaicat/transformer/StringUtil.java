package com.gruppometa.metaoaicat.transformer;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;


public class StringUtil {

	public static String toLowerCase(String value){
		if(value==null)
			return null;
		return  value.toLowerCase();
	}

	public static String hasExtension( String value){
		if(getExtensionPart(value)!=null)
			return "true";
		else
			return "false";
	}

	public static boolean contains(String value, String value2){
		return value.contains(value2);
	}

	public static String getBase64(String value){
		return Base64.getEncoder().encodeToString(value.getBytes());
	}

	static String tdiTeca= "oai:www.internetculturale.sbn.it/Teca:20:NT0000:";
	public static String getIiifId(String value){
		String ret = null;
		try {
			ret = URLDecoder.decode(value,"UTF-8");
			if(ret.contains(tdiTeca))
				ret = ret.substring(ret.indexOf(tdiTeca)+tdiTeca.length());
//			if(ret.startsWith("N:"))
//				ret = ret.substring(2);
			ret =  Base64.getEncoder().encodeToString(ret.getBytes()).replace("=","_");
		} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
		}
		return ret;
	}
	public static boolean isText(String value){
		return value!=null && (
				value.equalsIgnoreCase("testo a stampa")
						||value.equalsIgnoreCase("book")
						||value.equalsIgnoreCase("manoscritto")
						||value.equalsIgnoreCase("musica manoscritta")
						||value.equalsIgnoreCase("libretto per la musica")
						||value.equalsIgnoreCase("musica a stampa")
						||value.equalsIgnoreCase("Testo digitale")
						||value.equalsIgnoreCase("testo manoscritto")
						||value.equalsIgnoreCase("teato a stampa")
						||value.equalsIgnoreCase("teatro a stampa")

		);
	}

	public static boolean localTest = false;

	public static boolean isTdi(String id){
		if(localTest)
			return true;
		try {
			return id.contains(tdiTeca)|| id.contains(URLEncoder.encode(tdiTeca,"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			return false;
		}
	}

	public static boolean isExtension(String value){
		return value.contains("x") &&
				(value.contains("mm ")
						|| value.contains(" mm")
						||value.contains("cm ")
						|| value.contains(" cm")
						|| value.endsWith("mm")
						|| value.endsWith("cm")
				);
	}

	public static String getExtensionPart( String value){
		String[] parts = value.split(";");
		for(int i = 0; i < parts.length; i++){
			if (isExtension(parts[i]))
				return parts[i].trim();
		}
		return null;
	}

	public static String getWithoutExtensionPart(String value){
		String[] parts = value.split(";");
		String ret = "";
		for(int i = 0; i < parts.length; i++){
			if (!isExtension(parts[i])){
				if(ret.length()>0)
					ret +="; ";
				ret += parts[i];
			}
		}
		return ret.trim();
	}

	public static String urlencode(String value){
		if(value==null)
			return null;
		try {
			return  URLEncoder.encode(value,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
