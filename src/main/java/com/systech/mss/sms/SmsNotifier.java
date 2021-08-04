package com.systech.mss.sms;

import com.systech.mss.domain.User;
import com.systech.mss.repository.MailConfigRepository;
import com.systech.mss.repository.NotificationRepository;
import org.apache.deltaspike.core.api.message.Message;
import org.apache.deltaspike.core.api.message.MessageContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.Locale;

@RequestScoped
public class SmsNotifier {
    private static final String USER = "user";
    private static final String TICKETNUMBER = "ticketNumber";
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

//    public void sendSms(@ObservesAsync SmsEvent event) {
//
//        if (event.getAction() != null) {
//            switch (event.getAction()) {
//                case Constants.SEND_SUPPORT_MESSAGE:
//                    sendSupportEmail(event);
//                    return;
//                case Constants.SEND_ACTIVATION_EMAIL3:
//                    sendActivationEmail3(event);
//                    return;
//                default:
//            }
//        }
//
//        User user = event.getUser();
//        String to = user.getLogin();
//            Message message = getMessage(event.getUser());
//            String subject = message.template(String.format("{%s}", event.getSubjectTemplate())).toString();
//            String content = getContent(event, message);
//            try {
////                log.info("Send e-mail to '{}' with subject '{}' and content={}", to, subject, content);
//                // Prepare message
//                HtmlEmail email = new HtmlEmail();
//                email.setHostName(mailConfig.getHost());
//                email.setStartTLSEnabled(true);
//                email.setSmtpPort(mailConfig.getPort());
//                email.setAuthenticator(new DefaultAuthenticator(mailConfig.getUsername(),
//                        mailConfig.getPassword()));
//                email.setFrom(mailConfig.getFrom());
//                email.setSubject(subject);
//                email.setHtmlMsg(content);
//                email.addTo(to);
//                String s = email.send();
//                if (s != null) {
//                    notificationRepository.create(
//                            Notification.getInstance(
//                                    user.getUserDetails().getName(),
//                                    to,
//                                    "",
//                                    subject
//                            )
//                    );
//                }
//                log.info("Sent e-mail to User '{}'", to);
//            } catch (Exception e) {
//                log.warn("e-mail could not be sent to user '{}', exception is: {}", to, e.getMessage());
//            }
//
//
//    }
    private Message getMessage(User user) {
        return messageContext.messageSource("i18n.messages")
                .localeResolver(() -> Locale.forLanguageTag(user.getLangKey()))
                .message();
    }
}
