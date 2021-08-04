package com.systech.mss.domain;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.systech.mss.domain.common.UserStatus;
import com.systech.mss.service.dto.MemberDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NamedQuery(name = "findUserByLogin", query = "select u from User u where u.login = :login")
@NamedQuery(name = "findUserByEmail", query = "select u from User u where u.email = :email")
@NamedQuery(name = "findUserByResetKey", query = "select u from User u where u.resetKey = :resetKey")
@NamedQuery(name = "findUserByActivationKey", query = "select u from User u where u.activationKey = :activationKey")
@NamedQuery(name = "findUserBySecurityCode", query = "select u from User u where u.securityCode = :securityCode")
@NamedQuery(name = "findUserByUserId", query = "select u from User u where u.id = :id")
@NamedQuery(name = "findUserBySponsorId", query = "select u from User u where u.userDetails.sponsorId = :sponsorId")
@NamedQuery(name = "findUsersBySponsorRefNo", query = "select u from User u where u.userDetails.sponsorRefNo = :sponsorRefNo")
//@NamedEntityGraph(name = "graph.user.authorities", attributeNodes = @NamedAttributeNode("authorities"))
@Getter
@Setter
@ToString
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
//    @Pattern(regexp = LOGIN_REGEX)
    @Size(min = 1, max = 255)
    @Column(unique = true, nullable = false)
    private String login;

    @Size(max = 255)
    @Column(name = "first_name")
    private String firstName = "";

    @Size(max = 255)
    @Column(name = "last_name")
    private String lastName = "";

    @JsonbTransient
    @NotNull
    @Size(min = 32, max = 255)
    @Column(name = "password_hash")
    @JsonIgnore
    private String password;

    //    @Email(regexp = EMAIL_REGEX, message = EMAIL_REGEX_MESSAGE)
    @Size(min = 5, max = 255)
    @Column()
    private String email;

    @NotNull
    @Column(nullable = false)
    @JsonIgnore
    private boolean activated = false;

    @JsonbTransient
    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @JsonbTransient
    @Size(max = 20)
    @Column(name = "security_code", length = 20)
    @JsonIgnore
    private String securityCode;

    @JsonbTransient
    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Size(min = 2, max = 5)
    @Column(name = "lang_key", length = 5)
    @JsonIgnore
    private String langKey;

    @Column(name = "reset_date")
    @JsonIgnore
    private LocalDateTime resetDate = null;

    @Column(name = "fm_identifier")
    private String fmIdentifier;

    /**
     * Passed from FundMaster during registration
     */
    @Embedded
    MemberDTO userDetails;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id", nullable = false)
//    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("profileId")
    @Setter(AccessLevel.PUBLIC)
    private Profile profile;

    @Transient
    private String profileName;

    @Transient
    private String sponsorName;

    @Column
    @JsonIgnore
    private int numTrials = 0;

    @Column(name = "flg_firsttime")
    String flg_firstTime = String.valueOf(true);

    @Column
    private boolean lockedStatus = false;

    @Column
    private boolean deactivatedByAdmin = false;

    @Column
    private boolean approvedByCrm = false;

    @JsonbTransient
    @Column(name = "created_date", updatable = false)
    @JsonIgnore
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column
    private String photo;

    @Column
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Transient
    String shortDate;

    @Transient
    String cellPhone;

    @Transient
    String shortDateTime;

    @Column
    long authenticationTrials= 0L;

    @Column
    String parentSponsorEmail = "";

    @JsonProperty("profileId")
    public void setProfileById(Long profileId) {
        profile = Profile.fromId(profileId);
    }

    public static User fromId(Long userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }


}
