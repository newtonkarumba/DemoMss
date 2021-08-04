package com.systech.mss.mail;


import com.systech.mss.domain.*;
import com.systech.mss.repository.EmailTemplatesRepository;
import com.systech.mss.repository.MailConfigRepository;
import com.systech.mss.repository.NotificationRepository;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.apache.deltaspike.core.api.message.Message;
import org.apache.deltaspike.core.api.message.MessageContext;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;


//@RequestScoped
public class MailNotifierOld {

    private static final String USER = "user";
    private static final String USERNAME = "userName";
    private static final String TICKETNUMBER = "ticketNumber";
    private static final String CLAIM_CHANGES = "claimChanges";
    private static final String BENEFITNUMBER = "benefitNumber";
    private static final String MAILLINK = "mailLink";
    private static final String ACTIVATION_LINK = "activationLink";
    private static final String PWD_RESET_LINK = "pwdResetLink";
    private static final String BASE_URL = "baseUrl";


    @Inject
    private Logger log;

    @Inject
    private VelocityEngine engine;

    @Inject
    private MessageContext messageContext;

    @Inject
    private MailConfigRepository mailConfigRepository;

    @Inject
    NotificationRepository notificationRepository;

    @Inject
    private EmailTemplatesRepository emailTemplatesRepository;

    /**
     * Remove @ObservesAsync to prevent collision
     * @param event
     */
//    public void sendEmail(MailEvent event) {
//
//        User user = event.getUser();
//        String to = user.getEmail();
//
//        if (getMailConfig().isPresent()) {
//            MailConfig mailConfig = getMailConfig().get();
//
//            if (event.getAction() != null) {
//                switch (event.getAction()) {
//                    case Constants.SEND_SUPPORT_MESSAGE:
//                        log.info("Sending "+Constants.SEND_SUPPORT_MESSAGE);
//                        if(mailConfig.getMailType().equals(MailType.SSL)) {
//                            sendSupportEmail(event);
//                        }
//                        else{
//                            sendTLSEmail(event);
//                        }
//                        return;
//                    case Constants.SEND_ACTIVATION_EMAIL3:
//                        log.info("Sending "+Constants.SEND_ACTIVATION_EMAIL3);
//                        if(mailConfig.getMailType().equals(MailType.SSL)) {
//                            sendActivationEmail3(event);
//                        }
//                        else{
//                            sendTLSEmail(event);
//                        }
//                        return;
//                    case Constants.SEND_PLAIN_MESSAGE:
//                        log.info("Sending "+Constants.SEND_PLAIN_MESSAGE);
//                        if(mailConfig.getMailType().equals(MailType.SSL)) {
//                            sendPlainMessage(event);
//                        }
//                        else{
//                            sendTLSEmail(event);
//                        }
//                        return;
//                    default:
//                }
//            }
//
//            EmailTemplatesEnum emailTemplatesEnum = event.getEmailTemplatesEnum();
//            if (emailTemplatesEnum != null) {
//                log.info("Sending using template "+emailTemplatesEnum.getName());
//                if(mailConfig.getMailType().equals(MailType.SSL)){
//                    sendSSLEmail(event, emailTemplatesEnum,mailConfig);
//                }else{
//                    sendTLSEmail(mailConfig,event, emailTemplatesEnum);
//                }
//
//                return; //UKITOA JUWA UMEANZA KUWA MTU BLADFAKIN ☹
//                //☹☹☹ sawa kiongos
//            }
//
//        log.info("Send email Default option");
//            Message message = getMessage(event.getUser());
//            String subject = message.template(String.format("{%s}", event.getSubjectTemplate())).toString();
//            String content = getContent(event, message);
//            try {
////                log.info("Send e-mail to '{}' with subject '{}' and content={}", to, subject, content);
//                // Prepare message
//                HtmlEmail email = new HtmlEmail();
//                email.setHostName(mailConfig.getHost());
//                email.setStartTLSEnabled(mailConfig.isEnableTLS());
//                email.setSmtpPort(mailConfig.getPort());
//                email.setAuthenticator(new DefaultAuthenticator(mailConfig.getUsername(),
//                        mailConfig.getPassword()));
//                email.setFrom(mailConfig.getFrom());
//                email.setSubject(subject);
//                email.getMailSession().getProperties().put("mail.smtp.ssl.trust", mailConfig.getHost());
////                email.getMailSession().getProperties().put("mail.smtp.ssl.trust", "smtp.gmail.com");
//                email.setHtmlMsg(content);
//                email.addTo(to);
//                String s = email.send();
//                notificationRepository.create(
//                        Notification.getInstance(
//                                user.getUserDetails().getName(),
//                                to,
//                                content,
//                                subject,
//                                s != null
//                        )
//                );
//                log.info("Sent e-mail to User '{}'", to);
//            } catch (Exception e) {
//                e.printStackTrace();
//                log.warn("e-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
//            }
//
//        } else {
//            log.warn("e-mail could not be sent to user '{}', exception is: {}", to, EMAIL_CONFIGS_NOT_SET);
//        }
//    }

