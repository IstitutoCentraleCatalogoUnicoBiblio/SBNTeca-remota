package com.gruppometa.magteca.frontend.object;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The persistent class for the user_role database table.
 * 
 */
public class JsonRole implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;

	@JsonProperty("nome")
	private String name;
	
	@JsonProperty("gestione_upload")
	private boolean uploadNewManager;

	@JsonProperty("gestione_upload_aggiornamento")
	private boolean uploadUpdateManager;
	
	@JsonProperty("gestione_importazioni")
	private boolean importManager;
	
	@JsonProperty("gestione_importazioni_aggiornamento")
	private boolean updateManager;

	@JsonProperty("gestione_cambio_usage")
	private boolean changeUsageManager;
	
	@JsonProperty("gestione_elimina_progetti")
	private boolean deleteProjectManager;
	
	@JsonProperty("gestione_pannello_export")
	private boolean exportPanelManager;
	
	@JsonProperty("gestione_ricerche")
	private boolean searchManager;
	
	@JsonProperty("gestione_pubblicazioni")
	private boolean publicationManager;
	
	@JsonProperty("gestione_cancellazioni")
	private boolean deleteManager;
	
	@JsonProperty("gestione_normalizzazioni")
	private boolean normalizeManager;
	
	@JsonProperty("gestione_export")
	private boolean exportManager;

	@JsonProperty("gestione_draft")
	private boolean draftManager;

	@JsonProperty("gestione_statistiche")
	private boolean statsManager;
	
	@JsonProperty("gestione_statistiche_oggetti_digitali")
	private boolean digitalObjectStatsManager;

	@JsonProperty("gestione_oaiset")
	private boolean oaisetManager;

	@JsonProperty("gestione_utenti")
	private boolean userManager;
	
	@JsonProperty("gestione_ruoli")
	private boolean roleManager;

	
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isUploadNewManager() {
		return this.uploadNewManager;
	}

	public void setUploadNewManager(boolean uploadNewManager) {
		this.uploadNewManager = uploadNewManager;
	}

	public boolean isUploadUpdateManager() {
		return this.uploadUpdateManager;
	}

	public void setUploadUpdateManager(boolean uploadUpdateManager) {
		this.uploadUpdateManager = uploadUpdateManager;
	}
	
	public boolean isImportManager() {
		return this.importManager;
	}

	public void setImportManager(boolean importManager) {
		this.importManager = importManager;
	}
	
	public boolean isUpdateManager() {
		return this.updateManager;
	}

	public void setUpdateManager(boolean updateManager) {
		this.updateManager = updateManager;
	}

	public boolean isChangeUsageManager() {
		return this.changeUsageManager;
	}

	public void setChangeUsageManager(boolean changeUsageManager) {
		this.changeUsageManager = changeUsageManager;
	}
	
	public boolean isDeleteProjectManager() {
		return this.deleteProjectManager;
	}

	public void setDeleteProjectManager(boolean deleteProjectManager) {
		this.deleteProjectManager = deleteProjectManager;
	}

	public boolean isExportPanelManager() {
		return this.exportPanelManager;
	}

	public void setExportPanelManager(boolean exportPanelManager) {
		this.exportPanelManager = exportPanelManager;
	}

	public boolean isSearchManager() {
		return this.searchManager;
	}

	public void setSearchManager(boolean searchManager) {
		this.searchManager = searchManager;
	}

	public boolean isPublicationManager() {
		return this.publicationManager;
	}

	public void setPublicationManager(boolean publicationManager) {
		this.publicationManager = publicationManager;
	}

	public boolean isDeleteManager() {
		return this.deleteManager;
	}

	public void setDeleteManager(boolean deleteManager) {
		this.deleteManager = deleteManager;
	}

	public boolean isNormalizeManager() {
		return this.normalizeManager;
	}

	public void setNormalizeManager(boolean normalizeManager) {
		this.normalizeManager = normalizeManager;
	}

	public boolean isExportManager() {
		return this.exportManager;
	}

	public void setExportManager(boolean exportManager) {
		this.exportManager = exportManager;
	}

	public boolean isDraftManager() {
		return draftManager;
	}

	public void setDraftManager(boolean draftManager) {
		this.draftManager = draftManager;
	}

	public boolean isStatsManager() {
		return this.statsManager;
	}

	public void setStatsManager(boolean statsManager) {
		this.statsManager = statsManager;
	}
	
	public boolean isDigitalObjectStatsManager() {
		return this.digitalObjectStatsManager;
	}

	public void setDigitalObjectStatsManager(boolean digitalObjectStatsManager) {
		this.digitalObjectStatsManager = digitalObjectStatsManager;
	}

	public boolean isOaisetManager() {
		return this.oaisetManager;
	}

	public void setOaisetManager(boolean oaisetManager) {
		this.oaisetManager = oaisetManager;
	}

	public boolean isUserManager() {
		return this.userManager;
	}

	public void setUserManager(boolean userManager) {
		this.userManager = userManager;
	}

	public boolean isRoleManager() {
		return this.roleManager;
	}

	public void setRoleManager(boolean roleManager) {
		this.roleManager = roleManager;
	}

}