package com.systech.mss.service;

import com.systech.mss.config.Constants;
import com.systech.mss.domain.*;
import com.systech.mss.mail.MailEvent;
import com.systech.mss.vm.PlainEmailVm;
import org.slf4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;

@RequestScoped
public class MailService {

    @Inject
    private Logger log;

    @Inject
    private Event<MailEvent> eventProducer;
    @Inject
    private Event<PlainEmailVm> plainEventProducer;

    public void sendActivationEmail(User user) {
        eventProducer.fireAsync(new MailEvent(user, "email.activation.title", "activationEmail2"));

    }

    public void sendActivationEmail(User user,String... args) {
        eventProducer.fireAsync(new MailEvent(Constants.SEND_ACTIVATION_EMAIL3,user, "email.activation.title", "activationEmailTemp",args));
    }

    public void sendCreationEmail(User user) {
        eventProducer.fireAsync(new MailEvent(user, "email.creation.title", "creationEmail"));
    }

    public void sendPasswordResetMail(User user) {
        eventProducer.fireAsync(new MailEvent(user, "email.reset.title", "passwordResetEmail"));
    }


    public void sendTicketRaisedMail(User user, Ticket ticket) {
        eventProducer.fireAsync(new MailEvent(user,ticket, "email.TicketCreation.title", "ticketRaisedEmail"));
    }

    public void sendTicketRepliedMail(User user, Ticket ticket) {
        eventProducer.fireAsync(new MailEvent(user,ticket, "email.TicketReply.title", "ticketReplyEmail"));
    }

    public void sendSupportEmail(SupportMessages supportMessages){
        eventProducer.fireAsync(new MailEvent(Constants.SEND_SUPPORT_MESSAGE, supportMessages));
    }

    /**
     *
     * @param user USED TO GET USER DETAILS AND EMAIL
     * @param accountActivation USED TO DEFINE THE TYPE OF TEMPLATE TO USE
     * @param args A LIST OF REAL VALUES TO SUBSTITUTE IN template, indexing is strict ie do proper replacement in content
     */
    public void sendEmail(User user, EmailTemplatesEnum accountActivation, String... args) {
        log.info("sending email");
        eventProducer.fireAsync(new MailEvent(user,accountActivation, args));
    }
    public void sendEmail(Member member, EmailTemplatesEnum accountActivation, String... args) {
        log.info("sending email");
        eventProducer.fireAsync(new MailEvent(member,accountActivation, args));
    }
    public void sendEmail(StageMemberDetails stageMemberDetails, EmailTemplatesEnum accountActivation, String... args) {
        log.info("sending email");
        eventProducer.fireAsync(new MailEvent(stageMemberDetails,accountActivation, args));
    }

    /**
     * USED TO SEND QUICK PLAIN MESSAGES WITHOUT TEMPLATE
     * @param email receivers email
     * @param subject subject
     * @param message message
     */
    public void sendPlainEmail(String email, String subject, String message) {
        log.info("sending plain email...");
//        eventProducer.fireAsync(new MailEvent(Constants.SEND_PLAIN_MESSAGE,email,subject, message));
        plainEventProducer.fireAsync(new PlainEmailVm(Constants.SEND_PLAIN_MESSAGE,email,subject, message));
    }
}