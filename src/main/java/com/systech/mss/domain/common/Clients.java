package com.systech.mss.domain.common;

public enum Clients {
    ETL("ETL"),
    BENCON("BENCON"),
    NICO("NICO"),
    OTHERS("OTHERS");

    private String name;

    Clients(String name) {
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
