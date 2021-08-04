package com.systech.mss.controller;

import com.systech.mss.controller.vm.EmailTemplatesVM;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface EmailTemplatesController {
    @Timed
    @Operation(summary = "get all templates")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/emailTemplate/getAll")
    Response getAll();

    @Timed
    @Operation(summary = "get a template")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/emailTemplate/{emailTemplateEnum}")
    Response get(@PathParam("emailTemplateEnum") String emailTemplateEnum);

    @POST
    @Path("/emailTemplate")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response edit(@Valid EmailTemplatesVM emailTemplatesVM);

    @GET
    @Path("/getEmailTemplatesEnum")
    @Produces(MediaType.APPLICATION_JSON)
    Response getEmailTemplatesEnum();
}
