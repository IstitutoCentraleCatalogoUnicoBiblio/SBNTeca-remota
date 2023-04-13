package com.gruppometa.culturaitalia.admin.objects;

/**
 * Item per la configuraizone del provider OAI.
 *
 */
public class OaicatConfigItem {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public String getFormName() {
		return name!=null?name.replaceAll("\\.", "_"):null;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	protected int id;
	protected String name;
	protected String value;
}
