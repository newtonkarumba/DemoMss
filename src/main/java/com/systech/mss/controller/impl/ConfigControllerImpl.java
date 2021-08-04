package com.systech.mss.controller.impl;

import com.systech.mss.controller.ConfigController;
import com.systech.mss.controller.vm.ErrorVM;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.Config;
import com.systech.mss.domain.Documents;
import com.systech.mss.domain.StatusConfig;
import com.systech.mss.service.ConfigService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

public class ConfigControllerImpl extends BaseController implements ConfigController {

    @Inject
    private ConfigService configService;


    @Override
    public Response getSpecificFieldsOfActiveConfigs() {
        Object config = configRepository.getSpecificFieldsOfActiveConfigs();
        if (config != null) {
            log.info("Config created");
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().data(config).success(true).build())
                    .build();
        }
        return ErrorMsg("No Active config");
    }

    @Override
    public Response getAllConfigs() {

        List<Config> configs = configService.getAll();

        return configs != null ? Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(configs)
                                .build()
                ).build() :
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(ErrorVM.builder().success(false).msg("No data found").build())
                        .build();
    }

    @Override
    public Response createConfig(@Valid Config config) {
        config.setCreatedDate(LocalDateTime.now());
        List<Config> configs = configRepository.findAll();
        //set existing configs inactive
        for (Config config1 : configs) {
            config1.setStatusConfig(StatusConfig.INACTIVE);
            configRepository.edit(config1);
        }
        Config createdConfig = configRepository.create(config);
        if (createdConfig != null) {

            log.info("Config created");
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().data(createdConfig).success(true).build())
                    .build();


        }
        return ErrorMsg("Config not created");
    }


    @Override
    public Response editConfig(@Valid Config config) {
        if (configRepository.existsById(config.getId())) {
            configRepository.edit(config);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(config).msg("Config edited").build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("Config not found").build())
                .build();
    }

    @Override
    public Response deleteConfig(long id) {
        if (configRepository.existsById(id)) {
            configRepository.deleteById(id);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg("Config Deleted").build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("Config not found").build())
                .build();
    }

    @Override
    public Response updateBusinessImage(long documentId) {
        Config current = configRepository.getActiveConfig();
        if (current != null) {
            Documents documents = documentRepository.find(documentId);
            if (documents != null) {
                current.setBusinessImage(String.valueOf(documentId));
                configRepository.edit(current);
                return Response.ok()
                        .entity(SuccessVM
                                .builder()
                                .success(true)
                                .data("BusinessImage updated successfully")
                                .build()
                        ).build();

            }
            log.error("Document not found");
        }
        log.error("BusinessImage Not Found");
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg("Failed, please try again").build())
                .build();
    }

    @Override
    public Response enable2FA(boolean enable) {
        try {
            Config config = configRepository.getActiveConfig();
            config.setEnableTwoFactorAuth(enable);
            configRepository.edit(config);
            return SuccessMsg(enable ? "Enabled" : "Disabled", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ErrorMsg("No active configuration available");
    }

}
