package com.systech.mss.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.systech.mss.seurity.DateUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = Forms.TB_NAME)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Forms implements Serializable {

    @Transient
    public static final String TB_NAME = "forms";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    String title;

    @Column
    String description;

    @Column
    String fileName;

    @Column
    String originalFileName;

    @Column(updatable = false)
    @JsonIgnore
    Date createdAt = Calendar.getInstance().getTime();

    @Transient
    String shortDate;

    public String getShortDate() {
        return DateUtils.shortDate(getCreatedAt());
    }
}
