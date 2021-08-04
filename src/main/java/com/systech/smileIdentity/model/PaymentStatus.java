package com.systech.smileIdentity.model;

public enum PaymentStatus {
    PENDING("Pending"),
    NOT_PAID("Not Paid"),
    PAID_SUCCESSFUL_NOT_USED("Paid Successfully But Not Used"),
    PAID_SUCCESSFUL_USED("Paid Successfully and Used");

    private String name;

    PaymentStatus(String name) {
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
