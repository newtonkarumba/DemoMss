package com.systech.mss.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "profile")
@JsonIgnoreProperties({"users", "permissions"})
@ToString(exclude = {
        "permissions",
        "users"
})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
public class Profile implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 0, max = 50)
    @Column(name = "\"name\"", length = 50)
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private LoginIdentifier loginIdentifier;

    @OneToMany(mappedBy = "profile", fetch = LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> users;

    @ManyToMany(targetEntity = Permission.class, mappedBy = "profiles", cascade = {
            CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH
    }, fetch = FetchType.EAGER)
    Set<Permission> permissions;

    @ManyToMany(targetEntity = TicketCategory.class, mappedBy = "profiles", cascade = {
            CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH
    }, fetch = FetchType.EAGER)
    Set<TicketCategory> ticketCategories;

    public void addPermission(Permission permission) {
        getPermissions().add(permission);
        permission.getProfiles().add(this);
    }

    public void removePermission(Permission permission) {
        getPermissions().remove(permission);
        permission.getProfiles().remove(this);
    }

    public void addTicketCategory(TicketCategory ticketCategory) {
        getTicketCategories().add(ticketCategory);
    }

    public void removeTicketCategory(TicketCategory ticketCategory) {
        getTicketCategories().remove(ticketCategory);
    }


    public static Profile fromId(long profileId) {
        Profile profile = new Profile();
        profile.setId(profileId);
        return profile;
    }

    @Transient
    public String loginIdentifierName;
}
