package com.systech.smileIdentity.model;

public enum SelfieSource {
    MSS("mss"),
    IOS("ios"),
    ANDROID("android");

    private String name;

    SelfieSource(String name) {
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
