package com.systech.mss.controller;

import com.systech.mss.seurity.JwtTokenNeeded;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface DateValue {
    @GET
    @JwtTokenNeeded
    @Path("/dates")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAll();

    @POST
    @JwtTokenNeeded
    @Path("/dates/update/{billDate}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response updateDate(@PathParam("billDate") long billDate);
}
