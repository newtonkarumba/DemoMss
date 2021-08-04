package com.systech.mss.repository.impl;

import com.systech.mss.domain.TicketIssues;
import com.systech.mss.domain.TicketMessage;
import com.systech.mss.repository.TicketIssuesRepository;
import com.systech.mss.repository.TicketMessageRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TicketIssuesRepositoryImpl extends AbstractRepositoryImpl<TicketIssues,Long> implements TicketIssuesRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TicketIssuesRepositoryImpl() {
        super(TicketIssues.class);
    }
}
