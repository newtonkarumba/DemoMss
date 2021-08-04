package com.systech.mss.repository.impl;

import com.systech.mss.controller.vm.DateValueVm;
import com.systech.mss.domain.DateValues;
import com.systech.mss.domain.PermissionsMember;
import com.systech.mss.repository.DateRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class DateRepositoryImpl extends AbstractRepositoryImpl<DateValues,Long> implements DateRepository {
    @Inject
    private EntityManager em;

    public DateRepositoryImpl() {
        super(DateValues.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public DateValues getDates() {
        try {
            String sql = "FROM DateValues d ORDER BY d.id DESC";
            Query query = getEntityManager().createQuery(sql, DateValues.class);
            query.setMaxResults(1);
            return (DateValues) query.getSingleResult();
        } catch (Exception e) {
            return this.setDefault();
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public DateValues update(long billDate) {
        try {
            DateValues dateValues = getDates();
            if (dateValues != null) {
                dateValues.setDateNumber(billDate);
                return this.edit(dateValues);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public DateValues setDefault() {
        try {
            Query query = getEntityManager()
                    .createNativeQuery("TRUNCATE TABLE " + DateValues.TB_NAME);
            query.executeUpdate();
            return create(new DateValues());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
