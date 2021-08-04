package com.systech.mss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="bills")
@Getter
@Setter
@ToString()
@JsonIgnoreProperties({"ticketMessages"})
public class SponsorBill implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String  bookingDate;

    @Column
    private String  dueDate;

    @Column
    private Long year;

    @Column
    private String  month;



}
