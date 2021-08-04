package com.systech.mss.repository.impl;

import com.systech.mss.domain.ClaimDocuments;
import com.systech.mss.repository.ClaimDocumentsRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class ClaimDocumentsRepositoryImpl extends AbstractRepositoryImpl<ClaimDocuments,Long> implements ClaimDocumentsRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClaimDocumentsRepositoryImpl() {
        super(ClaimDocuments.class);
    }

    @Override
    public List<ClaimDocuments> getByClaimId(long claimId) {
        try {
            Query q=getEntityManager().createQuery("FROM ClaimDocuments c WHERE c.benefitRequestId=:claimId",
                    ClaimDocuments.class);
            q.setParameter("claimId",claimId);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<ClaimDocuments> getByClaimIdAndReasonForExit(long claimId, String benefitReason) {
        try {
            String sql="select * from claimdocuments c where c.benefitRequestId=:claimId and c.reasonForExit=:reasonForExit";
            Query q=getEntityManager().createNativeQuery(sql,
                    ClaimDocuments.class);
            q.setParameter("claimId",claimId);
            q.setParameter("reasonForExit",benefitReason);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<ClaimDocuments> getUploadedClaimDocuments(long claimId, String benefitReason) {
        try {
//            String sql="select * from claimdocuments c where (c.benefitRequestId=:claimId) and (c.uploaded) and (c.reasonForExit=:benefitReason) group by c.checkListName";
            String sql="select * from claimdocuments c where (c.benefitRequestId=:claimId) and (c.uploaded) and (c.reasonForExit=:benefitReason)";
            Query q=getEntityManager().createNativeQuery(sql,
                    ClaimDocuments.class);
            q.setParameter("claimId",claimId);
            q.setParameter("benefitReason",benefitReason);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
