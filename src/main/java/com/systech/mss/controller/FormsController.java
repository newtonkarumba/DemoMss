package com.systech.mss.controller;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface FormsController {
    @GET
    @Path("/forms/{mssUserId}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAll(@PathParam("mssUserId") long mssUserId);

    @Operation(summary = "Upload File Form")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Path("/forms/create/{mssUserId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response create(@PathParam("mssUserId") long mssUserId, MultipartFormDataInput input);

    @GET
    @Path("/forms/delete/{mssUserId}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response remove(@PathParam("mssUserId") long mssUserId, @PathParam("id") long id);
}
