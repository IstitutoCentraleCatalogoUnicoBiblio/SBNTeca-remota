package com.gruppometa.culturaitalia.admin.objects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Set OAI per la configurazione del provider OAI. 
 *
 */
public class OaiSet {
	public List<OaiSetLimiter> getLimiters() {
		return limiters;
	}
	public void setLimiters(List<OaiSetLimiter> limiters) {
		this.limiters = limiters;
	}
	public List<OaiSetConstant> getConstants() {
		return constants;
	}
	public void setConstants(List<OaiSetConstant> constants) {
		this.constants = constants;
	}
	/*
	public String getLimiter1Value() {
		return limiter1Value;
	}
	public void setLimiter1Value(String limiter1Value) {
		this.limiter1Value = limiter1Value;
	}
	public String getLimiter2Value() {
		return limiter2Value;
	}
	public void setLimiter2Value(String limiter2Value) {
		this.limiter2Value = limiter2Value;
	}
	public String getLimiter3Value() {
		return limiter3Value;
	}
	public void setLimiter3Value(String limiter3Value) {
		this.limiter3Value = limiter3Value;
	}
	public String getLimiter1() {
		return limiter1;
	}
	public void setLimiter1(String limiter1) {
		this.limiter1 = limiter1;
	}
	public String getLimiter2() {
		return limiter2;
	}
	public void setLimiter2(String limiter2) {
		this.limiter2 = limiter2;
	}
	public String getLimiter3() {
		return limiter3;
	}
	public void setLimiter3(String limiter3) {
		this.limiter3 = limiter3;
	}
	*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription_it() {
		return description_it;
	}
	public void setDescription_it(String description_it) {
		this.description_it = description_it;
	}
	public String getDescription_en() {
		return description_en;
	}
	public void setDescription_en(String description_en) {
		this.description_en = description_en;
	}
	public String getServletName() {
		return servletName;
	}
	public void setServletName(String servletName) {
		this.servletName = servletName;
	}
	protected int id;
	protected String spec;
	protected String name;
	protected String description_it;
	protected String description_en;
	protected String servletName;
	protected String project;
	protected String solrquery;
	public String getSolrquery() {
		return solrquery;
	}
	public void setSolrquery(String solrquery) {
		this.solrquery = solrquery;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	protected String type;
	/*
	protected String limiter1;
	protected String limiter2;
	protected String limiter3;
	protected String limiter1Value;
	protected String limiter2Value;
	protected String limiter3Value;
	*/
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	protected List<OaiSetProfile> profiles;
	
	public List<OaiSetProfile> getProfiles() {
		return profiles;
	}
	public void setProfiles(List<OaiSetProfile> profiles) {
		this.profiles = profiles;
	}
	protected List<OaiSetLimiter> limiters;
	protected List<OaiSetConstant> constants;
	public String[] getFields(){
		ArrayList<String> list = new ArrayList<String>();
		if(limiters!=null)
		for (Iterator<OaiSetLimiter> iterator = limiters.iterator(); iterator.hasNext();) {
			OaiSetLimiter limiter = (OaiSetLimiter) iterator.next();
			list.add(limiter.getLimiter());
		}
		return list.toArray(new String[list.size()]);
	}
	public void setValues(String[] str){
		// nothing solo per json
	}
	public void setFields(String[] str){
		// nothing solo per json
	}
	public String[] getValues(){
		ArrayList<String> list = new ArrayList<String>();
		if(limiters!=null)
		for (Iterator<OaiSetLimiter> iterator = limiters.iterator(); iterator.hasNext();) {
			OaiSetLimiter limiter = (OaiSetLimiter) iterator.next();
			list.add(limiter.getValue());
		}
		return list.toArray(new String[list.size()]);
	}
	/*
	public String[] getFields(){
		ArrayList<String> list = new ArrayList<String>();
		if(limiter1!=null && limiter1.trim().length()>0 && limiter1Value!=null && limiter1Value.trim().length()>0)
			list.add(limiter1);
		if(limiter2!=null && limiter2.trim().length()>0 && limiter2Value!=null && limiter2Value.trim().length()>0)
			list.add(limiter2);
		if(limiter3!=null && limiter3.trim().length()>0 && limiter3Value!=null && limiter3Value.trim().length()>0)
			list.add(limiter3);
		return list.toArray(new String[list.size()]);
	}
	public String[] getValues(){
		ArrayList<String> list = new ArrayList<String>();
		if(limiter1!=null && limiter1.trim().length()>0 && limiter1Value!=null && limiter1Value.trim().length()>0)
			list.add(limiter1Value);
		if(limiter2!=null && limiter2.trim().length()>0 && limiter2Value!=null && limiter2Value.trim().length()>0)
			list.add(limiter2Value);
		if(limiter3!=null && limiter3.trim().length()>0 && limiter3Value!=null && limiter3Value.trim().length()>0)
			list.add(limiter3Value);
		return list.toArray(new String[list.size()]);
		
	}
	*/
}
