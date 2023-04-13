package com.gruppometa.culturaitalia.skos;

public class Thesaurus {
	protected String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	protected String basedOn = null;
	public String getBasedOn() {
		return basedOn;
	}
	public void setBasedOn(String basedOn) {
		this.basedOn = basedOn;
	}
	protected String version;
	protected boolean usedByIndexSolr = false;
	protected boolean editable = true;
	protected boolean variant = false;
	protected boolean commited = false; 
	public boolean isCommited() {
		return commited;
	}
	public void setCommited(boolean commited) {
		this.commited = commited;
	}
	public boolean isVariant() {
		return variant;
	}
	public void setVariant(boolean variant) {
		this.variant = variant;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public boolean isUsedByIndexSolr() {
		return usedByIndexSolr;
	}
	public Version getVersionObj(){
		return new Version(version+"."+revision);
	}
	public void setUsedByIndexSolr(boolean usedByIndexSolr) {
		this.usedByIndexSolr = usedByIndexSolr;
	}
	protected int revision;
	protected String namespace;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getRevision() {
		return revision;
	}
	public void setRevision(int revision) {
		this.revision = revision;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
}
