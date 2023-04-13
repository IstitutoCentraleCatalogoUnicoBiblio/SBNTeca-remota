package com.gruppometa.culturaitalia.admin.objects;

public class OaiSetProfile {
	public OaiSetProfile(String name) {
		this.name = name;
	}
	public OaiSetProfile() {
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected String name;
}
