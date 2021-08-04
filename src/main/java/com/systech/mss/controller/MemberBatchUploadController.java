package com.systech.mss.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.systech.mss.controller.vm.SaveMemberBatchVM;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface MemberBatchUploadController {

    @Timed
    @Operation(summary = "Upload Batch template")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Path("/uploadMemberFile/{mssuserid}/{sponsorId}/{schemeId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response uploadFile(@PathParam("mssuserid") long mssuserid, @PathParam("sponsorId") long sponsorId,  @PathParam("schemeId")long schemeId, MultipartFormDataInput input);


    @Timed
    @Operation(summary = "Save Batch template")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Path("/save/{mssuserid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response saveFile(@PathParam("mssuserid") long mssuserid ,@Valid SaveMemberBatchVM saveMemberBatchVM) ;
}
