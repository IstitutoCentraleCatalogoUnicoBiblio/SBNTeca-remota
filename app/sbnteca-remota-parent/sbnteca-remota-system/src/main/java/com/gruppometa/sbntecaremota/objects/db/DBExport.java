package com.gruppometa.sbntecaremota.objects.db;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the export database table.
 * 
 */
@Entity
@Table(name="export")
@NamedQuery(name="DBExport.findAll", query="SELECT d FROM DBExport d")
public class DBExport implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String file;

	private String message;

	private int status;

	//bi-directional many-to-one association to DBTecaUser
	@ManyToOne
	@JoinColumn(name="id_user")
	private DBTecaUser tecaUser;

	public DBExport() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFile() {
		return this.file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public DBTecaUser getTecaUser() {
		return this.tecaUser;
	}

	public void setTecaUser(DBTecaUser tecaUser) {
		this.tecaUser = tecaUser;
	}

}