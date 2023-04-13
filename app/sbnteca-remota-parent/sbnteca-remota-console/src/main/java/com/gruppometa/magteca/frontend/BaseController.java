package com.gruppometa.magteca.frontend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gruppometa.magteca.frontend.authenticate.CheckJwtToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gruppometa.magteca.frontend.authenticate.MagtecaUser;
import com.gruppometa.magteca.frontend.object.JsonRole;
import com.gruppometa.magteca.frontend.object.JsonUser;
import com.gruppometa.magteca.frontend.object.Oaiset;
import com.gruppometa.magteca.frontend.object.OaisetProfile;

@Controller
@Configuration
public class BaseController {

	@Autowired
	protected WebAppConfiguration configuration;

	@Autowired
	protected CheckJwtToken checkJwtToken;

	// logger
	private static Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	@Value("${application.title}")
	protected String title;

	@Value("${configs.rootBackend}")
	protected String baseUrlTecaSystem;
	
	private void addUserID(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		model.addAttribute("title", title);
		if(auth.getPrincipal() instanceof MagtecaUser) {
		    MagtecaUser currentUser = (MagtecaUser) auth.getPrincipal();
			model.addAttribute("userID", currentUser.getID());
			model.addAttribute("accessToken", currentUser.getAccessToken());
			Date date = CheckJwtToken.getExpireDate(currentUser.getAccessToken());
			if(date==null || new Date().after(date)){
				logger.info("Token expired");
				currentUser.setAccessToken(checkJwtToken.getToken());
				model.addAttribute("accessToken", currentUser.getAccessToken());
			}
		}
	}


	
	@RequestMapping("/")
	public String home(Model model) {
		this.addUserID(model);
		return "home";
	}
	
	@RequestMapping("/home")
	public String home2(Model model) {
		this.addUserID(model);
		return "home";
	}
	
	/**
	 * Popup visualizzazione oggetto digitale (restituisce lo snippet HTML)
	 * 
	 * @param magID
	 * @param type
	 * @param sequenceNumber
	 * @param usage
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value="/displayResource/{type}/{sequence}/{usage}", method=RequestMethod.GET)
	public void testModaal(@RequestParam("id") String magID, @PathVariable("type") String type,
			@PathVariable("sequence") String sequenceNumber, @PathVariable("usage") String usage, 
			HttpServletResponse response) throws IOException {
		
		String resourcePath = type + "/" + sequenceNumber + "/" + usage;
		response.setContentType(MediaType.TEXT_HTML_VALUE);
		PrintWriter out = response.getWriter();
		
		if("img".equals(type) || "audio".equals(type) || "video".equals(type)) {
			out.write("<" + type + (!"img".equals(type) ? " controls=\"controls\" "
					+ "preload=\"" + ("video".equals(type) ? "auto" : "none") + "\"" : "") + " "
					+ "src=\"" + configuration.getBaseRest() + "digitalResource/resource/" + resourcePath + "?id=" + magID + "\""
					+ ("video".equals(type) ? " class=\"teca_video\"" : "") + ">"
					+ "</" + type + ">");
		}
		
		else {
			out.write("<iframe src=\"" + configuration.getBaseRest() + "digitalResource/resource/" + resourcePath + "?id=" + magID + "\" "
					+ "class=\"teca_doc\" />");
		}
	}

	@RequestMapping("/login")
	public String login(Model model) {
		this.addUserID(model);
		return "login";
	}

	@RequestMapping("/drafts")
	public String drafts(Model model) {
		this.addUserID(model);
		return "drafts";
	}

	@RequestMapping(value="/stats", method=RequestMethod.GET)
	public String stats(Model model) {
		this.addUserID(model);
		return "stats";
	}

	@RequestMapping(value="/objectsStats", method=RequestMethod.GET)
	public String objectStats(Model model) {
		this.addUserID(model);
		return "objectsStats";
	}

	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String search(Model model) {
		this.addUserID(model);
		model.addAttribute("selectableResults", "n");
		model.addAttribute("checkPublication", "n");
		return "search";
	}

	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(Model model) {
		this.addUserID(model);
		model.addAttribute("selectableResults", "y");
		model.addAttribute("checkPublication", "n");
		return "delete";
	}

	@RequestMapping(value="/public", method=RequestMethod.GET)
	public String publicMag(Model model) {
		this.addUserID(model);
		model.addAttribute("selectableResults", "y");
		model.addAttribute("checkPublication", "y");
		return "public";
	}

	@RequestMapping(value="/export", method=RequestMethod.GET)
	public String export(Model model) {
		this.addUserID(model);
		model.addAttribute("selectableResults", "y");
		model.addAttribute("checkPublication", "n");
		return "export";
	}

	@RequestMapping(value="/export_panel", method=RequestMethod.GET)
	public String exportPanel(Model model) {
		this.addUserID(model);
		return "export_panel";
	}

	@RequestMapping(value="/editor", method=RequestMethod.GET)
	public String editor(@RequestParam("id") String magID, Model model) {
		this.addUserID(model);
		return "magEditor";
	}

	@RequestMapping(value="/editorMets", method=RequestMethod.GET)
	public String editorMets(@RequestParam(value = "id", defaultValue = "") String metsId,
							 Model model) {
		this.addUserID(model);
		model.addAttribute("id", metsId);
		return "editorMets";
	}

	@RequestMapping(value="/mets-editor/index", method=RequestMethod.GET)
	public String editorMetsSub(@RequestParam("id") String metsId,
								Model model) {
		this.addUserID(model);
		model.addAttribute("id", metsId);
		model.addAttribute("baseUrlTecaSystem", baseUrlTecaSystem );
		return "editorMets_index";
	}

	/*
	@RequestMapping(value="/editor_demo", method=RequestMethod.GET)
	public String editorDemo(Model model) {
		this.addUserID(model);
		return "editor_demo";
	}
	*/
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public String searchUsers(Model model) {
		this.addUserID(model);
		return "users";
	}
	
