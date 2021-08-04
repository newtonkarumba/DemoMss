package com.systech.mss.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.systech.mss.controller.vm.ReasonForExitDocument;
import com.systech.mss.seurity.DateUtils;
import com.systech.mss.vm.benefitrequest.GroundOfBenefits;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = ClaimDocuments.TB_NAME)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ClaimDocuments {

    @Transient
    public static final String TB_NAME = "claimdocuments";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    String checkListName;

    @Column
    long benefitRequestId;

    @Column
    long documentId = 0L;

    @Column
    boolean uploaded = false;

    @Column
    String reasonForExit;

    @Column(updatable = false)
    @JsonIgnore
    Date createdAt = new Date();

    @Transient
    String shortDate;

    public static ClaimDocuments from(GroundOfBenefits groundOfBenefits, ReasonForExitDocument reasonForExitDocument) {
        ClaimDocuments claimDocuments = new ClaimDocuments();
        claimDocuments.setBenefitRequestId(groundOfBenefits.getId());
        claimDocuments.setCheckListName(reasonForExitDocument.getChecklistName());
        claimDocuments.setReasonForExit(reasonForExitDocument.getReasonForExit());
        return claimDocuments;
    }

    public static List<ClaimDocuments> format(List<ClaimDocuments> claimDocuments) {
        List<ClaimDocuments> claimDocuments1 = new ArrayList<>();
        for (ClaimDocuments claimDocument : claimDocuments) {
            claimDocument.setShortDate(DateUtils.shortDate(claimDocument.getCreatedAt()));
            claimDocuments1.add(claimDocument);
        }
        return claimDocuments1;
    }
}
