package com.systech.mss.repository.impl;


import com.systech.mss.domain.Config;
import com.systech.mss.domain.EmailTemplates;
import com.systech.mss.domain.FAQ;
import com.systech.mss.domain.common.Clients;
import com.systech.mss.repository.ClientConfigRepository;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class ClientConfigRepositoryImpl extends AbstractRepositoryImpl<Config, Long> implements ClientConfigRepository {

    @Inject
    Logger log;
    @Inject
    private EntityManager em;

    public ClientConfigRepositoryImpl() {
        super(Config.class);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public Config findClientConfig(Clients clients) {
        try {
            Query q = getEntityManager().createQuery("FROM Config  c WHERE c.client=:c", Config.class);
            q.setParameter("c", clients);
            q.setMaxResults(1);
            return (Config) q.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public Config editClientConfig(Clients clients) {
        return null;
    }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
