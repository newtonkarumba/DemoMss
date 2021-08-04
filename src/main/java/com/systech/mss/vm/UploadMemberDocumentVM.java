package com.systech.mss.vm;

import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UploadMemberDocumentVM {

    long documentId;

    long memberId;

    String docName;

    @Ignore
    String docNotes;

    @Ignore
    String docNum;

    @Ignore
    String docTypeId;
}
