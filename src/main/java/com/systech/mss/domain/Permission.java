package com.systech.mss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@ToString(exclude ={
        "profiles"
} )
@Getter
@Setter
@NoArgsConstructor
@Table(name = "menu")
@JsonIgnoreProperties({"profiles"})
public class Permission implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private long id;

    @Column(unique = true)
    @NotNull
    private String name;

    @ManyToMany(targetEntity = Profile.class, cascade = {
            CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH
    },fetch = FetchType.EAGER)
    @JoinTable(
            name = "profile_permissions",
            joinColumns = @JoinColumn(name = "permission_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id"))
    Set<Profile> profiles;

    public void addProfile(Profile profile) {
        getProfiles().add(profile);
        profile.getPermissions().add(this);
    }

    public void removeProfile(Profile profile) {
        getProfiles().remove(profile);
        profile.getPermissions().remove(this);
    }

    public static Permission fromId(long permissionId) {
        Permission permission = new Permission();
        permission.id = permissionId;
        return permission;
    }


}
