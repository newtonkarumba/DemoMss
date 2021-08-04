package com.systech.mss.vm;

import lombok.Getter;

@Getter
public class PlainEmailVm {
    String action;
    String email, subject, message;

    public PlainEmailVm(String action, String email, String subject, String message) {
        this.action = action;
        this.email = email;
        this.subject = subject;
        this.message = message;
    }
}
