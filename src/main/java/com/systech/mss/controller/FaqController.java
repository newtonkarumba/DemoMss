package com.systech.mss.controller;


import com.systech.mss.controller.vm.FaqVM;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface FaqController {
    @Timed
    @Operation(summary = "get all FAQs")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/faq/getAll")
    Response getAll();

    @Timed
    @Operation(summary = "get all FAQs By profile")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/faq/getAllByProfile/{profileId}")
    Response getAllByProfile(@PathParam("profileId") long profileId);

    @Timed
    @Operation(summary = "Add Edit FAQs")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/faq/addEditFAQ")
    Response addEditFAQ(@Valid FaqVM faqVM);

    @Timed
    @Operation(summary = "DELETE  FAQs")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/faq/delete/{mssUserId}/{id}")
    Response delete(@PathParam("mssUserId") long mssUserId,@PathParam("id") long id);
}
