package com.systech.mss.controller;

import com.systech.mss.seurity.JwtTokenNeeded;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface FmCreController {

    @Timed
    @Operation(summary = "get all schemes ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/scheme/getAllSchemes/{mssUserId}")
    Response getAllSchemes(@PathParam("mssUserId") long mssUserid);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "search scheme by name ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/scheme/searchSchemesByName/{mssUserId}/{schemeName}")
    Response searchSchemesByName(@PathParam("mssUserId") long mssUserid, @PathParam("schemeName") String schemeName);

    @Timed
    @Operation(summary = "search scheme by name ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/scheme/getSchemeSponsors/{mssUserId}/{schemeId}")
    Response getSchemeSponsors(@PathParam("mssUserId") long mssUserid, @PathParam("schemeId") long schemeId);
}
