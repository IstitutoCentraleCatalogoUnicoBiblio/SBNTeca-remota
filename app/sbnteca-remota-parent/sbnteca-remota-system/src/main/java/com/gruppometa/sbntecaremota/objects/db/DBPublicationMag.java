package com.gruppometa.sbntecaremota.objects.db;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the publication_mag database table.
 * 
 */
@Entity
@Table(name="publication_mag")
@NamedQuery(name="DBPublicationMag.findAll", query="SELECT d FROM DBPublicationMag d")
public class DBPublicationMag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="id_mag")
	private String idMag;

	private int publicate;

	//bi-directional many-to-one association to DBTecaProcess
	@ManyToOne
	@JoinColumn(name="id_job")
	private DBTecaProcess tecaProcess;

	public DBPublicationMag() {
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

	public int getPublicate() {
		return this.publicate;
	}

	public void setPublicate(int publicate) {
		this.publicate = publicate;
	}

	public DBTecaProcess getTecaProcess() {
		return this.tecaProcess;
	}

	public void setTecaProcess(DBTecaProcess tecaProcess) {
		this.tecaProcess = tecaProcess;
	}

}