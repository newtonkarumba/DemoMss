package com.systech.mss.repository.impl;

import com.systech.mss.domain.Profile;
import com.systech.mss.repository.ProfileRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class ProfileRepositoryImpl extends AbstractRepositoryImpl<Profile, Long> implements ProfileRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProfileRepositoryImpl() {
        super(Profile.class);
    }

    @Override
    public Profile findByName(String name) {
        try {
            return em.createQuery("FROM Profile p WHERE p.name=:name", Profile.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
