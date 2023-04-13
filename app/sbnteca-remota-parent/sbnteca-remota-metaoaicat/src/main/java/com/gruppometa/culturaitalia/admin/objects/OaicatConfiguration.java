package com.gruppometa.culturaitalia.admin.objects;

import java.util.List;

/**
 * L'insioem delle configurazione del Provider OAI.
 */
public class OaicatConfiguration {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getServletName() {
		return servletName;
	}
	public void setServletName(String servletName) {
		this.servletName = servletName;
	}
	public List<OaicatConfigItem> getItems() {
		return items;
	}
	public void setItems(List<OaicatConfigItem> items) {
		this.items = items;
	}
	protected int id;
	protected String servletName;
	protected List<OaicatConfigItem> items;	
}
