package com.systech.mss.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Lob;
import javax.validation.constraints.Email;

@Embeddable
@Getter
@Setter
@ToString
public class Address {

    @Column
   // @NotNull
    private String postalAddress;

    @Column
    //@NotNull
    private String fixedPhone;

    @Column
    private String secondaryPhone;

    @Column
   // @NotNull
    @Email
    private String email;

    @Column
    private String building;

    @Column
    private String road;

    @Column
    //@NotNull
    private String town;

    @Column
   // @NotNull
    private String country;

    @Embedded
    private Geo geo;

    @Lob
    private String businessHours;
}