package com.systech.mss.repository.impl;

import com.systech.mss.domain.Permission;
import com.systech.mss.domain.SponsorUser;
import com.systech.mss.repository.SponsorUserPermissionRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class SponsorUserPermissionRepositoryImpl extends AbstractRepositoryImpl<Permission,Long> implements SponsorUserPermissionRepository {

    @Inject
    private EntityManager em;

    public SponsorUserPermissionRepositoryImpl() {
        super(Permission.class);
    }

    @Override
    public List<Permission> getSponsorUsersPermissions() {
        return findAll();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
