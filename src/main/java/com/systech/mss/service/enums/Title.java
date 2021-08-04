package com.systech.mss.service.enums;

public enum  Title {
    MR("Mr."),
    MRS("Mrs."),
    MISS("Miss."),
    MS("Ms."),
    DR("Dr."),
    PROF("Prof."),
    REV("Rev."),
    ENG("Eng."),
    VIRTUAL("Virt."),
    HON("Hon."),
    UNKNOWN("");

    private String name;

    Title(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }



}
