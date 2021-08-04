/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.systech.mss.service.dto;

import com.systech.mss.service.enums.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MemberUploadDTO {
    private String id;
    private Long memberNo;
    private Long companyId;
    private Long schemeId;
    private String staffNo;
    private String idNo;
    private String pinNo;
    private String policyNo;
    private String partyRefNo;
    private String nationalPenNo;
    private String postalAddress;
    private String fixedPhone;
    private String cellPhone;
    private String secondaryPhone;
    private String fax;
    private String telex;
    private String building;
    private String road;
    private String town;
    private String residentialAddress;
    private String region;
    private String subRegion;
    private String depot;
    private String country;
    private String payrollNo;
    private String department;
    private String designation;
    private String title;
    private String dob;
    private String surname;
    private String firstname;
    private String othernames;
    private BigDecimal currentAnnualPensionableSalary;
    private String gender;
    private String maritalStatus;
    private MembershipStatus mbshipStatus;
    private String dateOfEmployment;
    private String dateJoinedScheme;
    private long mclassId;
    private YesNo datesConfirmed;
    private YesNo disabled;
    private String joinedPrevScheme;
    private Double avcRate;
    private String idType;
    private long profileId;
    private long jobGradeId;
    private String exitDate;
    private String reasonForExit;
    private String email;
    private String employerProdNo;
    private long sponsorId;
    private  String exception;



    private String placeOfBirthDistrictId;
    private String placeOfBirthTAId;
    private String placeOfBirthVillageId;
    private String permanentDistrictId;
    private String permanentTAId;
    private String permanentVillageId;





}
