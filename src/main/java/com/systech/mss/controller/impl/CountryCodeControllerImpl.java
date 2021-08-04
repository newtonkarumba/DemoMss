package com.systech.mss.controller.impl;

import com.systech.mss.controller.CountryCodeController;
import com.systech.mss.controller.vm.ErrorVM;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.CountryCode;
import com.systech.mss.repository.CountryCodeRepository;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

public class CountryCodeControllerImpl implements CountryCodeController {

    @Inject
    CountryCodeRepository countryCodeRepository;

    @Override
    public Response getAllCountryCode() {
        List<CountryCode> countryCodes = countryCodeRepository.findAll();
        return countryCodes!=null ?Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(countryCodes)
                                .build()
                ).build():
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(ErrorVM.builder().success(false).msg("No data found").build())
                        .build();
    }
}
