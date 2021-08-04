package com.systech.mss.controller;

import com.systech.mss.controller.vm.SaveBillVm;
import com.systech.mss.seurity.JwtTokenNeeded;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.json.simple.parser.ParseException;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public interface ProcessExceptionBillController {
    @Timed
    @Operation(summary = "Upload Bill Exception")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Path("/processException/{mssuserid}/{billId}/{sponsorId}/{schemeId}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    Response uploadFile(@PathParam("mssuserid") long mssuserid, @PathParam("billId") long billId, @PathParam("sponsorId") long sponsorId, @PathParam("schemeId") long schemeId,
                        MultipartFormDataInput input) throws ParseException;


    @Timed
    @JwtTokenNeeded
    @Operation(summary = "Save bill")
    @APIResponse(responseCode = "200", description = "OK")
    @APIResponse(responseCode = "401", description = "Unauthorized")
    @POST
    @Path("/saveBill/{mssuserid}/{schemeId}/{sponsorId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response saveFile(@PathParam("mssuserid") long mssuserid, @Valid SaveBillVm saveBillVm, @PathParam("schemeId") long schemeId, @PathParam("sponsorId") long sponsorId);

}
