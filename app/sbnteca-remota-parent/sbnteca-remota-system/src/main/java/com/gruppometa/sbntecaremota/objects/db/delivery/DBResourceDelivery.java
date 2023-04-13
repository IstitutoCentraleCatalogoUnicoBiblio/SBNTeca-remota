package com.gruppometa.sbntecaremota.objects.db.delivery;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the resource_delivery database table.
 * 
 */
// @Entity
// @Table(name="resource_delivery")
// @NamedQuery(name="DBResourceDelivery.findAll", query="SELECT d FROM DBResourceDelivery d")
public class DBResourceDelivery implements Serializable {
	private static final long serialVersionUID = 1L;

	// @Id
	private String id;

	private String name;

	// @Lob
	// @Column(name="resource_bin")
	private byte[] resourceBin;

	// @Column(name="resource_path")
	private String resourcePath;

	// @Column(name="usage_external")
	private String usageExternal;

	// @Column(name="usage_internal")
	private String usageInternal;

	public DBResourceDelivery() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getResourceBin() {
		return this.resourceBin;
	}

	public void setResourceBin(byte[] resourceBin) {
		this.resourceBin = resourceBin;
	}

	public String getResourcePath() {
		return this.resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public String getUsageExternal() {
		return this.usageExternal;
	}

	public void setUsageExternal(String usageExternal) {
		this.usageExternal = usageExternal;
	}

	public String getUsageInternal() {
		return this.usageInternal;
	}

	public void setUsageInternal(String usageInternal) {
		this.usageInternal = usageInternal;
	}

}