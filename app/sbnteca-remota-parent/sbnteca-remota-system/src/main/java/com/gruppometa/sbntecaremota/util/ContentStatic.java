package com.gruppometa.sbntecaremota.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Memorizzazione in cahce della mappa di configurazione
 *
 *
 */
public class ContentStatic {
    /*Classe di utilità dove vado a caricare i contenuti del file di properties di configurazione, ed altri contenuti che userò nell'applicazione*/
	private static  Properties properties=null;
	private static List<String> blacklistMimeTypes=null;
	
	/**
	 * Lettura file di configurazione .properties
	 * 
	 * @param filename
	 */
	public static void loadConfiguration(String filename) {
		if (properties == null)
		    properties=Utility.readFileProperties(filename);		
	}
	
	/**
	 * Restituisce la mappa di configurazione
	 * 
	 * @return Properties mappa di configurazione
	 */
	public static Properties getProperties() {
		return properties;
	}

	public static void setProperties(Properties propertiesNew) {
		properties = propertiesNew;
	}
	
	/**
	 * Lettura file blacklist MIME types per upload
	 * 
	 * @param filename file di configurazione
	 */
	public static void loadBlacklistMimeTypes(String filename) {
		if(blacklistMimeTypes == null)
			blacklistMimeTypes = Utility.readBlacklistMimeTypes(filename);
	}
	
	/**
	 * Restituisce la blacklist dei MIME types non consentiti in upload
	 * 
	 * 
	 * @return blacklist dei MIME types non consentiti in upload
	 */
	public static List<String> getBlacklistMimeTypes() {
		return blacklistMimeTypes;
	}

    public static Integer getInt(String s, int i) {
		if(getProperties().contains(s))
			return Integer.parseInt(getProperties().getProperty(s));
		return i;
    }
}
