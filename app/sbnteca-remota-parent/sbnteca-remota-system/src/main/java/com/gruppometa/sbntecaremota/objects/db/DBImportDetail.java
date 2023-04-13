package com.gruppometa.sbntecaremota.objects.db;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the import_detail database table.
 * 
 */
@Entity
@Table(name="import_detail")
@NamedQuery(name="DBImportDetail.findAll", query="SELECT d FROM DBImportDetail d")
public class DBImportDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="base_install")
	private String baseInstall;

	@Lob
	private String configuration;

	@Column(name="flag_update")
	private int flagUpdate;

	@Column(name="indexed_mag")
	private int indexedMag;

	private int mag_KO;

	private int mag_OK;

	private int mets_KO;

	private int mets_OK;

	@Column(name="num_mag")
	private int numMag;

	@Column(name="processed_mag")
	private int processedMag;

	@Column(name="public_flag")
	private int publicFlag;

	@Column(name="dress_flag")
	private int dressFlag;

	private String source;

	@Column(name="timestamp_end_validation")
	private BigInteger timestampEndValidation;

	@Column(name="timestamp_start_validation")
	private BigInteger timestampStartValidation;

	@Column(name="usage_a")
	private String usageA;

	@Column(name="usage_e")
	private String usageE;

	//bi-directional many-to-one association to DBTecaProcess
	@ManyToOne
	@JoinColumn(name="id_job")
	private DBTecaProcess tecaProcess;

	public DBImportDetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBaseInstall() {
		return this.baseInstall;
	}

	public void setBaseInstall(String baseInstall) {
		this.baseInstall = baseInstall;
	}

	public String getConfiguration() {
		return this.configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public int getFlagUpdate() {
		return this.flagUpdate;
	}

	public void setFlagUpdate(int flagUpdate) {
		this.flagUpdate = flagUpdate;
	}

	public int getIndexedMag() {
		return this.indexedMag;
	}

	public void setIndexedMag(int indexedMag) {
		this.indexedMag = indexedMag;
	}

	public int getMag_KO() {
		return this.mag_KO;
	}

	public void setMag_KO(int mag_KO) {
		this.mag_KO = mag_KO;
	}

	public int getMag_OK() {
		return this.mag_OK;
	}

	public void setMag_OK(int mag_OK) {
		this.mag_OK = mag_OK;
	}

	public int getNumMag() {
		return this.numMag;
	}

	public void setNumMag(int numMag) {
		this.numMag = numMag;
	}

	public int getProcessedMag() {
		return this.processedMag;
	}

	public void setProcessedMag(int processedMag) {
		this.processedMag = processedMag;
	}

	public int getPublicFlag() {
		return this.publicFlag;
	}

	public void setPublicFlag(int publicFlag) {
		this.publicFlag = publicFlag;
	}

	public int getDressFlag() {
		return this.dressFlag;
	}

	public void setDressFlag(int dressFlag) {
		this.dressFlag = dressFlag;
	}
	
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public BigInteger getTimestampEndValidation() {
		return this.timestampEndValidation;
	}

	public void setTimestampEndValidation(BigInteger timestampEndValidation) {
		this.timestampEndValidation = timestampEndValidation;
	}

	public BigInteger getTimestampStartValidation() {
		return this.timestampStartValidation;
	}

	public void setTimestampStartValidation(BigInteger timestampStartValidation) {
		this.timestampStartValidation = timestampStartValidation;
	}

	public String getUsageA() {
		return this.usageA;
	}

	public void setUsageA(String usageA) {
		this.usageA = usageA;
	}

	public String getUsageE() {
		return this.usageE;
	}

	public void setUsageE(String usageE) {
		this.usageE = usageE;
	}

	public DBTecaProcess getTecaProcess() {
		return this.tecaProcess;
	}

	public void setTecaProcess(DBTecaProcess tecaProcess) {
		this.tecaProcess = tecaProcess;
	}

	public int getMets_KO() {
		return mets_KO;
	}

	public void setMets_KO(int mets_KO) {
		this.mets_KO = mets_KO;
	}

	public int getMets_OK() {
		return mets_OK;
	}

	public void setMets_OK(int mets_OK) {
		this.mets_OK = mets_OK;
	}
}