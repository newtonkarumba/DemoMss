package com.systech.mss.controller.impl;

import com.google.gson.Gson;
import com.systech.mss.controller.MobileMoneyConfigController;
import com.systech.mss.controller.vm.ErrorVM;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.MobileMoneyConfig;
import com.systech.mss.domain.StatusConfig;
import com.systech.mss.mobilemoney.MpesaResponseDTO;
import com.systech.mss.repository.MobileMoneyRepository;
import com.systech.mss.service.MobileMoneyConfigService;
import com.systech.smileIdentity.model.PaymentStatus;
import com.systech.smileIdentity.model.SelfiePayment;
import com.systech.smileIdentity.repository.SelfiePaymentRepository;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MobileMoneyConfigControllerImpl implements MobileMoneyConfigController {
    @Inject
    private MobileMoneyRepository mobileMoneyRepository;

    @Inject
    private MobileMoneyConfigService mobileMoneyConfigService;

    @Inject
    private MobileMoneyConfig mobileMoneyConfig;

    @Inject
    Logger logger;

    @Inject
    private SelfiePaymentRepository selfiePaymentRepository;


    @Override
    public Response getSpecificFieldsOfActiveMobileMoneyConfigs() {
        Object mobilemoneyconfig=mobileMoneyRepository.getActiveConfig();
        if (mobilemoneyconfig!=null) {
            logger.info("Mobile Money Config created");
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().data(mobilemoneyconfig).success(true).build())
                    .build();
        }
        return ErrorMsg("No Active Mobile Money config");
    }

    @Override
    public Response getAllMobileMoneyConfigs() {
        List<MobileMoneyConfig> mobileMoneyConfigs = mobileMoneyConfigService.getAll();

        return mobileMoneyConfigs!=null ?Response.ok()
                .entity(
                        SuccessVM.builder()
                                .success(true)
                                .data(mobileMoneyConfigs)
                                .build()
                ).build():
                Response.status(Response.Status.BAD_REQUEST)
                        .entity(ErrorVM.builder().success(false).msg("No data found").build())
                        .build();
    }

    @Override
    public Response createMobileMoneyConfig(@Valid MobileMoneyConfig mobileMoneyConfig) {
        List<MobileMoneyConfig> mobileMoneyConfigs=mobileMoneyRepository.findAll();

        for(MobileMoneyConfig mobileMoneyConfig1:mobileMoneyConfigs){
            mobileMoneyConfig1.setStatus(StatusConfig.INACTIVE);
            mobileMoneyRepository.edit(mobileMoneyConfig1);
        }
        MobileMoneyConfig createdConfig= mobileMoneyRepository.create(mobileMoneyConfig);
        if (createdConfig!=null) {

            logger.info("Config created");
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().data(createdConfig).success(true).build())
                    .build();


        }
        return ErrorMsg("Config not created");
    }

    @Override
    public Response editMobileMoneyConfig(@Valid MobileMoneyConfig mobileMoneyConfig) {
        if(mobileMoneyRepository.existsById(mobileMoneyConfig.getId())) {
            mobileMoneyRepository.edit(mobileMoneyConfig);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).data(mobileMoneyConfig).msg("Mobile Money Config edited").build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("Config not found").build())
                .build();
    }

    @Override
    public Response deleteMobileMoneyConfig(long id) {
        if(mobileMoneyRepository.existsById(id)) {
            mobileMoneyRepository.deleteById(id);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg("Mobile Money Config Deleted").build())
                    .build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorVM.builder().success(false).
                        msg("Mobile Money Config not found").build())
                .build();
    }

    private Response ErrorMsg(String msg){
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorVM.builder().success(false).msg(
                        msg
                ).build())
                .build();
    }

    @Override
    public Response SmileIdentityMpesaCallBack(String response) {

        Gson gson = new Gson();
        MpesaResponseDTO mpesaResponseDTO = gson.fromJson(response, MpesaResponseDTO.class);
        SelfiePayment payment = selfiePaymentRepository.getPaymentByRequestId(mpesaResponseDTO.getBody().getStkCallback().getCheckoutRequestID());
        if(mpesaResponseDTO.getBody().getStkCallback().getResultCode() == 0){
            payment.setPaymentStatus(PaymentStatus.PAID_SUCCESSFUL_NOT_USED);
            selfiePaymentRepository.edit(payment);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg("Payment Successful").build())
                    .build();
        }
        else{
            payment.setPaymentStatus(PaymentStatus.NOT_PAID);
            selfiePaymentRepository.edit(payment);
            return ErrorMsg("Payment not Successful");
        }
    }

    @Override
    public Response smileIdentityMpesaTimeoutCallBack(String response) {

        Gson gson = new Gson();
        MpesaResponseDTO mpesaResponseDTO = gson.fromJson(response, MpesaResponseDTO.class);
        SelfiePayment payment = selfiePaymentRepository.getPaymentByRequestId(mpesaResponseDTO.getBody().getStkCallback().getCheckoutRequestID());
        if(mpesaResponseDTO.getBody().getStkCallback().getResultCode() == 0){
            payment.setPaymentStatus(PaymentStatus.PAID_SUCCESSFUL_NOT_USED);
            selfiePaymentRepository.edit(payment);
            return Response.status(Response.Status.OK)
                    .entity(SuccessVM.builder().success(true).msg("Payment Successful").build())
                    .build();
        }
        else{
            payment.setPaymentStatus(PaymentStatus.NOT_PAID);
            selfiePaymentRepository.edit(payment);
            return ErrorMsg("Payment not Successful");
        }
    }
}
