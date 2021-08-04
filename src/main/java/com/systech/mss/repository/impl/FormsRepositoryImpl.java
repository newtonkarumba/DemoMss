package com.systech.mss.repository.impl;

import com.systech.mss.domain.Forms;
import com.systech.mss.repository.FormsRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class FormsRepositoryImpl extends AbstractRepositoryImpl<Forms, Long> implements FormsRepository {


    @Inject
    private EntityManager em;

    public FormsRepositoryImpl() {
        super(Forms.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
