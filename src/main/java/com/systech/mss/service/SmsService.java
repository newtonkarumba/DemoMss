package com.systech.mss.service;

import com.systech.mss.domain.*;
import com.systech.mss.domain.common.Clients;
import com.systech.mss.repository.EmailTemplatesRepository;
import com.systech.mss.repository.MailConfigRepository;
import com.systech.mss.service.dto.FmListDTO;
import com.systech.mss.service.dto.MessageModelDTO;
import com.systech.mss.vm.OutgoingSMSVM;
import com.systech.mss.vm.SMSVM;
import org.slf4j.Logger;

import javax.inject.Inject;

public class SmsService {

    @Inject
    private Logger log;

    @Inject
    private EmailTemplatesRepository emailTemplatesRepository;

    @Inject
    private MailConfigRepository mailConfigRepository;

    @Inject
    private FundMasterClient fundMasterClient;


    public void sendSMS(User user, EmailTemplatesEnum emailTemplatesEnum, Config activeConfig, String... args) {
        log.info("starting send sms");
        MailConfig activeMailConfigs = mailConfigRepository.getActiveMailConfigs();
        OutgoingSMSVM outgoingSMSVM = new OutgoingSMSVM();
        String content = populateContent(user, emailTemplatesEnum, activeMailConfigs.getBaseUrl(), args);
        if (content.equals("")) {
            log.info("sending sms stopped");
            return;
        }
        outgoingSMSVM.setMsg(content);
        outgoingSMSVM.setRecipient(user.getUserDetails().getCellPhone());
        outgoingSMSVM.setType(user.getProfile().getName());
        outgoingSMSVM.setStatus(true);
        log.info("sending sms");
        completeSendingSms(outgoingSMSVM, activeConfig);

    }

    public void sendSMS(Member member, EmailTemplatesEnum emailTemplatesEnum, Config activeConfig, String... args) {
        log.info("starting send sms");
        MailConfig activeMailConfigs = mailConfigRepository.getActiveMailConfigs();
        OutgoingSMSVM outgoingSMSVM = new OutgoingSMSVM();
        String content = populateContent(member, emailTemplatesEnum, activeMailConfigs.getBaseUrl(), args);
        if (content.equals("")) {
            log.info("sending sms stopped");
            return;
        }
        outgoingSMSVM.setMsg(content);
        outgoingSMSVM.setRecipient(member.getPhoneNumber());
        outgoingSMSVM.setType("member");
        outgoingSMSVM.setStatus(true);
        log.info("sending sms");
        completeSendingSms(outgoingSMSVM, activeConfig);

    }

    public void sendSMS(StageMemberDetails stageMemberDetails, EmailTemplatesEnum emailTemplatesEnum, Config activeConfig, String... args) {
        log.info("starting send sms");
        MailConfig activeMailConfigs = mailConfigRepository.getActiveMailConfigs();
        OutgoingSMSVM outgoingSMSVM = new OutgoingSMSVM();
        String content = populateContent(stageMemberDetails, emailTemplatesEnum, activeMailConfigs.getBaseUrl(), args);
        if (content.equals("")) {
            log.info("sending sms stopped");
            return;
        }
        outgoingSMSVM.setMsg(content);
        outgoingSMSVM.setRecipient(stageMemberDetails.getDetails().getCellPhone());
        outgoingSMSVM.setType("member");
        outgoingSMSVM.setStatus(true);
        log.info("sending sms");
        completeSendingSms(outgoingSMSVM, activeConfig);
    }

    public void completeSendingSms(OutgoingSMSVM outgoingSMSVM, Config activeConfig) {
        if (activeConfig.getClient().equals(Clients.ETL)) {
            MessageModelDTO messageModelDTO = fundMasterClient.sendSmsEtl(
                    new SMSVM(
                            outgoingSMSVM.getMsg(),
                            outgoingSMSVM.getRecipient()
                    )
            );
            log.info("done sending sms>>>>>>>>>>>"+messageModelDTO.toString());
        } else {
            FmListDTO fmListDTO = fundMasterClient.sendSMS(outgoingSMSVM);
            log.info("done sending sms>>>>>>>>>>>"+fmListDTO.toString());
        }
    }

