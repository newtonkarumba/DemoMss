package com.systech.mss.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.systech.mss.util.Ignore;
import lombok.*;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class XeContributionDTO {
    private Long id;

    @Ignore
    private ContributionBillingBatchDto batch;
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
    private String exception;

    @Ignore
    private Long memberNo;

    @Ignore
    private String memberName;

    @Ignore
    private Integer counter;
    @Ignore
    public String nationalIdNo;
    @Ignore
    public String surname;
    @Ignore
    public String firstName;
    @Ignore
    public String otherNames;
    @Ignore
    public String newMember;
    @Ignore
    public double erRate;
    @Ignore
    public double eeRate;
    @Ignore
    public double avcRate;
    @Ignore
    public double adminFeesRate;
    @Ignore
    public double glaRate;
    @Ignore
    public double brokerRate;
    @Ignore
    public double adminFeesVatRate;
    @Ignore
    public String adminFeesInclusiveOfVat;
    @Ignore
    public String vatAdminCalculationFormula;

    /**
     * IN ETL NOT IN OTHERS
     */
    @Ignore
    @JsonIgnore
    private Long schemeId;
    @Ignore
    @JsonIgnore
    private Long sponsorId;
    @Ignore
    @JsonIgnore
    private BigDecimal totalContribution;
    @Ignore
    @JsonIgnore
    private String memberIdEtl;
    @Ignore
    @JsonIgnore
    private String dob;
    @Ignore
    @JsonIgnore
    private String ssnitNumber;
    @Ignore
    @JsonIgnore
    private String finalPath;
    @Ignore
    @JsonIgnore
    private String status;
    @Ignore
    @JsonIgnore
    private String comments;
    @Ignore
    @JsonIgnore
    private BigDecimal severanceDue;
    //END IN ETL

}
