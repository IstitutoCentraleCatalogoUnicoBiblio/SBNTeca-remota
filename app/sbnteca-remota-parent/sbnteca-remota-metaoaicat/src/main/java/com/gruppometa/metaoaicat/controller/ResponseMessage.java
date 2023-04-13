package com.gruppometa.metaoaicat.controller;

public class ResponseMessage{
	protected String message;
	protected String status = "ok";
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}