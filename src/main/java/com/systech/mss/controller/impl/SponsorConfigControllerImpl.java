package com.systech.mss.controller.impl;

import com.systech.mss.controller.SponsorConfigController;
import com.systech.mss.controller.vm.ErrorVM;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.Config;
import com.systech.mss.domain.LandingPageContent;
import com.systech.mss.domain.SponsorConfig;
import com.systech.mss.domain.StatusConfig;
import com.systech.mss.repository.SponsorConfigRepository;
import com.systech.mss.service.SponsorConfigService;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;


public class SponsorConfigControllerImpl implements SponsorConfigController {

    @Inject
    Logger logger;

    @Inject
    SponsorConfigRepository sponsorConfigRepository;


    @Inject
    SponsorConfigService sponsorConfigService;

    @Override
    public Response getSponsorActiveConfigs() {
        Object config=sponsorConfigRepository.getSponsorActiveConfigs();
        if (config!=null) {
            logger.info("Config created");
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().data(config).success(true).build())
                    .build();
        }
        return ErrorMsg("No Active config");
    }

    @Override
    public Response getAllSponsorConfigs() {
        List<SponsorConfig> sponsorConfigs = sponsorConfigService.getAll();

        return sponsorConfigs!=null ?Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(sponsorConfigs)
                                .build()
                ).build():
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(ErrorVM.builder().success(false).msg("No data found").build())
                        .build();
    }

    @Override
    public Response createSponsorConfig(SponsorConfig sponsorConfig) {
        sponsorConfig.setCreatedDate(LocalDateTime.now());
        List<SponsorConfig> sponsorConfigs=sponsorConfigRepository.findAll();
        //set existing configs inactive
        for(SponsorConfig sponsorConfig1:sponsorConfigs){
            sponsorConfig1.setStatusConfig(StatusConfig.INACTIVE);
            sponsorConfigRepository.edit(sponsorConfig1);
        }
        SponsorConfig createdSponsorConfig= sponsorConfigRepository.create(sponsorConfig);
        if (createdSponsorConfig!=null) {

            logger.info("Config created");
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().data(createdSponsorConfig).success(true).build())
                    .build();


        }
        return ErrorMsg("Config not created");
    }

    @Override
    public Response editSponsorConfig(long id, @Valid SponsorConfig sponsorConfig) {
        SponsorConfig sponsorConfig1 = sponsorConfigRepository.find(id);
        if(sponsorConfig1!=null) {
            sponsorConfig1.setStatusConfig(sponsorConfig.getStatusConfig());
            sponsorConfig1.setAuthorizationLevel(sponsorConfig.getAuthorizationLevel());
            sponsorConfigRepository.edit(sponsorConfig1);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg("Config Edited").build())
                    .build();

        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("Config not found").build())
                .build();
    }

    @Override
    public Response deleteSponsorConfig(long id) {
        if(sponsorConfigRepository.existsById(id)) {
            sponsorConfigRepository.deleteById(id);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg("Config Deleted").build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("Config not found").build())
                .build();
    }
    private Response ErrorMsg(String msg){
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        msg
                ).build())
                .build();
    }
}