	/**
	 * Creazione nuovo utente con caricamento dei ruoli per select box
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/userNew", method=RequestMethod.GET)
	public String newUser(Model model) {
		this.addUserID(model);
		model.addAttribute("user", new JsonUser());

		RestTemplate restTemplate = new RestTemplate();
		List<JsonRole> roles = Arrays.asList(restTemplate.getForObject(configuration.getBaseRestInternal() + "user/role/all", JsonRole[].class));
		model.addAttribute("roles", roles);
		
		return "userDetail";
	}
	
	@RequestMapping(value="/users", method=RequestMethod.POST)
	public String updateUser(@RequestParam("user") String jsonUser, Model model) {
		this.addUserID(model);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		try {
			JsonUser user = mapper.readValue(jsonUser, JsonUser.class);
			model.addAttribute("user", user);
			
			RestTemplate restTemplate = new RestTemplate();
			List<JsonRole> roles = Arrays.asList(restTemplate.getForObject(configuration.getBaseRestInternal() + "user/role/all", JsonRole[].class));
			model.addAttribute("roles", roles);
			
		} catch (JsonParseException e) {
			logger.error(e.getMessage());
			model.addAttribute("error", "Errore di lettura OAI set - " + e.getMessage());
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
			model.addAttribute("error", "Errore di lettura OAI set - " + e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
			model.addAttribute("error", "Errore di lettura OAI set - " + e.getMessage());
		}
		
		return "userDetail";
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.GET)
	public String upload(Model model) {
		this.addUserID(model);
		return "upload";
	}

	@RequestMapping(value="/uploadUpdate", method=RequestMethod.GET)
	public String uploadUpdate(Model model) {
		this.addUserID(model);
		return "uploadUpdate";
	}

	@RequestMapping(value="/uploadResources", method=RequestMethod.GET)
	public String uploadResources(Model model) {
		this.addUserID(model);
		return "uploadResources";
	}

	@RequestMapping(value="/uploadUnimarc", method=RequestMethod.GET)
	public String uploadUnimarc(Model model) {
		this.addUserID(model);
		return "uploadUnimarc";
	}

	@RequestMapping(value="/import", method=RequestMethod.GET)
	public String importMag(Model model) {
		this.addUserID(model);
		model.addAttribute("editable_projects", true);
		return "import";
	}

	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String updateMag(@RequestParam(value="validate", required=false) String draft, Model model) {
		this.addUserID(model);
		model.addAttribute("editable_projects", draft == null);
		return "update";
	}

	@RequestMapping(value="/changeUsage", method=RequestMethod.GET)
	public String changeUsage(Model model) {
		
		this.addUserID(model);
		model.addAttribute("editable_projects", true);
		return "changeUsage";
	}

	@RequestMapping(value="/normalize", method=RequestMethod.GET)
	public String normalize(Model model) {
		this.addUserID(model);
		model.addAttribute("selectableResults", "y");
		model.addAttribute("checkPublication", "n");
		return "normalize";
	}

	/**
	 * Mostra la lista dei sets
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/oaisets", method=RequestMethod.GET)
	public String oaisets(Model model) {
		this.addUserID(model);
		return "oaisets";
	}

	@RequestMapping(value="/vfs", method=RequestMethod.GET)
	public String vfs(Model model) {
		this.addUserID(model);
		return "vfs";
	}

	@RequestMapping(value="/vfs-container", method=RequestMethod.GET)
	public String vfsContainer(Model model) {
		this.addUserID(model);
		return "vfs-container";
	}

	@RequestMapping(value="/vfs-searchs", method=RequestMethod.GET)
	public String vfsSearch(Model model) {
		this.addUserID(model);
		return "vfs-search";
	}

	/**
	 * Dettaglio OAI set salvato
	 * 
	 * @param oaiset
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/oaisets", method=RequestMethod.POST)
	public String oaisets(@RequestParam("oaiset") String oaiset, Model model) {
		this.addUserID(model);
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		try {
			Oaiset oaisetObj = mapper.readValue(oaiset, Oaiset.class);
			model.addAttribute("oaiset", oaisetObj);
			boolean magProfile = false;
			boolean metsProfile = false;
			boolean metsSimpleProfile = false;
			boolean oaiDcProfile = false;
			
			for(OaisetProfile profile : oaisetObj.getProfiles()) {
				if("mag".equalsIgnoreCase(profile.getName()))
					magProfile = true;
				
				if("mets".equalsIgnoreCase(profile.getName()))
					metsProfile = true;

				if("mets-simple".equalsIgnoreCase(profile.getName()))
					metsSimpleProfile = true;

				if("oai_dc".equalsIgnoreCase(profile.getName()))
					oaiDcProfile = true;
			}
			
			model.addAttribute("checkMag", magProfile);
			model.addAttribute("checkMets", metsProfile);
			model.addAttribute("checkMetsSimple", metsSimpleProfile);
			model.addAttribute("checkOaiDc", oaiDcProfile);
			
		} catch (JsonParseException e) {
			logger.error(e.getMessage());
			model.addAttribute("error", "Errore di lettura OAI set - " + e.getMessage());
		} catch (JsonMappingException e) {
			logger.error(e.getMessage());
			model.addAttribute("error", "Errore di lettura OAI set - " + e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
			model.addAttribute("error", "Errore di lettura OAI set - " + e.getMessage());
		}
		
		return "oaisetDetail";
	}

	/**
	 * Nuovo OAI set
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/oaisetNew", method=RequestMethod.GET)
	public String oaisetNew(HttpSession session, Model model) {
		this.addUserID(model);
		session.removeAttribute("oaiset");
		model.addAttribute("oaiset", new Oaiset());
		model.addAttribute("checkMag", true);
		model.addAttribute("checkMets", false);
		model.addAttribute("checkMetsSimple", false);
		model.addAttribute("checkOaiDc", false);
		return "oaisetDetail";
	}
	
	/**
	 * Costruzione guidata OAI set
	 * 
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/oaisetBuildQuery", method=RequestMethod.POST)
	public String oaisetBuildQuerySolr(HttpServletRequest request, HttpSession session, Model model) {
		this.addUserID(model);
		
		Oaiset oaisetObj = new Oaiset();
		oaisetObj.setId(Integer.parseInt(request.getParameter("oaiset_id")));
		oaisetObj.setName(request.getParameter("oaiset_name"));
		oaisetObj.setSpec(request.getParameter("oaiset_spec"));
		oaisetObj.setDescriptionIt(request.getParameter("oaiset_description_it"));
		oaisetObj.setDescriptionEn(request.getParameter("oaiset_description_en"));
		oaisetObj.setProject(request.getParameter("oaiset_project"));
		oaisetObj.setSolrquery(request.getParameter("oaiset_solrquery"));
		
		if(request.getParameter("oaiset_main_profile") != null) {
			OaisetProfile profile = new OaisetProfile();
			profile.setName(request.getParameter("oaiset_main_profile"));
			oaisetObj.getProfiles().add(profile);
		}

		if(request.getParameter("oaiset_profile_oai_dc") != null) {
			OaisetProfile profile = new OaisetProfile();
			profile.setName("oai_dc");
			oaisetObj.getProfiles().add(profile);
		}
		
		session.setAttribute("oaiset", oaisetObj);
		model.addAttribute("selectableResults", "n");
		model.addAttribute("checkPublication", "n");
		model.addAttribute("update", Boolean.parseBoolean(request.getParameter("oaiset_update")));
		return "oaisetSearch";
	}

	/**
	 * Conferma costruzione guidata e ritorno al dettaglio OAI set
	 * 
	 * @param request
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/oaisetNew", method=RequestMethod.POST)
	public String oaisetNewGoToSearch(HttpServletRequest request, HttpSession session, Model model) {
		Oaiset oaisetObj = (Oaiset) session.getAttribute("oaiset");
		
		if(request.getParameter("solrquery") != null) {
			oaisetObj.setSolrquery(request.getParameter("solrquery"));
			session.setAttribute("oaiset", oaisetObj);
		}
		
		model.addAttribute("oaiset", oaisetObj);
		boolean magProfile = false;
		boolean metsProfile = false;
		boolean metsSimpleProfile = false;
		boolean oaiDcProfile = false;
		
		for(OaisetProfile profile : oaisetObj.getProfiles()) {
			if("mag".equalsIgnoreCase(profile.getName()))
				magProfile = true;
			
			if("mets".equalsIgnoreCase(profile.getName()))
				metsProfile = true;

			if("mets-simple".equalsIgnoreCase(profile.getName()))
				metsSimpleProfile = true;

			if("oai_dc".equalsIgnoreCase(profile.getName()))
				oaiDcProfile = true;
		}
		
		model.addAttribute("checkMag", magProfile);
		model.addAttribute("checkMets", metsProfile);
		model.addAttribute("checkMetsSimple", metsSimpleProfile);
		model.addAttribute("checkOaiDc", oaiDcProfile);
		return "oaisetDetail";
	}

	@RequestMapping(value="/clear", method=RequestMethod.GET)
	public String clear(Model model) {
		this.addUserID(model);
		return "clear";
	}
	
	@RequestMapping(value="/roles", method=RequestMethod.GET)
	public String roles(HttpServletRequest request, HttpSession session, Model model) throws JsonProcessingException {
		this.addUserID(model);
		String roleString = (String) session.getAttribute("default_role");
		
		if(roleString == null) {
			JsonRole role = new JsonRole();
			role.setName("#default");
			role.setUploadNewManager(false);
			role.setUploadUpdateManager(false);
			role.setImportManager(false);
			role.setUpdateManager(false);
			role.setChangeUsageManager(false);
			role.setDeleteProjectManager(false);
			role.setExportPanelManager(false);
			role.setSearchManager(true);
			role.setPublicationManager(true);
			role.setDeleteManager(true);
			role.setNormalizeManager(true);
			role.setExportManager(true);
			role.setStatsManager(true);
			role.setUserManager(false);
			role.setRoleManager(false);
			role.setOaisetManager(false);
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			roleString = mapper.writeValueAsString(role);
			session.setAttribute("default_role", roleString);
		}
		
		model.addAttribute("default_role", roleString);
		return "roles";
	}

	@RequestMapping(value="/magDetail", method=RequestMethod.GET)
	public String magDetail(@RequestParam("id") String id,
							@RequestParam("type") @DefaultValue("mag") String type,
							Model model) {
		this.addUserID(model);
		model.addAttribute("baseRest", configuration.getBaseRest());
		model.addAttribute("id", id);
		model.addAttribute("type", type);
		model.addAttribute("typeUpper", type.toUpperCase());
		return "magDetail";
	}

	@RequestMapping(value="/metsDetail", method=RequestMethod.GET)
	public String metsDetail(@RequestParam("id") String id, Model model) {
		this.addUserID(model);
		model.addAttribute("baseRest", configuration.getBaseRest());
		return "metsDetail";
	}

}
