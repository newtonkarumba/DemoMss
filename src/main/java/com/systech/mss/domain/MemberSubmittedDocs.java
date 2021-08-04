package com.systech.mss.domain;


import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.systech.mss.vm.UploadMemberDocumentVM;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
public class MemberSubmittedDocs implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "documentId", nullable = false)
    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("documentId")
    @Setter(AccessLevel.NONE)
    Documents documents;

    @Column
    long memberId;

    @Column
    String docName;

    @Column
    String docNotes;

    @Column
    String docNum;

    @Column
    String docTypeId;

    @Column
    boolean isApproved=false;

    @Transient
    String shortDate;

    public void setDocuments(Documents documents) {
        this.documents = documents;
    }

    public static MemberSubmittedDocs getInstance(Documents documents, UploadMemberDocumentVM uploadMemberDocumentVM){
       MemberSubmittedDocs memberSubmittedDocs=new MemberSubmittedDocs();
       memberSubmittedDocs.setMemberId(uploadMemberDocumentVM.getMemberId());
       memberSubmittedDocs.setDocName(uploadMemberDocumentVM.getDocName());
       memberSubmittedDocs.setDocNotes(uploadMemberDocumentVM.getDocNotes());
       memberSubmittedDocs.setDocNum(uploadMemberDocumentVM.getDocNum());
       memberSubmittedDocs.setDocTypeId(uploadMemberDocumentVM.getDocTypeId());
       memberSubmittedDocs.setDocuments(documents);
       return memberSubmittedDocs;
    }

    public static MemberSubmittedDocs getSimpleMemberSubmittedDocs(MemberSubmittedDocs submittedDocs){
        submittedDocs.setDocuments(Documents.getSimpleDocuments(
                submittedDocs.getDocuments()
        ));
        return submittedDocs;
    }
}
