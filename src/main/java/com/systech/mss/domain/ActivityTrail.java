package com.systech.mss.domain;

import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.Setter;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NamedQueries(
        {
                @NamedQuery(name = "findByUserId", query = "SELECT ac FROM ActivityTrail ac WHERE (ac.userId=:userId) ORDER BY ac.id DESC"),
                @NamedQuery(name = "findActivityOfDate", query = "SELECT ac FROM ActivityTrail ac ORDER BY ac.id DESC "),
                //@NamedQuery(name = "filterActivityLogOfUserByDate", query = "SELECT ac FROM ActivityTrail ac where ac.createdDate >=:dateFrom and ac.createdDate<:dateTo + INTERVAL 1 DAY ORDER BY ac.id DESC "),
                @NamedQuery(name = "findActivityLogOfUserOnDate", query = "SELECT ac FROM ActivityTrail ac ORDER BY ac.id DESC ")
        }
)

@Entity
@Table(name = "activitytrail")
@Setter
@Getter
public class ActivityTrail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long userId;

    @JsonbTransient
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Lob
    private String description;

    @Transient
    String userName;

    @Transient
    String shortDate;

    @Transient
    String shortDateTime;

//    @Transient
//    String UserName;

    @Ignore
    @Transient
    public String name;

    @Ignore
    @Transient
    public String profile;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}