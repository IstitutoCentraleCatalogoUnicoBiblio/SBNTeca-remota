package com.gruppometa.sbntecaremota.objects.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import javax.persistence.*;



/**
 * The persistent class for the teca_user database table.
 * 
 */
@Entity
@Table(name="teca_user")
@NamedQuery(name="DBTecaUser.findAll", query="SELECT d FROM DBTecaUser d")
public class DBTecaUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String email;

	@JsonProperty("abilitato")
	private boolean enabled;

	@JsonProperty("nome")
	private String name;

	private String password;

	@JsonProperty("cognome")
	private String surname;

	private String username;
	
	@JsonIgnore
	private boolean deleted = false;

	//bi-directional many-to-one association to DBUserRole
	@ManyToOne
	@JoinColumn(name="id_role")
	@JsonProperty("ruolo")
	private DBUserRole userRole;

	public DBTecaUser() {
	}

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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public DBUserRole getUserRole() {
		return this.userRole;
	}

	public void setUserRole(DBUserRole userRole) {
		this.userRole = userRole;
	}

}