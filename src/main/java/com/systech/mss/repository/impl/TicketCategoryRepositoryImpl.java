package com.systech.mss.repository.impl;

import com.systech.mss.domain.TicketCategory;
import com.systech.mss.repository.TicketCategoryRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class TicketCategoryRepositoryImpl extends AbstractRepositoryImpl<TicketCategory,Long> implements TicketCategoryRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TicketCategoryRepositoryImpl() {
        super(TicketCategory.class);
    }
}
