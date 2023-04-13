package com.gruppometa.sbntecaremota.restweb;

import com.gruppometa.data.mets.Mets;
import com.gruppometa.data.mods.Mods;
import com.gruppometa.mets2mag.MetsConvertor;
import com.gruppometa.sbntecaremota.mets.MetsCreator;
import com.gruppometa.sbntecaremota.mets.MetsValidator;
import com.gruppometa.sbntecaremota.objects.json.JsonDeleteProject;
import com.gruppometa.sbntecaremota.objects.json.JsonMagEditorGen;
import com.gruppometa.sbntecaremota.objects.validators.ValidationResult;
import com.gruppometa.sbntecaremota.restweb.objects.MagDetailService;
import com.gruppometa.sbntecaremota.restweb.objects.MagEditorService;
import com.gruppometa.sbntecaremota.restweb.objects.MagSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.log4j.Logger;
import org.apache.solr.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

@Component
@Scope("request")
@Path("/rest")
@Tag(name = "Servizi per METS")
public class MetsResource {
    private static Logger logger = Logger.getLogger(MetsResource.class);

    @Autowired
    protected MetsCreator metsCreator;

    protected MetsConvertor metsConvertor = new MetsConvertor();

    @Autowired
    protected MetsValidator metsValidator;

    @Autowired
    private MagSearchService magSearchService;

    @Autowired
    private MagDetailService magDetailService;

    @Autowired
    private MagEditorService magEditorService;


    @GET
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_XML + "; charset=UTF-8")
    @Path("getmetsxml")
    @Operation(summary = "Si ottiene il XML della risorsa")
    public Response getMets(@QueryParam("id") String idMag, @QueryParam("code") int code) {
        // controllo parametri richiesta
        if(idMag == null || idMag.isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).entity("ID del MAG non specificato").build();

        if(code > 4) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Codice di versione "
                    + "del MAG richiesta non specificato correttamente").build();
        }

        try {
            Document document = magSearchService.getMetsXmlDocument(idMag, code);
            if(document!=null)
                return Response.ok(document).build();
            else
                return Response.status(Response.Status.NOT_FOUND).entity("No xml for "+idMag).build();

        } catch (FileNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("metsCreate")
    @Tag(name = "Editore Mets")
    @Operation(summary = "Si crea la bozza mets")
    public Response createMets(@QueryParam("user") Integer userID, @QueryParam("project") String project,
                               @QueryParam("label") String label) {
        if(userID == null)
            return Response.status(Response.Status.BAD_REQUEST).entity("ID utente non specificato").build();

        if(project == null || project.isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).entity("Progetto non specificato").build();

        return Response.ok(magEditorService.createMetsForUser(project, userID, label)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("metsdata")
    @Tag(name = "Editore Mets")
    @Operation(summary = "Si ottiene il JSON della risorsa")
    public Response getMets(@QueryParam("id") String idMag) {
        // parameto non impostato
        if(idMag == null || idMag.isEmpty())
            return Response.status(Response.Status.BAD_REQUEST).entity("ID non specificato").build();
        try {
            return Response.ok(magDetailService.getMetsDetail(idMag)).build();
        } catch (FileNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("metsdata")
    @Operation(summary = "Servizio per aggiornare la risorsa tramite JSON")
    @Tag(name = "Editore Mets")
    public Response updateMets(Mets mets) {
        return updateMets(mets, true);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("metsdataAndPublish")
    @Operation(summary = "Servizio per aggiornare la risorsa tramite JSON e pubblica la risorse nella teca")
    @Tag(name = "Editore Mets")
    public Response updateMetsAndPublish(Mets mets) {
        return updateMets(mets, false);
    }

    private Response updateMets(Mets mets, boolean isDraft){
        // parametro non impostato
        if(mets == null || StringUtils.isEmpty(mets.getID()))
            return Response.status(Response.Status.BAD_REQUEST).entity("ID non specificato").build();
        try {
            ValidationResult validationResult = null;
            if(!isDraft)
                validationResult = metsValidator.validateMets(mets);
            if(isDraft || "OK".equalsIgnoreCase(validationResult.getStatus())
                    && (validationResult.getErrors()==null || validationResult.getErrors().size()==0)
            )
                return Response.ok(
                        metsCreator.createMets(mets.getID(),mets, isDraft)).build();
            else {
                JsonDeleteProject jsonDeleteProject = new JsonDeleteProject();
                if(validationResult.getErrors()!=null && validationResult.getErrors().size()>0)
                    jsonDeleteProject.setMessage(validationResult.getErrors().get(0).getMessage());
                jsonDeleteProject.setStatus(-2);
                return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(jsonDeleteProject).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Operation(summary = "Servizio per validare il JSON METS-SBN")
    @Path("validate/mets")
    @Tag(name = "Editore Mets")
    public Response validateMets(String mets) {
        return Response.ok(
                    metsValidator.validateMets(mets)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Operation(summary = "Servizio per validare il JSON METS-SBN")
    @Path("validateXml/mets")
    @Tag(name = "Editore Mets")
    public Response validateXmlMets(String mets) {
        return Response.ok(
                metsValidator.validateMetsXml(mets)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_XML + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Operation(summary = "Servizio per validare il JSON METS-SBN")
    @Path("convertXmlToJson/mets")
    @Tag(name = "Editore Mets")
    public Response convertMets(String metsXmlString) throws JAXBException {
        try {
            Mets mets = metsConvertor.readMetsFromString(metsXmlString);
            return Response.ok(
                    metsConvertor.convertMets2Json(mets)).build();
        }
        catch(Exception e){
            JsonDeleteProject jsonDeleteProject = new JsonDeleteProject();
            jsonDeleteProject.setStatus(-1);
            jsonDeleteProject.setMessage(""+e.getMessage()+" "+
                    (e.getCause()!=null?e.getCause().getMessage():""));
            return Response.status(Response.Status.BAD_REQUEST.getStatusCode()).entity(jsonDeleteProject).build();
        }
    }

    @GET
    @Path("/editor/sbnRequest/mods")
    @Tag(name = "Editore METS")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getBibBySbnRequest(@QueryParam("identifier") String identifier) {
        try {
        Mods mods = magEditorService.getModsBySbnRequest(identifier);
            return Response.ok(
                    metsConvertor.convertMods2Json(mods, null)).build();
        }
        catch(Exception e){
            JsonDeleteProject jsonDeleteProject = new JsonDeleteProject();
            jsonDeleteProject.setStatus(-1);
            jsonDeleteProject.setMessage(""+e.getMessage()+" "+
                    (e.getCause()!=null?e.getCause().getMessage():""));
            return Response.status(Response.Status.NOT_FOUND.getStatusCode()).entity(jsonDeleteProject).build();
        }
    }
}