    public String populateContent(User user, EmailTemplatesEnum emailTemplatesEnum, String baseUrl, String... args) {

        EmailTemplates byEmailTemplatesEnum = emailTemplatesRepository.findByEmailTemplatesEnum(emailTemplatesEnum);
        String content;
        if (emailTemplatesEnum != null) {
            content = byEmailTemplatesEnum.getSmsTemplate();
            if (content != null) {
                try {
                    switch (emailTemplatesEnum) {
                        case OTP_VERIFICATION:
                            if (user != null)
                                content = content.replaceAll("#name", user.getUserDetails().getName())
                                        .replaceAll("#token", args[0]);
                            break;
                        case ACCOUNT_ACTIVATION:
                        case PRINCIPAL_OFFICER_ACCOUNT_ACTIVATION:
                        case ADMIN_ACCOUNT_ACTIVATION:
                        case MEMBER_ACCOUNT_ACTIVATION:
                        case PASSWORD_RESET:
                            if (user != null)
                                content = content.replaceAll("#name", user.getUserDetails().getName())
                                        .replaceAll("#username", args[0])
                                        .replaceAll("#password", args[1])
                                        .replaceAll("#portalLink", baseUrl);
                            break;

                        case REQUEST_PASSWORD_RESET:
                            if (user != null)
                                content = content.replaceAll("#name", user.getUserDetails().getName())
                                        .replaceAll("#url",
                                                baseUrl + "/reset-password.jsp?key=" + user.getResetKey()
                                        );
                            break;

                        case MEMBER_CLAIM_INITIATED:
                        case CLAIM_STATUS:
                            if (user != null)
                                content = content.replaceAll("#name", user.getUserDetails().getName())
                                        .replaceAll("#benefitNumber", args[0])
                                        .replaceAll("#change", args[1])
                                        .replaceAll("#portalLink", baseUrl);
                            break;

                        case TICKET_RAISED:
                            if (user != null)
                                content = content.replaceAll("#name", user.getUserDetails().getName())
                                        .replaceAll("#ticketNumber", args[0])
                                        .replaceAll("#portalLink", baseUrl);
                            break;
                        case TICKET_REPLY:
                            if (user != null)
                                content = content.replaceAll("#name", user.getUserDetails().getName())
                                        .replaceAll("#ticketNumber", args[0])
                                        .replaceAll("#message", args[1])
                                        .replaceAll("#replyBy", args[2])
                                        .replaceAll("#timeReplied", args[3])
                                        .replaceAll("#portalLink", baseUrl);
                            break;

                        case PO_NEW_MEMBER_APPROVAL_REQUEST:
                            if (user != null)
                                content = content.replaceAll("#name", user.getUserDetails().getName())
                                        .replaceAll("#memberName", args[0])
                                        .replaceAll("#scheme", args[1])
                                        .replaceAll("#sponsor", args[2])
                                        .replaceAll("#portalLink", baseUrl);
                            break;

                        case PO_MEMBER_BIO_DATA_APPROVAL_REQUEST:
                        case PO_MEMBER_BENEFICIARY_APPROVAL_REQUEST:
                        case PO_PENDING_CLAIM:
                        case CLAIM_INITIATED:
                            if (user != null)
                                content = content.replaceAll("#name", user.getUserDetails().getName())
                                        .replaceAll("#memberName", args[0])
                                        .replaceAll("#portalLink", baseUrl);
                            break;

                        case MEMBER_BENEFICIARY_DECLINE:
                        case MEMBER_BENEFICIARY_APPROVAL:
                            if (user != null)
                                content = content.replaceAll("#name", user.getUserDetails().getName())
                                        .replaceAll("#portalLink", baseUrl);
                            break;

                        default:
                            log.warn("No template matching {}", emailTemplatesEnum.getName());
                    }
                } catch (Exception ignored) {
                }
            }
        } else {
            log.warn("Template content is NULL");
            content = "";
        }
        return content;
    }

    public String populateContent(Member member, EmailTemplatesEnum emailTemplatesEnum, String baseUrl, String... args) {
        try {
            EmailTemplates byEmailTemplatesEnum = emailTemplatesRepository.findByEmailTemplatesEnum(emailTemplatesEnum);
            String content;
            if (emailTemplatesEnum != null) {
                content = byEmailTemplatesEnum.getSmsTemplate();
                if (content != null) {
                    switch (emailTemplatesEnum) {
                        case NEW_MEMBER_REGISTERED:
                        case NEW_MEMBER_APPROVAL:
                        case NEW_MEMBER_DECLINE:
                            content = content.replaceAll("#name", (member.getFirstname() != null ? member.getFirstname() : "")
                                            + " "
                                            + (member.getLastname() != null ? member.getLastname() : " "))
                                    .replaceAll("#scheme", args[0])
                                    .replaceAll("#sponsor", args[1])
                                    .replaceAll("#portalLink", baseUrl);
                            break;

                        default:
                            log.warn("No template matching {}", emailTemplatesEnum.getName());
                    }
                }
            } else {
                log.warn("Template content is NULL");
                content = "";
            }
            return content;
        } catch (Exception e) {
            return "";
        }
    }

    public String populateContent(StageMemberDetails stageMemberDetails, EmailTemplatesEnum emailTemplatesEnum, String baseUrl, String... args) {
        try {
            EmailTemplates byEmailTemplatesEnum = emailTemplatesRepository.findByEmailTemplatesEnum(emailTemplatesEnum);
            String content;
            if (emailTemplatesEnum != null) {
                content = byEmailTemplatesEnum.getSmsTemplate();
                if (content != null) {
                    switch (emailTemplatesEnum) {
                        case MEMBER_BIO_DATA_UPDATE_APPROVAL:
                        case MEMBER_BIO_DATA_UPDATE_DECLINE:
                            content = content.replaceAll("#name", stageMemberDetails.getFname() + " " + stageMemberDetails.getLastName())
                                    .replaceAll("#portalLink", baseUrl);
                            break;
                        default:
                            log.warn("No template matching {}", emailTemplatesEnum.getName());
                    }
                }
            } else {
                log.warn("Template content is NULL");
                content = "";
            }
            return content;
        } catch (Exception e) {
            return "";
        }
    }
}
