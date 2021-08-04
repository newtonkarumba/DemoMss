package com.systech.mss.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.systech.mss.seurity.DateUtils;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Embeddable
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BroadCastMessages implements Serializable {

    @Column
    long toId;

    @Column
    String toName;

    @Column
    String login;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "toprofile_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("toProfile")
//    @Setter(AccessLevel.NONE)
    Profile toProfile;

    @Column
    long fromId;

    @Column
    String fromName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fromprofile_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("fromProfile")
//    @Setter(AccessLevel.NONE)
    Profile fromProfile;

    @Lob
    String documents;

    @Column
    String subject;

    @Lob
    String message;

    @Lob
    String receiversOutbox;

    @Column
    boolean isRead = false;

    @Column
    Date updatedAt = new Date();

    @Column(updatable = false)
    Date createdAt = new Date();

    public static BroadCastMessages from(String subject, String message, String documents, User user, Profile toProfile) {
        BroadCastMessages broadCastMessages=new BroadCastMessages();
        broadCastMessages.setSubject(subject);
        broadCastMessages.setMessage(message);
        broadCastMessages.setDocuments(documents);
        broadCastMessages.setFromId(user.getId());
        broadCastMessages.setLogin(user.getLogin());
        broadCastMessages.setFromName(user.getUserDetails().getName());
        broadCastMessages.setFromProfile(user.getProfile());
        broadCastMessages.setToProfile(toProfile);
        return broadCastMessages;
    }

    @Transient
    String shortUpdatedAt;
    @Transient
    String shortCreatedAt;
    @Transient
    String fromProfileName;
    @Transient
    String toProfileName;


    public String getShortCreatedAt() {
        return DateUtils.shortDate(getCreatedAt());
    }

    public String getShortUpdatedAt() {
        return DateUtils.shortDate(getUpdatedAt());
    }
}
