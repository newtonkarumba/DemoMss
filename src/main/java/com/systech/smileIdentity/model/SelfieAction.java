package com.systech.smileIdentity.model;

public enum SelfieAction {
    REGISTRATION("registration"),
    RE_REGISTRATION("re_registration"),
    AUTHENTICATION("authentication");

    private String name;

    SelfieAction(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
