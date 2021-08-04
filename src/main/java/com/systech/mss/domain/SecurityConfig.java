package com.systech.mss.domain;

import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="securityconfig")
@Getter
@Setter
public class SecurityConfig implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonbTransient
    @Column(name = "created_date",updatable = false)
    private LocalDateTime createdDate;

    @Column
    private String issuer;

    @Column
    private long tokenValidityMillis;

    @Column
    private long tokenValidityMillisForRememberMe;
}
