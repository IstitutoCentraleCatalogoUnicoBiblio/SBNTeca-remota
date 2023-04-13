package com.gruppometa.sbntecaremota.restweb;

import com.fasterxml.jackson.annotation.JsonView;
import com.gruppometa.sbntecaremota.Views;
import com.gruppometa.sbntecaremota.mag.MagCreator;
import com.gruppometa.sbntecaremota.mets.MetsCreator;
import com.gruppometa.sbntecaremota.objects.DataTableData;
import com.gruppometa.sbntecaremota.objects.json.JsonDeleteProject;
import com.gruppometa.sbntecaremota.objects.json.JsonImportReport;
import com.gruppometa.sbntecaremota.objects.json.JsonSearchRequest;
import com.gruppometa.sbntecaremota.objects.json.JsonSearchResultList;
import com.gruppometa.sbntecaremota.util.UtilSolr;
import com.gruppometa.sbntecaremota.util.Utility;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsFile;
import com.gruppometa.sbntecaremota.vfsfilesystem.VfsService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.StringUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.gruppometa.sbntecaremota.restweb.RestServices.uploadHttp400Error;

@Component
@Scope("request")
@Path("/rest/vfs")
@Tag(name = "Filesystem virtuale")
public class VirtualFileSystemResource {

    @Autowired
    protected VfsService vfsService;

    @Autowired
    protected MetsCreator metsCreator;

    @Autowired
    protected MagCreator magCreator;

    @GET
    @Path("/directories")
    @Operation(summary = "Si ottiene la lista della cartelle")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getProjects(@QueryParam("filter") String filter) {
        if (filter == null || filter.isEmpty())
            filter = "all";
        return Response.ok(vfsService.getDirectories(filter)).build();
    }

    @GET
    @Path("/unusedFiles")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getUnused(@QueryParam("filter") String filter) throws SolrServerException, IOException {
        if (filter == null || filter.isEmpty())
            filter = "all";
        return Response.ok(vfsService.getUnusedFiles(filter)).build();
    }

    @DELETE
    @Path("/unusedFiles")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response deleteUnused(@QueryParam("filter") String filter) throws SolrServerException, IOException {
        if (filter == null || filter.isEmpty())
            filter = "all";
        List<String> files = vfsService.getUnusedFiles(filter);
        for(String file: files){
            Files.delete(java.nio.file.Path.of(file));
        }
        return Response.ok(vfsService.getUnusedFiles(filter)).build();
    }

    @GET
    @Path("/containers")
    @Operation(summary = "Si ottiene la lista dei contenitori di una cartella")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getContainer(
            @QueryParam("idDirectory") String idDirectory,
            @QueryParam("idContainer") @DefaultValue("") String idContainer,
            @QueryParam("start") @DefaultValue("0") int start,
            @QueryParam("q") @DefaultValue("") String q,
            @QueryParam("rows") @DefaultValue("10") int rows,
            @QueryParam("filterUpload") @DefaultValue("false") boolean filterUpload
    ) {
        if (!StringUtils.isEmpty(idContainer) && !"null".equals(idContainer)) {
            VfsFile vfsFile = vfsService.getContainerById(idContainer, VfsFile.TYPE_CONTAINER);
            if(vfsFile!=null)
                return Response.ok(List.of(vfsFile)).build();
            else
                return Response.ok("{}").build();
        } else
            return Response.ok(vfsService.getContainers(idDirectory, null, q, start, rows, filterUpload)).build();
    }

