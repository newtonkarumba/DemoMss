package com.systech.mss.repository.impl;

import com.systech.mss.domain.Permission;
import com.systech.mss.repository.PermissionRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class PermissionRepositoryImpl extends AbstractRepositoryImpl<Permission,Long> implements PermissionRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermissionRepositoryImpl() {
        super(Permission.class);
    }
}
