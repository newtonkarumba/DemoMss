package com.systech.mss.sms;

import com.systech.mss.domain.*;

import java.io.Serializable;

public class SmsEvent {

    private User user;
    private Ticket ticket;
    private TicketMessage ticketMessage;
    private BenefitRequest benefitRequest;
    private String subjectTemplate;
    private String contentTemplate;

    private String action;
    private String[] args;
    private SupportMessages supportMessages;
    private Serializable serializable;

    public SmsEvent(User user, String subjectTemplate, String contentTemplate) {
        this.user = user;
        this.subjectTemplate = subjectTemplate;
        this.contentTemplate = contentTemplate;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public BenefitRequest getBenefitRequest() {
        return benefitRequest;
    }

    public User getUser() {
        return user;
    }

    public String getSubjectTemplate() {
        return subjectTemplate;
    }

    public String getContentTemplate() {
        return contentTemplate;
    }

    public String getAction() {
        return action;
    }

    public String[] getArgs() {
        return args;
    }

    public Serializable getSerializable() {
        return serializable;
    }

    public SupportMessages getSupportMessages() {
        return supportMessages;
    }

}
