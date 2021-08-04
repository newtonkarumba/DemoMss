package com.systech.mss.controller;

import com.systech.mss.controller.vm.ManagedUserVM;
import com.systech.mss.domain.User;
import com.systech.mss.seurity.JwtTokenNeeded;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URISyntaxException;

@Path("/api")
public interface UserController {

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "create a new user")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path(value = "/users")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response createUser(ManagedUserVM managedUserVM) throws URISyntaxException;

    @Timed
//    @JwtTokenNeeded
    @Operation(summary = "get All Users")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllUsers")
    Response getAllUsers();

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "reset  users")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/resetUser/{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    Response resetUser(@PathParam("id") long id);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "reset  users")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/resetPasswordByAdmin/{id}")
    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    Response resetPasswordByAdmin(@PathParam("id") long id);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "edit a user")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/editUser")
    Response editUser(@Valid User user);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "delete a User from mss")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/dropUser/{id}")
    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    Response dropUser(@PathParam("id") long id);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get a User from mss by id")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @Path("/getUser/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    Response getUser(@PathParam("id") long id);

    @JwtTokenNeeded
    @Operation(summary = "get All Users by sponsor id")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllUsers/{mssuserid}/{sponsorId}")
    Response getAllUsers(@PathParam("mssuserid") long mssuserid, @PathParam("sponsorId") long sponsorId);


    @JwtTokenNeeded
    @Operation(summary = "get All Users by sponsor id")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getUnapprovedClaimAuthorizerUsers/{mssuserid}")
    Response getUnapprovedClaimAuthorizerUsers(@PathParam("mssuserid") long mssuserid);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get Ticket created by user id")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user/{userId}/ticket/created")
    Response getTicketByCreatedByUserId(@PathParam("userId") long userId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get Ticket created by user id in range")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user/{userId}/{start}/{size}/ticket/created")
    Response getTicketByCreatedByUserIdInRange(@PathParam("userId") long userId, @PathParam("start") int start, @PathParam("size") int size);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get Ticket created by user id")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user/{userId}/ticket/received")
    Response getTicketByRecipientId(@PathParam("userId") long userId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get Ticket created by user id in range")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user/{userId}/{start}/{size}/ticket/received")
    Response getTicketByRecipientIdInRange(@PathParam("userId") long userId, @PathParam("start") int start, @PathParam("size") int size);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get All Users Count")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getAllUsersCount")
    Response getAllUsersCount() throws IOException;

    @Timed
//    @JwtTokenNeeded
    @Operation(summary = "get Users By Profile")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "bad request")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user/getUserByProfile/{mssuserid}/{profileName}")
    Response getUserByProfile(@PathParam("mssuserid") long mssuserid, @PathParam("profileName") String profileName);

    @GET
//    @JwtTokenNeeded
    @Path("/user/getUsersRegisteredBetween/{mssuserid}/{dateFrom}/{dateTo}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getUsersRegisteredBetween(@PathParam("mssuserid") long mssuserid, @PathParam("dateFrom") String dateFrom, @PathParam("dateTo") String dateTo);

    @GET
//    @JwtTokenNeeded
    @Path("/user/getUsersRegisteredBetween/{mssuserid}/{profileName}/{dateFrom}/{dateTo}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getUsersRegisteredBetween(@PathParam("mssuserid") long mssuserid, @PathParam("profileName") String profileName, @PathParam("dateFrom") String dateFrom, @PathParam("dateTo") String dateTo);

    @Timed
    @Operation(summary = "updateAccountImage")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Path("/user/updateAccountImage/{mssUserId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response updateAccountImage(@PathParam("mssUserId") long mssuserid, MultipartFormDataInput input);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "approveUnauthorizedUserByCrm")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Path("approveUnauthorizedUserByCrm/{mssUserId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response approveUnauthorizedUserByCrm(@PathParam("mssUserId") long mssuserid);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "searchUser")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Path("searchUser/{mssUserId}/{searchKey}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response searchUser(@PathParam("mssUserId") long mssuserid, @PathParam("searchKey") String searchKey);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "searchUser")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Path("searchUser/{mssUserId}/{searchKey}/{profile}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response searchUser(@PathParam("mssUserId") long mssuserid, @PathParam("searchKey") String searchKey, @PathParam("profile") String profile);

    @Timed
    @Operation(summary = "filterByScheme")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Path("users/filterbyscheme/{profile}/{schemeId}/{sponsorId}")
    @Produces(MediaType.APPLICATION_JSON)
    Response filterByScheme(@PathParam("profile") String profile, @PathParam("schemeId") long schemeId, @PathParam("sponsorId") long sponsorId);

}
