package com.systech.mss.domain;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.systech.mss.controller.vm.FaqVM;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = FAQ.TB_NAME)
@Getter
@Setter
@NoArgsConstructor
@ToString
public class FAQ extends BaseModel implements Serializable {

    @Transient
    public static final String TB_NAME = "faq";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    String title;

    @Column
    String subtitle;

    @Lob
    String body;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("profileId")
    Profile profile;

    @Column(updatable = false)
    Date createdAt = new Date();

    @Transient
    String profileName;
    @Transient
    String shortDate;
    @Transient
    long idProfile;

    public static FAQ getFAQ(FaqVM faqVM, Profile profile) {
        FAQ faq = new FAQ();
        faq.setId(faqVM.getId());
        faq.setTitle(faqVM.getTitle());
        faq.setSubtitle(faqVM.getSubtitle());
        faq.setBody(faqVM.getBody());
        faq.setProfile(profile);
        return faq;
    }
}
