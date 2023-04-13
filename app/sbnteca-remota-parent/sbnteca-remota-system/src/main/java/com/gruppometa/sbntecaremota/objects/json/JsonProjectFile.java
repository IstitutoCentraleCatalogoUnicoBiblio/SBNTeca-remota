package com.gruppometa.sbntecaremota.objects.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * JSON di richiesta per servizio di restituzione documenti MAG di un progetto
 * 
 *
 */
public class JsonProjectFile implements Serializable, Comparable<JsonProjectFile> {
	private static final long serialVersionUID = -1446287060391076662L;
	
	public static final String TYPE_DIRECTORY = "directory";
	public static final String TYPE_IMG = "img";
	public static final String TYPE_AUDIO = "audio";
	public static final String TYPE_VIDEO = "video";
	public static final String TYPE_OCR = "ocr";
	public static final String TYPE_DOC = "doc";
	
	
	@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
	private String id;
	
	// nome file
	@JsonProperty("nome")
	private String name;
	
	// tipo
	@JsonProperty("tipo")
	private String type;
	
	// label
	@JsonProperty("label")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String label;
	
	// file figli
	@JsonProperty("contenuto_directory")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JsonProjectFile> children = new ArrayList<JsonProjectFile>();
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public List<JsonProjectFile> getChildren() {
		return children;
	}
	
	public void setChildren(List<JsonProjectFile> children) {
		this.children = children;
	}
	
	public static void sort(List<JsonProjectFile> files) {
		for(JsonProjectFile jpf : files)
			sort(jpf.getChildren());
		
		Collections.sort(files);
	}

	@Override
	public int compareTo(JsonProjectFile o) {
		return this.name.compareTo(o.getName());
	}
	
	

}
