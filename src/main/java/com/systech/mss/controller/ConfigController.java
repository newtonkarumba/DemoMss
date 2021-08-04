package com.systech.mss.controller;


import com.systech.mss.domain.Config;
import com.systech.mss.seurity.JwtTokenNeeded;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface ConfigController {
    @Timed
    @Operation(summary = "get all configs details from fundmaster")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "401", description = "Unauthorised")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSpecificFieldsOfActiveConfigs")
    Response getSpecificFieldsOfActiveConfigs();

    @Timed
    @Operation(summary = "get all configs details from fundmaster")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "401", description = "Unauthorised")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllConfigs")
    Response getAllConfigs();

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "create Config to db ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/createConfig")
    Response createConfig(@Valid Config config);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "edit config")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/editConfig")
    Response editConfig(@Valid Config config);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "delete a Config  from mss")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/deleteConfig/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    Response deleteConfig(@PathParam("id") long id);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get  BusinessImage  details from fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/updateBusinessImage/{documentId}")
    Response updateBusinessImage(@PathParam("documentId") long documentId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "Enable Disable Two Factor AUth")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/enable2FA/{enable}")
    Response enable2FA(@PathParam("enable") boolean enable);

}
