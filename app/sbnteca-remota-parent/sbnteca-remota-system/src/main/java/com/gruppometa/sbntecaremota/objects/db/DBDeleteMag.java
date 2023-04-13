package com.gruppometa.sbntecaremota.objects.db;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the delete_mag database table.
 * 
 */
@Entity
@Table(name="delete_mag")
@NamedQuery(name="DBDeleteMag.findAll", query="SELECT d FROM DBDeleteMag d")
public class DBDeleteMag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="id_mag")
	private String idMag;

	//bi-directional many-to-one association to DBTecaProcess
	@ManyToOne
	@JoinColumn(name="id_job")
	private DBTecaProcess tecaProcess;

	public DBDeleteMag() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdMag() {
		return this.idMag;
	}

	public void setIdMag(String idMag) {
		this.idMag = idMag;
	}

	public DBTecaProcess getTecaProcess() {
		return this.tecaProcess;
	}

	public void setTecaProcess(DBTecaProcess tecaProcess) {
		this.tecaProcess = tecaProcess;
	}

}