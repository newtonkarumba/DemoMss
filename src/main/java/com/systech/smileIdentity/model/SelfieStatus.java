package com.systech.smileIdentity.model;

public enum SelfieStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private String name;

    SelfieStatus(String name) {
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
