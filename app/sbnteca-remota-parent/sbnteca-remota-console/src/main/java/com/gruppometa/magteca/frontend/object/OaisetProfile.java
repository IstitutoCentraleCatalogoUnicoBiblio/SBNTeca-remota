package com.gruppometa.magteca.frontend.object;

import java.io.Serializable;

public class OaisetProfile implements Serializable {
	private static final long serialVersionUID = -4007268457539178664L;
	
	// nome profilo
	private String name;
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
