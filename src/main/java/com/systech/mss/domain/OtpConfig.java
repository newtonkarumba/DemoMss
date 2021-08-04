package com.systech.mss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="otpconfig")
@ToString
public class OtpConfig  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private OtpIdentifier otpIdentifier;

    @Column
    private boolean enabled=false;

    @Column
    @JsonIgnore
    private Date createdAt=new Date();

    public static OtpConfig from(OtpIdentifier identifier) {
        OtpConfig otpConfig=new OtpConfig();
        otpConfig.setOtpIdentifier(identifier);
        return otpConfig;
    }
}
