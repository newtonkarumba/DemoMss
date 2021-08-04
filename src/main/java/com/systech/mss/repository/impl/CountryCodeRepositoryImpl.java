package com.systech.mss.repository.impl;

import com.systech.mss.domain.Config;
import com.systech.mss.domain.CountryCode;
import com.systech.mss.domain.StatusConfig;
import com.systech.mss.repository.CountryCodeRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static java.util.Collections.singletonMap;

public class CountryCodeRepositoryImpl extends AbstractRepositoryImpl<CountryCode, Long> implements CountryCodeRepository {

    @Inject
    private EntityManager em;

    public CountryCodeRepositoryImpl() {
        super(CountryCode.class);
    }

    @Override
    public CountryCode getActiveConfig() {
        try {
            String sql = "FROM CountryCode  cc WHERE cc.statusConfig=:countrycode ORDER BY  cc.id DESC";
            Query query = getEntityManager().createQuery(sql, CountryCode.class);
            query.setParameter("countrycode", StatusConfig.ACTIVE);
            query.setMaxResults(1);
            return (CountryCode) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<CountryCode> getCountryCode() {
        return findAll();
    }

    public List<CountryCode> getCountryCodeById(long id) {
        return findByNamedQuery("findCountryCodeById", singletonMap("Id", id));
    }
}


