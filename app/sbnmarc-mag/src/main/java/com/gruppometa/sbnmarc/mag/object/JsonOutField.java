package com.gruppometa.sbnmarc.mag.object;

import java.util.ArrayList;
import java.util.List;

public class JsonOutField {
	protected String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	protected String group;
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	protected boolean isbd=false;
	protected boolean hidden=false;
	protected double order;
	protected boolean forceHtml = false;

	public boolean isForceHtml() {
		return forceHtml;
	}

	public void setForceHtml(boolean forceHtml) {
		this.forceHtml = forceHtml;
	}

	public boolean isIsbd() {
		return isbd;
	}
	public void setIsbd(boolean isbd) {
		this.isbd = isbd;
	}
	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	public double getOrder() {
		return order;
	}
	public void setOrder(double order) {
		this.order = order;
	}
	protected List<JsonValue> values = new ArrayList<JsonValue>();
	public List<JsonValue> getValues() {
		return values;
	}
	public void setValues(List<JsonValue> values) {
		this.values = values;
	}

}
