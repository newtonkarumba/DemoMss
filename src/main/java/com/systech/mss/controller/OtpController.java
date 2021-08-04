package com.systech.mss.controller;

import com.systech.mss.controller.vm.OtpVM;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/api")
public interface OtpController {
    @Timed
//    @JwtTokenNeeded
    @Operation(summary = "Get EMAIL,PHONE OR NOTIFICATION DEVICE for user")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/otp-init/{login}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    Response otpInit(@PathParam("login") String login);

    @Timed
//    @JwtTokenNeeded
    @Operation(summary = "verify the credential")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/otp-v")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    Response otpVerify(@Valid OtpVM otpVM);

    @Timed
    @Operation(summary = "otp-send")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/otp-send/{login}/{otpIdentifier}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    Response sendOtpCode(@PathParam("login") String login,@PathParam("otpIdentifier") String otpIdentifier);

    @Timed
    @Operation(summary = "enableOtpConfig")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/otpconfig-enable/{otpIdentifier}/{enable}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    Response enableOtpConfig(@PathParam("otpIdentifier") String otpIdentifier,
                             @PathParam("enable") boolean enable);

    @Timed
    @Operation(summary = "Get user otps")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/getUserOtps/{username}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    Response getUserOtps(@PathParam("username") String username);
}
