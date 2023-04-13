package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonMagPresentationDis implements Serializable {
	private static final long serialVersionUID = -5624669210152005708L;
	
	// dis items
	@JsonProperty("dis_item")
	private List<JsonMagPresentationDisItem> disItems = 
			new ArrayList<JsonMagPresentationDisItem>();
	
	
	
	public List<JsonMagPresentationDisItem> getDisItems() {
		return disItems;
	}
	
	public void setDisItems(List<JsonMagPresentationDisItem> disItems) {
		this.disItems = disItems;
	}

}
