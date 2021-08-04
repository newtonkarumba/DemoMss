package com.systech.mss.service.enums;

public enum MaritalStatus {
    SINGLE("Single"),
    MARRIED("Married"),
    SEPARATED("Separated"),
    DIVORCED("Divorced"),
    WIDOWED("Widowed"),
    ABANDONED("Abandoned"),
    NOT_SPECIFIED("Not Specified");

    private String name;

    MaritalStatus(String name) {
        this.name = name;
    }

    public static MaritalStatus fromString(String text) {
        if (text != null) {
            for (MaritalStatus b : MaritalStatus.values()) {
                if (text.equalsIgnoreCase(b.name)) {
                    return b;
                }
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
