package com.systech.mss.repository.impl;

import com.systech.mss.domain.LandingPageContent;
import com.systech.mss.domain.StatusConfig;
import com.systech.mss.repository.LandingPageContentRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class LandingPageContentRepositoryImpl extends AbstractRepositoryImpl<LandingPageContent, Long> implements LandingPageContentRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LandingPageContentRepositoryImpl() {
        super(LandingPageContent.class);
    }


    @Override
    public LandingPageContent getActiveLandingPageContent() {

        try {
            String sql = "FROM LandingPageContent l WHERE  l.statusConfig='ACTIVE'";
            Query q = getEntityManager().createQuery(sql, LandingPageContent.class);
            //q.setParameter("statusConfig", StatusConfig.ACTIVE);
            return (LandingPageContent) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public LandingPageContent createLandingPageContent(LandingPageContent landingPageContent) {
        String sql = "UPDATE LandingPageContent l SET l.statusConfig=:statusConfig";
        Query q = getEntityManager().createQuery(sql);
        q.setParameter("statusConfig", StatusConfig.INACTIVE);
        q.executeUpdate();
        return create(landingPageContent);
    }

}
