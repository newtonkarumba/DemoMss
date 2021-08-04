package com.systech.mss.controller;

import com.systech.mss.controller.vm.ManagedUserVM;
import com.systech.mss.controller.vm.RegisterUserVm;
import com.systech.mss.domain.Member;
import com.systech.mss.service.dto.UserDTO;
import com.systech.mss.seurity.JwtTokenNeeded;
import com.systech.mss.vm.KeyAndPasswordVM;
import com.systech.mss.vm.PasswordChangeVM;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface AccountController {

    @Timed
    @Operation(summary = "register the user")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    Response registerAccount(@Valid ManagedUserVM managedUserVM);

    @Timed
    @Operation(summary = "register the user")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/registerUserAccount")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    Response registerUserAccount(@Valid RegisterUserVm registerUserVm);

    @Timed
    @Operation(summary = "register members in Batch")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/registerMembersInBatch/{mssUserId}")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response registerMembersInBatch(@PathParam("mssUserId") long mssUserId, MultipartFormDataInput input);

    @Timed
    @Operation(summary = "member self registration")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/register/new")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    Response newMemberRegisterAccount(@Valid Member member);

    @Timed
    @Operation(summary = "Member on boarding ETL")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/register/new/ETL")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    Response newMemberRegisterAccountETL(String data);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "updatePotentialMember")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/updatePotentialMember")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    Response updateMemberRegisterAccountETL(String data);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "Get members awaiting validation by scheme and sponsor")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/register/members/{schemeId}/{sponsorId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    Response getRegisteredNewMembers(@PathParam("schemeId") long schemeId,
                                            @PathParam("sponsorId") long sponsorId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "Get members awaiting validation")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/register/membersdetails/{memberId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    Response getRegisteredNewMemberDetails(@PathParam("memberId") long memberId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "Get members awaiting validation")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/register/member/docs/{memberId}")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    Response getRegisteredNewMembersDocs(@PathParam("memberId") long memberId);

    @Timed
    @Operation(summary = "Upload File")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Path("/register/new/upload/{memberId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response uploadFile(@PathParam("memberId") long memberId, MultipartFormDataInput input);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "register the user to Xe")
    @APIResponse(responseCode = "201", description = "Created")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/register/sendToXe")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
    Response newMemberRegisterSendToXe(@Valid Member member);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "approve New Member Details ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/register/approve/{mssUserId}/{id}")
    Response approveNewMemberDetails(@PathParam("mssUserId") long mssUserId, @PathParam("id") long id);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "decline New Member Details ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/register/decline/{mssUserId}/{id}")
    Response declineNewMemberDetails(@PathParam("mssUserId") long mssUserId, @PathParam("id") long id);

    @Timed
    @Operation(summary = "activate the registered user")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    @Path("/activate")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    Response activateAccount(@QueryParam("key") String key);

    @Timed
    @Operation(summary = "check if the user is authenticated")
    @Path("/authenticate")
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    String isAuthenticated();

    @JwtTokenNeeded
    @Timed
    @Operation(summary = "get the current user")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    @Path("/account")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    Response getAccount();

    @JwtTokenNeeded
    @Timed
    @Operation(summary = "update the current user information")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    @Path("/account")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    Response saveAccount(@Valid UserDTO userDTO);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "changes the current user's password")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/account/change-password")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    Response changePassword(PasswordChangeVM passwordChangeVM);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "changes the current user's password")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/account/change-password-ncp")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    Response changePasswordNoCurrentPwd(PasswordChangeVM passwordChangeVM);

    @Timed
    @Operation(summary = "Send an e-mail to reset the password")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @Path("/account/reset-password/{email}")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    Response requestPasswordReset(@PathParam("email") String mail);

    @Timed
    @Operation(summary = "reset the password")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    @Path("/account/reset-password/finish")
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    Response finishPasswordReset(KeyAndPasswordVM keyAndPassword);


    @GET
    @Path("/checkIfExits/{identifierValue}/{email}/{profile}")
    @Produces({MediaType.APPLICATION_JSON})
    Response checkIfMemberExists(@PathParam("identifierValue") String identifierValue, @PathParam("email") String email, @PathParam("profile") String profile);


    @GET
    @Path("/account/etl-rpwd/{username}/{type}")
    @Produces({MediaType.APPLICATION_JSON})
    Response etlRequestPasswordReset(@PathParam("username") String username,@PathParam("type") String type);

    @GET
    @Path("/account/etl-v/{username}/{otpcode}")
    @Produces({MediaType.APPLICATION_JSON})
    Response etlRequestPasswordVerifyCode(@PathParam("username") String username,
                                          @PathParam("otpcode") String otpCode);

    @Timed
    @Operation(summary = "reset the password")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "400", description = "Bad Request")
    @APIResponse(responseCode = "500", description = "Internal Server Error")
    @Path("/account/etl-newpwd/{username}/{pwd}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    Response etlResetPwdFinish(@PathParam("username") String username,@PathParam("pwd") String pwd);


    @GET
    @Path("/testEmail/{userId}/{action}")
    @Produces({MediaType.APPLICATION_JSON})
    Response testEmail(@PathParam("userId") long userId, @PathParam("action") String action);

    @GET
    @Path("/testEmailPlain/{email}")
    @Produces({MediaType.APPLICATION_JSON})
    Response testEmailPlain(@PathParam("email") String email);

}
