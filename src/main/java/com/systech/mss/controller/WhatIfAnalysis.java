package com.systech.mss.controller;

import com.systech.mss.controller.vm.ProjectionsForMemberVM;
import com.systech.mss.controller.vm.WhatIfAnalysisVM;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/api")
public interface WhatIfAnalysis {
    @Timed
    @Operation(summary = "calculateWhatIfAnalysis from fundmaster")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/whatIfAnalysis/{mssUserId}")
    Response whatIfAnalysis(
            @PathParam("mssUserId") long mssUserId,
            @Valid WhatIfAnalysisVM whatIfAnalysisVM);

    @Timed
    @Operation(summary = "get Projections For Member")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/getProjectionsForMember")
    Response getProjectionsForMember(@Valid ProjectionsForMemberVM projectionsForMemberVM);

    @Timed
    @Operation(summary = "get Projections For Member")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/benefitsprojectioncalculation")
    Response benefitsprojectioncalculation(@Valid ProjectionsForMemberVM projectionsForMemberVM);

}
