package com.systech.mss.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="memberFormConfigs")
@Getter
@Setter
@NoArgsConstructor
public class MemberFormConfigs extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column
    boolean membershipStatusReadOnly = false;
    @Column
    boolean membershipStatusHidden = true;
    @Column
    boolean schemeIdReadOnly = false;
    @Column
    boolean schemeIdHidden = true;
    @Column
    boolean salutationReadOnly = false;
    @Column
    boolean salutationHidden = false;
    @Column
    boolean surnameReadOnly = false;
    @Column
    boolean surnameHidden = false;
    @Column
    boolean firstnameReadOnly = false;
    @Column
    boolean firstnameHidden = false;
    @Column
    boolean othernamesReadOnly = false;
    @Column
    boolean othernamesHidden = false;
    @Column
    boolean genderReadOnly = false;
    @Column
    boolean genderHidden = false;
    @Column
    boolean maritalStatusReadOnly = false;
    @Column
    boolean maritalStatusHidden = false;
    @Column
    boolean maritalStatusAtEntryReadOnly = false;
    @Column
    boolean maritalStatusAtEntryHidden = false;
    @Column
    boolean dobReadOnly = false;
    @Column
    boolean dobHidden = false;
    @Column
    boolean idNoReadOnly = false;
    @Column
    boolean idNoHidden = false;
    @Column
    boolean otherIdNoReadOnly = false;
    @Column
    boolean otherIdNoHidden = true;
    @Column
    boolean taxPinNoReadOnly = false;
    @Column
    boolean taxPinNoHidden = false;
    @Column
    boolean fixedPhoneReadOnly = false;
    @Column
    boolean fixedPhoneHidden = false;
    @Column
    boolean secondaryPhoneReadOnly = false;
    @Column
    boolean secondaryPhoneHidden = false;
    @Column
    boolean cellPhoneReadOnly = false;
    @Column
    boolean cellPhoneHidden = false;
    @Column
    boolean emailReadOnly = false;
    @Column
    boolean emailHidden = false;
    @Column
    boolean otherContactsReadOnly = false;
    @Column
    boolean otherContactsHidden = false;
    @Column
    boolean postalAddressReadOnly = false;
    @Column
    boolean postalAddressHidden = false;
    @Column
    boolean buildingReadOnly = false;
    @Column
    boolean buildingHidden = false;
    @Column
    boolean roadReadOnly = false;
    @Column
    boolean roadHidden = false;
    @Column
    boolean townReadOnly = false;
    @Column
    boolean townHidden = false;
    @Column
    boolean nationalityReadOnly = false;
    @Column
    boolean nationalityHidden = false;
    @Column
    boolean countryReadOnly = false;
    @Column
    boolean countryHidden = false;
    @Column
    boolean residentialAddressReadOnly = false;
    @Column
    boolean residentialAddressHidden = false;
    @Column
    boolean placeOfBirthDistrictNameReadOnly = false;
    @Column
    boolean placeOfBirthDistrictNameHidden = false;
    @Column
    boolean placeOfBirthTANameReadOnly = false;
    @Column
    boolean placeOfBirthTANameHidden = false;
    @Column
    boolean villageNameReadOnly = false;
    @Column
    boolean villageNameHidden = false;
    @Column
    boolean permanentDistrictNameReadOnly = false;
    @Column
    boolean permanentDistrictNameHidden = false;
    @Column
    boolean permanentTANameReadOnly = false;
    @Column
    boolean permanentTANameNameHidden = false;
    @Column
    boolean permanentVillageNameReadOnly = false;
    @Column
    boolean permanentVillageNameHidden = false;
    @Column
    boolean staffNoReadOnly = false;
    @Column
    boolean staffNoHidden = false;
    @Column
    boolean nationalPenNoReadOnly = false;
    @Column
    boolean nationalPenNoHidden = false;
    @Column
    boolean designationReadOnly = false;
    @Column
    boolean designationHidden = false;
    @Column
    boolean departmentReadOnly = false;
    @Column
    boolean departmentHidden = false;
    @Column
    boolean dateOfAppointmentReadOnly = false;
    @Column
    boolean dateOfAppointmentHidden = false;
    @Column
    boolean jobGradeIdReadOnly = false;
    @Column
    boolean jobGradeIdHidden = true;
    @Column
    boolean monthlySalaryReadOnly = false;
    @Column
    boolean monthlySalaryHidden = false;
    @Column
    boolean contractEndDateReadOnly = false;
    @Column
    boolean contractEndDateHidden = true;
    @Column
    boolean memberNoReadOnly = false;
    @Column
    boolean memberNoHidden = false;
    @Column
    boolean membershipNoReadOnly = false;
    @Column
    boolean membershipNoHidden = true;
    @Column
    boolean partyRefNoReadOnly = false;
    @Column
    boolean partyRefNoHidden = true;
    @Column
    boolean dateJoinedSchemeReadOnly = false;
    @Column
    boolean dateJoinedSchemeHidden = false;
    @Column
    boolean companyIdReadOnly = false;
    @Column
    boolean companyIdHidden = true;
    @Column
    boolean mclassIdReadOnly = false;
    @Column
    boolean mclassIdHidden = true;
    @Column
    boolean policyNoReadOnly = false;
    @Column
    boolean policyNoHidden = true;
    @Column
    boolean regionReadOnly = false;
    @Column
    boolean regionHidden = true;
    @Column
    boolean depotReadOnly = false;
    @Column
    boolean depotHidden = true;
    @Column
    boolean submitFormHidden = false;
    @Column
    boolean fileHidden = false;
}
