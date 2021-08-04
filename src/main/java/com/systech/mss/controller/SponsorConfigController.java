package com.systech.mss.controller;

import com.systech.mss.domain.SponsorConfig;
import com.systech.mss.seurity.JwtTokenNeeded;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface SponsorConfigController {
    @Timed
    @Operation(summary = "get all configs details from fundmaster")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "401", description = "Unauthorised")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSponsorActiveConfigs")
    Response getSponsorActiveConfigs();

    @Timed
    @Operation(summary = "get all configs details from fundmaster")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "401", description = "Unauthorised")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllSponsorConfigs")
    Response getAllSponsorConfigs();

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "create Config to db ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/createSponsorConfig")
    Response createSponsorConfig(@Valid SponsorConfig config);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "edit config")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/editSponsorConfig/{id}")
    Response editSponsorConfig(@PathParam("id") long id, @Valid SponsorConfig sponsorConfig);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "delete a Config  from mss")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/deleteSponsorConfig/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    Response deleteSponsorConfig(@PathParam("id") long id);
}
