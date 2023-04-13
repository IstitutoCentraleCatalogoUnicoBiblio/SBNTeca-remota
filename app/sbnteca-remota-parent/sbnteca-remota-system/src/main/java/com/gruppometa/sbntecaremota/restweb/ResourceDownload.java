package com.gruppometa.sbntecaremota.restweb;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.gruppometa.sbntecaremota.objects.DataResourceDelivery;
import com.gruppometa.sbntecaremota.retrieve.MagResourceDelivery;
import com.gruppometa.sbntecaremota.util.ContentStatic;
import com.gruppometa.sbntecaremota.util.ThumbnailCreator;

/**
 * Servizi che implementano l'accesso al delivery
 * 
 * 
 *
 */
@Component
@Scope("request")
@Path("/digitalObject")
@Tag(name = "Servizi per le risorse")
public class ResourceDownload {

	@Context
	ServletContext context;

	@Context
	HttpServletRequest request;

	@Autowired
	private ThumbnailCreator thumbCreator;
	
	// resource delivery
	@Autowired
	private MagResourceDelivery delivery;
	
	// logger
	private static Logger logger = LoggerFactory.getLogger(ResourceDownload.class);
	
	
	
	/**
	 * Restituisce la risorsa digitale salvata nel sistema
	 * 
	 * DELIVERY OGGETTO DIGITALE IMPORTATO
	 * 
	 * @param deliveryID ID risorsa nel delivery
	 * @return Response risposta HTTP
	 */
	@GET
	@Path("/{id}")
	public Response getTecaDigitalResource(@PathParam("id") String deliveryID, 
			@QueryParam("mode") String mode, @QueryParam("w") Integer width,
			@QueryParam("cache") @DefaultValue("true") String cache,
			@QueryParam("h") Integer height) {
		
		DataResourceDelivery data = delivery.getResourceByID(deliveryID, false);
		
		// larghezza e altezza specificate
		if(width != null && height != null) {
			String resourceType = this.getResourceTypeByContentType(data.getContentType());
			return this.getPreview(getUrlFromRequest(request), resourceType, width, height, cache);
		}

		// modalità
		else if(mode != null && !mode.isEmpty()) {
			Properties configProperties = ContentStatic.getProperties();
			String propWidth = "thumbDelivery." + mode + ".width";
			String propHeight = "thumbDelivery." + mode + ".height";
			
			if(configProperties.containsKey(propWidth) && configProperties.containsKey(propHeight)) {
				int w = Integer.parseInt(configProperties.getProperty(propWidth, "32"));
				int h = Integer.parseInt(configProperties.getProperty(propHeight, "32"));
				String resourceType = this.getResourceTypeByContentType(data.getContentType());
				return this.getPreview(getUrlFromRequest(request), resourceType, w, h, cache);
			}
		}
		
		// ID non presente
		if(data == null) {
			logger.error("Errore 404: Risorsa digitale non trovata");
			
			return Response.status(Response.Status.NOT_FOUND).
					header("Content-Type", "text/plain; charset=UTF-8").
					entity("Errore 404: Risorsa digitale non trovata").
					build();
		}
		
		String contentType = data.getContentType();
		String disposition = contentType.startsWith("audio") || contentType.startsWith("video") ? "attachment" : "inline";
		
		return Response.ok(data.getStream()).
				header("Content-Type", data.getContentType()).
				header("Content-Length", data.getLength()).
				header("Content-Transfer-Encoding", "binary").
				header("Content-Disposition", disposition + "; filename=\"" + data.getResourceName() + "\"").
				header("Last-Modified", data.getLastModified()).
				build();
	}

	private String getUrlFromRequest(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		if("https".equals(request.getHeader("X-Forwarded-Proto"))){
			url = url.replace("http://","https://");
		}
		return url;
	}

	/**
	 * Restituisce la risorsa digitale salvata nel progetto originale
	 * 
	 * DELIVERY OGGETTO DIGITALE ORIGINALE (FILE SYSTEM)
	 * USATO PREVALENTEMENTE PER L'EDITOR E NELLA CREAZIONE DELLA VERSIONE ORIGINAL DEL MAG IN IMPORT
	 * 
	 * @param deliveryID ID risorsa nel delivery
	 * @return Response risposta HTTP
	 */
	@GET
	@Path("/{id}/original")
	public Response getOriginalDigitalResource(@PathParam("id") String deliveryID, 
			@QueryParam("mode") String mode, @QueryParam("w") Integer width, 
			@QueryParam("h") Integer height) {

		DataResourceDelivery data = delivery.getResourceByID(deliveryID, true);
		
		if(width != null && height != null) {
			String resourceType = this.getResourceTypeByContentType(data.getContentType());
			return this.getPreview(getUrlFromRequest(request), resourceType, width, height);
		}

		else if(mode != null && !mode.isEmpty()) {
			Properties configProperties = ContentStatic.getProperties();
			String propWidth = "thumbDelivery." + mode + ".width";
			String propHeight = "thumbDelivery." + mode + ".height";
			
			if(configProperties.containsKey(propWidth) && configProperties.containsKey(propHeight)) {
				int w = Integer.parseInt(configProperties.getProperty(propWidth, "32"));
				int h = Integer.parseInt(configProperties.getProperty(propHeight, "32"));
				String resourceType = this.getResourceTypeByContentType(data.getContentType());
				return this.getPreview(getUrlFromRequest(request), resourceType, w, h);
			}
		}
		
		// ID non presente
		if(data == null) {
			logger.error("Errore 404: Risorsa digitale non trovata");
			
			return Response.status(Response.Status.NOT_FOUND).
					header("Content-Type", "text/plain; charset=UTF-8").
					entity("Errore 404: Risorsa digitale non trovata").
					build();
		}
		
		String contentType = data.getContentType();
		String disposition = contentType.startsWith("audio") || contentType.startsWith("video") ? "attachment" : "inline";
		
		return Response.ok(data.getStream()).
				header("Content-Type", data.getContentType()).
				header("Content-Length", data.getLength()).
				header("Content-Transfer-Encoding", "binary").
				header("Content-Disposition", disposition + "; filename=\"" + data.getResourceName() + "\"").
				header("Last-Modified", data.getLastModified()).
				build();
	}
	
	/**
	 * Creazione risposta HTTP per thumbnail
	 * 
	 * @param urlResource URL della risorsa digitale
	 * @param type tipo di risorsa digitale
	 * @param width larghezza dell'immagine
	 * @param height altezza dell'immagine
	 * @return Response risosta HTTP
	 */
	private Response getPreview(String urlResource, String type, int width, int height){
		return getPreview(urlResource, type, width, height, null);
	}

	private Response getPreview(String urlResource, String type, int width, int height, String cache) {
		try {
			return Response.ok(thumbCreator.getThumb(urlResource, type, width, height, cache)).
					header("Content-Type", "image/jpeg").build();
			
		} catch (IOException e) {
			logger.error("Errore: " + e.getMessage());
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}
	
	/**
	 * Restituisce il tipo di risorsa digitale dato il suo content type
	 * 
	 * @param contentType content type
	 * @return String tipo di risorsa digitale (IMG, AUDIO, VIDEO, DOC)
	 */
	private String getResourceTypeByContentType(String contentType) {
		String[] parts = contentType.split("\\/");
		
		if(parts.length == 0 || "image".equalsIgnoreCase(parts[0]))
			return "img";
		
		else if("audio".equalsIgnoreCase(parts[0]) || "video".equalsIgnoreCase(parts[0]))
			return parts[0];
		
		else
			return "doc";
	}

}
