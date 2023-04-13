package com.gruppometa.sbntecaremota.objects.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;


/**
 * The persistent class for the teca_process database table.
 * 
 */
@Entity
@Table(name="teca_process")
@NamedQuery(name="DBTecaProcess.findAll", query="SELECT d FROM DBTecaProcess d")
public class DBTecaProcess implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Lob
	private String message;

	private int status;

	@Column(name="timestamp_end")
	private BigInteger timestampEnd;

	@Column(name="timestamp_start")
	private BigInteger timestampStart;

	//bi-directional many-to-one association to DBDeleteMag
	@OneToMany(mappedBy="tecaProcess")
	private List<DBDeleteMag> deleteMags;

	//bi-directional many-to-one association to DBImportDetail
	@OneToMany(mappedBy="tecaProcess")
	private List<DBImportDetail> importDetails;

	//bi-directional many-to-one association to DBImportMag
	@OneToMany(mappedBy="tecaProcess")
	private List<DBImportMag> importMags;

	//bi-directional many-to-one association to DBProjectHistory
	@OneToMany(mappedBy="tecaProcess")
	private List<DBProjectHistory> projectHistories;

	//bi-directional many-to-one association to DBPublicationMag
	@OneToMany(mappedBy="tecaProcess")
	private List<DBPublicationMag> publicationMags;

	//bi-directional many-to-one association to DBTecaUser
	@ManyToOne
	@JoinColumn(name="id_user")
	private DBTecaUser tecaUser;

	public DBTecaProcess() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public BigInteger getTimestampEnd() {
		return this.timestampEnd;
	}

	public void setTimestampEnd(BigInteger timestampEnd) {
		this.timestampEnd = timestampEnd;
	}

	public BigInteger getTimestampStart() {
		return this.timestampStart;
	}

	public void setTimestampStart(BigInteger timestampStart) {
		this.timestampStart = timestampStart;
	}

	public List<DBDeleteMag> getDeleteMags() {
		return this.deleteMags;
	}

	public void setDeleteMags(List<DBDeleteMag> deleteMags) {
		this.deleteMags = deleteMags;
	}

	public DBDeleteMag addDeleteMag(DBDeleteMag deleteMag) {
		getDeleteMags().add(deleteMag);
		deleteMag.setTecaProcess(this);

		return deleteMag;
	}

	public DBDeleteMag removeDeleteMag(DBDeleteMag deleteMag) {
		getDeleteMags().remove(deleteMag);
		deleteMag.setTecaProcess(null);

		return deleteMag;
	}

	public List<DBImportDetail> getImportDetails() {
		return this.importDetails;
	}

	public void setImportDetails(List<DBImportDetail> importDetails) {
		this.importDetails = importDetails;
	}

	public DBImportDetail addImportDetail(DBImportDetail importDetail) {
		getImportDetails().add(importDetail);
		importDetail.setTecaProcess(this);

		return importDetail;
	}

	public DBImportDetail removeImportDetail(DBImportDetail importDetail) {
		getImportDetails().remove(importDetail);
		importDetail.setTecaProcess(null);

		return importDetail;
	}

	public List<DBImportMag> getImportMags() {
		return this.importMags;
	}

	public void setImportMags(List<DBImportMag> importMags) {
		this.importMags = importMags;
	}

	public DBImportMag addImportMag(DBImportMag importMag) {
		getImportMags().add(importMag);
		importMag.setTecaProcess(this);

		return importMag;
	}

	public DBImportMag removeImportMag(DBImportMag importMag) {
		getImportMags().remove(importMag);
		importMag.setTecaProcess(null);

		return importMag;
	}

	public List<DBProjectHistory> getProjectHistories() {
		return this.projectHistories;
	}

	public void setProjectHistories(List<DBProjectHistory> projectHistories) {
		this.projectHistories = projectHistories;
	}

	public DBProjectHistory addProjectHistory(DBProjectHistory projectHistory) {
		getProjectHistories().add(projectHistory);
		projectHistory.setTecaProcess(this);

		return projectHistory;
	}

	public DBProjectHistory removeProjectHistory(DBProjectHistory projectHistory) {
		getProjectHistories().remove(projectHistory);
		projectHistory.setTecaProcess(null);

		return projectHistory;
	}

	public List<DBPublicationMag> getPublicationMags() {
		return this.publicationMags;
	}

	public void setPublicationMags(List<DBPublicationMag> publicationMags) {
		this.publicationMags = publicationMags;
	}

	public DBPublicationMag addPublicationMag(DBPublicationMag publicationMag) {
		getPublicationMags().add(publicationMag);
		publicationMag.setTecaProcess(this);

		return publicationMag;
	}

	public DBPublicationMag removePublicationMag(DBPublicationMag publicationMag) {
		getPublicationMags().remove(publicationMag);
		publicationMag.setTecaProcess(null);

		return publicationMag;
	}

	public DBTecaUser getTecaUser() {
		return this.tecaUser;
	}

	public void setTecaUser(DBTecaUser tecaUser) {
		this.tecaUser = tecaUser;
	}

}