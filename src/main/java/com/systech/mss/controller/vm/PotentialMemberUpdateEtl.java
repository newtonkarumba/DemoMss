package com.systech.mss.controller.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PotentialMemberUpdateEtl implements Serializable {
    @Ignore
    public String id;

    @Ignore
    public String name;

    @Ignore
    public String email;

    @Ignore
    public String dob;

    @Ignore
    public String idNo;

    @Ignore
    public String cellPhone;

    @Ignore
    public String ssnit;

    @Ignore
    public String maidenName;

    @Ignore
    public String father;
    @Ignore
    public String mother;

    @Ignore
    public String fatherAddress;

    @Ignore
    public String motherAddress;

    @Ignore
    public String sponsorName;

    @Ignore
    public String sponsorProdNo;

    @Ignore
    public String sponsorAddress;

    @Ignore
    public String maritalStatus;

    @Ignore
    public String age;

    @Ignore
    public String action;

    @Ignore
    public String town;
    @Ignore
    public String country;

    @Ignore
    public String gender;

    @Ignore
    public String salary;

    @Ignore
    public String hasT3;

    @Ignore
    public String schemeType;



    public Map<String, Object> xtractEtlUpdateMemberFromRequest() {
        try {
            Map<String, Object> jsonObject = new HashMap<>();
            jsonObject.put("potentialMemberId", getId());
            jsonObject.put("salary", getSalary());
            jsonObject.put("hasTier3", getHasT3());
            return jsonObject;
        } catch (Exception je) {
            je.printStackTrace();
        }
        return null;
    }
}
