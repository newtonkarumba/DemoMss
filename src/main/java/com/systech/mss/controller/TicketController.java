package com.systech.mss.controller;

import com.systech.mss.controller.vm.ForwardTicketVm;
import com.systech.mss.domain.Ticket;
import com.systech.mss.seurity.JwtTokenNeeded;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/api")
public interface TicketController {

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get Ticket")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ticket")
    Response getTickets(@QueryParam("start") int start, @QueryParam("limit") int size);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get all Ticket")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ticket/{start}/{limit}")
    Response getTicketsRange(@PathParam("start") int start, @PathParam("limit") int size);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get Ticket by id")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ticket/{id}")
    Response getTicketByiD(@PathParam("id") long id);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "create ticket")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ticket")
    Response createTicket(@Valid Ticket ticket);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "edit ticket")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ticket/{id}")
    Response editTicket(@PathParam("id") long id, @Valid Ticket ticket);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "close a ticket")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ticket/{id}/close")
    Response closeTicket(@PathParam("id") long id);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "open a ticket")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ticket/{id}/open")
    Response openTicket(@PathParam("id") long id);

    //    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get ticket by recipient profile using profile  id and sponsor id and/or scheme id")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ticket/getTicketByRecipientProfile/{profileName}/")
    Response getTicketByRecipientProfile(@PathParam("profileName") String profileName
            , @QueryParam("sponsorId") long sponsorId
            , @QueryParam("schemeId") long schemeId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get ticket brecent ticket  using profile  id ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ticket/getRecentTickets/{profileName}/{start}/{size}")
    Response getRecentTickets(@PathParam("profileName") String profileName, @PathParam("start") int start, @PathParam("size") int size);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get count of open support tickets ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ticket/getCountOfOpenSupportTicket/{profileName}")
    Response getCountOfOpenSupportTicket(@PathParam("profileName") String profileName) throws IOException;

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "forward ticket")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/ticket/forwardTicket")
    Response ForwardTicket(@Valid ForwardTicketVm forwardTicketVm);

    @GET
    @JwtTokenNeeded
    @Path("/ticket/getTicketRaisedBetween/{mssuserid}/{dateFrom}/{dateTo}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTicketRaisedBetween(@PathParam("mssuserid") long mssuserid, @PathParam("dateFrom") String dateFrom, @PathParam("dateTo") String dateTo);

    @GET
    @JwtTokenNeeded
    @Path("/ticket/getTicketRaisedBetween/{mssuserid}/{profileName}/{dateFrom}/{dateTo}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTicketRaisedBetween(@PathParam("mssuserid") long mssuserid, @PathParam("profileName") String profileName, @PathParam("dateFrom") String dateFrom, @PathParam("dateTo") String dateTo);

    @GET
    @JwtTokenNeeded
    @Path("/ticket/getTicketRaisedUnreplied/{mssuserid}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getTicketRaisedUnreplied(@PathParam("mssuserid") long mssuserid);

}
