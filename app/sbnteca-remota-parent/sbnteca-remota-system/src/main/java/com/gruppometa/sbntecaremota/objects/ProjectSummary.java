package com.gruppometa.sbntecaremota.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProjectSummary implements Serializable {
	private static final long serialVersionUID = -47683081377796170L;
	
	// mag
	private List<String> mags = new ArrayList<String>();

	// digital objects
	private List<String> digitalObjects = new ArrayList<String>();
	
	
	
	public List<String> getMags() {
		return mags;
	}
	
	public void setMags(List<String> mags) {
		this.mags = mags;
	}
	
	public List<String> getDigitalObjects() {
		return digitalObjects;
	}
	
	public void setDigitalObjects(List<String> digitalObjects) {
		this.digitalObjects = digitalObjects;
	}

}
