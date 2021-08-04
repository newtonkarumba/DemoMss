package com.systech.mss.domain;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "benefitrequest")
@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class BenefitRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String memberNo;

    @Column
    private Long memberId;

    @Column
    private Long mssUserId=0L;

    @Column
    private String dob;

    @Column
    private String email;

    @Column
    private String benefitReason;

    @Column
    private Long benefitReasonId;

    @Column
    private String paymentOption;

    @Column
    private double totalAmount=0.0;

    @Column
    private double amountPercentage=0.0;

    @Column
    private String earlyRetirementOption;

    @Column
    private String overseasCountry;

    @Column
    private String overseasAddress;

    @Column
    private String overseasTelephone;

    @Column
    private String overseasFax;

    @Column
    private String overseasEmail;

    @Column
    private String overseasBank;

    @Column
    private String overseasIbanDetails;

    @Column
    private String overseasSwiftCode;

    @Column
    private long staffPercentagePerVestingRule=0l;

    @Column
    private String employeeOutstandingLoan;

    @Column
    private String employeeOutstandingLoanAmount;

    @Column
    private String staffContribution;

    @Column
    private LocalDateTime dateApproved;

    @Column
    private boolean approved;

    @Column
    private boolean declined;

    @Column
    private boolean certify;

    @Column
    private LocalDateTime dateCertified;

    @Column
    private boolean authorize;

    @Column
    private boolean authorizeByCrm=false;

    @Column
    private LocalDateTime dateAuthorize;

    @Column
    private Long schemeId;

    @Lob
    private String declineReason;

    @Column
    private boolean employerHasEdited;

    @Enumerated(EnumType.STRING)
    private Status status=Status.PENDING;

    @Column
    private LocalDateTime dateDeclined;


    @Column
    private LocalDateTime dateApprovedByCrm;

    @Column(name = "date_created")
    private LocalDateTime dateCreated=LocalDateTime.now();

    @Column
    private long sponsorId;

    @Column
    private String bankName;

    @Column
    private String bankBranchName;

    @Column
    private String accountName;

    @Column
    private String accountNumber;

    @Column(name = "submitted")
    @Enumerated(EnumType.STRING)
    private YesNo submitted=YesNo.NO;

    @Column
    private String isPercentageOrAmount="";

    @Column
    private boolean sendToXi;

    @Column
    private String hasMortagageOrNo;

    @Column
    private BigDecimal mortageLoan;

    @Column
    private String medicalIssue;

    @Lob
    private String medicalExplanation;

    @Column
    boolean requiresDocuments=false;

    @Column
    boolean documentsUploaded=false;

    @Column(name = "is_edited")
    @Enumerated(EnumType.STRING)
    private YesNo isEdited=YesNo.NO;


    @Column(name = "approved_by_member")
    @Enumerated(EnumType.STRING)
    private YesNo isApprovedByMember=YesNo.NO;

    @Column(name = "posted_to_fm")
    @Enumerated(EnumType.STRING)
    private YesNo isPostedToFm=YesNo.NO;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "certifiedById")
    @JsonIdentityReference(alwaysAsId = true)
    @Setter(AccessLevel.NONE)
    private User certifiedBy;

    @JsonProperty("certifiedById")
    public void setCertifiedBy(long certifiedById) {
        certifiedBy = User.fromId(certifiedById);
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "approvedById")
    @JsonIdentityReference(alwaysAsId = true)
    @Setter(AccessLevel.NONE)
    private User approvedBy;

    @JsonProperty("approvedById")
    public void setApprovedById(long approvedById) {
        approvedBy = User.fromId(approvedById);
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "declinedById")
    @JsonIdentityReference(alwaysAsId = true)
    @Setter(AccessLevel.NONE)
    private User declinedBy;

    @JsonProperty("declinedById")
    public void setDeclinedById(long declinedById) {
        declinedBy = User.fromId(declinedById);
    }

    @Lob
    private String sponsorDeclineReason;

    @Lob
    private String CRMDeclineReason;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authorizedById")
    @JsonIdentityReference(alwaysAsId = true)
    @Setter(AccessLevel.NONE)
    private User authorizedBy;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "authorizedBycrmId")
    @JsonIdentityReference(alwaysAsId = true)
    @Setter(AccessLevel.NONE)
    private User crmApprovedUser;

    @JsonProperty("authorizedBycrmId")
    public void setAuthorizedByCrmId(long authorizedBycrmId) {
        crmApprovedUser = User.fromId(authorizedBycrmId);
    }


    @JsonProperty("authorizedById")
    public void setAuthorizedById(long authorizedById) {
        authorizedBy = User.fromId(authorizedById);
    }

    @Transient
    private String memberName;

    @Transient
    private String shortCreatedDate;

    @Transient
    private String approvedStatus;

    @Transient
    private String crmUser;

    @Transient
    private String certifiedStatus;

    @Transient
    private String authorizedStatus;

    @Transient
    private String displayAmountOrPercentage;

    @Transient
    private String displayStatus;

    @Transient
    private String age;

    @Transient
    private String doj;

    @Transient
    private String doe;

    @Transient
    private String schemeName;

}
