package com.systech.mss.controller;

import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface FmSchemeController {
    @Timed
    @Operation(summary = "get schemes from fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/schemes")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    Response getSchemes();

    @Timed
    @Operation(summary = "get schemes from fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/schemes/getSchemeById/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    Response getSchemeById(@PathParam("id") long id);

    @Timed
    @Operation(summary = "get schemes from fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/schemes/getSchemeByName/{schemeName}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    Response getSchemeByName(@PathParam("schemeName") String schemeName);

    @Timed
    @Operation(summary = "get schemes from fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/schemes/getAllBanks")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    Response getAllBanks();

    @Timed
    @Operation(summary = "get schemes from fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/schemes/getBankBranches/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    Response getBankBranches(@PathParam("id") long id);
}
