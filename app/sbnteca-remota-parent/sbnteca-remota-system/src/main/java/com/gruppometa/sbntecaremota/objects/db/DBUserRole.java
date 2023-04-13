package com.gruppometa.sbntecaremota.objects.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import javax.persistence.*;


import java.util.List;


/**
 * The persistent class for the user_role database table.
 * 
 */
@Entity
@Table(name="user_role")
@NamedQuery(name="DBUserRole.findAll", query="SELECT d FROM DBUserRole d")
public class DBUserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@JsonProperty("nome")
	private String name;
	
	@Column(name="upload_new_manager")
	@JsonProperty("gestione_upload")
	private boolean uploadNewManager;

	@Column(name="upload_update_manager")
	@JsonProperty("gestione_upload_aggiornamento")
	private boolean uploadUpdateManager;
	
	@Column(name="import_manager")
	@JsonProperty("gestione_importazioni")
	private boolean importManager;
	
	@Column(name="update_manager")
	@JsonProperty("gestione_importazioni_aggiornamento")
	private boolean updateManager;

	@Column(name="change_usage_manager")
	@JsonProperty("gestione_cambio_usage")
	private boolean changeUsageManager;
	
	@Column(name="delete_project_manager")
	@JsonProperty("gestione_elimina_progetti")
	private boolean deleteProjectManager;
	
	@Column(name="export_panel_manager")
	@JsonProperty("gestione_pannello_export")
	private boolean exportPanelManager;
	
	@Column(name="search_manager")
	@JsonProperty("gestione_ricerche")
	private boolean searchManager;
	
	@Column(name="publication_manager")
	@JsonProperty("gestione_pubblicazioni")
	private boolean publicationManager;
	
	@Column(name="delete_manager")
	@JsonProperty("gestione_cancellazioni")
	private boolean deleteManager;
	
	@Column(name="normalize_manager")
	@JsonProperty("gestione_normalizzazioni")
	private boolean normalizeManager;
	
	@Column(name="export_manager")
	@JsonProperty("gestione_export")
	private boolean exportManager;
	
	@Column(name="draft_manager")
	@JsonProperty("gestione_draft")
	private boolean draftManager;

	@Column(name="stats_manager")
	@JsonProperty("gestione_statistiche")
	private boolean statsManager;
	
	@Column(name="digital_object_stats_manager")
	@JsonProperty("gestione_statistiche_oggetti_digitali")
	private boolean digitalObjectStatsManager;

	@Column(name="oaiset_manager")
	@JsonProperty("gestione_oaiset")
	private boolean oaisetManager;

	@Column(name="user_manager")
	@JsonProperty("gestione_utenti")
	private boolean userManager;

	@Column(name="role_manager")
	@JsonProperty("gestione_ruoli")
	private boolean roleManager;

	//bi-directional many-to-one association to DBTecaUser
	@OneToMany(mappedBy="userRole")
	@JsonIgnore
	private List<DBTecaUser> tecaUsers;

	public DBUserRole() {
	}

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
		return this.draftManager;
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

	public List<DBTecaUser> getTecaUsers() {
		return this.tecaUsers;
	}

	public void setTecaUsers(List<DBTecaUser> tecaUsers) {
		this.tecaUsers = tecaUsers;
	}

	public DBTecaUser addTecaUser(DBTecaUser tecaUser) {
		getTecaUsers().add(tecaUser);
		tecaUser.setUserRole(this);

		return tecaUser;
	}

	public DBTecaUser removeTecaUser(DBTecaUser tecaUser) {
		getTecaUsers().remove(tecaUser);
		tecaUser.setUserRole(null);

		return tecaUser;
	}

}