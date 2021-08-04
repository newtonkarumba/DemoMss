package com.systech.smileIdentity.model;

import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "selfieResults")
@Setter
@Getter
public class SelfieResults implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String userId;

//    @Id
//    @GeneratedValue(generator = RandomStringGenerator.generatorName)
//    @GenericGenerator(name = RandomStringGenerator.generatorName, strategy = "a.b.c.smileIdentityJobs")

    @Column
    private Long cycleId;

    @Column
    private String jobId;

    @Lob
    private String image="";

    @Column
    @Enumerated(EnumType.STRING)
    private ResultSource source=ResultSource.FIRSTRESULTS;

    @Column boolean finalResult=false;

    @JsonbTransient
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }
}
