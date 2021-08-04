package com.systech.mss.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.inject.Inject;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
@NamedQueries(
        {
                @NamedQuery(name = "findSessionByUserId", query = "SELECT s FROM Session s WHERE (s.userId=:userId) ORDER BY s.id DESC"),

        }
)

@Entity
@Setter
@Getter
@Table(name="session")

public class Session implements Serializable {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("userId")
    @Setter(AccessLevel.NONE)
    private User userId;

    @JsonProperty("userId")
    public void setUserId(long id) {
        userId = User.fromId(id);
    }


    @Column(name = "created_date",updatable = false)
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime stoppedAt;

    @Column(updatable = false)
    @NotNull
    private String ipAddress;

    @NotNull
    private boolean active;

    @Transient
    private String  shortDate;

    @Transient
    private String  shortDateTime;

    @Transient
    private String profileName;

    @Transient
    private String userName;

    @Transient
    private String  stoppedShortDateTime;



}