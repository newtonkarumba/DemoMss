package com.systech.mss.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name="ticketIssues")
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TicketIssues {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String issue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("profileId")
    @Setter(AccessLevel.NONE)
    private Profile profileId;

    @JsonProperty("profileId")
    public void setCreatedById(long profileById) {
        profileId = Profile.fromId(profileById);
    }

    public static TicketIssues fromId(long ticketIssueId) {
        TicketIssues ticketIssues = new TicketIssues();
        ticketIssues.id = ticketIssueId;
        return ticketIssues;
    }

    @Transient
    private String profileName;
}

