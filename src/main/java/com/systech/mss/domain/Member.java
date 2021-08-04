package com.systech.mss.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.systech.mss.service.enums.Gender;
import com.systech.mss.service.enums.MaritalStatus;
import com.systech.mss.seurity.DateUtils;
import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


@Entity
@Table(name = Member.TB_NAME)
@Getter
@Setter
@NoArgsConstructor
public class Member implements Serializable {

    @Ignore
    private static final long serialVersionUID = 1L;

    @Ignore
    @Transient
    public static final String TB_NAME = "members";

    @Ignore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "othernames")
    private String othernames;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "maritalStatus")
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @Column(name = "dateOfBirth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "iddoc")
    private String identificationDocument;

    @Column(name = "otheridnumber")
    private String identificationDocumentNumber;

    @Column(name = "idNumber")
    private String idNumber;

    @Column(name = "emailAddress")
    private String emailAddress;

    @Column(name = "phoneNumber", unique = true)
    private String phoneNumber;

    @Ignore
    @Column(name = "secondaryPhoneNumber")
    private String secondaryPhoneNumber;

    @Column(name = "residentialAddress")
    private String residentialAddress;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "schemename", nullable = false)
    private String schemeName;

    @Column(name = "sponsorName", nullable = false)
    private String sponsorName;

    @Ignore
    @Column(name = "schemeid")
    private long schemeId;

    @Ignore
    @Column(updatable = false)
    private Date createdAt = Calendar.getInstance().getTime();

    @Ignore
    @Lob
    private String documents;

    @JsonProperty("disabled")
    public String getDisabled() {
        return this.disabled;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    @Ignore
    @Column(name = "disabled")
    String disabled;

    @JsonProperty("postalAddress")
    public String getPostalAddress() {
        return this.postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    @Ignore
    @Column(name = "postalAddress")
    String postalAddress;

    @JsonProperty("road")
    public String getRoad() {
        return this.road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    @Ignore
    @Column(name = "road")
    String road;

    @JsonProperty("nationality")
    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Ignore
    @Column(name = "nationality")
    String nationality;


    @JsonProperty("addressPlaceOfBirth")
    public String getAddressPlaceOfBirth() {
        return this.addressPlaceOfBirth;
    }

    public void setAddressPlaceOfBirth(String addressPlaceOfBirth) {
        this.addressPlaceOfBirth = addressPlaceOfBirth;
    }

    @Ignore
    @Column(name = "addressPlaceOfBirth")
    String addressPlaceOfBirth;

    @JsonProperty("addressTraditionalAuthority")
    public String getAddressTraditionalAuthority() {
        return this.addressTraditionalAuthority;
    }

    public void setAddressTraditionalAuthority(String addressTraditionalAuthority) {
        this.addressTraditionalAuthority = addressTraditionalAuthority;
    }

    @Ignore
    @Column(name = "addressTraditionalAuthority")
    String addressTraditionalAuthority;

    @JsonProperty("addressVillage")
    public String getAddressVillage() {
        return this.addressVillage;
    }

    public void setAddressVillage(String addressVillage) {
        this.addressVillage = addressVillage;
    }

    @Ignore
    @Column(name = "addressVillage")
    String addressVillage;

    @JsonProperty("pobPlaceOfBirth")
    public String getPobPlaceOfBirth() {
        return this.pobPlaceOfBirth;
    }

    public void setPobPlaceOfBirth(String pobPlaceOfBirth) {
        this.pobPlaceOfBirth = pobPlaceOfBirth;
    }

    @Ignore
    @Column(name = "pobPlaceOfBirth")
    String pobPlaceOfBirth;

    @JsonProperty("pobTraditionalAuthority")
    public String getPobTraditionalAuthority() {
        return this.pobTraditionalAuthority;
    }

    public void setPobTraditionalAuthority(String pobTraditionalAuthority) {
        this.pobTraditionalAuthority = pobTraditionalAuthority;
    }

    @Ignore
    @Column(name = "pobTraditionalAuthority")
    String pobTraditionalAuthority;

    @JsonProperty("pobVillage")
    public String getPobVillage() {
        return this.pobVillage;
    }

    public void setPobVillage(String pobVillage) {
        this.pobVillage = pobVillage;
    }

    @Ignore
    @Column(name = "pobVillage")
    String pobVillage;

    @JsonProperty("phmPlaceOfBirth")
    public String getPhmPlaceOfBirth() {
        return this.phmPlaceOfBirth;
    }

    public void setPhmPlaceOfBirth(String phmPlaceOfBirth) {
        this.phmPlaceOfBirth = phmPlaceOfBirth;
    }

    @Ignore
    @Column(name = "phmPlaceOfBirth")
    String phmPlaceOfBirth;

    @JsonProperty("phmTraditionalAuthority")
    public String getPhmTraditionalAuthority() {
        return this.phmTraditionalAuthority;
    }

    public void setPhmTraditionalAuthority(String phmTraditionalAuthority) {
        this.phmTraditionalAuthority = phmTraditionalAuthority;
    }

    @Ignore
    @Column(name = "phmTraditionalAuthority")
    String phmTraditionalAuthority;

    @JsonProperty("phmVillage")
    public String getPhmVillage() {
        return this.phmVillage;
    }

    public void setPhmVillage(String phmVillage) {
        this.phmVillage = phmVillage;
    }

    @Ignore
    @Column(name = "phmVillage")
    String phmVillage;

    @JsonProperty("employed")
    public String getEmployed() {
        return this.employed;
    }

    public void setEmployed(String employed) {
        this.employed = employed;
    }

    @Ignore
    @Column(name = "employed")
    String employed;

    @JsonProperty("staffNo")
    public String getStaffNo() {
        return this.staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    @Ignore
    @Column(name = "staffNo")
    String staffNo;

    @JsonProperty("nssn")
    public String getNssn() {
        return this.nssn;
    }

    public void setNssn(String nssn) {
        this.nssn = nssn;
    }

    @Ignore
    @Column(name = "nssn")
    String nssn;

    @JsonProperty("designation")
    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Ignore
    @Column(name = "designation")
    String designation;

    @JsonProperty("department")
    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Ignore
    @Column(name = "department")
    String department;

    @JsonProperty("dateOfAppointment")
    public String getDateOfAppointment() {
        return this.dateOfAppointment;
    }

    public void setDateOfAppointment(String dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    @Ignore
    @Column(name = "dateOfAppointment")
    String dateOfAppointment;

    @JsonProperty("currentMonthlySalary")
    public String getCurrentMonthlySalary() {
        return this.currentMonthlySalary;
    }

    public void setCurrentMonthlySalary(String currentMonthlySalary) {
        this.currentMonthlySalary = currentMonthlySalary;
    }

    @Ignore
    @Column(name = "currentMonthlySalary")
    String currentMonthlySalary;

    @Ignore
    @Column(name = "posted")
    private boolean posted = false;

    @Ignore
    @Transient
    String acceptTerms;

    @Ignore
    @Column(name = "sponsorId")
    long sponsorId;

    @Ignore
    @Column
    public String pobPlaceOfBirthID;

    @Ignore
    @Column
    public String pobTraditionalAuthorityID;

    @Ignore
    @Column
    public String pobVillageID;

    @Ignore
    @Column
    public String phmPlaceOfBirthID;

    @Ignore
    @Column
    public String phmTraditionalAuthorityID;

    @Ignore
    @Column
    public String phmVillageID;

    @Ignore
    @Column
    public String employerRefNo;


    @Ignore
    @Transient
    String dateOfBirthFormatted;

    public String getDateOfBirthFormatted() {
        return DateUtils.formatDate(getDateOfBirth(), "dd-MM-yyyy");
    }

    @Ignore
    @Transient
    String shortDate;

    @Ignore
    @Transient
    int numAttachedDocs = 0;
}
