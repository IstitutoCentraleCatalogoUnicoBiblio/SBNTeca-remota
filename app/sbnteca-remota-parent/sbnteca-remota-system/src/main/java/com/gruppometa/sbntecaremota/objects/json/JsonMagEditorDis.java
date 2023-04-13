package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class JsonMagEditorDis implements Serializable {
	private static final long serialVersionUID = -5624669210152005708L;
	
	// dis items
	@JsonProperty("dis_item")
	private List<JsonMagEditorDisItem> disItems = 
			new ArrayList<JsonMagEditorDisItem>();
	
	
	
	public List<JsonMagEditorDisItem> getDisItems() {
		return disItems;
	}
	
	public void setDisItems(List<JsonMagEditorDisItem> disItems) {
		this.disItems = disItems;
	}

}
