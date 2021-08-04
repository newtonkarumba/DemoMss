package com.systech.mss.repository.impl;

import com.systech.mss.domain.OtpConfig;
import com.systech.mss.domain.OtpIdentifier;
import com.systech.mss.repository.OtpConfigRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class OtpConfigRepositoryImpl extends AbstractRepositoryImpl<OtpConfig,Long> implements OtpConfigRepository {

    @Inject
    private EntityManager em;

    public OtpConfigRepositoryImpl() {
        super(OtpConfig.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<OtpConfig> findEnabled() {
        try {
            Query query=getEntityManager().createQuery("FROM OtpConfig o WHERE o.enabled=:enabled");
            query.setParameter("enabled",true);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OtpConfig findByIdentifier(OtpIdentifier identifier) {
        try {
            Query query=getEntityManager().createQuery("FROM OtpConfig o WHERE o.otpIdentifier=:identifier");
            query.setParameter("identifier",identifier);
            query.setMaxResults(1);
            return (OtpConfig) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
