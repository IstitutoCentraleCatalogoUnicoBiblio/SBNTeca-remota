package com.gruppometa.magteca.frontend.object;

import java.io.Serializable;

public class OaisetLimiter implements Serializable {
	private static final long serialVersionUID = -508140934401453923L;
	
	// limiter
	private String limiter;
	
	// valore
	private String value;
	
	
	
	public String getLimiter() {
		return limiter;
	}
	
	public void setLimiter(String limiter) {
		this.limiter = limiter;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}

}
