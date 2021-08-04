package com.systech.mss.vm.benefitrequest;


import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Embeddable
public class AddBeneficiaryVM implements Serializable {
    @Ignore
    @Column
    private long beneficiaryId;

    @Column
    private long benMemberId;

    @Ignore
    @Column
    private String unknown = getEmptyString(); //DO NOT REMOVE

    @Column
    private String benRelationshipType;

    @Ignore
    @Column
    private String memberName = getEmptyString();

    @Ignore
    @Column
    private String benMaidenName = getEmptyString();

    @Ignore
    @Column
    private String benNationality = getEmptyString();

    @Ignore
    @Column
    private String benCellPhone = getEmptyString();

    @Ignore
    @Column
    private long placeOfBirthDistrictId = 0L;

    @Ignore
    @Column
    private long placeOfBirthTAId = 0L;

    @Ignore
    @Column
    private long placeOfBirthVillageId = 0L;

    @Ignore
    @Column
    private long permanentDistrictId = 0L;

    @Ignore
    @Column
    private long permanentTAId = 0L;

    @Ignore
    @Column
    private long permanentVillageId = 0L;

    @Ignore
    @Column
    private long bankName = 0L;

    @Ignore
    @Column
    private long benBankBranchId = 0L;

    @Ignore
    @Column
    private String benAccountName = getEmptyString();

    @Ignore
    @Column
    private String benAccountNo = getEmptyString();

    @Column
    private String memberNo;

    @Column
    private String benRelationShipCategory;

    @Column
    private String benSurname;

    @Column
    private String memberEmail;

    @Column
    private String schemeId;

    @Column
    private String benFirstname;

    @Column
    private String benOthernames;

    @Ignore
    @Column
    private String benAttachmentname = getEmptyString();

    @Ignore
    @Column
    private String benAttachment = getEmptyString();

    @Ignore
    @Column
    private String name = getEmptyString();

    @Column
    private String benDob;

    @Ignore
    @Column
    private String age = getEmptyString();

    @Column
    private String benGender;

    @Column
    private String benMonthlyEntitlement;

    @Column
    private String benLumpsumEntitlement;

    @Column
    private String benIdNo = String.valueOf(0);

    @Ignore
    @Column
    private String benAddressPostalAddress = getEmptyString();

    @Column
    private String benMaritalStatus;

    @Ignore
    private String benPhysicalCondition = getEmptyString();

    @Ignore
    @Column
    private String benDateOfAppointment = getEmptyString();

    @Ignore
    @Column
    private String benBirthCert = getEmptyString();

    @Ignore
    @Column
    private String benGuardianSn = getEmptyString();

    @Ignore
    @Column
    private String benGuardianFn = getEmptyString();

    @Ignore
    @Column
    private String benGuardianOn = getEmptyString();

    @Ignore
    @Column
    private String benGuardianAddr = getEmptyString();

    @Ignore
    @Column
    private String benGuardianGender = "UNKNOWN";

    @Column
    private String legibilityStatus;

    @Ignore
    @Column
    private String benGuardianRelation = getEmptyString();

    @Column
    public String getName() {
        return getBenFirstname() + " " + getBenSurname();
    }

    @Transient
    public String getEmptyString() {
        return "";
    }

    @Ignore
    @Column
    public String nId;

    @Ignore
    @Column
    public String benGuardianEmail;

    @Ignore
    @Column
    public String benGuardianTown;

    @Ignore
    @Column
    public String benGuardianCountry;

    @Ignore
    @Column
    private String permanentDistrictName;

    @Ignore
    @Column
    private String permanentTAName;

    @Ignore
    @Column
    private String permanentVillageName;

    @Ignore
    @Column
    private String placeOfBirthDistrictName;

    @Ignore
    @Column
    private String placeOfBirthTAName;

    @Ignore
    @Column
    private String placeOfBirthVillageName;

    @Ignore
    @Column
    private String benBankName;

    @Ignore
    @Column
    private String benBankBranchName;

    @Ignore
    @Column
    String sponsorId;
}
