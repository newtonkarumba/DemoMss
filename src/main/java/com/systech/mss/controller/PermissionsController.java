package com.systech.mss.controller;

import com.systech.mss.controller.vm.PermissionsVM;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface    PermissionsController {
    @GET
    @Path("/perms")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAll();

    @GET
    @Path("/perms/{profileName}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getPermissionsForProfile(@PathParam("profileName") String profileName);

    @GET
    @Path("/perms/defaults/{profileName}")
    @Produces(MediaType.APPLICATION_JSON)
    Response setDefaults(@PathParam("profileName") String profileName);

    @GET
    @Path("/perms/defaults")
    @Produces(MediaType.APPLICATION_JSON)
    Response setDefaults();

    @POST
    @Path("/perms/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateProfile(@Valid PermissionsVM permissionsVM);

}
