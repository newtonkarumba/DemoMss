package com.systech.smileIdentity.model;

import com.systech.mss.domain.StatusConfig;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "smileIdentityConfig")
@Entity
@Getter
@Setter
public class SmileIdentityConfig implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String partnerId;

    @Column
    private String callbackApi;

    @Lob
    private String decodedApiKey;

    @Lob
    private String encodedApiKey;

    @Column
    private int serverId;

    @Column
    private boolean returnJobStatus=true;

    @Column
    private boolean returnHistory=true;

    @Column
    private boolean returnImages=true;

    @Column
    private long authenticationTrials=10;

    @Column
    private long registrationAmount=1;

    @Column
    private long authenticationAmount=1;

    @Column
    private long reRegistrationAmount=1;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusConfig statusConfig = StatusConfig.ACTIVE;

    @JsonbTransient
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }
}
