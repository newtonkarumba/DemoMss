package com.systech.mss.vm;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.systech.mss.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class AdminFileUploads {
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

    @Column
    String filePath;

    @Column
    long forProfileId = -1l; //-1 for all users

    @Lob
    String comments;

    @Column
    long fileSize = 0l;


    @Transient
    String fromUserFullName;


//    public static AdminFileUploads getAdminUploadsInstance(User user, AdminFileUploads adminFileUploads) {
//        com.systech.mss.domain.AdminFileUploads documents = new com.systech.mss.domain.AdminFileUploads();
//        documents.setComments(adminFileUploads.getComments());
//        documents.setFileName(adminFileUploads.getFileName());
//        documents.setFilePath(adminFileUploads.getFilePath());
//        documents.setFileSize(adminFileUploads.getFileSize());
//        documents.setForProfileId(adminFileUploads.getForProfileId());
//        documents.setFromUserId(fromUser.getId());
//        documents.setToUserId(adminFileUploads.getToUserId());
//        documents.setOriginalFileName(adminFileUploads.getOriginalFileName());
//        return documents;
//    }
}
