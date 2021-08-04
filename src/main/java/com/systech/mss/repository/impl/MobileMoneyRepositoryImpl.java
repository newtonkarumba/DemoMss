package com.systech.mss.repository.impl;

import com.systech.mss.domain.MobileMoneyConfig;
import com.systech.mss.domain.StatusConfig;
import com.systech.mss.repository.MobileMoneyRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class MobileMoneyRepositoryImpl extends AbstractRepositoryImpl<MobileMoneyConfig, Long> implements MobileMoneyRepository {

    @Inject
    private EntityManager em;

    public MobileMoneyRepositoryImpl() {
        super(MobileMoneyConfig.class);
    }

    @Override
    public MobileMoneyConfig getActiveConfig() {
        try {
            String sql = "FROM MobileMoneyConfig cf WHERE cf.status=:status ORDER BY cf.id DESC";
            Query q = getEntityManager().createQuery(sql, MobileMoneyConfig.class);
            q.setParameter("status", StatusConfig.ACTIVE);
            q.setMaxResults(1);
            return (MobileMoneyConfig) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
