package com.systech.mss.domain;

import com.systech.mss.controller.vm.MemberEditVM;
import com.systech.mss.seurity.DateUtils;
import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = StageMemberDetails.TB_NAME)
@Getter
@Setter
@ToString
public class StageMemberDetails implements Serializable {

    @Ignore
    @Transient
    public static final String TB_NAME = "stagememberdetails";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Ignore
    private long id;

    @Ignore
    @Column(unique = true)
    long memberId;

    @Ignore
    @Column(name = "fname")
    String fname;

    @Column
    String lastName;

    @Ignore
    @Lob
    String documents;

    @Ignore
    @Embedded
    MemberEditVM details;

    @Ignore
    @Column(updatable = false)
    Date createdAt = Calendar.getInstance().getTime();

    @Ignore
    @Transient
    String shortCreatedAt;

    @Ignore
    @Transient
    int numDocuments;

    public static StageMemberDetails from(long memberId, MemberEditVM memberEditVM) {
        StageMemberDetails stageMemberDetails = new StageMemberDetails();
        stageMemberDetails.setMemberId(memberId);
        stageMemberDetails.setFname(memberEditVM.getFirstname());
        stageMemberDetails.setLastName(memberEditVM.getSurname());
        stageMemberDetails.setDetails(memberEditVM);
        return stageMemberDetails;
    }

    public String getShortCreatedAt() {
        this.shortCreatedAt = DateUtils.shortDate(getCreatedAt());
        return shortCreatedAt;
    }
}
