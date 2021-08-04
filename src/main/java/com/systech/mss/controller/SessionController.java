package com.systech.mss.controller;

import com.systech.mss.seurity.JwtTokenNeeded;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/api")
public interface SessionController {
    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get Sessions from fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllSessions")
    Response getSessions();

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "filter Sessions by date from fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/filterSessions/{date}")
    Response filterSessions(String date);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get Sessions by id from fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/Sessions/{id}")
    Response getSessionsSingle(long id);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get Sessions from fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSessionCount")
    Response getSessionCount() throws IOException;

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get Sessions in a week")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSessionCountInAWeek")
    Response getSessionCountInAWeek();

}
