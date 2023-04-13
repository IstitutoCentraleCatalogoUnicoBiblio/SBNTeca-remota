package com.gruppometa.magteca.frontend.authenticate;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.gruppometa.magteca.frontend.WebAppConfiguration;
import com.gruppometa.magteca.frontend.object.JsonUser;

@Component
@Configuration
public class AuthenticationService implements UserDetailsService {

	@Autowired
	protected WebAppConfiguration configuration;

	@Autowired
	protected CheckJwtToken checkJwtToken;

	protected static Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

	/**
	 * Carica i dati utente, creando in base al ruolo le authorities per accedere
	 * alle sezioni della Teca
	 * 
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		RestTemplate restTemplate = new RestTemplate();
		
		try {
			String token = checkJwtToken.getToken();

			HttpHeaders headers = new HttpHeaders();
			headers.set(HttpHeaders.AUTHORIZATION, token);
			HttpEntity<String> request = new HttpEntity<>("map", headers);
			ResponseEntity<JsonUser> jsonResponse = restTemplate
					.exchange(configuration.getBaseRestInternal() +
					"user/login?username=" + username, HttpMethod.GET, request, JsonUser.class);
			JsonUser json = jsonResponse.getBody();
			if(json.getUsername() == null)
				throw new UsernameNotFoundException("Utente non trovato");
			
			boolean searchAreaManager = false;
			boolean statsAreaManager = false;
			List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			
			if(json.getUserRole().isUploadNewManager())
				authorities.add(new SimpleGrantedAuthority("ROLE_UPLOAD_MANAGER"));
			
			if(json.getUserRole().isUploadUpdateManager())
				authorities.add(new SimpleGrantedAuthority("ROLE_UPLOAD_UPDATE_MANAGER"));
			
			if(json.getUserRole().isImportManager())
				authorities.add(new SimpleGrantedAuthority("ROLE_IMPORT_MANAGER"));

			if(json.getUserRole().isUpdateManager())
				authorities.add(new SimpleGrantedAuthority("ROLE_UPDATE_MANAGER"));
			
			if(json.getUserRole().isChangeUsageManager())
				authorities.add(new SimpleGrantedAuthority("ROLE_CHANGE_USAGE_MANAGER"));
			
			if(json.getUserRole().isDeleteProjectManager())
				authorities.add(new SimpleGrantedAuthority("ROLE_DELETE_PROJECT_MANAGER"));
			
			if(json.getUserRole().isExportPanelManager())
				authorities.add(new SimpleGrantedAuthority("ROLE_EXPORT_PANEL_MANAGER"));

			if(json.getUserRole().isSearchManager()) {
				searchAreaManager = true;
				authorities.add(new SimpleGrantedAuthority("ROLE_SEARCH_MANAGER"));
			}
			
			if(json.getUserRole().isPublicationManager()) {
				searchAreaManager = true;
				authorities.add(new SimpleGrantedAuthority("ROLE_PUBLICATION_MANAGER"));
			}
			
			if(json.getUserRole().isDeleteManager()) {
				searchAreaManager = true;
				authorities.add(new SimpleGrantedAuthority("ROLE_DELETE_MANAGER"));
			}
			
			if(json.getUserRole().isNormalizeManager()) {
				searchAreaManager = true;
				authorities.add(new SimpleGrantedAuthority("ROLE_NORMALIZE_MANAGER"));
			}

			if(json.getUserRole().isExportManager()) {
				searchAreaManager = true;
				authorities.add(new SimpleGrantedAuthority("ROLE_EXPORT_MANAGER"));
			}
			
			if(json.getUserRole().isDraftManager()) {
				searchAreaManager = true;
				authorities.add(new SimpleGrantedAuthority("ROLE_DRAFT_MANAGER"));
			}
			
			if(searchAreaManager)
				authorities.add(new SimpleGrantedAuthority("ROLE_SEARCH_AREA_MANAGER"));
			
			if(json.getUserRole().isStatsManager()) {
				statsAreaManager = true;
				authorities.add(new SimpleGrantedAuthority("ROLE_STATS_MANAGER"));
			}
			
			if(json.getUserRole().isDigitalObjectStatsManager()) {
				statsAreaManager = true;
				authorities.add(new SimpleGrantedAuthority("ROLE_DIGITAL_OBJECT_STATS_MANAGER"));
			}
			
			if(statsAreaManager)
				authorities.add(new SimpleGrantedAuthority("ROLE_STATS_AREA_MANAGER"));

			if(json.getUserRole().isOaisetManager())
				authorities.add(new SimpleGrantedAuthority("ROLE_OAISET_MANAGER"));
			
			if(json.getUserRole().isUserManager())
				authorities.add(new SimpleGrantedAuthority("ROLE_USER_MANAGER"));

			if(json.getUserRole().isRoleManager())
				authorities.add(new SimpleGrantedAuthority("ROLE_ROLES_MANAGER"));
			
			if(json.getUserRole().isUserManager() || json.getUserRole().isRoleManager())
				authorities.add(new SimpleGrantedAuthority("ROLE_USER_AREA_MANAGER"));
			
			MagtecaUser magtecaUser =  new MagtecaUser(json.getId(), json.getUsername(), json.getPassword(),
					json.getEnabled(), json.getEnabled(), json.getEnabled(), 
					json.getEnabled(), authorities);
			magtecaUser.setAccessToken(token);
			return magtecaUser;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new UsernameNotFoundException(e.getMessage());
		}
	}

}
