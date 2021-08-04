package com.systech.mss.domain;

import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
@Table(name = "countryCode")
@NamedQuery(name = "findCountryCodeById", query = "select cc from CountryCode cc where cc.id = :id ")
@Setter
@Getter
public class CountryCode implements Serializable {



@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

@Column
@NotNull
private String country;

@Column
@NotNull
private String countryCode;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusConfig statusConfig = StatusConfig.ACTIVE;

}
