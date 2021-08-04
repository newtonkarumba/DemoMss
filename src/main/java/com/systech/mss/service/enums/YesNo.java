package com.systech.mss.service.enums;

public enum YesNo {
    YES("Yes"),
    NO("No");

    private String name;

    YesNo(String name) {
        this.name = name;
    }

    public static YesNo get(String val) {
        return fromString(val);
    }

    public static YesNo fromString(String val) {
        if (val == null || val.equals("")) return null;
        val = val.trim();
        if (val.equalsIgnoreCase("YES")) return YES;
        if (val.equalsIgnoreCase("NO")) return NO;
        return null;
    }

    public static YesNo fromOrdinal(int ord) {
        for (YesNo b : YesNo.values()) {
            if (b.ordinal() == ord) {
                return b;
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

