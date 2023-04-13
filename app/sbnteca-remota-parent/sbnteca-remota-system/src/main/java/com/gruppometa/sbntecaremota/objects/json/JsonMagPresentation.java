package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonMagPresentation implements Serializable {
	private static final long serialVersionUID = 9031395085953601549L;
	
	// import
	@JsonProperty("importazione")
	private JsonMagPresentationImport imp = new JsonMagPresentationImport();
	
	// gen
	private JsonMagPresentationGen gen = new JsonMagPresentationGen();
	
	// bib
	private JsonMagPresentationBib bib = new JsonMagPresentationBib();
	
	// stru
	private List<JsonMagPresentationStru> stru = new ArrayList<JsonMagPresentationStru>();
	
	// lista risorse
	@JsonProperty("risorse")
	private List<JsonMagPresentationResourceType> resources = new ArrayList<JsonMagPresentationResourceType>();
	
	// dis
	private JsonMagPresentationDis dis = new JsonMagPresentationDis();
	
	
	
	public JsonMagPresentationImport getImp() {
		return imp;
	}
	
	public void setImp(JsonMagPresentationImport imp) {
		this.imp = imp;
	}
	
	public JsonMagPresentationGen getGen() {
		return gen;
	}
	
	public void setGen(JsonMagPresentationGen gen) {
		this.gen = gen;
	}

	public JsonMagPresentationBib getBib() {
		return bib;
	}
	
	public void setBib(JsonMagPresentationBib bib) {
		this.bib = bib;
	}
	
	public List<JsonMagPresentationStru> getStru() {
		return stru;
	}
	
	public void setStru(List<JsonMagPresentationStru> stru) {
		this.stru = stru;
	}
	
	public List<JsonMagPresentationResourceType> getResources() {
		return resources;
	}
	
	public void setResources(List<JsonMagPresentationResourceType> resources) {
		this.resources = resources;
	}
	
	public JsonMagPresentationDis getDis() {
		return dis;
	}
	
	public void setDis(JsonMagPresentationDis dis) {
		this.dis = dis;
	}

}
