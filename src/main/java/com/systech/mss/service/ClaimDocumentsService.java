package com.systech.mss.service;

import com.systech.mss.config.Constants;
import com.systech.mss.domain.ClaimDocuments;
import com.systech.mss.domain.Ticket;
import com.systech.mss.repository.ClaimDocumentsRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ClaimDocumentsService {

    @PersistenceContext(name = Constants.PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public List<ClaimDocuments> getClaimDocuments(long id){
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ClaimDocuments> query = criteriaBuilder.createQuery(ClaimDocuments.class);
        Root<ClaimDocuments> from = query.from(ClaimDocuments.class);
        query.select(from)
                .where(
                        criteriaBuilder.equal(
                                from.get("benefitRequestId"),
                                id
                        )
                );
        return getEntityManager().createQuery(query).getResultList();
    }
}
