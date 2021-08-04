package com.systech.mss.domain;


import com.systech.mss.seurity.DateUtils;
import com.systech.mss.util.Ignore;
import com.systech.mss.vm.benefitrequest.AddBeneficiaryVM;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = StagedBeneficiaries.TB_NAME)
@Getter
@Setter
@ToString
public class StagedBeneficiaries implements Serializable {

    @Ignore
    @Transient
    public static final String TB_NAME = "stagedbeneficiaries";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Ignore
    private long id;

    @Ignore
    @Column
    private long userId; //MSS USER ID, CAN BE MEMBER, PO, CRE

    @Ignore
    @Embedded
    AddBeneficiaryVM details;

    @Ignore
    @Column
    private String documents;

    @Ignore
    @Column
    private boolean posted = false;

    @Ignore
    @Column(updatable = false)
    Date createdAt = Calendar.getInstance().getTime();

    @Ignore
    @Transient
    String shortCreatedAt;

    public static StagedBeneficiaries from(long mssUserId, AddBeneficiaryVM addBeneficiaryVM) {
        StagedBeneficiaries stagedBeneficiaries = new StagedBeneficiaries();
        stagedBeneficiaries.setUserId(mssUserId);
        stagedBeneficiaries.setDetails(addBeneficiaryVM);
        return stagedBeneficiaries;
    }

    public String getShortCreatedAt() {
        this.shortCreatedAt = DateUtils.shortDate(getCreatedAt());
        return shortCreatedAt;
    }

}
