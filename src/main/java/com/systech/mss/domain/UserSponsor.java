package com.systech.mss.domain;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.systech.mss.service.dto.GenericModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "tbl_user_Sponsors")
@Getter
@Setter
public class UserSponsor implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "parent_sponsor_id", nullable = true)
    //@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("parentSponsorId")
    @Setter(AccessLevel.NONE)
    private User parentSponsor;

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private
    Long id;

    @Column
    private String userProfile;
    @Column
    private Long profileID;
    @Column (unique = true)
    private String username;
    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Column
    private String email;
    @Column
    private String password;
    @Column
    private Date password_expiry;
    @Column
    private int attempt;
    @Column
    private String securityCode;
    @Column
    private String smsActivationCode;

    public void setSmsActivationCode(String smsActivationCode) {
        this.smsActivationCode = smsActivationCode;
    }

    @JsonProperty("parentSponsorId")
    public void setParentSponsorById(long parentSponsorId) {
        parentSponsor = User.fromId(parentSponsorId);

    }

    @Transient
    private String profileName;

    @Transient
    private String sponsorName;





}
