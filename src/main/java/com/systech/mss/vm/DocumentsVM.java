package com.systech.mss.vm;

import com.systech.mss.util.Ignore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentsVM {

    long userId;

    @Ignore
    long forProfileId=-1l;

    @Ignore
    long toUserId=-1l;

    String fileName;

    String originalFileName;

    String filePath;

    @Ignore
    String comments;

    @Ignore
    long size;
}
