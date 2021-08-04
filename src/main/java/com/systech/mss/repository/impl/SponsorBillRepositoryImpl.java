package com.systech.mss.repository.impl;

import com.systech.mss.domain.Session;
import com.systech.mss.domain.SponsorBill;
import com.systech.mss.repository.SponsorBillRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

public class SponsorBillRepositoryImpl extends AbstractRepositoryImpl<SponsorBill, Long> implements SponsorBillRepository {

    @Inject
    private EntityManager em;

    public SponsorBillRepositoryImpl() {
        super(SponsorBill.class);
    }


    @Override
    protected EntityManager getEntityManager() {
        return null;
    }
}
