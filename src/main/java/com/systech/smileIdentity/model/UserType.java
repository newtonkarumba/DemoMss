package com.systech.smileIdentity.model;

public enum UserType {
    MEMBER("member"),
    PENSIONER("pensioner");

    private String name;

    UserType(String name) {
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
