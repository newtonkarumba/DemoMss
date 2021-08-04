package com.systech.mss.repository.impl;

import com.systech.mss.controller.vm.PermissionsCrmVM;
import com.systech.mss.domain.PermissionsCRM;
import com.systech.mss.repository.PermissionsCrmRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class PermissionsCrmRepositoryImpl   extends AbstractRepositoryImpl<PermissionsCRM, Long> implements PermissionsCrmRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermissionsCrmRepositoryImpl() {
        super(PermissionsCRM.class);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsCRM getPermissions() {
        try {
            String sql = "FROM PermissionsCRM p ORDER BY p.id DESC";
            Query query = getEntityManager().createQuery(sql, PermissionsCRM.class);
            query.setMaxResults(1);
            return (PermissionsCRM) query.getSingleResult();
        } catch (Exception e) {
            return this.setDefault();
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsCRM setDefault() {
        try {
//            List<PermissionsCRM> list = findAll();
//            if (list != null) {
//                for (PermissionsCRM p : list) {
//                    remove(p);
//                }
//            }
            return create(new PermissionsCRM());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsCRM update(PermissionsCrmVM crmVM) {
        try {
            PermissionsCRM PermissionsCRM = getPermissions();
            if (PermissionsCRM != null) {
                PermissionsCRM.setHomeMenuActivated(crmVM.isHomeMenuActivated());
                PermissionsCRM.setSponsorMenuActivated(crmVM.isSponsorMenuActivated());
                PermissionsCRM.setClaimsMenuActivated(crmVM.isClaimsMenuActivated());
                PermissionsCRM.setTicketsMenuActivated(crmVM.isTicketsMenuActivated());
                PermissionsCRM.setManageAccountMenuActivated(crmVM.isManageAccountMenuActivated());
                PermissionsCRM.setAuditTrailMenuActivated(crmVM.isAuditTrailMenuActivated());
                PermissionsCRM.setApproveClaimAuthorizerActivated(crmVM.isApproveClaimAuthorizerActivated());
                return this.edit(PermissionsCRM);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
