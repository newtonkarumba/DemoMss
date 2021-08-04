package com.systech.mss.controller;

import com.systech.mss.seurity.JwtTokenNeeded;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface ActivityTrailController {

    @GET
    @JwtTokenNeeded
    @Path("/activityTrail/getAll/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getAll(@PathParam("userId") long userId);


    @GET
    @JwtTokenNeeded
    @Path("/activityTrail/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getByUserId(@PathParam("userId") long userId);


    @GET
    @JwtTokenNeeded
    @Path("/activityTrail/by-date/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getActivityOfDate(@PathParam("date") String date);

    @GET
    @JwtTokenNeeded
    @Path("/activityTrail/search/{mssuserid}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    Response SearchActivityTrail(@PathParam("mssuserid") long mssuserid,@PathParam("name") String name);

    @GET
    @JwtTokenNeeded
    @Path("/activityTrail/filter/{mssuserid}/{dateFrom}/{dateTo}")
    @Produces(MediaType.APPLICATION_JSON)
    Response filterActivityByDate(@PathParam("mssuserid") long mssuserid,@PathParam("dateFrom") String dateFrom, @PathParam("dateTo") String dateTo);

    @GET
    @Path("/activityTrailMultiUser/search/{mssuserid}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    Response activityTrailMultiUser(@PathParam("mssuserid") long mssuserid,@PathParam("name") String name);

    @GET
    @JwtTokenNeeded
    @Path("/activityTrailMultiUser/filter/{mssuserid}/{dateFrom}/{dateTo}")
    @Produces(MediaType.APPLICATION_JSON)
    Response activityTrailMultiUser(@PathParam("mssuserid") long mssuserid,@PathParam("dateFrom") String dateFrom, @PathParam("dateTo") String dateTo);

    @GET
    @JwtTokenNeeded
    @Path("/activityTrail/by-user-date/{userId}/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getActivityLogOfUserOnDate(@PathParam("userId") long userId, @PathParam("date") String date);
}
