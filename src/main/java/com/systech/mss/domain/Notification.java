package com.systech.mss.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="notification")
@ToString
public class Notification implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonbTransient
    @Column(name = "created_date",updatable = false)
    private LocalDateTime createdDate=LocalDateTime.now();

    @Column
    @NotNull
    private String name; //recipient name

    @Column
    @NotNull
    @Email
    private String emailFrom; //recipient email

    @Lob
    @NotNull
    private String message;

    @Column
    private int retryCount=0;

    @Column
    private String subject = "INQUIRY";

    @Column
    @NotNull
    private boolean delivered=true;

    public static Notification getInstance(@NotNull String name, @NotNull @Email String emailFrom,
                                           @NotNull String message,String subject,boolean isDelivered) {
        Notification notification=new Notification();
        notification.setName(name);
        notification.setEmailFrom(emailFrom);
        notification.setMessage(message);
        notification.setSubject(subject);
        notification.setDelivered(isDelivered);
        return notification;
    }
}