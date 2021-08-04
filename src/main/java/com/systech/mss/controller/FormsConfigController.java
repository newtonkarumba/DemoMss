package com.systech.mss.controller;

import com.systech.mss.controller.vm.FormConfigVm;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface FormsConfigController {

    @GET
    @Path("/config")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAll();

    @GET
    @Path("/config/{formName}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getConfigForForms(@PathParam("formName") String formName);

    @GET
    @Path("/config/defaults/{formName}")
    @Produces(MediaType.APPLICATION_JSON)
    Response setDefaults(@PathParam("formName") String formName);

    @POST
    @Path("/config/update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response update(@Valid FormConfigVm formConfigVm);

}
