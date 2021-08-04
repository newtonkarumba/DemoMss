package com.systech.mss.controller.impl;

import com.systech.mss.controller.FmSchemeController;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.service.FundMasterClient;
import com.systech.mss.service.dto.FmListDTO;
import org.json.simple.JSONObject;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

public class FmSchemeControllerImpl extends BaseController implements FmSchemeController {

    @Inject
    private FundMasterClient fundMasterClient;

    @Inject
    private Logger log;

    @Override
    public Response getSchemes() {
        FmListDTO fmListDTO = fundMasterClient.getSchemes();
        if (fmListDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getSchemeById(long id) {
        JSONObject jsonObject = fundMasterClient.getSchemeById(id);
        if (jsonObject != null) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true)
                            .data(jsonObject).build()).build();
        }
        return ErrorMsg("Scheme NOT Found");
    }

    @Override
    public Response getSchemeByName(String schemeName) {
        FmListDTO jsonObject = fundMasterClient.getSchemeByName(schemeName);
        if (jsonObject != null) {
            if (!jsonObject.getRows().isEmpty())
                return Response.status(Response.Status.OK)
                        .entity(SuccessVM.builder().success(true)
                                .data(jsonObject.getRows().get(0)).build()).build();
        }
        return ErrorMsg("Scheme NOT Found");
    }

    @Override
    public Response getAllBanks() {
        FmListDTO fmListDTO = fundMasterClient.getAllBanks();
        if (fmListDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

    @Override
    public Response getBankBranches(long id) {
        FmListDTO fmListDTO = fundMasterClient.getBankBranches(id);
        if (fmListDTO.isSuccess()) {
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(fmListDTO.getRows()).build())
                    .build();
        }
        return ErrorMsg("Mss Api call failed");
    }

}
