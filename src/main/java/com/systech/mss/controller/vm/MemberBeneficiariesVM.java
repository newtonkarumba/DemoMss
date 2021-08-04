package com.systech.mss.controller.vm;


import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor
//@ToString
@Getter
@Setter
public class MemberBeneficiariesVM {

    String firstname;

    String membershipNo;

    String exception;

    String gender;

    String hasTrustFund;

    int monthlyEntitlement;

    String memberName;

    String mstatus;

    String trustBalance;

    String taxpin;

    String idNo;

    String nominationFormNo;

    int lumpsumEntitlement;

    String attachment;

    String surname;

    String othernames;

    String beneficiaryNo;

    int id;

    String relationship;

    String birthCert;

    String physicalCondition;

    int memberId;

    String address;

    int lumpsumEntitlementTotal;

    String documentProvided;

    int memberNo;

    String partnerNumber;

    String dob;

    String name;

    String dateOfAppointment;

    String attachmentName;

    String category;

    int age;

    String status;

    String trustFund;

    @Ignore
    String maidenName;

    @Ignore
    String nationality;

    @Ignore
    String cellPhone;

    @Ignore
    String placeOfBirth;

    @Ignore
    String placeOfBirthTraditionalAuthority;

    @Ignore
    String placeOfBirthVillage;

    @Ignore
    String permanentPlaceOfBirth;

    @Ignore
    String permanentTraditionalAuthority;

    @Ignore
    String permanentVillage;

    @Ignore
    String bankName;

    @Ignore
    String bankBranch;

    @Ignore
    String accName;

    @Ignore
    String accnumber;

    @Ignore
    String dobFormatted;

    @Ignore
    String dateOfAppointmentFormatted;

    public String getDobFormatted(){
        try {
            if(getDob().equals("") || getDateOfAppointment().equals("NOT YET APPROVED")){
                return "1970-01-01";
            }
            SimpleDateFormat formatter=new SimpleDateFormat("MMM dd, yyyy");
            Date date=formatter.parse(getDob());
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            return formatter1.format(date);
        } catch (Exception e) {
//            e.printStackTrace();
            return "1970-01-01";
        }
    }

    public String getDateOfAppointmentFormatted(){
        try {
            if(getDateOfAppointment().equals("")|| getDateOfAppointment().equals("NOT YET APPROVED")){
                return "1970-01-01";
            }
            SimpleDateFormat formatter=new SimpleDateFormat("MMM dd, yyyy");
            Date date=formatter.parse(getDateOfAppointment());
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            return formatter1.format(date);
        } catch (Exception e) {
//            e.printStackTrace();
            return "1970-01-01";
        }
    }

}
