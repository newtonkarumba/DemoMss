package com.systech.mss.controller;

import com.systech.mss.controller.vm.ProfileVM;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface ProfileController {

    @GET
    @Path("/selfRegisterProfiles")
    @Produces(MediaType.APPLICATION_JSON)
    Response selfRegisterProfiles();

    @GET
    @Path("/profiles")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAll();

    @GET
    @Path("/profiles/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getById(@PathParam("id") long id);

    @POST
    @Path("/profiles/edit/{mssuserid}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response edit(@PathParam("mssuserid") long mssUserId, @Valid ProfileVM profileVM);
}