    private void requestPasswordReset(MailEvent event, EmailTemplatesEnum emailTemplatesEnum) {
        Optional<MailConfig> mailConfigOptional = getMailConfig();
        if (!mailConfigOptional.isPresent()) {
            return;
        }
        EmailTemplates emailTemplates = emailTemplatesRepository.findByEmailTemplatesEnum(
                emailTemplatesEnum
        );
        if (emailTemplates != null) {
            MailConfig mailConfig = mailConfigOptional.get();
            String baseUrl = mailConfig.getBaseUrl();
            String[] args = event.getArgs();
            User user = event.getUser();
            String title = emailTemplates.getTitle();
            String content = emailTemplates.getTemplate();
            if (content != null) {
                /*
                  see how to automate this 🤭
                  see a listing of all keys in selected EmailTemplatesEnum and replace each
                 */
                content = content.replaceAll("#name", user.getUserDetails().getName())
                        .replaceAll("#url",
                                "<a href=\"" +
                                        baseUrl + "/reset-password.jsp?key=" + user.getResetKey()
                                        + "\">Reset password</a>"
                        );
            } else {
                content = "";
            }

            sendGeneralEmail(mailConfigOptional.get(), user, title, content,user.getEmail());
        }
    }

    private void sendPlainMessage(MailEvent event) {
        if (getMailConfig().isPresent()) {
            MailConfig mailConfig = getMailConfig().get();
            String subject = event.getSubject(),
                    content=event.getMessage(),
                    to=event.getTo();
            try {
                HtmlEmail email = new HtmlEmail();
                email.setHostName(mailConfig.getHost());
                email.setStartTLSEnabled(mailConfig.isEnableTLS());
                email.setSmtpPort(mailConfig.getPort());
                email.setAuthenticator(new DefaultAuthenticator(mailConfig.getUsername(), mailConfig.getPassword()));
                email.setFrom(mailConfig.getFrom());
                email.setSubject(subject);
                email.getMailSession().getProperties().put("mail.smtp.ssl.trust",mailConfig.getHost());
                email.setHtmlMsg(content);
                email.addTo(to);
                String s = email.send();
                notificationRepository.create(
                        Notification.getInstance(
                                to,
                                to,
                                content,
                                subject,
                                s != null
                        )
                );
                log.info("Sent e-mail to User '{}'", to);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
    }

    private void sendAccountActivationEmail(MailEvent event, EmailTemplatesEnum emailTemplatesEnum) {
        Optional<MailConfig> mailConfigOptional = getMailConfig();
        if (!mailConfigOptional.isPresent()) {
            return;
        }
        EmailTemplates emailTemplates = emailTemplatesRepository.findByEmailTemplatesEnum(
                emailTemplatesEnum
        );
        if (emailTemplates != null) {
            MailConfig mailConfig = mailConfigOptional.get();
            String baseUrl = mailConfig.getBaseUrl();
            String[] args = event.getArgs();
            User user = event.getUser();
            String title = emailTemplates.getTitle();
            String content = emailTemplates.getTemplate();
            if (content != null) {
                /*
                  see how to automate this 🤭
                  see a listing of all keys in selected EmailTemplatesEnum and replace each
                 */
                content = content.replaceAll("#name", user.getUserDetails().getName())
                        .replaceAll("#username", args[0])
                        .replaceAll("#password", args[1])
                        .replaceAll("#portalLink", baseUrl);
            } else {

                content = "";
            }

            sendGeneralEmail(mailConfigOptional.get(), user, title, content,user.getEmail());
        }
    }

    private void sendMemberClaimActivityEmail(MailEvent event) {
        Optional<MailConfig> mailConfigOptional = getMailConfig();
        if (!mailConfigOptional.isPresent()) {
            return;
        }
        EmailTemplates emailTemplates = emailTemplatesRepository.findByEmailTemplatesEnum(
                EmailTemplatesEnum.CLAIM_STATUS
        );
        if (emailTemplates != null) {
            MailConfig mailConfig = mailConfigOptional.get();
            String baseUrl = mailConfig.getBaseUrl();
            User user = event.getUser();
            String[] args = event.getArgs();
            String title = emailTemplates.getTitle();
            String content = emailTemplates.getTemplate();
            if (content != null) {
                /*
                  see how to automate this 🤭
                  see a listing of all keys in emailTemplates and replace each
                 */
                content = content.replaceAll("#name", user.getUserDetails().getName())
                        .replaceAll("#benefitNumber", args[0])
                        .replaceAll("#change", args[1])
                        .replaceAll("#portalLink", baseUrl);
            } else {
                content = "";
            }

            sendGeneralEmail(mailConfigOptional.get(), user, title, content,user.getEmail());
        }
    }

    private void sendTicketRaisedEmail(MailEvent event) {
        Optional<MailConfig> mailConfigOptional = getMailConfig();
        if (!mailConfigOptional.isPresent()) {
            return;
        }
        EmailTemplates emailTemplates = emailTemplatesRepository.findByEmailTemplatesEnum(
                EmailTemplatesEnum.TICKET_RAISED
        );
        if (emailTemplates != null) {
            MailConfig mailConfig = mailConfigOptional.get();
            String baseUrl = mailConfig.getBaseUrl();
            String[] args = event.getArgs();
            User user = event.getUser();
            String title = emailTemplates.getTitle();
            String content = emailTemplates.getTemplate();
            if (content != null) {
                /*
                  see how to automate this 🤭
                  see a listing of all keys in emailTemplates and replace each
                 */
                content = content.replaceAll("#name", user.getUserDetails().getName())
                        .replaceAll("#ticketNumber", args[0])
                        .replaceAll("#portalLink", baseUrl);
            } else {
                content = "";
            }

            sendGeneralEmail(mailConfigOptional.get(), user, title, content,user.getEmail());
        }
    }

    private void sendTicketReplyEmail(MailEvent event) {
        Optional<MailConfig> mailConfigOptional = getMailConfig();
        if (!mailConfigOptional.isPresent()) {
            return;
        }
        EmailTemplates emailTemplates = emailTemplatesRepository.findByEmailTemplatesEnum(
                EmailTemplatesEnum.TICKET_REPLY
        );
        if (emailTemplates != null) {
            MailConfig mailConfig = mailConfigOptional.get();
            String baseUrl = mailConfig.getBaseUrl();
            String[] args = event.getArgs();
            User user = event.getUser();
            String title = emailTemplates.getTitle();
            String content = emailTemplates.getTemplate();
            if (content != null) {
                /*
                  see how to automate this 🤭
                  see a listing of all keys in emailTemplates and replace each
                 */
                content = content.replaceAll("#name", user.getUserDetails().getName())
                        .replaceAll("#ticketNumber", args[0])
                        .replaceAll("#message", args[1])
                        .replaceAll("#replyBy", args[2])
                        .replaceAll("#timeReplied", args[3])
                        .replaceAll("#portalLink", baseUrl);
            } else {
                content = "";
            }

            sendGeneralEmail(mailConfigOptional.get(), user, title, content,user.getEmail());
        }
    }

    private void sendSimpleEmail(MailEvent event,EmailTemplatesEnum emailTemplatesEnum) {
        Optional<MailConfig> mailConfigOptional = getMailConfig();
        if (!mailConfigOptional.isPresent()) {
            return;
        }
        EmailTemplates emailTemplates = emailTemplatesRepository.findByEmailTemplatesEnum(
                emailTemplatesEnum
        );
        if (emailTemplates != null) {
            MailConfig mailConfig = mailConfigOptional.get();
            String baseUrl = mailConfig.getBaseUrl();
//            String[] args = event.getArgs();
            User user = event.getUser();
            String title = emailTemplates.getTitle();
            String content = emailTemplates.getTemplate();
            if (content != null) {
                /*
                  see how to automate this 🤭
                  see a listing of all keys in emailTemplates and replace each
                 */
                content = content.replaceAll("#name", user.getUserDetails().getName())
                        .replaceAll("#portalLink", baseUrl);
            } else {
                content = "";
            }

            sendGeneralEmail(mailConfigOptional.get(), user, title, content,user.getEmail());
        }
    }

    private void sendEmployerEmail(MailEvent event,EmailTemplatesEnum emailTemplatesEnum) {
        Optional<MailConfig> mailConfigOptional = getMailConfig();
        if (!mailConfigOptional.isPresent()) {
            return;
        }
        EmailTemplates emailTemplates = emailTemplatesRepository.findByEmailTemplatesEnum(
                emailTemplatesEnum
        );
        if (emailTemplates != null) {
            MailConfig mailConfig = mailConfigOptional.get();
            String baseUrl = mailConfig.getBaseUrl();
            String[] args = event.getArgs();
            User user = event.getUser();
            String title = emailTemplates.getTitle();
            String content = emailTemplates.getTemplate();
            if (content != null) {
                /*
                  see how to automate this 🤭
                  see a listing of all keys in emailTemplates and replace each
                 */
                content = content.replaceAll("#name", user.getUserDetails().getName())
                        .replaceAll("#memberName", args[0])
                        .replaceAll("#portalLink", baseUrl);
            } else {
                content = "";
            }

            sendGeneralEmail(mailConfigOptional.get(), user, title, content,user.getEmail());
        }
    }

    private void sendSSLEmail(MailEvent event,EmailTemplatesEnum emailTemplatesEnum,MailConfig mailConfig) {
        EmailTemplates emailTemplates = emailTemplatesRepository.findByEmailTemplatesEnum(
                emailTemplatesEnum
        );
        String to="";
        if(emailTemplatesEnum.equals(EmailTemplatesEnum.NEW_MEMBER_APPROVAL) || emailTemplatesEnum.equals(EmailTemplatesEnum.NEW_MEMBER_DECLINE)){
            to=event.getMember().getEmailAddress();
        }else  if(emailTemplatesEnum.equals(EmailTemplatesEnum.MEMBER_BIO_DATA_UPDATE_APPROVAL) || emailTemplatesEnum.equals(EmailTemplatesEnum.MEMBER_BIO_DATA_UPDATE_DECLINE)){
            to=event.getStageMemberDetails().getDetails().getEmail();
        }
        else{
            to=event.getUser().getEmail();
        }
        if (emailTemplates != null) {
            User user = event.getUser();
            String title = emailTemplates.getTitle();
            String content = populateContent(emailTemplatesEnum,emailTemplates,event,mailConfig);
            sendGeneralEmail(mailConfig, user, title, content,to);
        }
    }

    private void sendGeneralEmail(MailConfig mailConfig, User user, String title, String content,String to) {
        try {

            Template t = engine.getTemplate("mails/general.html");
            VelocityContext context = new VelocityContext();

            context.put("title", title);
            context.put("content", content);

            StringWriter writer = new StringWriter();
            t.merge(context, writer);
            String cc = writer.toString();

            HtmlEmail email = new HtmlEmail();
            email.setHostName(mailConfig.getHost());
            email.setStartTLSEnabled(mailConfig.isEnableTLS());
            email.setSmtpPort(mailConfig.getPort());
            email.setAuthenticator(new DefaultAuthenticator(mailConfig.getUsername(), mailConfig.getPassword()));
            email.setFrom(mailConfig.getFrom());
            email.setSubject(title);
            email.getMailSession().getProperties().put("mail.smtp.ssl.trust", mailConfig.getHost());
            email.setHtmlMsg(cc);
            email.addTo(to);
            String s = email.send();
            notificationRepository.create(
                    Notification.getInstance(
                            user.getUserDetails().getName(),
                            user.getEmail(),
                            content,
                            title,
                            s != null
                    )
            );
            log.info(s);
            log.info("Sent e-mail to User '{}'", user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("e-mail could not be sent to user '{}', exception is: {}", user.getEmail(), e.getMessage());
        }

    }

    private void sendActivationEmail3(MailEvent mailEvent) {
        if (getMailConfig().isPresent()) {
            User user = mailEvent.getUser();
            MailConfig mailConfig = getMailConfig().get();

            Message message = getMessage(user);
            String baseUrl = mailConfig.getBaseUrl();

            String subject = message.template(String.format("{%s}", mailEvent.getSubjectTemplate())).toString();
            ;
            String[] args = mailEvent.getArgs();
            try {

                Template t = engine.getTemplate(String.format("mails/%s.html", mailEvent.getContentTemplate()));
                VelocityContext context = new VelocityContext();

                context.put("name", user.getUserDetails().getName());
                context.put("username", args[0]);
                context.put("password", args[1]);
                context.put("portalLink", baseUrl);

                context.put("props", (Function<String, Message>) message::template);
                StringWriter writer = new StringWriter();
                t.merge(context, writer);
                String content = writer.toString();

                HtmlEmail email = new HtmlEmail();
                email.setHostName(mailConfig.getHost());
                email.setStartTLSEnabled(mailConfig.isEnableTLS());
                email.setSmtpPort(mailConfig.getPort());
                email.setAuthenticator(new DefaultAuthenticator(mailConfig.getUsername(), mailConfig.getPassword()));
                email.setFrom(mailConfig.getFrom());
                email.setSubject(subject);
                email.getMailSession().getProperties().put("mail.smtp.ssl.trust", mailConfig.getHost());
                email.setHtmlMsg(content);
                email.addTo(user.getEmail());
                email.send();
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
    }

    private void sendSupportEmail(MailEvent event) {
        if (getMailConfig().isPresent()) {
            SupportMessages supportMessages = event.getSupportMessages();
            MailConfig mailConfig = getMailConfig().get();
            String subject = "ACTION REQUIRED";
            String content = supportMessages.getMessage();
            try {
                HtmlEmail email = new HtmlEmail();
                email.setHostName(mailConfig.getHost());
                email.setStartTLSEnabled(mailConfig.isEnableTLS());
                email.setSmtpPort(mailConfig.getPort());
                email.setAuthenticator(new DefaultAuthenticator(mailConfig.getUsername(),
                        mailConfig.getPassword()));
                email.setFrom(supportMessages.getEmail());
                email.setSubject(subject);
                email.getMailSession().getProperties().put("mail.smtp.ssl.trust", mailConfig.getHost());
                email.setHtmlMsg(content);
                email.addTo(mailConfig.getSupportEmail());
                email.send();
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
    }

    private String getContent(MailEvent mailEvent, Message message) {
        String baseUrl = getMailConfig().isPresent() ? getMailConfig().get().getBaseUrl() : "";
        Template t = engine.getTemplate(String.format("mails/%s.html", mailEvent.getContentTemplate()));
        VelocityContext context = new VelocityContext();
        if (mailEvent.getTicket() != null) {
            context.put(TICKETNUMBER, mailEvent.getTicket().getId());
            context.put(MAILLINK, baseUrl + "/mssvision/#mainticket");
        }
        if (mailEvent.getBenefitRequest() != null) {
            context.put(BENEFITNUMBER, mailEvent.getBenefitRequest().getId());
        }
        if (mailEvent.getChanges() != null) {
            context.put(CLAIM_CHANGES, mailEvent.getChanges());
        }
        context.put(USER, mailEvent.getUser());
        context.put(USERNAME, mailEvent.getUser().getUserDetails().getName());
        context.put(PWD_RESET_LINK, baseUrl + "/reset-password.jsp?key=" + mailEvent.getUser().getResetKey());
        context.put(ACTIVATION_LINK, baseUrl + "/handle?action=activate&key=" + mailEvent.getUser().getActivationKey());

        context.put("props", (Function<String, Message>) message::template);
        StringWriter writer = new StringWriter();
        t.merge(context, writer);
        return writer.toString();

    }

    private Message getMessage(User user) {
        return messageContext.messageSource("i18n.messages")
                .localeResolver(() -> Locale.forLanguageTag(user.getLangKey()))
                .message();
    }

    private Optional<MailConfig> getMailConfig() {
        return mailConfigRepository.findAll().stream()
                .filter(MailConfig::isEnabled)
                .findFirst();
    }

    public void sendTLSEmail(MailConfig mailConfig, MailEvent event, EmailTemplatesEnum emailTemplatesEnum) {
        User user=event.getUser();
        String[] args = event.getArgs();
        String baseUrl = mailConfig.getBaseUrl();
        EmailTemplates emailTemplates = emailTemplatesRepository.findByEmailTemplatesEnum(
                emailTemplatesEnum
        );
        String content=populateContent(emailTemplatesEnum,emailTemplates,event,mailConfig);

        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", mailConfig.getHost());
        props.put("mail.smtp.port", mailConfig.getPort());
        javax.mail.Session session = javax.mail.Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailConfig.getUsername(), mailConfig.getPassword());
            }
        });
        try {
            MimeMessage msg = new MimeMessage(session);
            String to="";
            if(emailTemplatesEnum.equals(EmailTemplatesEnum.NEW_MEMBER_APPROVAL) || emailTemplatesEnum.equals(EmailTemplatesEnum.NEW_MEMBER_DECLINE)){
                to=event.getMember().getEmailAddress();
            }else  if(emailTemplatesEnum.equals(EmailTemplatesEnum.MEMBER_BIO_DATA_UPDATE_APPROVAL) || emailTemplatesEnum.equals(EmailTemplatesEnum.MEMBER_BIO_DATA_UPDATE_DECLINE)){
                to=event.getStageMemberDetails().getDetails().getEmail();
            }
            else{
                to=event.getUser().getEmail();
            }
            log.warn(" User Recepient email ==== " + to );
            InternetAddress[] address = InternetAddress.parse(to, true);
            msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
            msg.setSubject(emailTemplates.getTitle());
            msg.setSentDate(new Date());
            msg.setText(content);
            msg.setHeader("XPriority", "1");
            Transport.send(msg);
            notificationRepository.create(
                    Notification.getInstance(
                            user.getUserDetails().getName(),
                            user.getEmail(),
                            content,
                            msg.getSubject(),
                            true
                    )
            );
            log.warn("Mail has been sent successfully");
        } catch (MessagingException mex) {
            mex.printStackTrace();
            log.warn("Unable to send an email" + mex);
        }
    }

    public void sendTLSEmail(MailEvent event) {
        if (getMailConfig().isPresent()) {

            MailConfig mailConfig = getMailConfig().get();
            String subject = event.getSubject(),
                    content = event.getMessage(),
                    to = event.getTo();

            User user = event.getUser();
            Properties props = new Properties();
            props.put("mail.smtp.auth", true);
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", mailConfig.getHost());
            props.put("mail.smtp.port", mailConfig.getPort());
            javax.mail.Session session = javax.mail.Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(mailConfig.getUsername(), mailConfig.getPassword());
                }
            });
            try {
                MimeMessage msg = new MimeMessage(session);

                log.warn(" User Recepient email ==== " + to);
                InternetAddress[] address = InternetAddress.parse(to, true);
                msg.setRecipients(javax.mail.Message.RecipientType.TO, address);
                msg.setSubject(subject);
                msg.setSentDate(new Date());
                msg.setText(content);
                msg.setHeader("XPriority", "1");
                log.info(msg.toString());
                Transport.send(msg);
                notificationRepository.create(
                        Notification.getInstance(
                                user.getUserDetails().getName(),
                                user.getEmail(),
                                content,
                                msg.getSubject(),
                                true
                        )
                );
                log.warn("Mail has been sent successfully");
            } catch (MessagingException mex) {
                mex.printStackTrace();
                log.warn("Unable to send an email" + mex);
            }
        }
    }
    private String populateContent(EmailTemplatesEnum emailTemplatesEnum,EmailTemplates emailTemplates, MailEvent event,MailConfig mailConfig){

        String content=emailTemplates.getTemplate();
        User user=event.getUser();
        String[] args = event.getArgs();
        String baseUrl = mailConfig.getBaseUrl();
        if(content != null) {
            switch (emailTemplatesEnum) {
                case ACCOUNT_ACTIVATION:
                case PRINCIPAL_OFFICER_ACCOUNT_ACTIVATION:
                case ADMIN_ACCOUNT_ACTIVATION:
                case MEMBER_ACCOUNT_ACTIVATION:
                case PASSWORD_RESET:
                    content = content.replaceAll("#name", user.getUserDetails().getName())
                            .replaceAll("#username", args[0])
                            .replaceAll("#password", args[1])
                            .replaceAll("#portalLink", baseUrl);
                    break;
                case REQUEST_PASSWORD_RESET:
                    content = content.replaceAll("#name", user.getUserDetails().getName())
                            .replaceAll("#url",
                                    "<a href=\"" +
                                            baseUrl + "/reset-password.jsp?key=" + user.getResetKey()
                                            + "\">Reset password</a>"
                            );
                    break;
                case CLAIM_STATUS:
                    content = content.replaceAll("#name", user.getUserDetails().getName())
                            .replaceAll("#benefitNumber", args[0])
                            .replaceAll("#change", args[1])
                            .replaceAll("#portalLink", baseUrl);
                    break;
                case TICKET_RAISED:
                    content = content.replaceAll("#name", user.getUserDetails().getName())
                            .replaceAll("#ticketNumber", args[0])
                            .replaceAll("#portalLink", baseUrl);
                    break;
                case TICKET_REPLY:
                    content = content.replaceAll("#name", user.getUserDetails().getName())
                            .replaceAll("#ticketNumber", args[0])
                            .replaceAll("#message", args[1])
                            .replaceAll("#replyBy", args[2])
                            .replaceAll("#timeReplied", args[3])
                            .replaceAll("#portalLink", baseUrl);
                    break;
                case PO_NEW_MEMBER_APPROVAL_REQUEST:
                case PO_MEMBER_BENEFICIARY_APPROVAL_REQUEST:
                case PO_PENDING_CLAIM:
                case CLAIM_INITIATED:
                    content = content.replaceAll("#name", user.getUserDetails().getName())
                            .replaceAll("#memberName", args[0])
                            .replaceAll("#portalLink", baseUrl);
                    break;
                case NEW_MEMBER_APPROVAL:
                case NEW_MEMBER_DECLINE:
                    content = content.replaceAll("#name", ( event.getMember().getFirstname() != null ? event.getMember().getFirstname() : "" )
                            +" "
                            +(event.getMember().getLastname() != null ? event.getMember().getLastname() : " " ))
                            .replaceAll("#portalLink", baseUrl);
                    break;
                case MEMBER_BIO_DATA_UPDATE_APPROVAL:
                case MEMBER_BIO_DATA_UPDATE_DECLINE:
                    content = content.replaceAll("#name", event.getStageMemberDetails().getFname()+" "+event.getStageMemberDetails().getLastName())
                            .replaceAll("#portalLink", baseUrl);
                    break;
                case MEMBER_BENEFICIARY_DECLINE:
                case MEMBER_BENEFICIARY_APPROVAL:
                    content = content.replaceAll("#name", user.getUserDetails().getName())
                            .replaceAll("#portalLink", baseUrl);
                    break;
                default:
            }
        }
        else{
            content = "";
        }
        return content;
    }
}
