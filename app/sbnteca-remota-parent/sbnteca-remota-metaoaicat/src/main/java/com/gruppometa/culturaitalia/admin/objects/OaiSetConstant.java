package com.gruppometa.culturaitalia.admin.objects;

public class OaiSetConstant {
	public OaiSetConstant(){
		
	}
	public OaiSetConstant(String fieldName, String value) {
		this.fieldName = fieldName;
		this.value = value;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fiedlName) {
		this.fieldName = fiedlName;
	}
	public String getValue() { 
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	protected String fieldName;
	protected String value;
}
