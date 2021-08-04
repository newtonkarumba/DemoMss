package com.systech.mss.controller;

import com.systech.mss.domain.SupportMessages;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface GeneralController {

    @Timed
    @Operation(summary = "receive Support Message")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/support/send")
    Response receiveSupportMessage(@Valid SupportMessages supportMessages);


    @GET
    @Path("/countries")
    @Produces(MediaType.APPLICATION_JSON)
    Response countries();

    @GET
    @Path("/district")
    @Produces(MediaType.APPLICATION_JSON)
    Response district();


    @GET
    @Path("/traditionalAuthority/{districtId}")
    @Produces(MediaType.APPLICATION_JSON)
    Response traditionalAuthority(@PathParam("districtId") long districtId);


    @GET
    @Path("/village/{traditionalAuthorityId}")
    @Produces(MediaType.APPLICATION_JSON)
    Response village(@PathParam("traditionalAuthorityId") long traditionalAuthorityId);


}
