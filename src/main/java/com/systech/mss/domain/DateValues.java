package com.systech.mss.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = DateValues.TB_NAME)
@Getter
@Setter
@NoArgsConstructor
public class DateValues implements Serializable {
    @Transient
    public static final String TB_NAME = "date_values";

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column
    private long dateNumber=0l;
}
