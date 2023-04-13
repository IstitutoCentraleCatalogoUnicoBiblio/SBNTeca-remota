package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonMagPresentationHoldings implements Serializable {
	private static final long serialVersionUID = 699200597795775994L;
	
	// id
	private String id;
	
	// localizzazione
	@JsonProperty("biblioteca")
	private String library;
	
	// numero di inventory
	@JsonProperty("numero_inventario")
	private String inventoryNumber;
	
	// segnature
	@JsonProperty("segnature")
	private List<String> shelfmarks = new ArrayList<String>();
	
	
	
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
	
	public List<String> getShelfmarks() {
		return shelfmarks;
	}
	
	public void setShelfmarks(List<String> shelfmarks) {
		this.shelfmarks = shelfmarks;
	}

}
