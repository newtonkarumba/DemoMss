package com.systech.mss.domain;

import com.systech.mss.controller.vm.EmailTemplatesVM;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = EmailTemplates.TB_NAME)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class EmailTemplates implements Serializable {

    @Transient
    public static final String TB_NAME = "emailtemplate";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    String title;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    EmailTemplatesEnum templatesType;

    @Lob
    String template;

    @Lob
    String smsTemplate;

    /**
     * Comma separated list of keys to replace in template, in template should be defined by $key
     */
    @Column
    String namedKeys;

    @Column(updatable = false)
    Date createdAt = new Date();

    @Transient
    String templatesTypeString;

    @Transient
    String description;

    @Transient
    String[] namedKeysList;

    public static EmailTemplates getInstance(EmailTemplatesVM emailTemplatesVM) {
        EmailTemplates emailTemplates = new EmailTemplates();
        emailTemplates.setTemplate(emailTemplatesVM.getTemplate());
        emailTemplates.setTitle(emailTemplatesVM.getTitle());
        emailTemplates.setDescription(emailTemplatesVM.getDescription());
        emailTemplates.setTemplatesType(emailTemplatesVM.getTemplatesType());
        emailTemplates.setSmsTemplate(emailTemplates.getSmsTemplate());
        String[] keys = emailTemplatesVM.getTemplatesType().getNamedKeys();
        if (keys != null) {
            StringBuilder s = new StringBuilder();
            for (String key : keys) {
                s.append(key).append(",");
            }
            emailTemplates.setNamedKeys(s.toString());
        }
        return emailTemplates;
    }

    public static EmailTemplates from(EmailTemplatesEnum emailTemplatesEnum) {
        EmailTemplates emailTemplates = new EmailTemplates();
        emailTemplates.setTemplate("");
        emailTemplates.setTitle(emailTemplatesEnum.getName());
        emailTemplates.setTemplatesType(emailTemplatesEnum);
        String[] keys = emailTemplatesEnum.getNamedKeys();
        if (keys != null) {
            StringBuilder s = new StringBuilder();
            for (String key : keys) {
                s.append(key).append(",");
            }
            emailTemplates.setNamedKeys(s.toString());
        }
        return emailTemplates;
    }
}
