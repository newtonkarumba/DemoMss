package com.systech.mss.repository.impl;


import com.systech.mss.domain.UserSponsor;
import com.systech.mss.repository.SponsorUserRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;


import static java.util.Collections.singletonMap;

public class SponsorUserRepositoryImpl extends AbstractRepositoryImpl<UserSponsor,Long> implements SponsorUserRepository {

    @Inject
    private EntityManager em;

    public SponsorUserRepositoryImpl() {
        super(UserSponsor.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<UserSponsor> getSponsorUsers() {
        return findAll();
    }

    @Override
    public List<UserSponsor> getSponsorUserBySponsorId(long sponsorId) {
        return findByNamedQuery("findSponsorUserBySponsorId", singletonMap("sponsorId", sponsorId));
    }

    @Override
    public List<UserSponsor> getSponsorUserByName(String name) {
        return findByNamedQuery("findSponsorUserByName", singletonMap("name", name));
    }


}
