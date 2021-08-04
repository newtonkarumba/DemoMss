package com.systech.smileIdentity.repository.impl;


import com.systech.mss.repository.impl.AbstractRepositoryImpl;
import com.systech.smileIdentity.model.SelfieResults;
import com.systech.smileIdentity.repository.SelfieResultsRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class SelfieResultsRepositoryImpl extends AbstractRepositoryImpl<SelfieResults,Long> implements SelfieResultsRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SelfieResultsRepositoryImpl() {
        super(SelfieResults.class);
    }

    @Override
    public SelfieResults getResultsByJobId(String jobId) {
        try {
            Query query = getEntityManager().createQuery(
                    "FROM SelfieResults m WHERE  m.jobId=:jobId ", SelfieResults.class
            );
            query.setParameter("jobId",jobId);
            return (SelfieResults) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
