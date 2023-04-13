package com.gruppometa.culturaitalia.admin.objects;

public class OaiSetLimiter {
	public OaiSetLimiter(){}
	public OaiSetLimiter(String limiter, String value) {
		this.limiter = limiter;
		this.value = value;
	}
	public String getLimiter() {
		return limiter;
	}
	public void setLimiter(String limiter) {
		this.limiter = limiter;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	protected String limiter;
	protected String value;
}
