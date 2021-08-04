package com.systech.mss.controller.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ReasonForExitDocument implements Serializable {
    @JsonProperty("id")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String id;

    @JsonProperty("mandatory")
    public String getMandatory() {
        return this.mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    String mandatory;

    @JsonProperty("checklistName")
    public String getChecklistName() {
        return this.checklistName;
    }

    public void setChecklistName(String checklistName) {
        this.checklistName = checklistName;
    }

    String checklistName;

    @JsonProperty("effectiveDate")
    public String getEffectiveDate() {
        return this.effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    String effectiveDate;

    @JsonProperty("reasonForExit")
    public String getReasonForExit() {
        return this.reasonForExit;
    }

    public void setReasonForExit(String reasonForExit) {
        this.reasonForExit = reasonForExit;
    }

    String reasonForExit;

    @JsonProperty("appliesForAllExits")
    public String getAppliesForAllExits() {
        return this.appliesForAllExits;
    }

    public void setAppliesForAllExits(String appliesForAllExits) {
        this.appliesForAllExits = appliesForAllExits;
    }

    String appliesForAllExits;

}
