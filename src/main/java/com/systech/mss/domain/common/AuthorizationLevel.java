package com.systech.mss.domain.common;

public enum AuthorizationLevel {
    LEVEL_TWO("LEVEL TWO"),
    LEVEL_THREE("LEVEL THREE");

    private String name;

    AuthorizationLevel(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
