package com.systech.mss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name="ticketcategory")
@ToString(exclude ={
        "profiles"
} )
@JsonIgnoreProperties({"profiles"})
@Getter
@Setter
public class TicketCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(unique = true)
    @NotNull
    private String name;

    @Column
    @NotNull
    private String description;

    @ManyToMany(targetEntity = Profile.class, cascade = {
            CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH
    },fetch = FetchType.EAGER)
    @JoinTable(
            name = "ticketcategory_profiles",
            joinColumns = @JoinColumn(name = "ticketcategory_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id"))
    Set<Profile> profiles;

    public void addProfile(Profile profile) {
        getProfiles().add(profile);
        profile.getTicketCategories().add(this);
    }

    public void removeProfile(Profile profile) {
        getProfiles().remove(profile);
        profile.getTicketCategories().remove(this);
    }

    public static TicketCategory fromId(long ticketCategoryId) {
        TicketCategory ticketCategory = new TicketCategory();
        ticketCategory.id = ticketCategoryId;
        return ticketCategory;
    }



    @JsonbTransient
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }



}
