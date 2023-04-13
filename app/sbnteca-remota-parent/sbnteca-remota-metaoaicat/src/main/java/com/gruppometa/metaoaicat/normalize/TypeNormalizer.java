package com.gruppometa.metaoaicat.normalize;

import java.util.HashMap;

public class TypeNormalizer {
	public static HashMap<String, String> tipoType = new HashMap<String, String>();
	static{
		tipoType.put( "A" , "Testo a stampa");
		tipoType.put( "a" ,  "Testo a stampa");
		tipoType.put( "books" ,  "Testo a stampa");
		tipoType.put( "documents" ,  "Testo a stampa");
		tipoType.put( "digitalisations" ,  "Testo a stampa");
		tipoType.put( "B" ,  "Manoscritto");
		tipoType.put( "b" ,  "Manoscritto");
		tipoType.put( "manuscripts" ,  "Manoscritto");
		tipoType.put( "C" ,  "Musica a stampa");
		tipoType.put( "c" ,  "Musica a stampa");
		tipoType.put( "D" ,  "Musica manoscritta");
		tipoType.put( "d" ,  "Musica manoscritta");
		tipoType.put( "E" ,  "Cartografia a stampa");// 28-06-2010: "Documento cartografico");
		tipoType.put( "maps" ,  "Cartografia a stampa");
		tipoType.put( "e" ,  "Cartografia a stampa");// 28-06-2010: "Documento cartografico");
		tipoType.put( "G" ,  "Materiale video");// 28-06-2010:"Documento da proiettare o video");
		tipoType.put( "g" ,  "Materiale video");// 28-06-2010:"Documento da proiettare o video");
		tipoType.put( "i" ,  "Registrazione sonora non musicale");
		tipoType.put( "J" ,  "Registrazione sonora musicale"); // 28-06-2010
		tipoType.put( "j" ,  "Registrazione sonora musicale"); // 28-06-2010 
		tipoType.put( "K" ,  "Materiale grafico");// 28-06-2010:"Documento grafico");
		tipoType.put( "k" ,  "Materiale grafico");// 28-06-2010:"Documento grafico");
		tipoType.put( "L" ,  "Archivio elettronico");
		tipoType.put( "l" ,  "Archivio elettronico");
		tipoType.put( "M" ,   "Materiale multimediale");// 28-06-2010:"Documento multimediale");
		tipoType.put( "m" ,   "Materiale multimediale");// 28-06-2010:"Documento multimediale");
		tipoType.put( "R" ,  "Oggetto a tre dimensioni");// 28-06-2010: "Oggetto tridimensionale");
		tipoType.put( "r" ,  "Oggetto a tre dimensioni");// 28-06-2010:"Oggetto tridimensionale");
		tipoType.put( "text" ,  "Testo digitale");
		// 28-06-2010
		tipoType.put( "F" ,  "Fascicolo");
		tipoType.put( "f" ,  "Fascicolo");
		// 10-12-2010 per mag....
		tipoType.put( "fascicolo" ,  "Manoscritto");
		tipoType.put( "grafica bidimensionale (disegni, dipinti etc.)","Materiale grafico");
		tipoType.put( "grafics","Materiale grafico");
		tipoType.put( "image", "Image");
		tipoType.put( "libretto","Libretto per musica");
		tipoType.put( "libretti","Libretto per musica");
		tipoType.put( "libro corale","Musica manoscritta");
		tipoType.put( "manifesto-locandina", "Manifesto-locandina");
		tipoType.put( "monografia","Manoscritto");
		tipoType.put( "printed music","Musica a stampa");
		tipoType.put( "registrazione sonora","Registrazione sonora musicale");
		tipoType.put( "serials","Testo a stampa");
		tipoType.put( "stampato","Testo a stampa");
		tipoType.put( "testo digitale","Text");
		tipoType.put( "testo manoscritto","Manoscritto");
		
		tipoType.put( "altro","Musica a stampa");
		tipoType.put( "archivio elettronico","Archivio elettronico");
		tipoType.put( "biglietto","Lettera manoscritta");
		tipoType.put( "biglietto da visita","Lettera manoscritta");
		tipoType.put( "bozzetto","Materiale grafico");
		tipoType.put( "busta","Lettera manoscritta");
		tipoType.put( "cartografia a stampa","Cartografia a stampa");
		tipoType.put( "cartografia manoscritta","Cartografia manoscritta");
		tipoType.put( "cartolina illustrata","Lettera manoscritta");
		tipoType.put( "cartolina postale","Lettera manoscritta");
		tipoType.put( "copialettere","Lettera manoscritta");			
		tipoType.put( "disposizioni sceniche","Materiale grafico");
		tipoType.put( "documenti vari","Documenti vari");
		tipoType.put( "documento grafico","Materiale grafico");
		tipoType.put( "figurino","Materiale grafico");
		tipoType.put( "fotografia", "Fotografia");
		tipoType.put( "grafica bidimensionale (disegni, dipinti etc.)","Materiale grafico");
		tipoType.put( "graphics","Materiale grafico");		
		tipoType.put( "lettera","Lettera manoscritta");
		tipoType.put( "lettera manoscritta","Lettera manoscritta");
		//tipoType.put( "libretto","Libretto per musica");
		//tipoType.put( "manifesto-locandina","Materiale grafico");
		tipoType.put( "musica a stampa con correzioni mss.","Musica a stampa");
		tipoType.put( "musica a stampa con correzioni mss.e autografe","Musica a stampa");
		tipoType.put( "pianta scenica","Materiale grafico");
		tipoType.put( "riduzione canto e pianoforte","Musica a stampa");
		tipoType.put( "risorsa elettronica","Risorsa elettronica");
		tipoType.put( "tavola di attrezzeria","Materiale grafico");
		tipoType.put( "telegramma","Testo a stampa");
		tipoType.put( "testo a stampa","Testo a stampa");
	}
	public static String normalize(String str){
		if(tipoType.get(str.toLowerCase())!=null)
			return tipoType.get(str.toLowerCase());
		return str;
	}
}
