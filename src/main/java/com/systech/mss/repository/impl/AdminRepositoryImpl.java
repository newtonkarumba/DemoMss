package com.systech.mss.repository.impl;

import com.systech.mss.domain.Admins;
import com.systech.mss.domain.User;
import com.systech.mss.repository.AdminRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.singletonMap;

public  class AdminRepositoryImpl extends AbstractRepositoryImpl<Admins, Long> implements AdminRepository  {
    @Inject
    private EntityManager em;

    public AdminRepositoryImpl() {
        super(Admins.class);
    }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public Optional<Admins> findOneByStaffNo(String StaffNo) {
        return Optional.empty();
    }

    @Override
    public List<Admins> getAdmins() {
        return findAll();
    }

    @Override
    public List<Admins> getAdminById(long id) {
        return findByNamedQuery("findAdminById", singletonMap("Id", id));
    }


}
