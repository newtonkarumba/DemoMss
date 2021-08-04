package com.systech.mss.repository.impl;

import com.systech.mss.domain.TicketMessage;
import com.systech.mss.repository.TicketMessageRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TicketMessageRepositoryImpl extends AbstractRepositoryImpl<TicketMessage,Long> implements TicketMessageRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TicketMessageRepositoryImpl() {
        super(TicketMessage.class);
    }
}
