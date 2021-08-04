package com.systech.mss.domain;

import com.systech.mss.domain.common.AuthorizationLevel;
import com.systech.mss.seurity.DateUtils;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sponsor_config")
@Setter
@Getter
public class SponsorConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonbTransient
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column
    @Enumerated(EnumType.STRING)
    private AuthorizationLevel authorizationLevel=AuthorizationLevel.LEVEL_THREE;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusConfig statusConfig = StatusConfig.ACTIVE;

    @Transient
    String shortDate;

    @Transient
    private String shortDateTime;

    public static SponsorConfig getSimpleConfig(SponsorConfig sponsorConfig) {

        return sponsorConfig;
    }
}
