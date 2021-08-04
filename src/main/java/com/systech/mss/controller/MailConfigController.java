package com.systech.mss.controller;

import com.systech.mss.domain.MailConfig;
import com.systech.mss.seurity.JwtTokenNeeded;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface MailConfigController {

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get all mail configs details from fundmaster")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "401", description = "Unauthorised")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllMailConfigs")
    Response getAllMailConfigs();

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "create Config to db ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/createMailConfig")
    Response createMailConfig(@Valid MailConfig mailConfig);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "edit config")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/editMailConfig")
    Response editMailConfig(@Valid MailConfig mailConfig);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "delete a mail Config  from mss")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/deleteMailConfig/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    Response deleteMailConfig(@PathParam("id") long id);
}