    @GET
    @Path("/containersDataTable")
    @Operation(summary = "Si ottiene la lista dei contenitori di una cartella")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getContainerDataTable(
            @QueryParam("idDirectory") String idDirectory,
            @QueryParam("idContainer") @DefaultValue("") String idContainer,
            @QueryParam("start") @DefaultValue("0") int start,
            @QueryParam("q") @DefaultValue("") String q,
            @QueryParam("length") @DefaultValue("10") int rows,
            @QueryParam("draw") @DefaultValue("0") int draw,
            @QueryParam("filterUpload") @DefaultValue("false") boolean filterUpload,
            @QueryParam(value = "search[value]") @DefaultValue("") String term,
            WebRequest webRequest
    ) {
        if (!StringUtils.isEmpty(idContainer) && !"null".equals(idContainer)) {
            VfsFile vfsFile = vfsService.getContainerById(idContainer, VfsFile.TYPE_CONTAINER);
            if(vfsFile!=null)
                return Response.ok(makeDataTable(List.of(vfsFile),1,draw)).build();
            else
                return Response.ok("{}").build();
        } else {
            long count = vfsService.getContainersCount(idDirectory, q);
            return Response.ok(makeDataTable(vfsService.getContainers(idDirectory, null, q, start, rows, filterUpload),
                    count, draw)).build();
        }
    }

    private Object makeDataTable(List<VfsFile> containers, long count, int draw) {
        DataTableData dataTableData = new DataTableData();
        dataTableData.setRecordsFiltered((int) count);
        dataTableData.setRecordsTotal((int) count);
        dataTableData.setData(new ArrayList<>());
        dataTableData.setDraw(draw+1);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(VfsFile cFile: containers){
            List<String> values = new ArrayList<>();
            values.add(cFile.getId());
            values.add(cFile.getId());
            values.add(cFile.getLabel());
            values.add(cFile.getResourceType());
            values.add(""+cFile.isDraft());
            if(cFile.getTimestamp()!=null)
                values.add(formatter.format(cFile.getTimestamp()));
            else
                values.add(null);
            //values.add(StringUtils.isEmpty(cFile.getIdPublic())?"":cFile.getIdPublic());
            values.add(cFile.getIdPublic());
            //values.add("");
            dataTableData.getData().add(values);
        }
        return dataTableData;
    }

