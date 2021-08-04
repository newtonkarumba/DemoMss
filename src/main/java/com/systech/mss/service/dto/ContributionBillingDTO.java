package com.systech.mss.service.dto;

import com.systech.mss.util.Ignore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ContributionBillingDTO {

    private Long id;

    @Ignore
    private ContributionBillingBatchDto batch;

    @Ignore
    private Long schemeId;

    @Ignore
    private Long sponsorId;

    @Ignore
    private Long memberId;
    @Ignore
    private Long staffNo;

    private BigDecimal salary;

    @Temporal(TemporalType.DATE)
    private Date salaryDate;
    @Ignore
    private BigDecimal er;
    @Ignore
    private BigDecimal ee;
    @Ignore
    private BigDecimal avc;
    @Ignore
    private BigDecimal avcer;
    @Ignore
    private BigDecimal groupLife;
    @Ignore
    private BigDecimal adminFeesWithoutTax;
    @Ignore
    private BigDecimal taxOnAdminFees;
    @Ignore
    private BigDecimal adminFees; //with tax
    @Ignore
    private BigDecimal penaltyPayment;
    @Ignore
    private BigDecimal brokerageFee;
    @Ignore
    private BigDecimal augmentary;
    @Ignore
    private BigDecimal erValidated;
    @Ignore
    private BigDecimal eeValidated;
    @Ignore
    private BigDecimal avcValidated;
    @Ignore
    private BigDecimal avcerValidated;
    @Ignore
    private BigDecimal groupLifeValidated;
    @Ignore
    private BigDecimal adminFeesValidated;
    @Ignore
    private BigDecimal adminFeesWithoutTaxValidated;
    @Ignore
    private BigDecimal taxOnAdminFeesValidated;
    @Ignore
    private BigDecimal penaltyPaymentValidated;
    @Ignore
    private BigDecimal brokerageFeeValidated;
    @Ignore
    private BigDecimal augmentaryValidated;
    @Ignore
    private BigDecimal salaryValidated;
    @Ignore
    private BigDecimal totalContribution;

    @Ignore
    private String exception;

    @Ignore
    private String membershipNo;

    @Ignore
    private Long memberNo;
    @Ignore
    private String memberIdEtl;

    @Ignore
    private String memberName;


//    @Ignore
//    private String idNumber;

    @Ignore
    private Integer counter;
    @Ignore
    private String dob;

    @Ignore
    private String ssnitNumber;
    @Ignore
    private String finalPath;
    @Ignore
    private String status;
    @Ignore
    private String comments;
    @Ignore
    private BigDecimal severanceDue;


}
