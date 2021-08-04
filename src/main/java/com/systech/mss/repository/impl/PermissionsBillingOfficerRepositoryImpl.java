package com.systech.mss.repository.impl;

import com.systech.mss.controller.vm.PermissionsBillingOfficerVM;
import com.systech.mss.controller.vm.PermissionsCREVM;
import com.systech.mss.domain.PermissionsBillingOfficer;
import com.systech.mss.domain.PermissionsCRE;
import com.systech.mss.domain.PermissionsSponsor;
import com.systech.mss.repository.PermissionsBillingOfficerRepository;
import com.systech.mss.repository.PermissionsCRERepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class PermissionsBillingOfficerRepositoryImpl  extends AbstractRepositoryImpl<PermissionsBillingOfficer, Long> implements PermissionsBillingOfficerRepository {
    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermissionsBillingOfficerRepositoryImpl() {
        super(PermissionsBillingOfficer.class);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsBillingOfficer getPermissions() {
        try {
            String sql = "FROM PermissionsBillingOfficer p ORDER BY p.id DESC";
            Query query = getEntityManager().createQuery(sql, PermissionsBillingOfficer.class);
            query.setMaxResults(1);
            return (PermissionsBillingOfficer) query.getSingleResult();
        } catch (Exception e) {
            return this.setDefault();
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsBillingOfficer setDefault() {
        try {
            return create(new PermissionsBillingOfficer());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsBillingOfficer update(PermissionsBillingOfficerVM permissionsBillingOfficerVM) {
        try {
            PermissionsBillingOfficer permissionsBillingOfficer = getPermissions();
            if (permissionsBillingOfficer != null) {
                permissionsBillingOfficer.setHomeMenuActivated(permissionsBillingOfficerVM.isHomeMenuActivated());
                permissionsBillingOfficer.setPersonalInfoMenuActivated(permissionsBillingOfficerVM.isPersonalInfoMenuActivated());
                permissionsBillingOfficer.setMembersMenuActivated(permissionsBillingOfficerVM.isMembersMenuActivated());
                permissionsBillingOfficer.setStagedContributionsMenuActivated(permissionsBillingOfficerVM.isStagedContributionsMenuActivated());
                permissionsBillingOfficer.setClaimsMenuActivated(permissionsBillingOfficerVM.isClaimsMenuActivated());
                permissionsBillingOfficer.setBillsMenuActivated(permissionsBillingOfficerVM.isBillsMenuActivated());
                permissionsBillingOfficer.setReceiptsMenuActivated(permissionsBillingOfficerVM.isReceiptsMenuActivated());
                permissionsBillingOfficer.setDmsMenuActivated(permissionsBillingOfficerVM.isDmsMenuActivated());
                permissionsBillingOfficer.setTicketsMenuActivated(permissionsBillingOfficerVM.isTicketsMenuActivated());
                permissionsBillingOfficer.setUsersAccountMenuActivated(permissionsBillingOfficerVM.isUsersAccountMenuActivated());
                permissionsBillingOfficer.setManageAccountMenuActivated(permissionsBillingOfficerVM.isManageAccountMenuActivated());
                permissionsBillingOfficer.setAuditTrailMenuActivated(permissionsBillingOfficerVM.isAuditTrailMenuActivated());
                permissionsBillingOfficer.setAllowBookBill(permissionsBillingOfficerVM.isAllowBookBill());
                permissionsBillingOfficer.setAllowStageContributions(permissionsBillingOfficerVM.isAllowStageContributions());
                permissionsBillingOfficer.setAllowAddSingleUser(permissionsBillingOfficerVM.isAllowAddSingleUser());
                permissionsBillingOfficer.setAllowAddBatchUser(permissionsBillingOfficerVM.isAllowAddBatchUser());
                permissionsBillingOfficer.setAllowApproveDocuments(permissionsBillingOfficerVM.isAllowApproveDocuments());
                permissionsBillingOfficer.setTpfaMenuActivated(permissionsBillingOfficerVM.isTpfaMenuActivated());
                permissionsBillingOfficer.setSponsorConfigMenuActivated(permissionsBillingOfficerVM.isSponsorConfigMenuActivated());
                permissionsBillingOfficer.setBenefitsMenuActivated(permissionsBillingOfficerVM.isBenefitsMenuActivated());
                return this.edit(permissionsBillingOfficer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
