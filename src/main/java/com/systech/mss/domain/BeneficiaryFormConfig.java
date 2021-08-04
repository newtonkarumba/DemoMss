package com.systech.mss.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="beneficiaryFormConfig")
@Getter
@Setter
@NoArgsConstructor
public class BeneficiaryFormConfig extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Column
    boolean benFirstnameReadOnly = false;
    @Column
    boolean benFirstnameHidden = false;
    @Column
    boolean benSurnameReadOnly = false;
    @Column
    boolean benSurnameHidden = false;
    @Column
    boolean benOthernamesReadOnly = false;
    @Column
    boolean benOthernamesHidden = false;
    @Column
    boolean benRelationShipCategoryReadOnly = false;
    @Column
    boolean benRelationShipCategoryHidden = false;
    @Column
    boolean benDobReadOnly = false;
    @Column
    boolean benDobHidden = false;
    @Column
    boolean benRelationshipTypeReadOnly = false;
    @Column
    boolean benRelationshipTypeHidden = false;
    @Column
    boolean benLumpsumEntitlementReadOnly = false;
    @Column
    boolean benLumpsumEntitlementHidden = false;
    @Column
    boolean benMonthlyEntitlementReadOnly = false;
    @Column
    boolean benMonthlyEntitlementHidden = false;
    @Column
    boolean benGenderReadOnly = false;
    @Column
    boolean benGenderHidden = false;
    @Column
    boolean benMaidenNameReadOnly = false;
    @Column
    boolean benMaidenNameHidden = false;
    @Column
    boolean benIdNoReadOnly = false;
    @Column
    boolean benIdNoHidden = false;
    @Column
    boolean benNationalityReadOnly = false;
    @Column
    boolean benNationalityHidden = false;
    @Column
    boolean benAddressPostalAddressReadOnly = false;
    @Column
    boolean benAddressPostalAddressHidden = false;
    @Column
    boolean benCellPhoneReadOnly = false;
    @Column
    boolean benCellPhoneHidden = false;
    @Column
    boolean benDateOfAppointmentReadOnly = false;
    @Column
    boolean benDateOfAppointmentHidden = false;
    @Column
    boolean benNIdReadOnly = false;
    @Column
    boolean benNIdHidden = true;
    @Column
    boolean placeOfBirthDistrictIdReadOnly = false;
    @Column
    boolean placeOfBirthDistrictIdHidden = false;
    @Column
    boolean placeOfBirthTAIdReadOnly = false;
    @Column
    boolean placeOfBirthTAIdHidden = false;
    @Column
    boolean placeOfBirthVillageIdReadOnly = false;
    @Column
    boolean placeOfBirthVillageIdHidden = false;
    @Column
    boolean bankNameReadOnly = false;
    @Column
    boolean bankNameHidden = false;
    @Column
    boolean benBankBranchIdReadOnly = false;
    @Column
    boolean benBankBranchIdHidden = false;
    @Column
    boolean benAccountNameReadOnly = false;
    @Column
    boolean benAccountNameHidden = false;
    @Column
    boolean benAccountNoReadOnly = false;
    @Column
    boolean benAccountNoHidden = false;
    @Column
    boolean permanentDistrictIdReadOnly = false;
    @Column
    boolean permanentDistrictIdHidden = false;
    @Column
    boolean permanentTAIdReadOnly = false;
    @Column
    boolean permanentTAIdHidden = false;
    @Column
    boolean permanentVillageIdReadOnly = false;
    @Column
    boolean permanentVillageIdHidden = false;
    @Column
    boolean fileReadOnly = false;
    @Column
    boolean fileHidden = false;
    @Column
    boolean benGuardianFnReadOnly = false;
    @Column
    boolean benGuardianFnHidden = true;
    @Column
    boolean benGuardianSnReadOnly = false;
    @Column
    boolean benGuardianSnHidden = true;
    @Column
    boolean benGuardianOnReadOnly = false;
    @Column
    boolean benGuardianOnHidden = true;
    @Column
    boolean benGuardianGenderReadOnly = false;
    @Column
    boolean benGuardianGenderHidden = true;
    @Column
    boolean benGuardianEmailReadOnly = false;
    @Column
    boolean benGuardianEmailHidden = true;
    @Column
    boolean benGuardianAddrReadOnly = false;
    @Column
    boolean benGuardianAddrHidden = true;
    @Column
    boolean benGuardianTownReadOnly = false;
    @Column
    boolean benGuardianTownHidden = true;
    @Column
    boolean benGuardianCountryReadOnly = false;
    @Column
    boolean benGuardianCountryHidden = true;
    @Column
    boolean benGuardianRelationReadOnly = false;
    @Column
    boolean benGuardianRelationHidden = true;
    @Column
    boolean saveBeneficiaryHidden = false;
}
