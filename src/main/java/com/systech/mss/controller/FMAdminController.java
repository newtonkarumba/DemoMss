package com.systech.mss.controller;

import com.systech.mss.controller.vm.CreateAdminVM;
import com.systech.mss.domain.Admins;
import com.systech.mss.seurity.JwtTokenNeeded;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface FMAdminController {

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get admin details by staff number")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAdminByStaffNo/{StaffNo}")
    Response getAdminByStaffNo(@PathParam("StaffNo") long StaffNo);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get all admin details from fundmaster")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "401", description = "Unauthorised")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAdminDetailsAll")
    Response getAdminDetailsAll();

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "create Admin to db ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/createAdmin")
    Response createAdmin(@Valid Admins admins);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "create Admin to db ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/admin/createAdmin")
    Response adminCreateAdmin(@Valid CreateAdminVM adminVM);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "create Admin to db ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/admin/edit/{mssUserId}")
    Response adminEditAdmin(@PathParam("mssUserId")long mssUserId,@Valid CreateAdminVM adminVM);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "delete an Admin  from mss")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/lockAccount/{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    Response lockAccount(@PathParam("id") long id);


    @Timed
    @Operation(summary = "Upload File")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Path("/sendFileToUsers/{mssUserId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response sendFileToUsers(@PathParam("mssUserId") long mssUserId, MultipartFormDataInput input);

}


