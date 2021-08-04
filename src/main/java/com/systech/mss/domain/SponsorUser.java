package com.systech.mss.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "sponsor_users")
@NamedQuery(name = "findSponsorUserByStaffNo", query = "select su from SponsorUser su where su.staffNo = :staffNo")
@NamedQuery(name = "findSponsorUserByName", query = "select su from SponsorUser su where su.name = :name")

@Setter
@Getter
public class SponsorUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private Long staffNo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "permission_id", unique = true)
    @Setter(AccessLevel.NONE)
    private Permission permission;

}
