package com.systech.mss.vm.benefitrequest;

import com.systech.mss.domain.YesNo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class BenefitsToFMVM{
    private String benefitCertifiedBy;
    private BigDecimal benefitTotalAmount;
    private long benefitMemberId;
    private String benefitAuthorizedBy;
    private String benefitDateApproved;
    private long benefitReasonId;
    private double benefitAmountPercentage;
    private String benefitBenefitReason;
    private String benefitDeclinedReason;
    private YesNo benefitIsMemberVested;
    private YesNo benefitDoMemberHasMortgage;
    private String benefitDateDeclined;
    private String benefitIsPercentageOrAmount;
    private String benefitDateAuthorized;
    private long benefitVestingPercentage;
    private String benefitApprovedBy;
    private String benefitEmail;
    private String benefitDateCertified;
    private long benefitBenefitId;
    private BigDecimal benefitLiabilityAmount;
    private YesNo benefitDoMemberHasLiability;
    private String benefitBankBranchName;
    private BigDecimal benefitMortgageAmount;
    private String benefitDeclinedBy;
    private String benefitBankName;
    private String benefitMemberNo;
    private String benefitAccountNo;
    private String benefitAccountName;
}
