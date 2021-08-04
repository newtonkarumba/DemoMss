package com.systech.mss.repository.impl;

import com.systech.mss.domain.StageMemberDetails;
import com.systech.mss.repository.StageMemberDetailsRepository;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class StageMemberDetailsRepositoryImpl extends AbstractRepositoryImpl<StageMemberDetails, Long> implements StageMemberDetailsRepository {

    @Inject
    private EntityManager em;

    @Inject
    Logger log;

    public StageMemberDetailsRepositoryImpl() {
        super(StageMemberDetails.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public StageMemberDetails findByMemberId(long memberId) {
        try {
            Query query = getEntityManager().createQuery("FROM  StageMemberDetails  s WHERE  s.memberId=:memberId", StageMemberDetails.class);
            query.setParameter("memberId", memberId);
            return (StageMemberDetails) query.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public List<StageMemberDetails> findByScheme(long schemeId) {
        try {
            Query q = getEntityManager().createNativeQuery("select * from stagememberdetails s where s.schemeId=:schemeId",
                    StageMemberDetails.class);
            q.setParameter("schemeId", schemeId);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<StageMemberDetails> findBySchemeAndSponsor(long schemeId, long sponsorId) {
        try {
            Query q = getEntityManager().createNativeQuery("select * from stagememberdetails s where " +
                            "(s.schemeId=:schemeId) and (s.sponsorId=:sponsorId)",
                    StageMemberDetails.class);
            q.setParameter("schemeId", schemeId);
            q.setParameter("sponsorId", sponsorId);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
