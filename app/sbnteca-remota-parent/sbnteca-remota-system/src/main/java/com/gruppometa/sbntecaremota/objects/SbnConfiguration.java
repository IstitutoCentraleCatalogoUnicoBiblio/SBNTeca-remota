package com.gruppometa.sbntecaremota.objects;

import java.io.Serializable;

public class SbnConfiguration implements Serializable {
	private static final long serialVersionUID = 3407624801106235660L;
	
	// tipo di richiesta (OPAC/SBNMARC)
	private String requestType;
	
	// URL (SBNMARC)
	private String requestUrl;
	
	// username (SBNMARC)
	private String requestUsername;
	
	
	public String getRequestType() {
		return requestType;
	}
	
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	
	public String getRequestUrl() {
		return requestUrl;
	}
	
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	
	public String getRequestUsername() {
		return requestUsername;
	}
	
	public void setRequestUsername(String requestUsername) {
		this.requestUsername = requestUsername;
	}

}
