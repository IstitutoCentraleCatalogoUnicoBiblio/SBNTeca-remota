package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonMagPresentationUsageResourceList implements Serializable {
	private static final long serialVersionUID = 5331911025020166584L;
	
	// usage
	private String usage;
	
	// esterne
	private boolean export = true;
	
	// risorse
	@JsonProperty("risorse_digitali")
	private List<JsonMagPresentationResource> resources = new ArrayList<JsonMagPresentationResource>();
	
	
	
	public String getUsage() {
		return usage;
	}
	
	public void setUsage(String usage) {
		this.usage = usage;
	}
	
	public boolean getExport() {
		return export;
	}
	
	public void setExport(boolean export) {
		this.export = export;
	}
	
	public List<JsonMagPresentationResource> getResources() {
		return resources;
	}
	
	public void setResources(List<JsonMagPresentationResource> resources) {
		this.resources = resources;
	}

}
