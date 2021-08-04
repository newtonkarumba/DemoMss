package com.systech.mss.repository.impl;

import com.systech.mss.controller.vm.PermissionsCREVM;
import com.systech.mss.domain.PermissionsCRE;
import com.systech.mss.repository.PermissionsCRERepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class PermissionsCRERepositoryImpl extends AbstractRepositoryImpl<PermissionsCRE, Long> implements PermissionsCRERepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermissionsCRERepositoryImpl() {
        super(PermissionsCRE.class);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsCRE getPermissions() {
        try {
            String sql = "FROM PermissionsCRE p ORDER BY p.id DESC";
            Query query = getEntityManager().createQuery(sql, PermissionsCRE.class);
            query.setMaxResults(1);
            return (PermissionsCRE) query.getSingleResult();
        } catch (Exception e) {
            return this.setDefault();
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsCRE setDefault() {
        try {
//            List<PermissionsCRE> list = findAll();
//            if (list != null) {
//                for (PermissionsCRE permissionsCRE : list) {
//                    remove(permissionsCRE);
//                }
//            }
            return create(new PermissionsCRE());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsCRE update(PermissionsCREVM crevm) {
        try {
            PermissionsCRE permissionsCRE = getPermissions();
            if (permissionsCRE != null) {
                permissionsCRE.setHomeMenuActivated(crevm.isHomeMenuActivated());
                permissionsCRE.setSchemesMenuActivated(crevm.isSchemesMenuActivated());
                permissionsCRE.setAllowInitiateClaims(crevm.isAllowInitiateClaims());
                permissionsCRE.setAllowUploadDocs(crevm.isAllowUploadDocs());
                permissionsCRE.setTicketsMenuActivated(crevm.isTicketsMenuActivated());
                permissionsCRE.setManageAccountMenuActivated(crevm.isManageAccountMenuActivated());
                permissionsCRE.setAuditTrailMenuActivated(crevm.isAuditTrailMenuActivated());
                return this.edit(permissionsCRE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
