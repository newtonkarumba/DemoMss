package com.systech.mss.repository;



import com.systech.mss.domain.Session;

import java.util.List;

public interface SessionRepository extends  AbstractRepository<Session,Long> {
    List<Session> getBySessionId(long id);
    List<Session> getByUserId(long id);
    List<Session> getSessionOfDate(String date);
}
