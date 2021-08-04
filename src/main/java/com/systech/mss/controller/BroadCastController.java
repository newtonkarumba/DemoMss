package com.systech.mss.controller;

import com.systech.mss.seurity.JwtTokenNeeded;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface BroadCastController {

    @Timed
    @Operation(summary = "Upload File")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Path("/sendBroadCast/{mssUserId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response sendBroadCast(@PathParam("mssUserId") long mssUserId, MultipartFormDataInput input);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "getInboxMessagesForUser")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/getInboxMessagesForUser/{mssUserId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    Response getInboxMessagesForUser(@PathParam("mssUserId") long mssUserId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "getOutboxMessagesForUser")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/getOutboxMessagesForUser/{mssUserId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    Response getOutboxMessagesForUser(@PathParam("mssUserId") long mssUserId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "setMessageRead")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/setMessageRead/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    Response setMessageRead(@PathParam("id") long id);


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "getUnreadMessage")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/getUnreadMessage/{mssUserId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    Response getUnreadMessage(@PathParam("mssUserId") long mssUserId);
}
