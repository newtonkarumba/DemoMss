package com.systech.mss.controller.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.systech.mss.util.Ignore;

import java.io.Serializable;
import java.util.Date;

public class PotentialMemberVM implements Serializable {

    @JsonProperty("tier3")
    public String  getTier3() {
        return this.tier3;
    }

    public void setTier3(String tier3) {
        this.tier3 = tier3;
    }

    String tier3;

    @JsonProperty("sponsorId")
    public int getSponsorId() {
        return this.sponsorId;
    }

    public void setSponsorId(int sponsorId) {
        this.sponsorId = sponsorId;
    }

    int sponsorId;

    @JsonProperty("schemeId")
    public int getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(int schemeId) {
        this.schemeId = schemeId;
    }

    int schemeId;

    @JsonProperty("title")
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String title;

    @JsonProperty("lastname")
    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    String lastname;

    @JsonProperty("firstname")
    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    String firstname;

    @JsonProperty("othernames")
    public String getOthernames() {
        return this.othernames;
    }

    public void setOthernames(String othernames) {
        this.othernames = othernames;
    }

    String othernames;

    @JsonProperty("gender")
    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    String gender;

    @JsonProperty("maritalStatus")
    public String getMaritalStatus() {
        return this.maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    String maritalStatus;

    @JsonProperty("maritalStatusAtDoe")
    public String getMaritalStatusAtDoe() {
        return this.maritalStatusAtDoe;
    }

    public void setMaritalStatusAtDoe(String maritalStatusAtDoe) {
        this.maritalStatusAtDoe = maritalStatusAtDoe;
    }

    String maritalStatusAtDoe;

    @JsonProperty("dateOfBirth")
    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    String dateOfBirth;

    @JsonProperty("idNumber")
    public String getIdNumber() {
        return this.idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    String idNumber;

    @JsonProperty("otherIdNo")
    public String getOtherIdNo() {
        return this.otherIdNo;
    }

    public void setOtherIdNo(String otherIdNo) {
        this.otherIdNo = otherIdNo;
    }

    String otherIdNo;

    @JsonProperty("pinNo")
    public String getPinNo() {
        return this.pinNo;
    }

    public void setPinNo(String pinNo) {
        this.pinNo = pinNo;
    }

    String pinNo;

    @JsonProperty("phoneNumber")
    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    String phoneNumber;

    @JsonProperty("secondaryPhoneNumber")
    public String getSecondaryPhoneNumber() {
        return this.secondaryPhoneNumber;
    }

    public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
        this.secondaryPhoneNumber = secondaryPhoneNumber;
    }

    String secondaryPhoneNumber;

    @JsonProperty("fixedPhone")
    public String getFixedPhone() {
        return this.fixedPhone;
    }

    public void setFixedPhone(String fixedPhone) {
        this.fixedPhone = fixedPhone;
    }

    String fixedPhone;

    @JsonProperty("otherContacts")
    public String getOtherContacts() {
        return this.otherContacts;
    }

    public void setOtherContacts(String otherContacts) {
        this.otherContacts = otherContacts;
    }

    String otherContacts;

    @JsonProperty("postalAddress")
    public String getPostalAddress() {
        return this.postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    String postalAddress;

    @JsonProperty("building")
    public String getBuilding() {
        return this.building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    String building;

    @JsonProperty("road")
    public String getRoad() {
        return this.road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    String road;

    @JsonProperty("town")
    public String getTown() {
        return this.town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    String town;

    @JsonProperty("nationality")
    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    String nationality;

    @JsonProperty("country")
    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    String country;

    @JsonProperty("emailAddress")
    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    String emailAddress;

    @JsonProperty("residentialAddress")
    public String getResidentialAddress() {
        return this.residentialAddress;
    }

    public void setResidentialAddress(String residentialAddress) {
        this.residentialAddress = residentialAddress;
    }

    String residentialAddress;

    @JsonProperty("placeOfBirthDistrictName")
    public Object getPlaceOfBirthDistrictName() {
        return this.placeOfBirthDistrictName;
    }

    public void setPlaceOfBirthDistrictName(Object placeOfBirthDistrictName) {
        this.placeOfBirthDistrictName = placeOfBirthDistrictName;
    }

    Object placeOfBirthDistrictName;

    @JsonProperty("placeOfBirthTAName")
    public Object getPlaceOfBirthTAName() {
        return this.placeOfBirthTAName;
    }

    public void setPlaceOfBirthTAName(Object placeOfBirthTAName) {
        this.placeOfBirthTAName = placeOfBirthTAName;
    }

    Object placeOfBirthTAName;

    @JsonProperty("villageName")
    public Object getVillageName() {
        return this.villageName;
    }

    public void setVillageName(Object villageName) {
        this.villageName = villageName;
    }

    Object villageName;

    @JsonProperty("permanentDistrictName")
    public Object getPermanentDistrictName() {
        return this.permanentDistrictName;
    }

    public void setPermanentDistrictName(Object permanentDistrictName) {
        this.permanentDistrictName = permanentDistrictName;
    }

    Object permanentDistrictName;

    @JsonProperty("permanentTAName")
    public Object getPermanentTAName() {
        return this.permanentTAName;
    }

    public void setPermanentTAName(Object permanentTAName) {
        this.permanentTAName = permanentTAName;
    }

    Object permanentTAName;

    @JsonProperty("permanentVillageName")
    public Object getPermanentVillageName() {
        return this.permanentVillageName;
    }

    public void setPermanentVillageName(Object permanentVillageName) {
        this.permanentVillageName = permanentVillageName;
    }

    Object permanentVillageName;

    @JsonProperty("dateJoinedScheme")
    public String getDateJoinedScheme() {
        return this.dateJoinedScheme;
    }

    public void setDateJoinedScheme(String dateJoinedScheme) {
        this.dateJoinedScheme = dateJoinedScheme;
    }

    String dateJoinedScheme;

    @JsonProperty("companyId")
    public int getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    int companyId;

    @JsonProperty("mclassId")
    public int getMclassId() {
        return this.mclassId;
    }

    public void setMclassId(int mclassId) {
        this.mclassId = mclassId;
    }

    int mclassId;

    @JsonProperty("staffNo")
    public String getStaffNo() {
        return this.staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    String staffNo;

    @JsonProperty("nationalPenNo")
    public String getNationalPenNo() {
        return this.nationalPenNo;
    }

    public void setNationalPenNo(String nationalPenNo) {
        this.nationalPenNo = nationalPenNo;
    }

    String nationalPenNo;

    @JsonProperty("designation")
    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    String designation;

    @JsonProperty("department")
    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    String department;

    @JsonProperty("monthlySalary")
    public int getMonthlySalary() {
        return this.monthlySalary;
    }

    public void setMonthlySalary(int monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    int monthlySalary;

    @JsonProperty("dateOfAppointment")
    public String getDateOfAppointment() {
        return this.dateOfAppointment;
    }

    public void setDateOfAppointment(String dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    String dateOfAppointment;

}
