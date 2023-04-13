package com.gruppometa.sbntecaremota.objects.db;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the oai_identifier database table.
 * 
 */
@Entity
@Table(name="oai_identifier")
@NamedQuery(name="DBOaiIdentifier.findAll", query="SELECT o FROM DBOaiIdentifier o")
public class DBOaiIdentifier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String identifier;

	private int count;

	public DBOaiIdentifier() {
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public int getCount() {
		return this.count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}