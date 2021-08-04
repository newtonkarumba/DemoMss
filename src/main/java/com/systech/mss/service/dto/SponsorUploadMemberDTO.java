package com.systech.mss.service.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SponsorUploadMemberDTO  implements Serializable {

    private Long id;
    private String mbio_id;
    private String memberNo;
    private String othernames;
    private String membershipNo;
    private String firstname;
    private String surname;
    private String title;
    private String partyRefNo;
    private String partnerNo;
    private String policyNo;
    private String staffNo;
    private String name;
    private String ssnit;
    private String idNumber;
    private String idType;
    private String age;
    private String terminateCover;
    private String pinNo;
    private String postalAddress;
    private String phoneNumber;
    //private String cellPhoneNumber;
    private String secondaryPhoneNumber;
    private String emailAddress;
    private String nationalPenNo;
    private String gender;
    private String department;
    private String dateOfBirth;
    private String dateOfEmployment;
    private String maritalStatus;
    private String dateJoinedScheme;
    private String schemeId;
    private String country;
    private String town;
    private String sponsorId;
    private String sponsorProdNo;
    private String sponsorName;
    private String sponsorAddress;
    private String dateApproved;
    private String dateDeclined;
    private String declined;
    private  String declineReason;
    private  String maidenSurname;
    private  String maidenFirstname;
    private  String maidenOthername;
    private  String father;
    private  String mother;
    private  String motherAddress;
    private  String fatherAddress;
    private  String identificationDetails;
    private  String schemeType;
    private  String nextOfKinName;
    private  String nextOfKinRltship;
    private  String nextOfKinAddress;
    private  String otherIdentificationType;
    private  String otherIdentificationNumber;
    private String annualPensionableSalary;
    private String profile;
    private String region;
    private String subRegion;
    private String county;
    private String depot;
    private String designation;
    private String road;
    private String mbshipStatus;
    private String planType;
    private String updateExceptions;
    private  String hasTier3;
    private  String memberClassName;
    private  String residentialAddress;
    private  String building;
    private  String jobGrade;
    private  String district;
    private  String traditionalAuthorities;
    private  String village;
    private  String parmanentDistrict;
    private  String parmanentTraditionalAuthorities;
    private  String parmanentVillage;

}
