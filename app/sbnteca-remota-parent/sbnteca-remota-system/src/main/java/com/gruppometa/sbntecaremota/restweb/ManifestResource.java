package com.gruppometa.sbntecaremota.restweb;

import com.gruppometa.sbntecaremota.iiif.Manifest3Repository;
import com.gruppometa.sbntecaremota.iiif.PathMapper;
import com.gruppometa.sbntecaremota.model.iiif.v3.Manifest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Scope("request")
@Path("/iiif")
@Tag(name = "Servizi IIIF")
public class ManifestResource {
    @Autowired
    Manifest3Repository manifest3Repository;

    @GET
    @Path("/v3/{id}/manifest.json")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Manifest getManifest(@PathParam("id") String id){
        String idReal = PathMapper.decode(id);
        //teca = PathMapper.decode(teca);
        return manifest3Repository.getManifest(idReal, id);
    }

    @GET
    @Path("/v3/manifestById.json")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Manifest getManifestFromId(@QueryParam("id") String id,
                                      @QueryParam("usage") @DefaultValue("3") String usage){
        String encoded = PathMapper.encode(id);
        return manifest3Repository.getManifest(id, usage, encoded);
    }

}
