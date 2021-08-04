package com.systech.mss.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="mailconfig")
@Setter
@Getter
public class MailConfig implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="active")
    private boolean enabled;

    @Column(name = "smtp_host")
    private String host;

    @Column(name = "smtp_port")
    private int port;

    @Column(name = "smtp_username")
    private String username;

    @Column(name = "smtp_password")
    private String password;

    @Column(name = "smtp_email_from")
    private String from;

    @Column(name = "smtp_base_utl")
    private String baseUrl;

    @Column
    private String supportEmail;

    @Column
    private boolean enableTLS=true;

    @Column
    @Enumerated(EnumType.STRING)
    private MailType mailType=MailType.SSL;
}
