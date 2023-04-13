package com.gruppometa.metaoaicat.normalize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

//import com.gruppometa.internetculturale.services.ServiceFactory;

public class LanguageNormalizer{ 
	
	protected static HashMap<String, String> langues =  new HashMap<String, String>();
	protected static void createLangues(){
//		HashMap<String, String> lang = ServiceFactory.getInstance().getConfigurationService().getLanguageHash();
//		if(lang!=null){
//			for (Iterator<String> iterator = lang.keySet().iterator(); iterator.hasNext();) {
//				String key = (String) iterator.next();
//				langues.put(lang.get(key), key);
//			}
//		}
	}
	public static String normalize(String str){
		if(langues.size()==0){
			synchronized (langues) {
				if(langues.size()==0)
					createLangues();
			}
		}
		String[] s = getLanguages(str, null);
		String ret = "";
		for (int i = 0; i < s.length; i++) {
			if(ret.length()>0)
				ret +=",";
			ret+= (langues.get(s[i])!=null)?langues.get(s[i]):s[i];
		}
		return ret;
	}
	
	protected String[] invalidLanguages = new String[]{"851","a","abi","e"};
	protected static  HashMap<String, String> localLanguageHash = new HashMap<String, String>();
	protected static String[] itLanguages = new String[]{"ita","it","bgi","cli","cmi",
			"eta","fli","iat","itd","iya","loi","mi","mii",
			"ni","pi","pui","rmi","sii","sri","ta","toi","vei","vni"};
	static{
		for (int i = 0; i < itLanguages .length; i++) {
			localLanguageHash.put(itLanguages[i], "ita");
		}
		localLanguageHash.put("de","ger");
		localLanguageHash.put("deu","ger");
		localLanguageHash.put("ted","ger");
		localLanguageHash.put("ger","ger");
		localLanguageHash.put("fre","fre");
		localLanguageHash.put("fra","fre");
		localLanguageHash.put("fr","fre");
		localLanguageHash.put("el","fre"); /*15/12/2010*/
		localLanguageHash.put("lat","lat");
	}
	
	protected static String[] getLanguages(String text, HashMap<String, String> languangeHash){
		if(text==null)
			return null;
		String[] vals = text.split(";|,|\\s");
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < vals.length; i++) {
			vals[i] = vals[i].toLowerCase().trim();
			//if(vals[i].length()==0 || StringUtil.contains(invalidLanguages, vals[i]))
			//	continue;
			if (localLanguageHash.get(vals[i])!=null)
				vals[i] = localLanguageHash.get(vals[i]);
			else if(vals[i].equals("at")||vals[i].equals("la") || vals[i].equals("latino volgarizzato"))
				vals[i] = "lat";
			else if(vals[i].equals("en") || vals[i].equals("ing") )
				vals[i] = "eng";
			else if(vals[i].equals("slava"))
				vals[i] = "slava (altra lingua)";
			else if(vals[i].equals("ebr"))
				vals[i] = "heb";
			else if(vals[i].equals("pii"))
				vals[i] = "piemontese";
			else if(vals[i].equals("es"))
				vals[i] = "spa";
			else if(vals[i].equals("jap"))
				vals[i] = "jpn";
			else if(vals[i].equals("gr") || vals[i].equals("greco") )
				vals[i] = "grc";
			else if(vals[i].equals("bs")
					||vals[i].equals("er")
					||vals[i].equals("g")
					||vals[i].equals("rei")
					||vals[i].equals("s")					
					||vals[i].equals("tagalog")
					||vals[i].equals("tag")
					||vals[i].equals("menangkabau")/*15/12/2010*/)
				vals[i] = "abs";
			else if(vals[i].equals("nl"))
				vals[i] = "dut";
			else if(vals[i].equals("pl"))
				vals[i] = "pol";
			else if(vals[i].equals("sv"))
				vals[i] = "swe";
			else if(vals[i].equals("cin"))
				vals[i] = "chi";			
			else if(vals[i].equals("multilingua"))
				vals[i] = "mis";
			if(languangeHash!=null && languangeHash.get(vals[i])!=null)
				vals[i] = languangeHash.get(vals[i]);
			if(vals[i].length()>0)
				list.add(vals[i]);
		}
		
		return list.toArray(new String[list.size()]);
	}

}
