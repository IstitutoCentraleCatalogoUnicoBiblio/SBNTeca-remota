package com.gruppometa.magteca.frontend;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="configs")
public class WebAppConfiguration {
	
	protected boolean inMemory;
	protected String uploadBaseDir;
	protected String baseRest;
	protected String baseRestInternal;
	protected String rootBackend;
	protected String rootFrontend;
	protected String oaiBaseRest;
	protected String oaiHandler;

	
	
	public boolean isInMemory() {
		return inMemory;
	}

	public void setInMemory(boolean inMemory) {
		this.inMemory = inMemory;
	}
	
	@Bean(name="uploadBaseDir")
	public String getUploadBaseDir() {
		return uploadBaseDir;
	}
	
	public void setUploadBaseDir(String uploadBaseDir) {
		this.uploadBaseDir = uploadBaseDir;
	}
	
	@Bean(name="baseRest")
	public String getBaseRest() {
		return baseRest;
	}

	@Bean(name="baseRestInternal")
	public String getBaseRestInternal() {
		return baseRestInternal;
	}

	public void setBaseRestInternal(String baseRestInternal) {
		this.baseRestInternal = baseRestInternal;
	}

	public void setBaseRest(String baseRest) {
		this.baseRest = baseRest;
	}
	
	@Bean(name="rootBackend")
	public String getRootBackend() {
		return rootBackend;
	}

	public void setRootBackend(String rootBackend) {
		this.rootBackend = rootBackend;
	}

	@Bean(name="rootFrontend")
	public String getRootFrontend() {
		return rootFrontend;
	}

	public void setRootFrontend(String rootFrontend) {
		this.rootFrontend = rootFrontend;
	}

	@Bean(name="oaiBaseRest")
	public String getOaiBaseRest() {
		return oaiBaseRest;
	}

	public void setOaiBaseRest(String oaiBaseRest) {
		this.oaiBaseRest = oaiBaseRest;
	}
	
	@Bean(name="oaiHandler")
	public String getOaiHandler() {
		return oaiHandler;
	}
	
	public void setOaiHandler(String oaiHandler) {
		this.oaiHandler = oaiHandler;
	}

}
