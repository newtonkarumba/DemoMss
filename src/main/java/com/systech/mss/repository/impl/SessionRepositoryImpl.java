package com.systech.mss.repository.impl;

import com.systech.mss.domain.ActivityTrail;
import com.systech.mss.domain.Session;
import com.systech.mss.repository.SessionRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static java.util.Collections.singletonMap;

public class SessionRepositoryImpl extends AbstractRepositoryImpl<Session,Long> implements SessionRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SessionRepositoryImpl() {
        super(Session.class);
    }

    @Override
    public List<Session> getBySessionId(long id) {
        return findByNamedQuery("findBySessionId", singletonMap("id", id));
    }

    @Override
    public List<Session> getByUserId(long id) {
        return findByNamedQuery("findSessionByUserId", singletonMap("id", id));
    }

    @Override
    public List<Session> getSessionOfDate(String date) {
        return findByNamedQuery("findSessionOfDate", singletonMap("dateFilter", date));
    }
}
