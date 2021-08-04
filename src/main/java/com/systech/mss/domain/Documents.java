package com.systech.mss.domain;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.systech.mss.seurity.DateUtils;
import com.systech.mss.vm.DocumentsVM;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@ToString
public class Documents implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonProperty("fromUserId")
    @JoinColumn(name = "fromUserId", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    User fromUserId;

    @JsonProperty("fromUserId")
    public void setFromUserId(long id) {
        fromUserId = User.fromId(id);
    }

    @Column
    long toUserId; //-1 for all users


    @Column
    String fileName;

    @Column
    String originalFileName;

    @Lob
    String filePath;

    @Column
    long forProfileId = -1l; //-1 for all users

    @Lob
    String comments;

    @Column
    long fileSize = 0l;

    @Column
    LocalDateTime expiryDate;

    @Column
    LocalDateTime createdAt = LocalDateTime.now();

    @Transient
    String fromUserFullName;

    @Transient
    String profileName;

    @Transient
    String shortDate;

    public static Documents getDocumentsInstance(User fromUser, @Valid DocumentsVM documentsVM) {
        Documents documents = new Documents();

        documents.setComments(documentsVM.getComments());
        documents.setFileName(documentsVM.getFileName());
        documents.setFilePath(documentsVM.getFilePath());
        documents.setFileSize(documentsVM.getSize());
        documents.setForProfileId(documentsVM.getForProfileId());
        documents.setFromUserId(fromUser.getId());
        documents.setToUserId(documentsVM.getToUserId());
        documents.setOriginalFileName(documentsVM.getOriginalFileName());
        return documents;
    }

    public static Documents getSimpleDocuments(Documents documents){
        documents.setShortDate(DateUtils.shortDate(documents.getCreatedAt()));
        documents.fromUserId=null;
        documents.setCreatedAt(null);
        documents.setFilePath(null);
        return documents;
    }
}
