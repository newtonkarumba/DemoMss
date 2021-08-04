package com.systech.mss.controller.impl;

import com.systech.mss.controller.FormsConfigController;
import com.systech.mss.controller.vm.ConfigFormName;
import com.systech.mss.controller.vm.FormConfigVm;
import com.systech.mss.controller.vm.SuccessVM;
import com.systech.mss.domain.BaseModel;
import com.systech.mss.repository.BeneficiaryFormConfigsRepository;
import com.systech.mss.repository.MemberFormConfigsRepository;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

public class FormsConfigControllerImpl extends BaseController implements FormsConfigController {

    @Inject
    private MemberFormConfigsRepository memberFormConfigsRepository;

    @Inject
    private BeneficiaryFormConfigsRepository beneficiaryFormConfigsRepository;


    @Override
    public Response getAll() {
        String[] forms = {
                ConfigFormName.MEMBER.name(),
                ConfigFormName.BENEFICIARY.name()
        };
        Map<String, Object> objectMap = new HashMap<>();
        for (String form : forms) {
            objectMap.put(form, getConfigPerForms(form));
        }
        return Response.ok()
                .entity(
                        SuccessVM.builder().success(true)
                                .data(objectMap)
                                .build()
                ).build();
    }

    @Override
    public Response getConfigForForms(String formName) {
        ConfigFormName configFormName=ConfigFormName.valueOf(formName);
        BaseModel baseModel=getConfigPerForms(configFormName.name());
        if (baseModel == null)
            return ErrorMsg("Unknown Form Name");

        return Response.ok()
                .entity(
                        SuccessVM.builder().success(true)
                                .data(baseModel)
                                .build()
                ).build();
    }

    @Override
    public Response setDefaults(String formName) {
        ConfigFormName configFormName=ConfigFormName.valueOf(formName);
        BaseModel baseModel;
        switch (configFormName){
            case MEMBER:
                baseModel=memberFormConfigsRepository.setDefault();
                break;
            case BENEFICIARY:
                baseModel=beneficiaryFormConfigsRepository.setDefault();
                break;
            default:
                return ErrorMsg("Unknown Form Name");
        }
        return Response.ok()
                .entity(
                        SuccessVM.builder().success(true)
                                .msg("Default set Successfully")
                                .data(baseModel)
                                .build()
                ).build();
    }

    @Override
    public Response update(@Valid FormConfigVm formConfigVm) {
        ConfigFormName configFormName=ConfigFormName.valueOf(formConfigVm.getConfigFormName().name());
        BaseModel baseModel;
        switch (configFormName){
            case MEMBER:
                baseModel=memberFormConfigsRepository.update(formConfigVm.getMemberFormConfigs());
                break;
            case BENEFICIARY:
                baseModel=beneficiaryFormConfigsRepository.update(formConfigVm.getBeneficiaryFormConfig());
                break;
            default:
                return ErrorMsg("Unknown Form Name");
        }
        return Response.ok()
                .entity(
                        SuccessVM.builder().success(true)
                                .msg("Updated Successfully")
                                .data(baseModel)
                                .build()
                ).build();
    }

    public BaseModel getConfigPerForms (String formName){
        ConfigFormName configFormName=ConfigFormName.valueOf(formName);
        BaseModel baseModel;
        switch (configFormName){
            case MEMBER:
                baseModel=memberFormConfigsRepository.getConfigs();
                break;
            case BENEFICIARY:
                baseModel=beneficiaryFormConfigsRepository.getConfigs();
                break;
            default:
                return null;
        }
        return baseModel;

    }

}
