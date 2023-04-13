package com.gruppometa.sbntecaremota.objects.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;


/**
 * The persistent class for the project database table.
 * 
 */
@Entity
@Table(name="project")
@NamedQuery(name="DBProject.findAll", query="SELECT d FROM DBProject d")
public class DBProject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="last_modified")
	private BigInteger lastModified;

	@Column(name="last_operation")
	@Enumerated(EnumType.STRING)
	private ProjectOperation lastOperation;

	private String name;

	private int status;

	//bi-directional many-to-one association to DBImportMag
	@OneToMany(mappedBy="project")
	private List<DBImportMag> importMags;

	//bi-directional many-to-one association to DBProjectHistory
	@OneToMany(fetch = FetchType.EAGER, mappedBy="project", cascade = CascadeType.ALL)
	private List<DBProjectHistory> projectHistories;

	public DBProject() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigInteger getLastModified() {
		return this.lastModified;
	}

	public void setLastModified(BigInteger lastModified) {
		this.lastModified = lastModified;
	}

	public ProjectOperation getLastOperation() {
		return this.lastOperation;
	}

	public void setLastOperation(ProjectOperation lastOperation) {
		this.lastOperation = lastOperation;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<DBImportMag> getImportMags() {
		return this.importMags;
	}

	public void setImportMags(List<DBImportMag> importMags) {
		this.importMags = importMags;
	}

	public DBImportMag addImportMag(DBImportMag importMag) {
		getImportMags().add(importMag);
		importMag.setProject(this);

		return importMag;
	}

	public DBImportMag removeImportMag(DBImportMag importMag) {
		getImportMags().remove(importMag);
		importMag.setProject(null);

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
		projectHistory.setProject(this);

		return projectHistory;
	}

	public DBProjectHistory removeProjectHistory(DBProjectHistory projectHistory) {
		getProjectHistories().remove(projectHistory);
		projectHistory.setProject(null);

		return projectHistory;
	}

}