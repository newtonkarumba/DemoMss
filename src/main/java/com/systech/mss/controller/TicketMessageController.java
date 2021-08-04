package com.systech.mss.controller;

import com.systech.mss.domain.TicketMessage;
import com.systech.mss.seurity.JwtTokenNeeded;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface TicketMessageController {

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get Ticket Message")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ticket/{id}/message")
    Response getTicketMessage(@PathParam("id") long id);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get Ticket Message")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ticket/{id}/message/reload")
    Response getReloadTicketMessage(@PathParam("id") long id);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get Ticket Message by id")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ticket/message/{id}")
    Response getTicketMessageByiD(@PathParam("id") long id);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get tickets in a Ticket Message ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ticket/{id}/message/{userId}/user")
    Response getTicketsMessageByUserId(@PathParam("id") long id, @PathParam("userId") long userId);


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "create Ticket Message")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ticket/message")
    Response createTicketMessage(@Valid TicketMessage ticketMessage);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "edit Ticket Message")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ticket/message/{id}")
    Response editTicketMessage(@PathParam("id") long id, @Valid TicketMessage ticketMessage);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "Delete a Ticket Message")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ticket/message/{id}")
    Response deleteTicketMessage(@PathParam("id") long id);
}
