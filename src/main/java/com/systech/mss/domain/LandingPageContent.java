package com.systech.mss.domain;

import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="landingpagecontent")
@Getter
@Setter
public class LandingPageContent implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonbTransient
    @Column(name = "created_date",updatable = false)
    private LocalDateTime createdDate;

    @Lob
    private String welcomeMessage;

    @Column
    private long logo= 0L; //comes from Documents id

    @Column
    private long pensionerImage= 0L; //comes from Documents id

    @Column
    private long loginImage=0L;

    @Column
    private long memberIcon=0L;

    @Column
    private long pensionerIcon=0L;

    @Lob
    private String whySaveMessage;

    @Lob
    private String memberMessage;

    @Lob
    private String pensionerMessage;

    @Lob
    private String mapLoc;


    @Embedded
    private Address address;

    @Column
    @Enumerated(EnumType.STRING)
    StatusConfig statusConfig=StatusConfig.ACTIVE;

    @Transient
    String addressString;

    @Transient
    String shortDate;
}