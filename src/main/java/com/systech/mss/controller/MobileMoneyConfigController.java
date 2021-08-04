package com.systech.mss.controller;

import com.systech.mss.domain.MobileMoneyConfig;
import com.systech.mss.seurity.JwtTokenNeeded;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface MobileMoneyConfigController {

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get all mobile money configs details from fundmaster")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "401", description = "Unauthorised")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getSpecificFieldsOfActiveMobileMoneyConfigs")
    Response getSpecificFieldsOfActiveMobileMoneyConfigs();

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get all mobile money configs details from fundmaster")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "401", description = "Unauthorised")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllMobileMoneyConfigs")
    Response getAllMobileMoneyConfigs();

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "create Config to db ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/createMobileMoneyConfig")
    Response createMobileMoneyConfig(@Valid MobileMoneyConfig mobileMoneyConfig);


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "edit config")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/editMobileMoneyConfig")
    Response editMobileMoneyConfig(@Valid MobileMoneyConfig mobileMoneyConfig);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "delete a mobile money Config  from mss")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/deleteMobileMoneyConfig/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    Response deleteMobileMoneyConfig(@PathParam("id") long id);


    @Timed
    @Operation(summary = "mpesa callback")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/mpesaCallBack/smileIdentity")
    Response SmileIdentityMpesaCallBack(String response);

    @Timed
    @Operation(summary = "mpesa timeout")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/mpesaTimeoutCallBack/smileIdentity")
    Response smileIdentityMpesaTimeoutCallBack(String s);

}
