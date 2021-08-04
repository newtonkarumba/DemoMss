package com.systech.mss.domain;

public enum OtpIdentifier {
    EMAIL("Email"),
    SMS("SMS"),
    PUSH_NOTIFICATION("Push Notification");

    private String name;

    OtpIdentifier(String s) {
        this.name = s;
    }

    public static OtpIdentifier from(String o) {
        OtpIdentifier[] otpIdentifiers=OtpIdentifier.values();
        for (OtpIdentifier otpIdentifier : otpIdentifiers) {
            if (otpIdentifier.getName().equalsIgnoreCase(o)){
                return otpIdentifier;
            }
        }
        return null;
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
