package com.gruppometa.sbntecaremota.restweb;

import com.gruppometa.sbntecaremota.upgrade.UpgradeComponent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Scope("request")
@Path("/rest/upgrade")
@Tag(name = "Upgrade da versione 1 a 2")
public class UpgradeResource {

    protected static final Logger logger = LoggerFactory.getLogger(UpgradeResource.class);

    @Autowired
    protected UpgradeComponent upgradeComponent;

    @GET
    @Path("/stato")
    @Operation(summary = "Si ottiene lo stato del job di upgrade")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response getStato() {
        return Response.ok(upgradeComponent.getStato()).build();
    }

    @GET
    @Path("/stop")
    @Operation(summary = "Stop il job di upgrade")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response stop() {
        upgradeComponent.setStopped(true);
        return Response.ok(upgradeComponent.getStato()).build();
    }

    @GET
    @Path("/copy")
    @Operation(summary = "Si ottiene lo stato del job di upgrade")
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response copy(@QueryParam("query") @DefaultValue("") String query,
                         @QueryParam("start") @DefaultValue("0") int start,
                         @QueryParam("rows") @DefaultValue("1") int rows,
                         @QueryParam("all") @DefaultValue("false") boolean all,
                         @QueryParam("forceOverride") @DefaultValue("false") boolean forceOverride,
                         @QueryParam("onlyToDress") @DefaultValue("false") boolean onlyToDress
    ) {
        upgradeComponent.getStato().put("stato", "started");
        upgradeComponent.copyData(query, start, rows, all, forceOverride, onlyToDress);
        return Response.ok(upgradeComponent.getStato()).build();
    }
}
