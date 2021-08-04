package com.systech.mss.repository.impl;


import com.systech.mss.domain.MailConfig;
import com.systech.mss.repository.MailConfigRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

public class MailConfigRepositoryImpl extends AbstractRepositoryImpl<MailConfig, Long> implements MailConfigRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MailConfigRepositoryImpl() {
        super(MailConfig.class);
    }

    @Override
    public List<MailConfig> getMailConfigs() {
        return findAll();
    }

    @Override
    public MailConfig getActiveMailConfigs() {
        List<MailConfig> mailConfigs = getMailConfigs();
        if (mailConfigs != null && !mailConfigs.isEmpty()) {
            Collections.reverse(mailConfigs);
            return mailConfigs.get(0);
        }
        return null;
    }
}
