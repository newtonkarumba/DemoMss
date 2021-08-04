package com.systech.mss.controller.impl;

import com.systech.mss.controller.MailConfigController;
import com.systech.mss.controller.vm.ErrorVM;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.MailConfig;
import com.systech.mss.repository.MailConfigRepository;
import com.systech.mss.service.MailConfigService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.util.List;

public class MailConfigControllerImpl extends BaseController implements MailConfigController {
    @Inject
    private MailConfigRepository mailConfigRepository;

    @Inject
    private MailConfigService mailConfigService;


    @Override
    public Response getAllMailConfigs() {
        List<MailConfig> mailConfigs = mailConfigService.getAll();

        return mailConfigs != null ? Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(mailConfigs)
                                .build()
                ).build() :
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(ErrorVM.builder().success(false).msg("No data found").build())
                        .build();
    }


    @Override
    public Response createMailConfig(@Valid MailConfig mailConfig) {
        List<MailConfig> mailConfigs = mailConfigRepository.findAll();

        for (MailConfig mailConfig1 : mailConfigs) {
            mailConfigRepository.edit(mailConfig1);
        }
        MailConfig createdConfig = mailConfigRepository.create(mailConfig);
        if (createdConfig != null) {

            log.info("Config created");
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().data(createdConfig).success(true).build())
                    .build();


        }
        return ErrorMsg("Config not created");
    }

    @Override
    public Response editMailConfig(@Valid MailConfig mailConfig) {
        if (mailConfigRepository.existsById(mailConfig.getId())) {
            mailConfigRepository.edit(mailConfig);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(mailConfig).msg("Mail Config edited").build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("Config not found").build())
                .build();
    }

    @Override
    public Response deleteMailConfig(long id) {
        if (mailConfigRepository.existsById(id)) {
            mailConfigRepository.deleteById(id);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg("Mail Config Deleted").build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("Mail Config not found").build())
                .build();
    }

}
