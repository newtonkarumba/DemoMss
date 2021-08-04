
package com.systech.mss.controller.vm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.util.Ignore;
import lombok.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDetailsVM {
    @Ignore
    private String fax;
    @Ignore
    private String telex;
    @Ignore
    private String age;
    @Ignore
    private String payrollNo;
    @Ignore
    private String photoUrl;
    @Ignore
    private String benId;
    @Ignore
    private String unitization;
    @Ignore
    private String nhif;

    @Ignore
    private String building;
    @Ignore
    private String cellPhone;
    @Ignore
    private String costCenter;
    @Ignore
    private String costCenterId;
    @Ignore
    private String country;
    @Ignore
    private String currentAnnualPensionableSalary;
    @Ignore
    private String dateJoinedScheme;

    @Ignore
    private String joinedPrevScheme;
    @Ignore
    private String dateOfAppointment;
    @Ignore
    private String dateOfEmployment;
    @Ignore
    private String department;
    @Ignore
    private String depot;
    @Ignore
    private String designation;
    @Ignore
    private String dob;
    @Ignore
    private String email;
    @Ignore
    private String facebook;
    @Ignore
    private String firstname;
    @Ignore
    private String fixedPhone;
    @Ignore
    private String gender;
    @Ignore
    private String googlePlus;
    @Ignore
    private String id;
    @Ignore
    private String idNo;
    @Ignore
    private String linkedIn;
    @Ignore
    private String maidenName;
    @Ignore
    private String maritalStatus;
    @Ignore
    private String maritalStatusAtDoe;
    @Ignore
    private String maritalStatusAtDoeName;
    @Ignore
    private String maritalStatusName;
    @Ignore
    private String mbio_id;
    @Ignore
    private String mbshipStatus;
    @Ignore
    private String memberNo;
    @Ignore
    private String memberclassId;
    @Ignore
    private String ssnit;
    @Ignore
    private String memberclassname;
    @Ignore
    private String membershipNo;
    @Ignore
    private String monthlySalary;
    @Ignore
    private String name;
    @Ignore
    private String nationalPenNo;
    @Ignore
    private String nationality;
    @Ignore
    private String otherContacts;
    @Ignore
    private String otherIdNo;
    @Ignore
    private String otherIdType;
    @Ignore
    private String othernames;
    @Ignore
    private String partnerNumber;
    @Ignore
    private String partyrefno;
    @Ignore
    private String permanentDistrictName;
    @Ignore
    private String permanentTAId;
    @Ignore
    private String permanentTAName;
    @Ignore
    private String permanentVillageId;
    @Ignore
    private String permanentVillageName;
    @Ignore
    private String pinNo;
    @Ignore
    private String placeOfBirthDistrictId;
    @Ignore
    private String placeOfBirthDistrictName;
    @Ignore
    private String placeOfBirthTAId;
    @Ignore
    private String placeOfBirthTAName;
    @Ignore
    private String pmtDistrictId;
    @Ignore
    private String policyNo;
    @Ignore
    private String postalAddress;
    @Ignore
    private String region;
    @Ignore
    private String residentialAddress;
    @Ignore
    private String retirementAge;
    @Ignore
    private String retirementDate;
    @Ignore
    private String road;
    @Ignore
    private String schemeId;
    @Ignore
    private String secondaryPhone;
    @Ignore
    private String staffNo;
    @Ignore
    private String subregion;
    @Ignore
    private String surname;
    @Ignore
    private String terminateCover;
    @Ignore
    private String title;
    @Ignore
    private String town;
    @Ignore
    private String twitter;
    @Ignore
    private String villageId;
    @Ignore
    private String villageName;

    public static MemberDetailsVM from(Object membersDetails) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(membersDetails);
            return objectMapper.readValue(jsonString, MemberDetailsVM.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDob() {
        try {
            if(dob.equals("")){
                return "1970-01-01";
            }
            SimpleDateFormat formatter=new SimpleDateFormat("MMM dd, yyyy");
            Date date=formatter.parse(dob);
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            return formatter1.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "1970-01-01";
        }
    }

    public String getDateJoinedScheme() {
        try {
            if(dateJoinedScheme.equals("")){
                return "1970-01-01";
            }
            SimpleDateFormat formatter=new SimpleDateFormat("MMM dd, yyyy");
            Date date=formatter.parse(dateJoinedScheme);
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            return formatter1.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "1970-01-01";
        }
    }
}
