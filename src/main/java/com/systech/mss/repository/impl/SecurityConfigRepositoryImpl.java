package com.systech.mss.repository.impl;

import com.systech.mss.domain.SecurityConfig;
import com.systech.mss.repository.SecurityConfigRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class SecurityConfigRepositoryImpl extends AbstractRepositoryImpl<SecurityConfig,Long> implements SecurityConfigRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SecurityConfigRepositoryImpl() {
        super(SecurityConfig.class);
    }
}
