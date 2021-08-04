package com.systech.mss.domain;

import com.systech.mss.domain.common.Clients;
import com.systech.mss.seurity.DateUtils;
import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "config")
@NamedQuery(name = "findConfigById", query = "select cg from Config cg where cg.id = :id ")
@Setter
@Getter
public class Config implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonbTransient
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column
    @NotNull
    private String fmUsername;

    @Column
    @NotNull
    private String fmPassword;

    @Column
    @NotNull
    private String fmBasePath;

    @Column
    private String mpesaMiddleWarePath;

    @Column
    private String middlewarePassword;

    @Column
    private String middlewareUsername;

    @Column
    private String emailHost;
    @Column
    private int emailPort;

    @Column
    private String emailUsername;

    @Column
    private String emailPassword;

    @Column
    private String emailFrom;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusConfig statusConfig = StatusConfig.ACTIVE;

    @Column
    private String currencyName;

    @Column
    private String currencyShortName;

    @Column
    private String country = "Kenya";

    @Column
    private String countryCode = "254";

    @Column
    private int numTrials;

    @Column
    private String businessName;

    @Column
    private String businessImage;//comes from Documents id

    @Column
    private String registrationDeclaration;

    @Column
    @Enumerated(EnumType.STRING)
    private Clients client=Clients.OTHERS;

    @Column
    private String reportServerUrl;

    @Column(name = "two_fa_auth")
    @Ignore
    private boolean enableTwoFactorAuth=false;

    @Column
    @Ignore
    private boolean allowEmailNotification=true;

    @Column
    @Ignore
    private boolean allowSmsNotification=true;

    @Transient
    String shortDate;

    @Transient
    private String shortDateTime;

    public static Config getSimpleConfig(Config config) {
        config.setShortDate(
                DateUtils.shortDate(config.getCreatedDate())
        );
        config.setCreatedDate(null);
        return config;
    }

}