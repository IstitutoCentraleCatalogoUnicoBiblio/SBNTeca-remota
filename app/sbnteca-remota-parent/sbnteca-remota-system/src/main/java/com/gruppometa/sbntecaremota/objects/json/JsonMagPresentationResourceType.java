package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonMagPresentationResourceType implements Serializable {
	private static final long serialVersionUID = 8763367962084829883L;
	
	// tipo risorsa digitale
	@JsonProperty("tipo")
	private String type;
	
	// lista per usages
	@JsonProperty("lista_usage")
	private List<JsonMagPresentationUsageResourceList> usages = new ArrayList<JsonMagPresentationUsageResourceList>();
	
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public List<JsonMagPresentationUsageResourceList> getUsages() {
		return usages;
	}
	
	public void setUsages(List<JsonMagPresentationUsageResourceList> usages) {
		this.usages = usages;
	}

}
