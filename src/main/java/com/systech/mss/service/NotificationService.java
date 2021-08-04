package com.systech.mss.service;

import com.systech.mss.domain.*;
import com.systech.mss.repository.ConfigRepository;
import org.slf4j.Logger;

import javax.inject.Inject;

public class NotificationService {

    @Inject
    private MailService mailService;

    @Inject
    private SmsService smsService;

    @Inject
    private Logger logger;

    @Inject
    private ConfigRepository configRepository;
    public void sendNotification(User user, EmailTemplatesEnum emailTemplatesEnum, String... args) {
        Config activeConfig = configRepository.getActiveConfig();
        logger.info("initiating sms 1");
        if(activeConfig.isAllowSmsNotification()){
            logger.info("initiating sms 2");
            if(user.getUserDetails().getCellPhone() != null) {
                logger.info("initiating sms 3");
                smsService.sendSMS(user, emailTemplatesEnum, activeConfig, args);
            }
        }
        if(activeConfig.isAllowEmailNotification()) {
            if(user.getEmail() != null) {
                mailService.sendEmail(user, emailTemplatesEnum, args);
            }
        }


    }
    public void sendNotification(Member member, EmailTemplatesEnum emailTemplatesEnum, String... args) {
        Config activeConfig = configRepository.getActiveConfig();
        if(activeConfig.isAllowSmsNotification()){
            if(member.getPhoneNumber() != null) {
                smsService.sendSMS(member, emailTemplatesEnum, activeConfig, args);
            }
        }
        if(activeConfig.isAllowEmailNotification()) {
            if(member.getEmailAddress() != null) {
                mailService.sendEmail(member, emailTemplatesEnum, args);
            }
        }

    }
    public void sendNotification(StageMemberDetails stageMemberDetails, EmailTemplatesEnum emailTemplatesEnum, String... args) {
        Config activeConfig = configRepository.getActiveConfig();
        if(activeConfig.isAllowSmsNotification()){
            if(stageMemberDetails.getDetails().getCellPhone() != null) {
                smsService.sendSMS(stageMemberDetails, emailTemplatesEnum, activeConfig, args);
            }
        }
        if(activeConfig.isAllowEmailNotification()) {
            if(stageMemberDetails.getDetails().getEmail() != null) {
                mailService.sendEmail(stageMemberDetails, emailTemplatesEnum, args);
            }
        }

    }
}