    @GET
    @Path("/containers-names")
    @Hidden
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getContainer(@QueryParam("q") @DefaultValue("") String q){
        return Response.ok(vfsService.getContainers(null, null, q, 0, 10, false)
                .stream().map(v-> v.getId()).collect(Collectors.toList())
            ).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/search")
    @Operation(summary = "Ricerca per i cartelle, contenitori e oggetti")
    public JsonSearchResultList searchMag(JsonSearchRequest request,
                                          @QueryParam("facetOnly") Boolean facetOnly) {

        return vfsService.search(request, facetOnly != null ? 0 : request.getLength(), true);
    }

    @GET
    @Path("/container")
    @Operation(summary = "Si ottiene l'informazioni su un contenitore")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getContainer1(
            @QueryParam("id") String idContainer
    ) {
        return Response.ok(vfsService.getContainerById(idContainer, VfsFile.TYPE_CONTAINER)).build();
    }

    @GET
    @Path("/vfsFile")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getVfsFile(
            @QueryParam("id") String idContainer,
            @QueryParam("vfsType") String vfsType
    ) {
        return Response.ok(vfsService.getContainerById(idContainer, vfsType)).build();
    }

    @POST
    @Path("/vfsFile")
    @Consumes(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response updatVfsFile(
            VfsFile vfsFile) {
        return Response.ok(vfsService.updateVfsFile(vfsFile)).build();
    }

    @GET
    @Path("/createMets")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response createMets(@QueryParam("id") String id) {
        return Response.ok(metsCreator.createMets(id, true)).build();
    }

    @GET
    @Path("/createMag")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response createMag(@QueryParam("id") String id) {
        return Response.ok(magCreator.createMag(id, true)).build();
    }

    @GET
    @Path("/publishDraftMets")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response publishDraftMets(@QueryParam("id") String id) {
        JsonImportReport report = new JsonImportReport();
        UtilSolr.setFieldSolr(id, "draft","0", true);
        return Response.ok(report).build();
    }

    @POST
    @Path("/vfsFileUpload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response updateUploadVfsFile(@FormDataParam("parent") String parent,
                                        @FormDataParam("id") String id,
                                        @FormDataParam("label") String label,
                                        @FormDataParam("directory") String directory,
                                        @FormDataParam("vfsType") String vfsType,
                                        @FormDataParam("usage") String usage,
                                        @FormDataParam("frontespizio") String frontespizio,
                                        @FormDataParam("file") InputStream uploadStream,
                                        @FormDataParam("file") FormDataContentDisposition formData) {
        if((vfsType==null || vfsType.equals("object")) && (parent == null || parent.isEmpty()))
            return uploadHttp400Error("ID del padre/container non specificato", null);
        VfsFile vfsFile = null;
        if(!StringUtils.isEmpty(id) && !id.equals("0"))
            vfsFile = vfsService.getVfsFileById(id, vfsType);
        if(vfsFile==null) {
            vfsFile = new VfsFile();
            vfsFile.setId(id);
            vfsFile.setVfsType(vfsType);
        }
        vfsFile.setParent(parent!=null?parent:directory);
        vfsFile.getDirectory().add(directory);
        vfsFile.setLabel(label);
        vfsFile.setFrontespizio(frontespizio);
        vfsFile.setUsage(usage);
        if(formData!=null && !StringUtils.isEmpty(formData.getFileName()))
            vfsFile.setFilename(formData.getFileName());
        JsonDeleteProject jsonDeleteProject = vfsService.updateVfsFile(vfsFile);
        if(jsonDeleteProject.getStatus()!=0 || uploadStream == null || formData.getSize()==0 || StringUtils.isEmpty(formData.getFileName()))
                return Response.ok(jsonDeleteProject).build();
        List<String> list = new ArrayList<>();
        vfsFile.setContainer(list);
        vfsFile.getContainer().add(parent);
        return Response.ok(vfsService.upload(vfsFile,
                formData.getFileName(), uploadStream)).build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response uploadFile(@FormDataParam("idContainer") String idContainer,
                               @FormDataParam("file") InputStream uploadStream,
                               @FormDataParam("file") FormDataContentDisposition formData) throws IOException {
        if(idContainer == null || idContainer.isEmpty())
            return uploadHttp400Error("ID del container non specificato", null);
        if(uploadStream == null)
            return uploadHttp400Error("File non specificato", null);
        return Response.ok(vfsService.upload(idContainer,
                formData.getFileName(), uploadStream)).build();
    }

    @DELETE
    @Path("/resource")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response deleteResource(@QueryParam("id") String id){
        return Response.ok(vfsService.deleteObjectById(id)).build();
    }

    @DELETE
    @Path("/container")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response deleteContainer(@QueryParam("id") String id){
        return Response.ok(vfsService.deleteContainerById(id)).build();
    }

    @DELETE
    @Path("/directory")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response deleteDirectory(@QueryParam("id") String id){
        return Response.ok(vfsService.deleteDirectoryById(id)).build();
    }

    @GET
    @Path("/resources")
    @Tag(name = "Editore Mets")
    @Operation(description = "Restituisce la lista delle risorse di un contenitore")
    @ApiResponse(description = "Desc", content = @Content(
                array = @ArraySchema( schema= @Schema(implementation = VfsFile.class))))
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getResources(
            @QueryParam("idContainer") String idContainer,
            @QueryParam("start") @DefaultValue("0") int start,
            @QueryParam("rows") @DefaultValue("10") int rows) {
        return Response.ok(vfsService.getResources(idContainer, start, rows)).build();
    }

    @GET
    @Path("/resourcesUnused")
    @Tag(name = "Editore Mets")
    @JsonView(Views.PublicView.class)
    @Operation(description = "Descrizione",summary = "Restituisce la lista delle risorse di un contenitore non linkato al Meta")
    @ApiResponse(description = "Desc", content = @Content(
            array = @ArraySchema( schema= @Schema(implementation = VfsFile.class))))
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getResourcesUnused(
            @QueryParam("idContainer") String idContainer) {
        return Response.ok(vfsService.getResources(idContainer, 0, Integer.MAX_VALUE, true)).build();
    }

}
