package com.systech.mss.controller;

import com.systech.mss.seurity.JwtTokenNeeded;
import com.systech.mss.vm.benefitrequest.AddBeneficiaryVM;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/api")
public interface FmPensionerController {
    @Timed
    @JwtTokenNeeded
    @Operation(summary = "Get Pensioner Details from fundmaster")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "401", description = "Unauthorised")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getpensionerdetails/{pensionerId}")
    Response getPensionerDetails(@PathParam("pensionerId") long pensionerId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get Recent Contributions from fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getPenionerPersonalInfo/{pensionerId}")
    Response getPenionerPersonalInfo(@PathParam("pensionerId") long memberId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get Employment Details from fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getPensionerEmploymentDetails/{memberId}")
    Response getPensionerEmploymentDetails();

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "Get Pensioner Payrolls from fundmaster")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "401", description = "Unauthorised")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getLastPayrollByPensioner/{pensionerNo}/{schemeId}")
    Response getPensionerPayrolls(@PathParam("pensionerNo") String pensionerNo, @PathParam("schemeId") Long schemeId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "filter Payrolls by year or month or both")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "401", description = "Unauthorised")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/filterPayrolls/{pensionerNo}/{schemeId}")
    Response filterPayrolls(@PathParam("pensionerNo") String pensionerNo,
                            @PathParam("schemeId") long schemeId,
                            @QueryParam("month") String month,
                            @QueryParam("year") String year
    ) throws IOException;

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "Get Pensioner Payroll Deductions from fundmaster")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "401", description = "Unauthorised")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getPensionerDeductions/{pensionerNo}/{schemeId}")
    Response getPensionerPayrollDeductions(@PathParam("pensionerNo") String pensionerNo, @PathParam("schemeId") long schemeId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "Get Pensioner Payroll years fundmaster")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "401", description = "Unauthorised")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getpayrollyears")
    Response getPensionerPayrollYears();

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "Get Pensioner Advice from FM")
    @APIResponse(responseCode = "200", description = "Ok")
    @APIResponse(responseCode = "401", description = "Unauthorised")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getpensioneradvice/{schemeId}/{pensionerId}/{year}")
    Response getPensionerAdvice(@PathParam("schemeId") String schemeId,@PathParam("pensionerId") String pensionerId,@PathParam("year") String year);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get Beneficiaries from fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getPensionerBeneficiaries/{memberId}")
    Response getPensionerBeneficiaries(@PathParam("memberId") long memberId);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "add Beneficiary from fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/addPensionerBeneficiary")
    Response addPensionerBeneficiary(@Valid AddBeneficiaryVM addBeneficiaryVM);

    @Timed
    @JwtTokenNeeded
    @Operation(summary = "get pensioner CEO from fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getPensionerCOE/{mssUserId}/{pensionerNo}")
    Response getPensionerCOE(@PathParam("mssUserId") long mssUserId, @PathParam("pensionerNo") String pensionerNo) throws IOException;
}
