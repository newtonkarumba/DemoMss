package com.systech.mss.repository.impl;

import com.systech.mss.domain.EmailTemplates;
import com.systech.mss.domain.EmailTemplatesEnum;
import com.systech.mss.repository.EmailTemplatesRepository;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class EmailTemplatesRepositoryImpl extends AbstractRepositoryImpl<EmailTemplates, Long> implements EmailTemplatesRepository {


    @Inject
    Logger log;
    @Inject
    private EntityManager em;

    public EmailTemplatesRepositoryImpl() {
        super(EmailTemplates.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public EmailTemplates findByEmailTemplatesEnum(EmailTemplatesEnum emailTemplatesEnum) {
        try {
            Query q = getEntityManager().createQuery("FROM EmailTemplates  e WHERE e.templatesType=:e", EmailTemplates.class);
            q.setParameter("e", emailTemplatesEnum);
            q.setMaxResults(1);
            return (EmailTemplates) q.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
