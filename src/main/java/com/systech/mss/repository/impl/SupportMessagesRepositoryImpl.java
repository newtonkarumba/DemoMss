package com.systech.mss.repository.impl;

import com.systech.mss.domain.SupportMessages;
import com.systech.mss.repository.SupportMessagesRepository;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class SupportMessagesRepositoryImpl extends AbstractRepositoryImpl<SupportMessages,Long> implements SupportMessagesRepository {


    @Inject
    private EntityManager em;

    @Inject
    Logger log;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SupportMessagesRepositoryImpl() {
        super(SupportMessages.class);
    }

}
