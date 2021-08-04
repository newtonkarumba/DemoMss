package com.systech.mss.domain;

import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = MobileMoneyConfig.TB_NAME)
@Setter
@Getter
public class MobileMoneyConfig {

    @Transient
    public static final String TB_NAME = "mobilemoneyconfig";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private double minAmount = 1.0;

    @Column
    private String mpesaPaybill;   // business shortcode

    @Lob
    private String mpesaAppKey; // from dev account

    @Lob
    private String mpesaAppSecret; // from dev account

    @Lob
    private String mpesaPassKey; //eg bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919

    @Lob
    private String callbackUrl;

    @Lob
    private String timeoutUrl;

    @Lob
    private String accountReference;
    @Lob
    private String mobileMoneyProcedure;

    @Column
    @Ignore
    boolean isLive = false;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusConfig status = StatusConfig.ACTIVE;
}
