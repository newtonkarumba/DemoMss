package com.systech.mss.service.enums;

public enum IDType {
    MEMBER_NUMBER("Member Number"),
    TAX_PIN("Tax Pin"),
    PARTY_REF_NO("Party Reference No"),
    POLICY_NO("Policy Number"),
    NATIONAL("National ID"),
    PASSPORT("Passport No"),
    VOTER("Voter ID"),
    PENNO("NHIS"),
    DRIVER("Driver's License"),
    NOT_SPECIFIED("Not Specified");

    private String name;

    IDType(String name) {
        this.name = name;
    }

    public static IDType fromString(String text) {
        if (text != null) {
            try {
                // try getting the raw value
                return IDType.valueOf(text);
            } catch (Exception e) {
                // the text did not match any raw values, now try the other option
                for (IDType b : IDType.values()) {
                    if (text.equalsIgnoreCase(b.toString())) {
                        return b;
                    }
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

    public String toJson() {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"value\":")
                .append("\"")
                .append(this.name())
                .append("\",")
                .append("\"name\":\"")
                .append(this.getName())
                .append("\"},")
                .append("\n");
        return sb.toString();
    }
}
