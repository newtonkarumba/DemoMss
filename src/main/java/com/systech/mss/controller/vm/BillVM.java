package com.systech.mss.controller.vm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.systech.mss.util.Ignore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillVM implements Serializable {
    @Ignore
    public String id;
    @Ignore
    public String amount;
    @Ignore
    public String amountValidated;
    @Ignore
    public String validationDifference;
    @Ignore
    public String countTransactions;
    @Ignore
    public String billDate;
    @Ignore
    public String expectedPaymentDate;
    @Ignore
    public String datePrepared;
    @Ignore
    public String dateCertified;
    @Ignore
    public String dateApproved;
    @Ignore
    public String sponsorId;
    @Ignore
    public String billRefNo;
    @Ignore
    public String contributionMonthYear;
    @Ignore
    public String sponsorName;
    @Ignore
    public String preparer;
    @Ignore
    public String certifier;
    @Ignore
    public String approver;
    @Ignore
    public String certified;
    @Ignore
    public String approved;
    @Ignore
    public String posted;
    @Ignore
    public String invoiceId;
    @Ignore
    public String validated;
    @Ignore
    public String erValidated;
    @Ignore
    public String eeValidated;
    @Ignore
    public String avcerValidated;
    @Ignore
    public String avcValidated;
    @Ignore
    public String groupLifeValidated;
    @Ignore
    public String adminFeesValidated;
    @Ignore
    public String adminFeesWithoutTaxValidated;
    @Ignore
    public String taxOnAdminFeesValidated;
    @Ignore
    public String penaltyPaymentValidated;
    @Ignore
    public String brokerageFeeValidated;
    @Ignore
    public String augmentaryValidated;
    @Ignore
    public String fileName;
    @Ignore
    public String blank;

    public static BillVM from(Object o) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String s= objectMapper.writeValueAsString(o);
            return objectMapper.readValue(s, BillVM.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
