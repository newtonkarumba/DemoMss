package com.systech.mss.controller.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.systech.mss.seurity.DateUtils;
import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;


@Embeddable
@NoArgsConstructor
@Setter
@Getter
@ToString
public class MemberEditVM implements Serializable {
    @Ignore
    @Transient
    @JsonProperty
            ("member_Id")
    Long member_Id;

    @Ignore
    @Transient
    @JsonProperty
            ("mbshipStatus")
    String mbshipStatus;

    @Ignore
    @Column
    @JsonProperty
            ("schemeId")
    Long schemeId;

    @Ignore
    @Column
    @JsonProperty
            ("title")
    String title;

    @Ignore
    @Column
    @JsonProperty
            ("surname")
    String surname;

    @Ignore
    @Column
    @JsonProperty
            ("firstname")
    String firstname;

    @Ignore
    @Column
    @JsonProperty
            ("othernames")
    String othernames;

    @Ignore
    @Column
    @JsonProperty
            ("gender")
    String gender;

    @Ignore
    @Column
    @JsonProperty
            ("maritalStatus")
    String maritalStatus;

    @Ignore
    @Column
    @JsonProperty
            ("maritalStatusAtDoeName")
    String maritalStatusAtDoeName;

    @Ignore
    @Column
    @JsonProperty
            ("dob")
    Date dob;

    @Ignore
    @Column
    @JsonProperty
            ("idNo")
    String idNo;

    @Ignore
    @Column
    @JsonProperty
            ("pinNo")
    String pinNo;


    @Ignore
    @Column
    @JsonProperty
            ("region")
    String region;

    @Ignore
    @Column
    @JsonProperty
            ("fixedPhone")
    String fixedPhone;

    @Ignore
    @Column
    @JsonProperty
            ("secondaryPhone")
    String secondaryPhone;

    @Ignore
    @Column
    @JsonProperty
            ("cellPhone")
    String cellPhone;

    @Ignore
    @Column
    @JsonProperty
            ("email")
    String email;

    @Ignore
    @Column
    @JsonProperty
            ("otherContacts")
    String otherContacts;

    @Ignore
    @Column
    @JsonProperty
            ("postalAddress")
    String postalAddress;

    @Ignore
    @Column
    @JsonProperty
            ("building")
    String building;

    @Ignore
    @Column
    @JsonProperty
            ("road")
    String road;

    @Ignore
    @Column
    @JsonProperty
            ("town")
    String town;

    @Ignore
    @Column
    @JsonProperty
            ("nationality")
    String nationality;

    @Ignore
    @Column
    @JsonProperty
            ("country")
    String country;

    @Ignore
    @Column
    @JsonProperty
            ("residentialAddress")
    String residentialAddress;

    @Ignore
    @Column
    @JsonProperty
            ("placeOfBirthDistrictName")
    String placeOfBirthDistrictName;

    @Ignore
    @Column
    @JsonProperty
            ("placeOfBirthTAName")
    String placeOfBirthTAName;

    @Ignore
    @Column
    @JsonProperty
            ("villageName")
    String villageName;

    @Ignore
    @Column
    @JsonProperty
            ("permanentDistrictName")
    String permanentDistrictName;

    @Ignore
    @Column
    @JsonProperty
            ("permanentTAName")
    String permanentTAName;

    @Ignore
    @Column
    @JsonProperty
            ("permanentVillageName")
    String permanentVillageName;

    @Ignore
    @Column
    @JsonProperty
            ("staffNo")
    String staffNo;

    @Ignore
    @Column
    @JsonProperty
            ("nationalPenNo")
    String nationalPenNo;

    @Ignore
    @Column
    @JsonProperty
            ("designation")
    String designation;

    @Ignore
    @Column
    @JsonProperty
            ("department")
    String department;

    @Ignore
    @Column
    @JsonProperty
            ("dateOfAppointment")
    String dateOfAppointment;

    @Ignore
    @Column
    @JsonProperty
            ("monthlySalary")
    double monthlySalary;

    @Ignore
    @Column
    @JsonProperty
            ("memberNo")
    String memberNo;

    @Ignore
    @Column
    @JsonProperty("dateJoinedScheme")

    Date dateJoinedScheme;

    @Transient
    String dobString;

    public String getDobString() {
        return DateUtils.shortDate(getDob());
    }

    @Ignore
    @Column
    public String otherIdNo;

    @Ignore
    @Column
    public String membershipNo;

    @Ignore
    @Column
    public String policyNo;

    @Ignore
    @Column
    public String depot;

    @Ignore
    @Column
    public String jobGradeId;

    @Ignore
    @Column
    public String contractEndDate;

    @Ignore
    @Column
    public String partyRefNo;

    @Ignore
    @Column
    public String companyId;

    @Ignore
    @Column
    public String mclassId;

    @Ignore
    @Column
    String sponsorId;

}
