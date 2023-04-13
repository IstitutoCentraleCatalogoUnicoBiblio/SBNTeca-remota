package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;


public class JsonMagEditorHoldings implements Serializable {
	private static final long serialVersionUID = 699200597795775994L;
	
	// id
	private String id;
	
	// localizzazione
	private String library;
	
	// numero di inventory
	@JsonProperty("inventory_number")
	private String inventoryNumber;
	
	// segnature
	@JsonProperty("shelfmark")
	private List<Map<String, String>> shelfmarks = new ArrayList<Map<String, String>>();
	
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLibrary() {
		return library;
	}
	
	public void setLibrary(String library) {
		this.library = library;
	}

	public String getInventoryNumber() {
		return inventoryNumber;
	}
	
	public void setInventoryNumber(String inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}
	
	public List<Map<String, String>> getShelfmarks() {
		return shelfmarks;
	}
	
	public void setShelfmarks(List<Map<String, String>> shelfmarks) {
		this.shelfmarks = shelfmarks;
	}

}
