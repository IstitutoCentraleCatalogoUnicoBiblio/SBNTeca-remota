package com.gruppometa.sbntecaremota.objects.json;

public class JsonUserOpMessage {
	
	public static final String OK = "OK";
	public static final String KO = "KO";
	
	// risultato
	private String result;
	
	// messaggio di errore
	private String message;
	
	
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

}
