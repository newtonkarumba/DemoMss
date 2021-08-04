package com.systech.mss.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "stage_contribution")
@NamedQuery(name = "findFalseContribution", query = "select s from StageContribution s where (s.sendToXi)=false")
@Getter
@Setter
@ToString
public class StageContribution implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private long memberId = 0l;

    @Column
    private long schemeId = 0l;

    @Column
    private String paybill;

    @Column
    private String requestId;

    @Column
    private BigDecimal amount;

    @Column
    private String phoneNumber;

    @Column
    private String mpesaPhoneNumber;

    @Column
    private String MerchantRequestID;

    @Column
    private String timestamp;

    @Column
    private String password;

    @Lob
    private String resultMessage;

    @Column(updatable = false)
    private Date createdAt = new Date();

    @Column
    private boolean sendToXi = false;

    @Transient
    private String memberName;
    @Transient
    private String shortCreatedDate;
}
