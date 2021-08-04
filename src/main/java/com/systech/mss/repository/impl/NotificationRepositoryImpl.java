package com.systech.mss.repository.impl;

import com.systech.mss.domain.Notification;
import com.systech.mss.repository.NotificationRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class NotificationRepositoryImpl extends AbstractRepositoryImpl<Notification,Long> implements NotificationRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NotificationRepositoryImpl() {
        super(Notification.class);
    }
}
