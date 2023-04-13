package com.gruppometa.sbntecaremota.objects.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the project_history database table.
 * 
 */
@Entity
@Table(name="project_history")
@NamedQuery(name="DBProjectHistory.findAll", query="SELECT d FROM DBProjectHistory d")
public class DBProjectHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="import_type")
	private int importType;

	@Column(name="indexed_mag")
	private int indexedMag;

	@Column(name="operation_type")
	@Enumerated(EnumType.STRING)
	private ProjectOperation operationType;

	@Column(name="processed_mag")
	private int processedMag;

	private int status;

	@Column(name="timestamp_operation")
	private BigInteger timestampOperation;

	//bi-directional many-to-one association to DBProject
	@ManyToOne
	@JoinColumn(name="id_project")
	private DBProject project;

	//bi-directional many-to-one association to DBTecaProcess
	@ManyToOne
	@JoinColumn(name="id_job")
	private DBTecaProcess tecaProcess;

	public DBProjectHistory() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getImportType() {
		return this.importType;
	}

	public void setImportType(int importType) {
		this.importType = importType;
	}

	public int getIndexedMag() {
		return this.indexedMag;
	}

	public void setIndexedMag(int indexedMag) {
		this.indexedMag = indexedMag;
	}

	public ProjectOperation getOperationType() {
		return this.operationType;
	}

	public void setOperationType(ProjectOperation operationType) {
		this.operationType = operationType;
	}

	public int getProcessedMag() {
		return this.processedMag;
	}

	public void setProcessedMag(int processedMag) {
		this.processedMag = processedMag;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public BigInteger getTimestampOperation() {
		return this.timestampOperation;
	}

	public void setTimestampOperation(BigInteger timestampOperation) {
		this.timestampOperation = timestampOperation;
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