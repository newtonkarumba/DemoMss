package com.systech.mss.controller;

import com.systech.mss.seurity.JwtTokenNeeded;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface FmPrincipalOfficerController {

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get all schemes ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getPrincipalOfficerDetails/{mssUserId}/{principalOfficerId}")
    Response getPrincipalOfficerDetails(@PathParam("mssUserId") long mssUserid,
                                        @PathParam("principalOfficerId") long principalOfficerId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get all schemes ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getPrincipalOfficerSchemes/{mssUserId}/{principalOfficerId}")
    Response getPrincipalOfficerSchemes(@PathParam("mssUserId") long mssUserid,
                                        @PathParam("principalOfficerId") long principalOfficerId);


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get all schemes ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getPrincipalOfficerEmployers/{mssUserId}/{principalOfficerId}/{schemeId}")
    Response getPrincipalOfficerEmployers(@PathParam("mssUserId") long mssUserid,
                                          @PathParam("principalOfficerId") long principalOfficerId,
                                          @PathParam("schemeId") long schemeId);


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get all schemes ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getPrincipalOfficerCostCenters/{mssUserId}/{principalOfficerId}/{schemeId}/{sponsorId}")
    Response getPrincipalOfficerCostCenters(@PathParam("mssUserId") long mssUserid,
                                            @PathParam("principalOfficerId") long principalOfficerId,
                                            @PathParam("schemeId") long schemeId,
                                            @PathParam("sponsorId") long sponsorId);


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get all schemes ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getPaidClaims/{mssUserId}/{schemeId}/{sponsorId}/{dateFrom}/{dateTo}")
    Response getPaidClaims(@PathParam("mssUserId") long mssUserid,
                           @PathParam("schemeId") long schemeId,
                           @PathParam("sponsorId") long sponsorId,
                           @PathParam("dateFrom") String dateFrom,
                           @PathParam("dateTo") String dateTo,
                           @QueryParam("start") int start,
                           @QueryParam("size") int size);


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get all schemes ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllClaims/{mssUserId}/{schemeId}/{sponsorId}/{dateFrom}/{dateTo}")
    Response getAllClaims(@PathParam("mssUserId") long mssUserid,
                          @PathParam("schemeId") long schemeId,
                          @PathParam("sponsorId") long sponsorId,
                          @PathParam("dateFrom") String dateFrom,
                          @PathParam("dateTo") String dateTo,
                          @QueryParam("start") int start,
                          @QueryParam("size") int size);


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get all schemes ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getPaidClaims/{mssUserId}/{schemeId}/{sponsorId}")
    Response getPaidClaims(@PathParam("mssUserId") long mssUserid,
                           @PathParam("schemeId") long schemeId,
                           @PathParam("sponsorId") long sponsorId,
                           @QueryParam("start") int start,
                           @QueryParam("size") int size);


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get all schemes ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllClaims/{mssUserId}/{schemeId}/{sponsorId}")
    Response getAllClaims(@PathParam("mssUserId") long mssUserid,
                          @PathParam("schemeId") long schemeId,
                          @PathParam("sponsorId") long sponsorId,
                          @QueryParam("start") int start,
                          @QueryParam("size") int size);

}
