package com.systech.mss.domain.common;

public enum UserStatus {
    ACTIVE("ACTIVE"),
    DELETED("DELETED");

    private String name;

    UserStatus(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
