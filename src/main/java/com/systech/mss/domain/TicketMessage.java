package com.systech.mss.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name ="ticketmessage")
public class TicketMessage  implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Lob
    @NotNull
    private String message;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("created_by_id")
    @Setter(AccessLevel.NONE)
    private User createdBy;

    @JsonProperty("created_by_id")
    public void setCreatedById(long createdById) {
        createdBy = User.fromId(createdById);
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ticketmessage_id")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("ticket_id")
    @Setter(AccessLevel.NONE)
    private Ticket ticket;

    @JsonProperty("ticket_id")
    public void setTicketById(long ticketId) {
        ticket = Ticket.fromId(ticketId);
    }

    @JsonbTransient
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }

    @Transient
    private String ownerName;

    @Transient
    private String profileName;

    @Transient
    private String shortDate;

    @Transient
    private String shortDateTime;


}
