package com.systech.mss.repository.impl;

import com.systech.mss.controller.vm.PermissionsPensionerVM;
import com.systech.mss.domain.PermissionsPensioner;
import com.systech.mss.repository.PermissionsPensionerRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class PermissionsPensionerRepositoryImpl extends AbstractRepositoryImpl<PermissionsPensioner, Long> implements PermissionsPensionerRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermissionsPensionerRepositoryImpl() {
        super(PermissionsPensioner.class);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsPensioner getPermissions() {
        try {
            String sql = "FROM PermissionsPensioner p ORDER BY p.id DESC";
            Query query = getEntityManager().createQuery(sql, PermissionsPensioner.class);
            query.setMaxResults(1);
            return (PermissionsPensioner) query.getSingleResult();
        } catch (Exception e) {
            return this.setDefault();
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsPensioner setDefault() {
        try {
//            List<PermissionsPensioner> list = findAll();
//            if (list != null) {
//                for (PermissionsPensioner p : list) {
//                    remove(p);
//                }
//            }
            return create(new PermissionsPensioner());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsPensioner update(PermissionsPensionerVM pensionerVM) {
        try {
            PermissionsPensioner PermissionsPensioner = getPermissions();
            if (PermissionsPensioner != null) {
                PermissionsPensioner.setHomeMenuActivated(pensionerVM.isHomeMenuActivated());
                PermissionsPensioner.setPersonalInfoMenuActivated(pensionerVM.isPersonalInfoMenuActivated());
                PermissionsPensioner.setPayrollsMenuActivated(pensionerVM.isPayrollsMenuActivated());
                PermissionsPensioner.setDeductionsMenuActivated(pensionerVM.isDeductionsMenuActivated());
                PermissionsPensioner.setCoeMenuActivated(pensionerVM.isCoeMenuActivated());
                PermissionsPensioner.setTicketsMenuActivated(pensionerVM.isTicketsMenuActivated());
                PermissionsPensioner.setManageAccountMenuActivated(pensionerVM.isManageAccountMenuActivated());
                PermissionsPensioner.setAuditTrailMenuActivated(pensionerVM.isAuditTrailMenuActivated());
                PermissionsPensioner.setCanRegisterSelfie(pensionerVM.isCanRegisterSelfie());
                PermissionsPensioner.setCanAuthenticateSelfie(pensionerVM.isCanAuthenticateSelfie());
                PermissionsPensioner.setCanUpdateSelfie(pensionerVM.isCanUpdateSelfie());
                return this.edit(PermissionsPensioner);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
