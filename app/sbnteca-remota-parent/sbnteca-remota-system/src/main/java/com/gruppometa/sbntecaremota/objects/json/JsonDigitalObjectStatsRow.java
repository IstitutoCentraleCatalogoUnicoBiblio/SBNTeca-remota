package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonDigitalObjectStatsRow implements Serializable {
	private static final long serialVersionUID = 8116586639122841729L;
	
	// lista valori
	@JsonProperty("valori")
	private List<String> values = new ArrayList<String>();

	// numero documenti
	@JsonProperty("numero_documenti")
	private long count = 0;
	
    // numero immagini
	@JsonProperty("numero_immagini")
    private int numberImg = 0;
    
    // numero video
	@JsonProperty("numero_video")
    private int numberVideo = 0;
    
    // numero audio
	@JsonProperty("numero_audio")
    private int numberAudio = 0;
    
    // numero OCR
	@JsonProperty("numero_ocr")
    private int numberOcr = 0;
    
    // numero documenti
	@JsonProperty("numero_doc")
    private int numberDoc = 0;
	
	
	
	/**
	 * Restituisce la lista dei valori raggruppati
	 * 
	 * @return List<String> lista dei valori raggruppati
	 */
	public List<String> getValues() {
		return values;
	}
	
	/**
	 * Imposta la lista dei valori raggruppati
	 * 
	 * @param values lista dei valori raggruppati
	 */
	public void setValues(List<String> values) {
		this.values = values;
	}
	
	/**
	 * Restituisce il numero di documenti
	 * 
	 * @return long numero di documenti
	 */
	public long getCount() {
		return count;
	}
	
	/**
	 * Imposta il numero di documenti
	 * 
	 * @param count numero di documenti
	 */
	public void setCount(long count) {
		this.count = count;
	}

	/**
	 * Restituisce il numero delle risorse di tipo immagine (IMG)
	 * 
	 * @return int numero delle risorse di tipo immagine (IMG)
	 */
	public int getNumberImg() {
		return numberImg;
	}

	/**
	 * Imposta il numero delle risorse di tipo immagine (IMG)
	 * 
	 * @param numberImg numero delle risorse di tipo immagine (IMG)
	 */
	public void setNumberImg(int numberImg) {
		this.numberImg = numberImg;
	}

	/**
	 * Restituisce il numero delle risorse di tipo video (VIDEO)
	 * 
	 * @return int numero delle risorse di tipo video (VIDEO)
	 */
	public int getNumberVideo() {
		return numberVideo;
	}

	/**
	 * Imposta il numero delle risorse di tipo video (VIDEO)
	 * 
	 * @param numberVideo numero delle risorse di tipo video (VIDEO)
	 */
	public void setNumberVideo(int numberVideo) {
		this.numberVideo = numberVideo;
	}

	/**
	 * Restituisce il numero delle risorse di tipo audio (AUDIO)
	 * 
	 * @return int numero delle risorse di tipo audio (AUDIO)
	 */
	public int getNumberAudio() {
		return numberAudio;
	}

	/**
	 * Imposta il numero delle risorse di tipo audio (AUDIO)
	 * 
	 * @param numberAudio numero delle risorse di tipo audio (AUDIO)
	 */
	public void setNumberAudio(int numberAudio) {
		this.numberAudio = numberAudio;
	}

	/**
	 * Restituisce il numero delle risorse di tipo ocr (OCR)
	 * 
	 * @return int numero delle risorse di tipo ocr (OCR)
	 */
	public int getNumberOcr() {
		return numberOcr;
	}

	/**
	 * Imposta il numero delle risorse di tipo ocr (OCR)
	 * 
	 * @param numberOcr numero delle risorse di tipo ocr (OCR)
	 */
	public void setNumberOcr(int numberOcr) {
		this.numberOcr = numberOcr;
	}

	/**
	 * Restituisce il numero delle risorse di tipo documento (DOC)
	 * 
	 * @return int numero delle risorse di tipo documento (DOC)
	 */
	public int getNumberDoc() {
		return numberDoc;
	}

	/**
	 * Imposta il numero delle risorse di tipo documento (DOC)
	 * 
	 * @param numberDoc numero delle risorse di tipo documento (DOC)
	 */
	public void setNumberDoc(int numberDoc) {
		this.numberDoc = numberDoc;
	}

}
