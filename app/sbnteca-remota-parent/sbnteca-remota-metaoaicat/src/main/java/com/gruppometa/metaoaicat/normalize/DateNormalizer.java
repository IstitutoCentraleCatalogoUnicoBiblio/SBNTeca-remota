package com.gruppometa.metaoaicat.normalize;


public class DateNormalizer {

	/**
	 * Crea una stringa in formato W3CDTF:
	 *  Year:
	 *  	YYYY (e.g. 1997)
	 *  Year and month:
		    YYYY-MM (e.g. 1997-07)
		Complete date:
		    YYYY-MM-DD (e.g. 1997-07-16)
		Complete date plus hours and minutes:
		    YYYY-MM-DDThh:mmTZD (e.g. 1997-07-16T19:20+01:00)
		Complete date plus hours, minutes and seconds:
		    YYYY-MM-DDThh:mm:ssTZD (e.g. 1997-07-16T19:20:30+01:00)
		Complete date plus hours, minutes, seconds and a decimal fraction of a second
		    	YYYY-MM-DDThh:mm:ss.sTZD (e.g. 1997-07-16T19:20:30.45+01:00) 
	 * @param str
	 * @return
	 */
	public static String normalize(String str){
		if(str==null)
			return null;
		if(str.toLowerCase().contains("vol") && str.length()>4)
			return str.substring(str.length()-4);
		return str;
	}
}
