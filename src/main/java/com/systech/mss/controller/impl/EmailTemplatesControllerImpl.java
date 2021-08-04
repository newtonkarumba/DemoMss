package com.systech.mss.controller.impl;

import com.systech.mss.controller.EmailTemplatesController;
import com.systech.mss.controller.vm.EmailTemplatesVM;
import com.systech.mss.domain.EmailTemplates;
import com.systech.mss.domain.EmailTemplatesEnum;
import com.systech.mss.repository.EmailTemplatesRepository;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmailTemplatesControllerImpl extends BaseController implements EmailTemplatesController {

    @Inject
    EmailTemplatesRepository emailTemplatesRepository;

    @Override
    public Response getAll() {
        List<EmailTemplates> emailTemplates = emailTemplatesRepository.findAll();
        if (emailTemplates != null) {
            Collections.reverse(emailTemplates);
            return SuccessMsg("Done", setExtraDetails(emailTemplates));
        }
        return ErrorMsg("Please try again");
    }

    @Override
    public Response get(String emailTemplateEnum) {
        EmailTemplatesEnum emailTemplatesEnum = EmailTemplatesEnum.getEmailTemplatesEnum(emailTemplateEnum);
        if (emailTemplatesEnum == null) {
            emailTemplatesEnum = EmailTemplatesEnum.from(emailTemplateEnum);
            if (emailTemplatesEnum == null)
                return ErrorMsg("Unknown template");
        }
        EmailTemplates emailTemplates = emailTemplatesRepository.findByEmailTemplatesEnum(emailTemplatesEnum);
        if (emailTemplates != null) {
            return SuccessMsg("Done", setExtraDetailsSingle(emailTemplates));
        }
        return SuccessMsg("Done", setExtraDetailsSingle(EmailTemplates.from(emailTemplatesEnum)));
    }

    @Override
    public Response edit(EmailTemplatesVM emailTemplatesVM) {
        EmailTemplatesEnum emailTemplatesEnum = emailTemplatesVM.getTemplatesType();
        if (emailTemplatesEnum == null) return ErrorMsg("Unknown template");
        EmailTemplates emailTemplates = emailTemplatesRepository.findByEmailTemplatesEnum(emailTemplatesEnum);
        if (emailTemplates != null) {
            emailTemplates.setTitle(emailTemplatesVM.getTitle());
            emailTemplates.setTemplate(emailTemplatesVM.getTemplate());
            emailTemplates.setSmsTemplate(emailTemplatesVM.getSmsTemplate());
            emailTemplatesRepository.edit(emailTemplates);
            return SuccessMsg("Done", null);
        }
        emailTemplates = EmailTemplates.getInstance(emailTemplatesVM);
        EmailTemplates emailTemplates1 = emailTemplatesRepository.create(emailTemplates);
        if (emailTemplates1 != null) {
            return SuccessMsg("Done", null);
        }
        return ErrorMsg("Please try again");
    }

    private List<EmailTemplates> setExtraDetails(List<EmailTemplates> emailTemplates) {
        for (EmailTemplates emailTemplate : emailTemplates) {
            String[] namedKeys = emailTemplate.getTemplatesType().getNamedKeys();
            if (namedKeys != null) {
                emailTemplate.setNamedKeysList(namedKeys);
                emailTemplate.setDescription(emailTemplate.getTemplatesType().getDescription());
                emailTemplate.setTemplatesTypeString(emailTemplate.getTemplatesType().getName());
            }
        }
        return emailTemplates;
    }

    private EmailTemplates setExtraDetailsSingle(EmailTemplates emailTemplate) {
        String[] namedKeys = emailTemplate.getTemplatesType().getNamedKeys();
        if (namedKeys != null) {
            emailTemplate.setNamedKeysList(namedKeys);
            emailTemplate.setDescription(emailTemplate.getTemplatesType().getDescription());
            emailTemplate.setTemplatesTypeString(emailTemplate.getTemplatesType().getName());
        }
        return emailTemplate;
    }


    @Override
    public Response getEmailTemplatesEnum() {
        List<EmailTemplatesVM> emailTemplatesVMS = new ArrayList<>();
        EmailTemplatesEnum[] emailTemplatesEnums = EmailTemplatesEnum.values();
        for (EmailTemplatesEnum emailTemplatesEnum : emailTemplatesEnums) {
            EmailTemplatesVM emailTemplatesVM = new EmailTemplatesVM();
            emailTemplatesVM.setTitle(emailTemplatesEnum.getName());
            emailTemplatesVM.setCategory(emailTemplatesEnum.name());
            emailTemplatesVM.setDescription(emailTemplatesEnum.getDescription());
            String[] namedKeys = emailTemplatesEnum.getNamedKeys();
            List<String> namedKeysList = new ArrayList<>();
            Collections.addAll(namedKeysList, namedKeys);
            emailTemplatesVM.setNamedKeysList(namedKeysList);

            emailTemplatesVMS.add(emailTemplatesVM);
        }

        return SuccessMsg("Done", emailTemplatesVMS);
    }
}
