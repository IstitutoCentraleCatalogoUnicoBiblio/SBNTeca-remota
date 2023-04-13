package com.gruppometa.sbntecaremota.objects.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the import_mag database table.
 * 
 */
@Entity
@Table(name="import_mag")
@NamedQuery(name="DBImportMag.findAll", query="SELECT d FROM DBImportMag d")
public class DBImportMag implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Lob
	private String errors;

	@Lob
	@Column(name="fatal_errors")
	private String fatalErrors;

	private String mag;

	private String result;

	private String status;

	@Column(name="status_message")
	private String statusMessage;

	@Column(name="validation_time")
	private BigInteger validationTime;

	@Lob
	private String warnings;

	//bi-directional many-to-one association to DBProject
	@ManyToOne
	@JoinColumn(name="id_project")
	private DBProject project;

	//bi-directional many-to-one association to DBTecaProcess
	@ManyToOne
	@JoinColumn(name="id_job")
	private DBTecaProcess tecaProcess;

	public DBImportMag() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getErrors() {
		return this.errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

	public String getFatalErrors() {
		return this.fatalErrors;
	}

	public void setFatalErrors(String fatalErrors) {
		this.fatalErrors = fatalErrors;
	}

	public String getMag() {
		return this.mag;
	}

	public void setMag(String mag) {
		this.mag = mag;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusMessage() {
		return this.statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public BigInteger getValidationTime() {
		return this.validationTime;
	}

	public void setValidationTime(BigInteger validationTime) {
		this.validationTime = validationTime;
	}

	public String getWarnings() {
		return this.warnings;
	}

	public void setWarnings(String warnings) {
		this.warnings = warnings;
	}

	public DBProject getProject() {
		return this.project;
	}

	public void setProject(DBProject project) {
		this.project = project;
	}

	public DBTecaProcess getTecaProcess() {
		return this.tecaProcess;
	}

	public void setTecaProcess(DBTecaProcess tecaProcess) {
		this.tecaProcess = tecaProcess;
	}

}