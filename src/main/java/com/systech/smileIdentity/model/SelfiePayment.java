package com.systech.smileIdentity.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table()
@Getter
@Setter
@ToString
public class SelfiePayment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private long userId = 0l;

    @Column
    @Enumerated(EnumType.STRING)
    private SelfieAction action;

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
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus=PaymentStatus.PENDING;


    @Transient
    private String memberName;
    @Transient
    private String shortCreatedDate;
}
