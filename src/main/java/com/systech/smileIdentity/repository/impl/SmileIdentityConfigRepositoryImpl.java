package com.systech.smileIdentity.repository.impl;


import com.systech.mss.domain.StatusConfig;
import com.systech.mss.repository.impl.AbstractRepositoryImpl;
import com.systech.smileIdentity.model.SmileIdentityConfig;
import com.systech.smileIdentity.repository.SmileIdentityConfigRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class SmileIdentityConfigRepositoryImpl extends AbstractRepositoryImpl<SmileIdentityConfig,Long> implements SmileIdentityConfigRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SmileIdentityConfigRepositoryImpl() {
        super(SmileIdentityConfig.class);
    }
    @Override
    public SmileIdentityConfig getActiveConfig() {
        try {
            String sql = "FROM SmileIdentityConfig  c WHERE c.statusConfig=:config ORDER BY  c.id DESC";
            Query query = getEntityManager().createQuery(sql, SmileIdentityConfig.class);
            query.setParameter("config", StatusConfig.ACTIVE);
            query.setMaxResults(1);
            return (SmileIdentityConfig) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
