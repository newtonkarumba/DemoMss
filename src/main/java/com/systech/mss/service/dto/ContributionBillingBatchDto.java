package com.systech.mss.service.dto;

import com.systech.mss.util.Ignore;
import lombok.*;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ContributionBillingBatchDto {

    private Long id;

    private Month contributionMonth;


    private Integer contributionYear;


    private Date billDate;


    private Date expectedPaymentDate;


//    private Date paymentDeadlineDate;


    private BigDecimal amount;


    private BigDecimal amountValidated;


    private BigDecimal validationDifference;


    private Integer countTransactions;


    private Long sponsorId;

    private Date datePrepared;


    private Date dateCertified;


    private Date dateApproved;


    private Long preparedById;


    private Long certifiedById;


    private Long approvedById;


    private String billRefNo;


    private Long invoiceId;


    private Long contributionBatchId;


    private Long billMailSentById;


    private Date dateMailSent;


    private Long billValidatedById;


    private Date dateBillValidated;


    private Long validationMailSentById;


    private Date dateValidationMailSent;

    @Ignore
    private String sponsorName;

    @Ignore
    private String preparedBy;

    @Ignore
    private String certifiedBy;

    @Ignore
    private String approvedBy;

    @Ignore
    public int costCenterId;

    @Ignore
    public String billingSource;

    @Ignore
    public String paymentRef;


}
