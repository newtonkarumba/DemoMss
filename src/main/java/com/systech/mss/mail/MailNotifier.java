package com.systech.mss.mail;


import com.sun.mail.util.MailSSLSocketFactory;
import com.systech.mss.config.Constants;
import com.systech.mss.domain.*;
import com.systech.mss.repository.EmailTemplatesRepository;
import com.systech.mss.repository.MailConfigRepository;
import com.systech.mss.repository.NotificationRepository;
import com.systech.mss.vm.PlainEmailVm;
import lombok.NonNull;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.apache.deltaspike.core.api.message.Message;
import org.apache.deltaspike.core.api.message.MessageContext;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.ejb.*;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.StringWriter;
import java.util.*;
import java.util.function.Function;

import static com.systech.mss.config.Constants.EMAIL_CONFIGS_NOT_SET;
import static com.systech.mss.util.StringUtil.sanitize;


@Startup
//@ApplicationScoped
@Stateful
@Remote
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class MailNotifier {

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

    public void sendEmail(@ObservesAsync @NonNull MailEvent event) {

        if (getMailConfig().isPresent()) {
            MailConfig mailConfig = getMailConfig().get();

            if (event.getAction() != null) {
                switch (event.getAction()) {
                    case Constants.SEND_SUPPORT_MESSAGE:
                        log.info("Sending " + Constants.SEND_SUPPORT_MESSAGE);
                        sendSupportEmail(event);
                        return;
                    case Constants.SEND_ACTIVATION_EMAIL3:
                        log.info("Sending " + Constants.SEND_ACTIVATION_EMAIL3);
                        sendActivationEmail3(event);
                        return;
                    default:
                }
            }

            EmailTemplatesEnum emailTemplatesEnum = event.getEmailTemplatesEnum();
            if (emailTemplatesEnum != null) {
                log.info("Sending using template " + emailTemplatesEnum.getName());

                if (mailConfig.getMailType().equals(MailType.SSL)) {
                    log.warn("Inside SSL");
                    sendSSLEmail(mailConfig, event, emailTemplatesEnum);
                } else {
                    log.warn("IN TLS");
                    sendTLSEmail(mailConfig, event, emailTemplatesEnum);
                }
                return; //UKITOA JUWA UMEANZA KUWA MTU BLADFAKIN ☹
                //☹☹☹ sawa kiongos
            }

            log.info("Send email Default option");

            User user = event.getUser();
            try {
                String to = user.getEmail();
                Message message = getMessage(event.getUser());
                String subject = message.template(String.format("{%s}", event.getSubjectTemplate())).toString();
                String content = getContent(event, message);
                sendSSLEmail(
                        user.getUserDetails().getName(),
                        to,
                        subject,
                        content
                );
            } catch (Exception e) {
                log.warn("e-mail could not be sent to user,exception is: {}", "USER NOT FOUND");
            }
        } else {
            log.warn("e-mail could not be sent to user,exception is: {}", EMAIL_CONFIGS_NOT_SET);
        }
    }

    public void sendPlainMessage(@ObservesAsync @NonNull PlainEmailVm plainEmailVm) {
        String subject = plainEmailVm.getSubject(),
                content = plainEmailVm.getMessage(),
                to = plainEmailVm.getEmail();
        sendMyEmail(
                to,
                to,
                subject,
                content
        );
    }

    private void sendActivationEmail3(MailEvent mailEvent) {
        if (getMailConfig().isPresent()) {
            User user = mailEvent.getUser();
            MailConfig mailConfig = getMailConfig().get();
            Message message = getMessage(user);
            String baseUrl = mailConfig.getBaseUrl();
            String subject = message.template(String.format("{%s}", mailEvent.getSubjectTemplate())).toString();
            String[] args = mailEvent.getArgs();

            try {

                Template t = engine.getTemplate(String.format("mails/%s.html", mailEvent.getContentTemplate()));
                VelocityContext context = new VelocityContext();

                context.put("name", user.getUserDetails().getName());
                context.put("username", args[0]);
                context.put("password", args[1]);
                context.put("portalLink", sanitize(baseUrl));

                context.put("props", (Function<String, Message>) message::template);
                StringWriter writer = new StringWriter();
                t.merge(context, writer);
                String content = writer.toString();
                sendMyEmail(
                        user.getUserDetails().getName(),
                        user.getEmail(),
                        subject,
                        content
                );

            } catch (Exception e) {
                e.printStackTrace();
                log.error(e.getMessage());
            }
        }
    }

    private void sendMyEmail(String toName, String emailTo, String subject, String content) {
        if (getMailConfig().isPresent()) {
            MailConfig mailConfig = getMailConfig().get();
            if (mailConfig.getMailType().equals(MailType.SSL)) {
                sendSSLEmail(
                        toName,
                        emailTo,
                        subject,
                        content
                );
            } else {
                sendTLSEmail(
                        toName,
                        emailTo,
                        subject,
                        content
                );
            }
        }
    }

    private void sendSupportEmail(MailEvent event) {
        if (getMailConfig().isPresent()) {
            SupportMessages supportMessages = event.getSupportMessages();
            MailConfig mailConfig = getMailConfig().get();
            String subject = "ACTION REQUIRED";
            String content = supportMessages.getMessage();
            sendMyEmail(
                    mailConfig.getSupportEmail(),
                    mailConfig.getSupportEmail(),
                    subject,
                    content
            );
        }
    }

    private String getContent(MailEvent mailEvent, Message message) {
        String baseUrl = getMailConfig().isPresent() ? getMailConfig().get().getBaseUrl() : "";
        Template t = engine.getTemplate(String.format("mails/%s.html", mailEvent.getContentTemplate()));
        VelocityContext context = new VelocityContext();
        if (mailEvent.getTicket() != null) {
            String TICKETNUMBER = "ticketNumber";
            context.put(TICKETNUMBER, mailEvent.getTicket().getId());
            String MAILLINK = "mailLink";
            context.put(MAILLINK, baseUrl + "/mssvision/#mainticket");
        }
        if (mailEvent.getBenefitRequest() != null) {
            String BENEFITNUMBER = "benefitNumber";
            context.put(BENEFITNUMBER, mailEvent.getBenefitRequest().getId());
        }
        if (mailEvent.getChanges() != null) {
            String CLAIM_CHANGES = "claimChanges";
            context.put(CLAIM_CHANGES, mailEvent.getChanges());
        }
        String USER = "user";
        context.put(USER, mailEvent.getUser());
        String USERNAME = "userName";
        context.put(USERNAME, mailEvent.getUser().getUserDetails().getName());
        String PWD_RESET_LINK = "pwdResetLink";
        context.put(PWD_RESET_LINK, baseUrl + "/reset-password.jsp?key=" + mailEvent.getUser().getResetKey());
        String ACTIVATION_LINK = "activationLink";
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

    private void sendSSLEmail(MailConfig mailConfig, MailEvent event, EmailTemplatesEnum emailTemplatesEnum) {
        EmailTemplates emailTemplates = emailTemplatesRepository.findByEmailTemplatesEnum(
                emailTemplatesEnum
        );
        String to, name;
        switch (emailTemplatesEnum) {
            case NEW_MEMBER_REGISTERED:
            case NEW_MEMBER_APPROVAL:
            case NEW_MEMBER_DECLINE:
                log.warn("In First if");
                to = event.getMember().getEmailAddress();
                name = event.getMember().getFirstname();
                break;
            case MEMBER_BIO_DATA_UPDATE_APPROVAL:
            case MEMBER_BIO_DATA_UPDATE_DECLINE:
                log.warn("In First else if");
                to = event.getStageMemberDetails().getDetails().getEmail();
                name = event.getStageMemberDetails().getDetails().getFirstname();
                break;
            default:
                log.warn("Other template...");
                to = event.getUser().getEmail();
                name = event.getUser().getFirstName();
                log.warn("TO email is.. +" + to);
        }
        log.error("Recipient {} Email Found", to);
        if (emailTemplates != null) {
            log.error("Email Template {} found", emailTemplatesEnum.getName());
            String title = emailTemplates.getTitle();
            String content = populateContent(emailTemplatesEnum, emailTemplates, event, mailConfig);
            sendSSLEmail(
                    name,
                    to,
                    title,
                    content
            );
        }
    }

    public void sendTLSEmail(MailConfig mailConfig, MailEvent event, EmailTemplatesEnum emailTemplatesEnum) {
        log.warn("IN send tls email");
        log.warn("TEMPLATE IS: " + emailTemplatesEnum.toString());
        EmailTemplates emailTemplates = emailTemplatesRepository.findByEmailTemplatesEnum(
                emailTemplatesEnum
        );
        log.warn("Template Title: " + emailTemplates.getTitle());
        String content = populateContent(emailTemplatesEnum, emailTemplates, event, mailConfig);
        String to, name;
        switch (emailTemplatesEnum) {
            case NEW_MEMBER_REGISTERED:
            case NEW_MEMBER_APPROVAL:
            case NEW_MEMBER_DECLINE:
                log.warn("In First if");
                to = event.getMember().getEmailAddress();
                name = event.getMember().getFirstname();
                break;
            case MEMBER_BIO_DATA_UPDATE_APPROVAL:
            case MEMBER_BIO_DATA_UPDATE_DECLINE:
                log.warn("In First else if");
                to = event.getStageMemberDetails().getDetails().getEmail();
                name = event.getStageMemberDetails().getDetails().getFirstname();
                break;
            default:
                log.warn("Other template...");
                to = event.getUser().getEmail();
                name = event.getUser().getFirstName();
                log.warn("TO email is.. +" + to);
        }

        log.error("Recipient {} Email Found", to);
        log.error("Email Template {} found", emailTemplatesEnum.getName());
        sendTLSEmail(
                name,
                to,
                emailTemplates.getTitle(),
                content
        );
    }

    /**
     * @param toName  senderName
     * @param to      recipient
     * @param subject subject
     * @param content message
     */
    public void sendTLSEmail(String toName, String to, String subject, String content) {
        log.warn("In tls 2 send email");
        if (getMailConfig().isPresent()) {
            try {
                log.info("Now initiating TLS....");
                MailConfig mailConfig = getMailConfig().get();
                List<String> recipients = new ArrayList<>();
                recipients.add(to);
                boolean sent = this.sendEmail(recipients, subject, content, mailConfig.getFrom().toLowerCase(),
                        mailConfig.getFrom(),
                        null);
                log.warn("Email sent status: " + sent);
                notificationRepository.create(
                        Notification.getInstance(
                                toName,
                                to,
                                content,
                                subject,
                                true
                        ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param toName  senderName
     * @param to      recipient
     * @param subject subject
     * @param content message
     */
    public void sendSSLEmail(String toName, String to, String subject, String content) {
        if (getMailConfig().isPresent()) {
            try {
                log.info("Now initiating SSL....");
                MailConfig mailConfig = getMailConfig().get();
                HtmlEmail email = new HtmlEmail();
                email.setHostName(mailConfig.getHost());
                email.setStartTLSEnabled(mailConfig.isEnableTLS());
                email.setSmtpPort(mailConfig.getPort());
                email.setAuthenticator(new DefaultAuthenticator(mailConfig.getUsername(),
                        mailConfig.getPassword()));
                email.setFrom(mailConfig.getFrom());
                email.setSubject(subject);
                email.getMailSession().getProperties().put("mail.smtp.ssl.trust", mailConfig.getHost());
                email.setHtmlMsg(content);
                email.addTo(to);
                String s = email.send();
                notificationRepository.create(
                        Notification.getInstance(
                                toName,
                                to,
                                content,
                                subject,
                                s != null
                        )
                );
                log.info("Sent e-mail to User '{}'", to);
            } catch (Exception e) {
                e.printStackTrace();
                log.warn("e-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
            }
        }
    }

    private String populateContent(EmailTemplatesEnum emailTemplatesEnum, EmailTemplates emailTemplates, MailEvent event, MailConfig mailConfig) {

        try {
            String content = emailTemplates.getTemplate();
            User user = event.getUser();
            String[] args = event.getArgs();
            String baseUrl = mailConfig.getBaseUrl();
            if (content != null) {
                log.warn("Template content is NOT NULL");
                switch (emailTemplatesEnum) {
                    case OTP_VERIFICATION:
                        if (user != null)
                            content = content.replaceAll("#name", sanitize(user.getUserDetails().getName()))
                                    .replaceAll("#token", sanitize(args[0]));
                        break;
                    case ACCOUNT_ACTIVATION:
                    case PRINCIPAL_OFFICER_ACCOUNT_ACTIVATION:
                    case ADMIN_ACCOUNT_ACTIVATION:
                    case MEMBER_ACCOUNT_ACTIVATION:
                    case PASSWORD_RESET:
                        if (user != null)
                            content = content.replaceAll("#name", sanitize(user.getUserDetails().getName()))
                                    .replaceAll("#username", sanitize(args[0]))
                                    .replaceAll("#password", sanitize(args[1]))
                                    .replaceAll("#portalLink", sanitize(baseUrl));
                        break;

                    case REQUEST_PASSWORD_RESET:
                        if (user != null)
                            content = content.replaceAll("#name", sanitize(user.getUserDetails().getName()))
                                    .replaceAll("#url",
                                            sanitize("<a href=\"" +
                                                    baseUrl + "/reset-password.jsp?key=" + user.getResetKey()
                                                    + "\">Reset password</a>")
                                    );
                        break;

                    case MEMBER_CLAIM_INITIATED:
                    case CLAIM_STATUS:
                        if (user != null)
                            content = content.replaceAll("#name", sanitize(user.getUserDetails().getName()))
                                    .replaceAll("#benefitNumber", sanitize(args[0]))
                                    .replaceAll("#change", sanitize(args[1]))
                                    .replaceAll("#portalLink", sanitize(baseUrl));
                        break;

                    case TICKET_RAISED:
                        if (user != null)
                            content = content.replaceAll("#name", sanitize(user.getUserDetails().getName()))
                                    .replaceAll("#ticketNumber", sanitize(args[0]))
                                    .replaceAll("#portalLink", sanitize(baseUrl));
                        break;
                    case TICKET_REPLY:
                        if (user != null)
                            content = content.replaceAll("#name", user.getUserDetails().getName())
                                    .replaceAll("#ticketNumber", sanitize(args[0]))
                                    .replaceAll("#message", sanitize(args[1]))
                                    .replaceAll("#replyBy", sanitize(args[2]))
                                    .replaceAll("#timeReplied", sanitize(args[3]))
                                    .replaceAll("#portalLink", sanitize(baseUrl));
                        break;

                    case PO_NEW_MEMBER_APPROVAL_REQUEST:
                        if (user != null)
                            content = content.replaceAll("#name", user.getUserDetails().getName())
                                    .replaceAll("#memberName", sanitize(args[0]))
                                    .replaceAll("#scheme", sanitize(args[1]))
                                    .replaceAll("#sponsor", sanitize(args[2]))
                                    .replaceAll("#portalLink", sanitize(baseUrl));
                        break;

                    case PO_MEMBER_BIO_DATA_APPROVAL_REQUEST:
                    case PO_MEMBER_BENEFICIARY_APPROVAL_REQUEST:
                    case PO_PENDING_CLAIM:
                    case CLAIM_INITIATED:
                        if (user != null)
                            content = content.replaceAll("#name", user.getUserDetails().getName())
                                    .replaceAll("#memberName", sanitize(args[0]))
                                    .replaceAll("#portalLink", sanitize(baseUrl));
                        break;

                    case NEW_MEMBER_REGISTERED:
                    case NEW_MEMBER_APPROVAL:
                    case NEW_MEMBER_DECLINE:
                        Member member = event.getMember();
                        content = content.replaceAll("#name", (member.getFirstname() != null ? member.getFirstname() : "")
                                        + " "
                                        + (member.getLastname() != null ? member.getLastname() : " "))
                                .replaceAll("#scheme", sanitize(args[0]))
                                .replaceAll("#sponsor", sanitize(args[1]))
                                .replaceAll("#portalLink", sanitize(baseUrl));
                        break;

                    case MEMBER_BIO_DATA_UPDATE_APPROVAL:
                    case MEMBER_BIO_DATA_UPDATE_DECLINE:
                        content = content.replaceAll("#name", event.getStageMemberDetails().getFname() + " " + event.getStageMemberDetails().getLastName())
                                .replaceAll("#portalLink", sanitize(baseUrl));
                        break;

                    case MEMBER_BENEFICIARY_DECLINE:
                    case MEMBER_BENEFICIARY_APPROVAL:
                        if (user != null)
                            content = content.replaceAll("#name", user.getUserDetails().getName())
                                    .replaceAll("#portalLink", sanitize(baseUrl));
                        break;

                    default:
                        log.warn("No template matching {}", emailTemplatesEnum.getName());
                }
            } else {
                log.warn("Template content is NULL");
                content = "";
            }
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private Optional<MailConfig> getMailConfig() {
        return mailConfigRepository.findAll().stream()
                .filter(MailConfig::isEnabled)
                .findFirst();
    }

    public boolean sendEmail(List<String> recipients, String subject, String msg, String from, String senderName, List<String> attachments) throws Exception {
        if (!getMailConfig().isPresent())
            return false;

        MailConfig mailConfig = getMailConfig().get();
        Properties props = new Properties();

        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", mailConfig.getHost());
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.socketFactory.fallback", "true");

        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.socketFactory", sf);

        javax.mail.Session mailSession = javax.mail.Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailConfig.getUsername(), mailConfig.getPassword());
            }
        });

//        Session mailSession = Session.getDefaultInstance(props);
        mailSession.setProtocolForAddress("rfc822", "smtps");
        // mailSession.setDebug(true);
        MimeMessage message = new MimeMessage(mailSession);

        // set the from and to address
        InternetAddress addressFrom = new InternetAddress(from, senderName);
        message.setFrom(addressFrom);

        try {

            List<String> sanitizedRecipients = new ArrayList<>(recipients);

            InternetAddress[] addressTo = new InternetAddress[sanitizedRecipients.size()];


            int i = 0;
            int j = 0;
            for (String addre : sanitizedRecipients) {
                addressTo[i] = new InternetAddress(addre);
                i++;
            }
            message.setRecipients(javax.mail.Message.RecipientType.TO, addressTo);
            // Set Subject: header field
            message.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setContent(msg, "text/html");
            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            if (attachments != null) {
                if (attachments.size() > 0) {
                    String[] att = new String[attachments.size()];
                    for (String oneattach : attachments) {
                        if (oneattach != null) {
                            att[j] = oneattach;

                            MimeBodyPart attchmnt = new MimeBodyPart();

                            FileDataSource source = new FileDataSource(att[j]);

                            attchmnt.setDataHandler(new DataHandler(source));

                            attchmnt.setFileName(source.getName());
                            multipart.addBodyPart(attchmnt);

                            j++;
                        }
                    }
                }
            }

            // Send the complete message parts
            message.setContent(multipart, "text/html; charset=utf-8");

            Transport transport = mailSession.getTransport();
            transport.connect
                    (mailConfig.getHost(), mailConfig.getPort(), mailConfig.getUsername(),
                            mailConfig.getPassword());

            transport.sendMessage(message,
                    message.getRecipients(javax.mail.Message.RecipientType.TO));
            transport.close();
            return true;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
