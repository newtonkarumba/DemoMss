package com.systech.mss.repository.impl;

import com.systech.mss.domain.FAQ;
import com.systech.mss.repository.FaqControllerRepository;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class FaqControllerRepositoryImpl extends AbstractRepositoryImpl<FAQ, Long> implements FaqControllerRepository {

    @Inject
    private EntityManager em;

    @Inject
    Logger log;

    public FaqControllerRepositoryImpl() {
        super(FAQ.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public List<FAQ> getAllByProfile(long profileId) {
        try {
            Query q = getEntityManager().createQuery("FROM FAQ q WHERE  q.profile.id=:id ORDER BY q.id DESC ");
            q.setParameter("id", profileId);
            return q.getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public FAQ addEditFAQ(FAQ faq) {
        try {
            if (faq.getId() != 0L) {
                return this.edit(faq);
            }
            return this.create(faq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
