package com.systech.mss.domain;

import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "supportmessages")
@Getter
@Setter
@ToString
public class SupportMessages implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Ignore
    private long id;

    @Column
    private String name;

    @Column
    private String email;

    @Lob
    private String message;

    @JsonbTransient
    @Column(name = "created_date", updatable = false)
    @Ignore
    private LocalDateTime createdDate=LocalDateTime.now();

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @Ignore
    private StatusConfig statusConfig=StatusConfig.ACTIVE;

    @Transient
    @Ignore
    private String shortDate;
}
