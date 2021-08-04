package com.systech.mss.controller;

import com.systech.mss.controller.vm.PostFormByIdVM;
import com.systech.mss.seurity.JwtTokenNeeded;
import com.systech.mss.vm.DocumentsVM;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.simple.parser.ParseException;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/api")
public interface DocumentsController {

    @Timed
    @Operation(summary = "Upload File")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Path("/documents/upload/{mssuserid}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response uploadFile(@PathParam("mssuserid") long mssuserid, MultipartFormDataInput input) throws ParseException;

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "uploadDocument")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/documents/uploadDocument")
    Response uploadDocument(@Valid DocumentsVM documentsVM);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "documents getUserUploadedDocs")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/documents/uploaded/{userId}")
    Response getUserUploadedDocs(@PathParam("userId") long userId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "documents getAllForUserOnly")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/documents/received/{userId}")
    Response getAllForUserOnly(@PathParam("userId") long userId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "documents getForPublicOnly")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/documents/public")
    Response getForPublicOnly();

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "documents getDocumentsForApproval")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/documents/getDocumentsForApproval")
    Response getDocumentsForApproval();

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "documents getDocumentsForApproval")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/documents/getDocumentsForApprovalByMemberId/{memberId}")
    Response getDocumentsForApprovalByMemberId(@PathParam("memberId") long memberId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "approve documents ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/documents/approveDocuments")
    Response approveDocuments(@Valid PostFormByIdVM postFormByIdVM);


}
