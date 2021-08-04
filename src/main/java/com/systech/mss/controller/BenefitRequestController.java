package com.systech.mss.controller;

import com.systech.mss.controller.vm.BenefitDeclineVM;
import com.systech.mss.controller.vm.PostFormByIdVM;
import com.systech.mss.seurity.JwtTokenNeeded;
import com.systech.mss.vm.benefitrequest.*;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.simple.parser.ParseException;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/api")
public interface BenefitRequestController {

    @Timed
    @Operation(summary = "Upload File")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Path("/benefitRequest/uploadClaimDocuments/{mssuserid}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response uploadClaimDocuments(@PathParam("mssuserid") long mssuserid, MultipartFormDataInput input) throws ParseException;

    @Timed
    @Operation(summary = "Upload File")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Path("/benefitRequest/uploadClaimDocument/{mssuserid}/{claimDocumentId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response uploadClaimDocument(@PathParam("mssuserid") long mssuserid, @PathParam("claimDocumentId") long claimDocumentId, MultipartFormDataInput input) throws ParseException;

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get single row member details from fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/getMemberBenefitRequests/{mssUserId}/{memberId}")
    Response getMemberBenefitRequests(@PathParam("mssUserId") long mssUserId, @PathParam("memberId") long memberId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "save Personal Details to MSS")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/savePersonalDetails/{mssUserId}/{memberId}")
    Response savePersonalDetails(@PathParam("mssUserId") long mssUserId, @PathParam("memberId") long memberId, @Valid PersonalDetailsVM personalDetailsVM); //step one

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "save Ground Of Benefits To Mss")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/saveGroundOfBenefits/{mssUserId}/{memberId}")
    Response saveGroundOfBenefits(@PathParam("mssUserId") long mssUserId, @PathParam("memberId") long memberId, @Valid GroundOfBenefits groundOfBenefits); //step one


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "Get documents for reason for exit")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/getDocumentsForReasonOfExit/{mssUserId}/{reasonForExitId}")
    Response getDocumentsForReasonOfExit(@PathParam("mssUserId") long mssUserId, @PathParam("reasonForExitId") long reasonForExitId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get single row member details from fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/updateSetDocumentsUploaded/{mssUserId}/{recordId}")
    Response updateSetDocumentsUploaded(@PathParam("mssUserId") long mssUserId, @PathParam("recordId") long recordId);


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "save Ground Of Benefits To Mss")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/saveMedicalReasons/{mssUserId}")
    Response saveMedicalReasons(@PathParam("mssUserId") long mssUserId, @Valid MedicalReasons medicalReasons);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "save Ground Of Benefits To Mss")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/savePaymentOptions/{mssUserId}")
    Response savePaymentOptions(@PathParam("mssUserId") long mssUserId, @Valid PaymentOptionsVM paymentOptionsVM); //step four

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "save Ground Of Benefits To Mss")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/checkVestingLiabilities/{mssUserId}/{id}")
    Response checkVestingLiabilities(@PathParam("mssUserId") long mssUserId, @PathParam("id") long id);


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "save Ground Of Benefits To Mss")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/saveBankDetails/{mssUserId}")
    Response saveBankDetails(@PathParam("mssUserId") long mssUserId, @Valid BankDetailsVM bankDetailsVM);//step six

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "save Ground Of Benefits To Mss")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/finishSavingBenefitRequest/{mssUserId}/{reqId}")
    Response finishSavingBenefitRequest(@PathParam("mssUserId") long mssUserId, @PathParam("reqId") long reqId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "save Ground Of Benefits To Mss")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/finishSavingBenefitRequestPo/{mssUserId}/{reqId}")
    Response finishSavingBenefitRequestPo(@PathParam("mssUserId") long mssUserId, @PathParam("reqId") long reqId);


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get benefits from members by scheme id and sponsor id")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/getBenefitsBySchemeIdAndSponsorId/{schemeId}/{sponsorId}")
    Response getBenefitsBySchemeIdAndSponsorId(@PathParam("schemeId") long schemeId, @PathParam("sponsorId") long sponsorId);


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get benefits from members by scheme id and sponsor id")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/getBenefitsBySchemeId/{mssUserId}/{schemeId}")
    Response getBenefitsBySchemeId(@PathParam("mssUserId") long mssUserId, @PathParam("schemeId") long schemeId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get benefits from members by scheme id and sponsor id")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/getUnapprovedBenefitsBySchemeId/{mssUserId}/{schemeId}")
    Response getUnapprovedBenefitsBySchemeId(@PathParam("mssUserId") long mssUserId, @PathParam("schemeId") long schemeId);

    @Timed
    @Operation(summary = "get benefits from members by scheme id and sponsor id")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/getUncertifiedBenefitsBySchemeId/{mssUserId}/{schemeId}")
    Response getUncertifiedBenefitsBySchemeId(@PathParam("mssUserId") long mssUserId, @PathParam("schemeId") long schemeId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get benefits from members by scheme id and sponsor id")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/getUnauthorizedBenefitsBySchemeId/{mssUserId}/{schemeId}")
    Response getUnauthorizedBenefitsBySchemeId(@PathParam("mssUserId") long mssUserId, @PathParam("schemeId") long schemeId);


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get  all benefits ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/getAllBenefits/{mssUserId}")
    Response getAllBenefits(@PathParam("mssUserId") long mssUserId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get  all benefits ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/getAllUnAuthorizedCrmBenefits/{mssUserId}")
    Response getAllUnAuthorizedCrmBenefits(@PathParam("mssUserId") long mssUserId);


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get  all benefits ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/saveBenefitsFM")
    Response saveBenefitsFM(@Valid PostFormByIdVM postFormByIdVM);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "approve benefits ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/approveBenefits")
    Response approveBenefits(@Valid PostFormByIdVM postFormByIdVM);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "certify benefits ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/certifyBenefits")
    Response certifyBenefits(@Valid PostFormByIdVM postFormByIdVM);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "authorize benefits ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/authorizeBenefits")
    Response authorizeBenefits(@Valid PostFormByIdVM postFormByIdVM);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "approve benefits ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/sponsorDeclineBenefits")
    Response sponsorDeclineBenefits(@Valid BenefitDeclineVM benefitDeclineVM);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "approve benefits ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/CRMDeclineBenefits")
    Response CRMDeclineBenefits(@Valid BenefitDeclineVM benefitDeclineVM);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get benefit by id")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/{id}")
    Response getBenefitByid(@PathParam("id") long id);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get benefit by id")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/getBenefitByMemberNo/{memberNO}")
    Response getBenefitByMemberNo(@PathParam("memberNO") String memberNO);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "approve authorize and push benefits to fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/authorizeAndPushBenefitsToFM")
    Response authorizeAndPushBenefitsToFM(@Valid PostFormByIdVM postFormByIdVM);


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "approve authorize and push benefits to fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/memberauthorizeAndPushBenefitsToFM")
    Response memberAuthorizeAndPushBenefitsToFM(@Valid PostFormByIdVM postFormByIdVM);


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "approve authorize and push benefits to fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/benefitAuthorizerAuthorizeAndPushBenefitsToFM")
    Response benefitAuthorizerAuthorizeAndPushBenefitsToFM(@Valid PostFormByIdVM postFormByIdVM);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "approve authorize and push benefits to fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/authorizeAndPushBenefitsToFMPO")
    Response authorizeAndPushBenefitsToFMPO(@Valid PostFormByIdVM postFormByIdVM);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get Recent Claims ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/getRecentClaims/{start}/{size}")
    Response getRecentClaims(@PathParam("start") int start, @PathParam("size") int size);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get count of pending claims ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/getCountOfPendingClaims")
    Response getCountOfPendingClaims() throws IOException;

    @GET
    @JwtTokenNeeded
    @Path("/benefitRequest/search/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    Response SearchBenefitRequest(@PathParam("name") String name);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get count of pending claims ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/getMemberEditedClaims/{mssUserId}/{memberId}")
    Response getMemberEditedClaims(@PathParam("mssUserId") long mssUserId, @PathParam("memberId") long memberId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get count of pending claims ")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/editMemberClaim/{mssUserId}/{benefitId}/{amount}")
    Response editMemberClaim(
            @PathParam("mssUserId") long mssUserId,
            @PathParam("benefitId") long benefitId,
            @PathParam("amount") String amount
    );

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "approveMemberClaim")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/approveMemberClaim/{mssUserId}/{benefitId}")
    Response approveMemberClaim(
            @PathParam("mssUserId") long mssUserId,
            @PathParam("benefitId") long benefitId
    );


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get Claim Documents")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitRequest/getClaimDocuments/{mssUserId}/{claimId}")
    Response getClaimDocuments(@PathParam("mssUserId") long mssUserId, @PathParam("claimId") long claimId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "documents getClaimUploadedDocs")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/documents/getClaimUploadedDocs/{mssUserId}/{claimId}")
    Response getClaimUploadedDocs(@PathParam("mssUserId") long mssUserId,@PathParam("claimId") long claimId);


}
