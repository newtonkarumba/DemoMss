package com.systech.mss.repository;

import com.systech.mss.domain.Documents;
import com.systech.mss.domain.User;

import java.util.List;

public interface DocumentRepository  extends  AbstractRepository<Documents,Long> {

    List<Documents> getUserUploadedDocs(User user);

    List<Documents> getAllForUserOnly(User user);

    List<Documents> getForPublicOnly();
}
