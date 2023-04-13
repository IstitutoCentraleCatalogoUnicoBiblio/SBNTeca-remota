package com.gruppometa.magteca.frontend.object;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


public class JsonUser implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	private String email;

	@JsonProperty("abilitato")
	private boolean enabled = true;

	private String password;

	private String username;

	//bi-directional many-to-one association to UserRole
	@JsonProperty("ruolo")
	private JsonRole userRole;

	@JsonProperty("nome")
	private String name;

	@JsonProperty("cognome")
	private String surname;
	
	

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public JsonRole getUserRole() {
		return this.userRole;
	}

	public void setUserRole(JsonRole userRole) {
		this.userRole = userRole;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

}