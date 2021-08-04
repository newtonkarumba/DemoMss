package com.systech.smileIdentity.controller;

import com.systech.mss.seurity.JwtTokenNeeded;
import com.systech.smileIdentity.DTO.ProcessSelfieDto;
import com.systech.smileIdentity.Service.vm.SelfiePaymentVm;
import com.systech.smileIdentity.model.SelfieAction;
import com.systech.smileIdentity.model.SmileIdentityConfig;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/api/smileIdentity")
public interface SmileIdentityController {

    @Operation(summary = "smile identity")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "401", description = "Unauthorised")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/smileIdentityCallback")
    Response smileIdentityCallbackPost(String json);

    @Operation(summary = "test register")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "401", description = "Unauthorised")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/testRegister/{userId}/{jobId}")
    Response testRegister(@PathParam("userId") String userId,@PathParam("jobId") String jobId );

    @Operation(summary = "test register")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "401", description = "Unauthorised")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/processSelfie/{cycleId}/{mssUserId}")
    Response processSelfie(@Valid ProcessSelfieDto processSelfieDto,
                           @PathParam("cycleId") long cycleId,
                           @PathParam("mssUserId") long mssUserId
    );


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get all configs details from fundmaster")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "401", description = "Unauthorised")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllSelfieConfigs")
    Response getAllSelfieConfigs();

    @Timed
    @Operation(summary = "create Config to db ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/createConfig")
    Response createConfig(@Valid SmileIdentityConfig smileIdentityConfig);



    @Timed
    @Operation(summary = "edit config")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/saveConfig")
    Response saveConfig(@Valid SmileIdentityConfig smileIdentityConfig);

    @Timed
    @Operation(summary = "delete a Config  from mss")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/deleteConfig/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    Response deleteConfig(@PathParam("id") long id);

    @Timed
    @Operation(summary = "Re enroll user")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/ReEnrollUser/{mssUserId}/{userId}")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    Response ReEnrollUser(@PathParam("mssUserId") long mssUserId,@PathParam("userId") long userId);

    @Timed
    @Operation(summary = "deactivate user")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/DeactivateUser/{mssUserId}/{userId}")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    Response DeactivateUser(@PathParam("mssUserId") long mssUserId,@PathParam("userId") long userId);

    @Timed
    @Operation(summary = "delete a user")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/DeleteUser/{mssUserId}/{userId}")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    Response DeleteUser(@PathParam("mssUserId") long mssUserId,@PathParam("userId") long userId);

    @Timed
    @Operation(summary = "load User Images")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/loadUserImages/{mssUserId}/{userId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    Response loadUserImages(@PathParam("mssUserId") long mssUserId,@PathParam("userId") long userId) throws IOException;

    @Timed
    @Operation(summary = "load User Registration Image")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/loadUserRegistrationImage/{mssUserId}/{userId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    Response loadUserRegistrationImage(@PathParam("mssUserId") long mssUserId,@PathParam("userId") long userId);

    @Timed
    @Operation(summary = "load User Registration Image")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/checkIfViableForAuthentication/{mssUserId}/{userId}/{cycleId}/{schemeId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    Response checkIfViableForAuthentication(@PathParam("mssUserId") long mssUserId,
                                            @PathParam("userId") long userId,
                                            @PathParam("cycleId") long cycleId,
                                            @PathParam("schemeId") long schemeId
    );

    @Timed
    @Operation(summary = "load User Registration Image")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/checkIfViableForRegistration/{mssUserId}/{userId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    Response checkIfViableForRegistration(@PathParam("mssUserId") long mssUserId,
                                            @PathParam("userId") long userId
    );

    @Timed
    @Operation(summary = "load User Registration Image")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/loadSelfieRegistrationDetails/{mssUserId}/{memberNo}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    Response loadSelfieRegistrationDetails(@PathParam("mssUserId") long mssUserId,
                                            @PathParam("memberNo") String searchKey
    );

    @Timed
    @Operation(summary = "make Payment")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/makePayment/{mssUserId}")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    Response makePayment(@PathParam("mssUserId") long mssUserId, @Valid SelfiePaymentVm selfiePaymentVm);

    @Timed
    @Operation(summary = "make Payment")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/checkPaymentStatus/{mssUserId}/{userId}/{selfieAction}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    Response checkPaymentStatus(@PathParam("mssUserId") long mssUserId,
                                @PathParam("userId") long userId,
                                @PathParam("selfieAction") SelfieAction selfieAction
    );

    @Timed
    @Operation(summary = "get users Payments")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/getUsersAllPayments/{mssUserId}/{userId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    Response getUsersAllPayments(@PathParam("mssUserId") long mssUserId,
                                @PathParam("userId") long userId
    );


    @Timed
    @Operation(summary = "get users Payments")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/resetAuthenticationTrials/{mssUserId}/{userId}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    Response resetAuthenticationTrials(@PathParam("mssUserId") long mssUserId,
                                @PathParam("userId") long userId
    );

}
