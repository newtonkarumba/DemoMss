package com.systech.mss.domain;

import com.fasterxml.jackson.annotation.*;
import com.systech.mss.domain.common.StringPrefixedSequenceIdGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="ticket")
@Getter
@Setter
@ToString(exclude={
        "ticketMessages"
})
@JsonIgnoreProperties({"ticketMessages"})

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Ticket implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @NotNull
    private boolean closed=false;

    @Column
    @NotNull
    private String subject;

    @NotNull
    @Lob
    private String body;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticketcategory_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("ticketcategory_id")
    @Setter(AccessLevel.NONE)
    private TicketCategory ticketCategory;

    @JsonProperty("ticketcategory_id")
    public void setTicketCategoryById(long ticketCategoryId) {
        ticketCategory = TicketCategory.fromId(ticketCategoryId);
    }

    @OneToMany(mappedBy = "ticket", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TicketMessage> ticketMessages;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("createdById")
    @Setter(AccessLevel.NONE)
    private User createdBy;

    @JsonProperty("createdById")
    public void setCreatedById(long createdById) {
        createdBy = User.fromId(createdById);
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("recipientId")
    @Setter(AccessLevel.NONE)
    private User recepient;

    @JsonProperty("recipientId")
    public void setRecipientById(long recipientId) {
        recepient = User.fromId(recipientId);
    }



    public static Ticket fromId(long ticketId) {
        Ticket ticket = new Ticket();
        ticket.id = ticketId;
        return ticket;
    }

    @Column
    @Enumerated(EnumType.STRING)
    private  TicketPriority priority;

    @JsonbTransient
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("profileId")
    @Setter(AccessLevel.NONE)
    private Profile profileId;

    @JsonProperty("profileId")
    public void setProfileById(long setProfileById) {
        profileId = Profile.fromId(setProfileById);
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticketIssue_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("ticketIssueId")
    @Setter(AccessLevel.NONE)
    private TicketIssues ticketIssueId;

    @JsonProperty("ticketIssueId")
    public void setTicketIssueById(long ticketIssueById) {
        ticketIssueId = TicketIssues.fromId(ticketIssueById);
    }

    @Column
    private long schemeId=0L;

    @Column
    private long sponsorId=0L;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    @Column
    private long newOwnerRepliesCount=0L;

    @Column
    private long newSupportRepliesCount=0L;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "forwadedBy_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("forwardedById")
    @Setter(AccessLevel.NONE)
    private User forwardedBy=null;

    @JsonProperty("forwardedById")
    public void setForwardedBy(long forwardedById) {
        forwardedBy = User.fromId(forwardedById);
    }

    @Column
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String TicketRefNo;

    @Transient
    private String ownerName;

    @Transient
    private String receiverName;

    @Transient
    private String status;

    @Transient
    private String shortDate;

    @Transient
    private String shortDateTime;


}
