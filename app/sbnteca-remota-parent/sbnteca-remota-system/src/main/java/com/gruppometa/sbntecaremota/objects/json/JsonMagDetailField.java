package com.gruppometa.sbntecaremota.objects.json;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class JsonMagDetailField implements Serializable {
	private static final long serialVersionUID = -7766003729257655042L;
	
	// name
	private String name;
	
	// label
	private String label;
	
	// value
	@JsonSerialize(include= JsonSerialize.Inclusion.NON_NULL)
	private Object value;

	// value
	@JsonSerialize(include= JsonSerialize.Inclusion.NON_EMPTY)
	private List<Object> values = new ArrayList<Object>();
	
	
	
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
	
	public Object getValue() {
		return value;
	}
	
	public void setValue(Object value) {
		this.value = value;
	}

	public List<Object> getValues() {
		return values;
	}
	
	public void setValues(List<Object> values) {
		this.values = values;
	}

}
