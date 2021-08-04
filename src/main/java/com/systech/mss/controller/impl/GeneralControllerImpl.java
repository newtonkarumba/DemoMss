package com.systech.mss.controller.impl;

import com.systech.mss.controller.GeneralController;
import com.systech.mss.controller.vm.CountryVm;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.SupportMessages;
import com.systech.mss.repository.SupportMessagesRepository;
import com.systech.mss.service.dto.FmListDTO;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class GeneralControllerImpl extends BaseController implements GeneralController {


    @Inject
    SupportMessagesRepository supportMessagesRepository;

    @Override
    public Response receiveSupportMessage(@Valid SupportMessages supportMessages) {
        SupportMessages messages = supportMessagesRepository.create(
                supportMessages
        );
        if (messages != null) {
            mailService.sendSupportEmail(supportMessages);
            return Response.status(Response.Status.OK)
                    .entity(
                            SuccessVM.builder()
                                    .success(true)
                                    .msg("Your message has been received, you will be contacted within 24hrs")
                                    .build()
                    ).build();
        }
        return ErrorMsg("Please try again");
    }

    @Override
    public Response countries() {
        List<CountryVm> list = new ArrayList<>();
        for (String s : country_list) {
            list.add(new CountryVm(s));
        }
        return SuccessMsg("Done", list);
    }

    @Override
    public Response district() {
        FmListDTO fmListDTO = fundMasterClient.district();
        if (fmListDTO != null)
            if (fmListDTO.isSuccess()) {
                return SuccessMsg("done", fmListDTO.getRows());
            }
        return ErrorMsg("Please try again");
    }

    @Override
    public Response traditionalAuthority(long districtId) {
        FmListDTO fmListDTO = fundMasterClient.traditionalAuthority(districtId);
        if (fmListDTO != null)
            if (fmListDTO.isSuccess()) {
                return SuccessMsg("done", fmListDTO.getRows());
            }
        return ErrorMsg("Please try again");
    }

    @Override
    public Response village(long traditionalAuthorityId) {
        FmListDTO fmListDTO = fundMasterClient.village(traditionalAuthorityId);
        if (fmListDTO != null)
            if (fmListDTO.isSuccess()) {
                return SuccessMsg("done", fmListDTO.getRows());
            }
        return ErrorMsg("Please try again");
    }

}
