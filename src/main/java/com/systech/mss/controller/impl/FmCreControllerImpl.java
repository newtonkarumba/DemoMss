package com.systech.mss.controller.impl;

import com.systech.mss.controller.FmCreController;
import com.systech.mss.controller.vm.ErrorVM;
import com.systech.mss.controller.vm.SchemeVM;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.service.ActivityTrailService;
import com.systech.mss.service.FMCREClient;
import com.systech.mss.service.dto.FmListDTO;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

public class FmCreControllerImpl implements FmCreController {

    @Inject
    FMCREClient fmcreClient;

    @Inject
    ActivityTrailService activityTrailService;

    @Override
    public Response getAllSchemes(long mssUserId) {
        //log activity
        activityTrailService.logActivityTrail(mssUserId, "fetched all schemes");
        List<SchemeVM> list = fmcreClient.getAllScheme();
        if (list != null)
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(list).build())
                    .build();
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Error encountered"
                ).build())
                .build();
    }

    @Override
    public Response searchSchemesByName(long mssUserId, String schemeName) {
        //log activity
        activityTrailService.logActivityTrail(mssUserId, "Searched for scheme by name " + schemeName);


        FmListDTO fmListDTO = fmcreClient.getSchemeByName(schemeName);
        if (fmListDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();
    }

    @Override
    public Response getSchemeSponsors(long mssUserid, long schemeId) {
        //log activity
        activityTrailService.logActivityTrail(mssUserid, "fetched for sponsors for schemeId " + schemeId);


        FmListDTO fmListDTO = fmcreClient.getSchemeSponsors(schemeId);
        if (fmListDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        "Mss Api call failed"
                ).build())
                .build();
    }
}
