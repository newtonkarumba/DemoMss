package com.systech.mss.repository.impl;

import com.systech.mss.domain.StagedBeneficiaries;
import com.systech.mss.repository.StagedBeneficiariesRepository;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class StagedBeneficiariesRepositoryImpl extends AbstractRepositoryImpl<StagedBeneficiaries, Long> implements StagedBeneficiariesRepository {

    @Inject
    private EntityManager em;

    @Inject
    Logger log;

    public StagedBeneficiariesRepositoryImpl() {
        super(StagedBeneficiaries.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<StagedBeneficiaries> findByScheme(long schemeId) {
        try {
            Query q= getEntityManager().createNativeQuery("select * from stagedbeneficiaries s where s.schemeId=:schemeId",
                    StagedBeneficiaries.class);
            q.setParameter("schemeId",schemeId);
            return q.getResultList();
        } catch (Exception e) {
           return new ArrayList<>();
        }
    }

    @Override
    public List<StagedBeneficiaries> findBySchemeAndSponsor(long schemeId, long sponsorId) {
        try {
            Query q= getEntityManager().createNativeQuery("select * from stagedbeneficiaries s " +
                            "where (s.schemeId=:schemeId) and (s.sponsorId=:sponsorId)",
                    StagedBeneficiaries.class);
            q.setParameter("schemeId",schemeId);
            q.setParameter("sponsorId",sponsorId);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
