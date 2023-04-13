package com.gruppometa.culturaitalia.skos;


import java.util.ArrayList;
import java.util.List;


public class SkosItem {
	public List<String> getThemes() {
		return themes;
	}
	public void setThemesString(String themes) {
		if(themes==null)
			return;
		String[] ts = themes.split(",");
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < ts.length; i++) {
			list.add(ts[i]);
		}
		this.themes = list;
	}
	public String getThemesString(){
		String strs = "";
		if(this.themes==null)
			return null;
		for (String str : themes) {
			if(strs.length()>0)  //
				strs+=",";
			strs+=str;
		} 
		return strs;
	}
	public void setThemes(List<String> themes) {
		this.themes = themes;
	}
	protected int id;
	protected int revision;
	protected String thesaurusVersion;
	protected String linked=null;
	protected String relatedMatch=null;
	protected long hits=0;
	protected String status;
	protected String oldName;
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getHits() {
		return hits;
	}
	public void setHits(long hits) {
		this.hits = hits;
	}
	public List<String> getProviders() {
		return providers;
	}
	public void setProviders(List<String> providers) {
		this.providers = providers;
	}
	protected List<String> providers =null;  
	public String getRelatedMatch() {
		return relatedMatch;
	}
	public void setRelatedMatch(String relatedMatch) {
		this.relatedMatch = relatedMatch;
	}
	public boolean isRemovedd(){
		return removed!=null && removed.equalsIgnoreCase("true");
	}
	protected String removed=null;
	public String getLinked() {
		return linked;
	}
	public void setLinked(String linked) {
		this.linked = linked;
	}
	public String getRemoved() {
		return removed;
	}
	public void setRemoved(String removed) {
		this.removed = removed;
	}
	public int getRevision() {
		return revision;
	}
	public void setRevision(int revision) {
		this.revision = revision;
	}
	public String getThesaurusVersion() {
		return thesaurusVersion;
	}
	public void setThesaurusVersion(String thesaurusVersion) {
		this.thesaurusVersion = thesaurusVersion;
	}
	protected String name;
	protected String namespace = "http://culturaitalia.it/pico/thesaurus/";
	protected String version;
	protected String key;
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getVersion() {
		if(version==null && name!=null && name.startsWith(namespace) && name.contains("#") && name.contains("/"))
			return name.substring(name.lastIndexOf("/")+1,name.indexOf("#"));		
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getKey() {
		if(key==null && name!=null && name.startsWith(namespace) && name.contains("#"))
			return name.substring(name.indexOf("#")+1);
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	protected String labelIt;
	protected String labelEn;
	protected String altLabelIt;
	protected String altLabelEn;
	public String getAltLabelIt() {
		return altLabelIt;
	}
	public void setAltLabelIt(String altLabelIt) {
		this.altLabelIt = altLabelIt;
	}
	public String getAltLabelEn() {
		return altLabelEn;
	}
	public void setAltLabelEn(String altLabelEn) {
		this.altLabelEn = altLabelEn;
	}
	protected String broader;
	protected List<String> themes;
	public String toString(){
		return name + " : " + labelIt + " / "+labelEn+ " -> "+broader;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param id the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the labelIt
	 */
	public String getLabelIt() {
		return labelIt;
	}
	/**
	 * @param labelIt the labelIt to set
	 */
	public void setLabelIt(String labelIt) {
		this.labelIt = labelIt;
	}
	/**
	 * @return the labelEn
	 */
	public String getLabelEn() {
		return labelEn;
	}
	/**
	 * @param labelEn the labelEn to set
	 */
	public void setLabelEn(String labelEn) {
		this.labelEn = labelEn;
	}
	/**
	 * @return the broader
	 */
	public String getBroader() {
		return broader;
	}
	/**
	 * @param broader the broader to set
	 */
	public void setBroader(String broader) {
		this.broader = broader;
	}
	public boolean isGreaterVersion(String otherVersion){
		if(otherVersion==null)
			return false;
		if(getVersion()==null)
			return true;
		return otherVersion.compareTo(getVersion())<0;
	}
}
