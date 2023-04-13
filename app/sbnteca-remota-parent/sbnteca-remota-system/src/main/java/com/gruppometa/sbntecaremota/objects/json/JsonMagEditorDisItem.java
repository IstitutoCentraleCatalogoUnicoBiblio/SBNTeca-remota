package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;

public class JsonMagEditorDisItem implements Serializable {
	private static final long serialVersionUID = -8470935923534634808L;
	
	// file
	private String file;
	
	// preview
	private String preview;
	
	// available
	private String available;
	
	
	
	public String getFile() {
		return file;
	}
	
	public void setFile(String file) {
		this.file = file;
	}
	
	public String getPreview() {
		return preview;
	}
	
	public void setPreview(String preview) {
		this.preview = preview;
	}
	
	public String getAvailable() {
		return available;
	}
	
	public void setAvailable(String available) {
		this.available = available;
	}

}
