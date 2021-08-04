
package com.systech.mss.domain;

import com.systech.mss.seurity.DateUtils;
import com.systech.mss.util.Ignore;
import com.systech.mss.util.StringUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.systech.mss.util.StringUtil.toDouble;
import static com.systech.mss.util.StringUtil.toLong;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MssMemberDTO implements Serializable {

    @Ignore
    private Double avcRate;
    @Ignore
    private String building;
    @Ignore
    private String cellPhone;
    @Ignore
    private Long companyId;
    @Ignore
    private String country;
    @Ignore
    private Double currentAnnualPensionableSalary;
    @Ignore
    private String dateJoinedScheme;
    @Ignore
    private String dateOfEmployment;
    @Ignore
    private String datesConfirmed;
    @Ignore
    private String department;
    @Ignore
    private String depot;
    @Ignore
    private String designation;
    @Ignore
    private String disabled;
    @Ignore
    private String dob;
    @Ignore
    private String email;
    @Ignore
    private String employerProdNo;
    @Ignore
    private String exception;
    @Ignore
    private String exitDate;
    @Ignore
    private String fax;
    @Ignore
    private String firstname;
    @Ignore
    private String fixedPhone;
    @Ignore
    private String gender;
    @Ignore
    private Long id;
    @Ignore
    private String idNo;
    @Ignore
    private String idType;
    @Ignore
    private Long jobGradeId;
    @Ignore
    private String joinedPrevScheme;
    @Ignore
    private String maritalStatus;
    @Ignore
    private String mbshipStatus;
    @Ignore
    private Long mclassId;
    @Ignore
    private Long memberNo;
    @Ignore
    private String nationalPenNo;
    @Ignore
    private String othernames;
    @Ignore
    private String partyRefNo;
    @Ignore
    private String payrollNo;
    @Ignore
    private Long permanentDistrictId;
    @Ignore
    private Long permanentTAId;
    @Ignore
    private Long permanentVillageId;
    @Ignore
    private String pinNo;
    @Ignore
    private Long placeOfBirthDistrictId;
    @Ignore
    private Long placeOfBirthTAId;
    @Ignore
    private Long placeOfBirthVillageId;
    @Ignore
    private String policyNo;
    @Ignore
    private String postalAddress;
    @Ignore
    private Long profileId;
    @Ignore
    private String reasonForExit;
    @Ignore
    private String region;
    @Ignore
    private String residentialAddress;
    @Ignore
    private String road;
    @Ignore
    private Long schemeId;
    @Ignore
    private String secondaryPhone;
    @Ignore
    private Long sponsorId;
    @Ignore
    private String staffNo;
    @Ignore
    private String subRegion;
    @Ignore
    private String surname;
    @Ignore
    private String telex;
    @Ignore
    private String title;
    @Ignore
    private String town;

    public MssMemberDTO(Double avcRate, String building, String cellPhone, Long companyId, String country, Double currentAnnualPensionableSalary, String dateJoinedScheme, String dateOfEmployment, String datesConfirmed, String department, String depot, String designation, String disabled, String dob, String email, String employerProdNo, String exception, String exitDate, String fax, String firstname, String fixedPhone, String gender, Long id, String idNo, String idType, Long jobGradeId, String joinedPrevScheme, String maritalStatus, String mbshipStatus, Long mclassId, Long memberNo, String nationalPenNo, String othernames, String partyRefNo, String payrollNo, Long permanentDistrictId, Long permanentTAId, Long permanentVillageId, String pinNo, Long placeOfBirthDistrictId, Long placeOfBirthTAId, Long placeOfBirthVillageId, String policyNo, String postalAddress, Long profileId, String reasonForExit, String region, String residentialAddress, String road, Long schemeId, String secondaryPhone, Long sponsorId, String staffNo, String subRegion, String surname, String telex, String title, String town) {
        this.setAvcRate(avcRate);
        this.setBuilding(building);
        this.setCellPhone(cellPhone);
        this.setCompanyId(companyId);
        this.setCountry(country);
        this.setCurrentAnnualPensionableSalary(currentAnnualPensionableSalary);
        this.setDateJoinedScheme(dateJoinedScheme);
        this.setDateOfEmployment(dateOfEmployment);
        this.setDatesConfirmed(datesConfirmed);
        this.setDepartment(department);
        this.setDepot(depot);
        this.setDesignation(designation);
        this.setDisabled(disabled);
        this.setDob(dob);
        this.setEmail(email);
        this.setEmployerProdNo(employerProdNo);
        this.setException(exception);
        this.setExitDate(exitDate);
        this.setFax(fax);
        this.setFirstname(firstname);
        this.setFixedPhone(fixedPhone);
        this.setGender(gender);
        this.setId(null);
        this.setIdNo(idNo);
        this.setIdType(idType);
        this.setJobGradeId(jobGradeId);
        this.setDateJoinedScheme(dateJoinedScheme);
        this.setMaritalStatus(maritalStatus);
        this.setMbshipStatus(mbshipStatus);
        this.setMclassId(mclassId);
        this.setMemberNo(memberNo);
        this.setNationalPenNo(nationalPenNo);
        this.setOthernames(othernames);
        this.setPartyRefNo(partyRefNo);
        this.setPayrollNo(payrollNo);
        this.setPermanentDistrictId(permanentDistrictId);
        this.setPermanentTAId(permanentTAId);
        this.setPermanentVillageId(permanentVillageId);
        this.setPinNo(pinNo);
        this.setPlaceOfBirthDistrictId(placeOfBirthDistrictId);
        this.setPlaceOfBirthTAId(placeOfBirthTAId);
        this.setPlaceOfBirthVillageId(placeOfBirthVillageId);
        this.setPolicyNo(policyNo);
        this.setPostalAddress(postalAddress);
        this.setProfileId(profileId);
        this.setReasonForExit(reasonForExit);
        this.setRegion(region);
        this.setResidentialAddress(residentialAddress);
        this.setRoad(road);
        this.setSchemeId(schemeId);
        this.setSecondaryPhone(secondaryPhone);
        this.setSponsorId(sponsorId);
        this.setStaffNo(staffNo);
        this.setSubRegion(subRegion);
        this.setSurname(surname);
        this.setTelex(telex);
        this.setTitle(title);
        this.setTown(town);
    }

    public static MssMemberDTO from(Member member) {
        DateFormat format_ = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String dOE = DateUtils.formatDate(member.getDateOfAppointment(), "yyyy-MM-dd"),
                defaultDate = format_.format(new Date());
        MssMemberDTO mssMemberDTO = new MssMemberDTO();
        mssMemberDTO.setAvcRate(null);
        mssMemberDTO.setBuilding("");
        mssMemberDTO.setCellPhone(member.getPhoneNumber());
        mssMemberDTO.setCompanyId(null);
        mssMemberDTO.setCountry(member.getCountry());

        mssMemberDTO.setDateJoinedScheme(defaultDate);
        mssMemberDTO.setDateOfEmployment(dOE);
//        mssMemberDTO.setDatesConfirmed(defaultDate);   //YES/NO
        mssMemberDTO.setDepartment(member.getDepartment());
        mssMemberDTO.setDepot("");
        mssMemberDTO.setDesignation(member.getDesignation());
        mssMemberDTO.setDisabled(StringUtil.defaultValue(member.getDisabled(), "YES").toString().toUpperCase(Locale.getDefault()));
        mssMemberDTO.setDob(format_.format(member.getDateOfBirth()));
        mssMemberDTO.setEmail(member.getEmailAddress());
        mssMemberDTO.setEmployerProdNo(member.getEmployerRefNo());
        mssMemberDTO.setException(null);
//        mssMemberDTO.setExitDate();
        mssMemberDTO.setFax("");
        mssMemberDTO.setFirstname(member.getFirstname());
        mssMemberDTO.setFixedPhone(member.getPhoneNumber());
        mssMemberDTO.setGender(StringUtil.defaultValue(member.getGender().name()).toString());
        mssMemberDTO.setId(null);
        mssMemberDTO.setIdNo(member.getIdNumber());
        String idDoc = member.getIdentificationDocument();
        String idType = idDoc == null || idDoc.isEmpty() ?
                "NATIONAL" : member.getIdentificationDocument();
        mssMemberDTO.setIdType(idType);
//        mssMemberDTO.setJobGradeId();
        mssMemberDTO.setDateJoinedScheme(defaultDate);
        mssMemberDTO.setMaritalStatus(StringUtil.defaultValue(member.getMaritalStatus().name()).toString());
        mssMemberDTO.setMbshipStatus("ACTIVE");
        mssMemberDTO.setMclassId(null);
        mssMemberDTO.setMemberNo(null);
        mssMemberDTO.setNationalPenNo(member.getNssn());
        mssMemberDTO.setOthernames(member.getOthernames());
        mssMemberDTO.setPartyRefNo("");
        mssMemberDTO.setPayrollNo("");
        mssMemberDTO.setPinNo("");
        try {
            mssMemberDTO.setCurrentAnnualPensionableSalary(toDouble(member.getCurrentMonthlySalary()));
            mssMemberDTO.setPermanentDistrictId(toLong(member.getPhmPlaceOfBirthID()));
            mssMemberDTO.setPermanentTAId(Long.parseLong(member.getPhmTraditionalAuthorityID()));
            mssMemberDTO.setPermanentVillageId(toLong(member.getPhmVillageID()));
            mssMemberDTO.setPlaceOfBirthDistrictId(toLong(member.getPobPlaceOfBirthID()));
            mssMemberDTO.setPlaceOfBirthTAId(toLong(member.getPobTraditionalAuthorityID()));
            mssMemberDTO.setPlaceOfBirthVillageId(toLong(member.getPobVillageID()));
        } catch (NumberFormatException ignored) {

        }
        mssMemberDTO.setPolicyNo("");
        mssMemberDTO.setPostalAddress(member.getPostalAddress());
//        mssMemberDTO.setProfileId();
//        mssMemberDTO.setReasonForExit();
        mssMemberDTO.setRegion("");
        mssMemberDTO.setResidentialAddress(member.getResidentialAddress());
        mssMemberDTO.setRoad(member.getRoad());
        mssMemberDTO.setSchemeId(member.getSchemeId());
        mssMemberDTO.setSecondaryPhone(member.getSecondaryPhoneNumber());
        mssMemberDTO.setSponsorId(member.getSponsorId());
        mssMemberDTO.setStaffNo(member.getStaffNo());
        mssMemberDTO.setSubRegion("");
        mssMemberDTO.setSurname(member.getLastname());
        mssMemberDTO.setTelex("");
        mssMemberDTO.setTitle(member.getTitle());
        mssMemberDTO.setTown(member.getCity());
        return mssMemberDTO;
    }
}
