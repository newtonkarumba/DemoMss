package com.systech.mss.config;

import org.json.JSONObject;

import javax.ejb.Local;

@Local
public interface ApiEJB {
    JSONObject checkIfIdNumberExists(String sponsorId, String idType, String idnumber, String isSponsorID);
    boolean uploadMemberDocument(String params);

    boolean saveBatchPotentialMember(String params);
}
