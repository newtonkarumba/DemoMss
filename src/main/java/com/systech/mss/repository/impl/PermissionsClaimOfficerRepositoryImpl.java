package com.systech.mss.repository.impl;

import com.systech.mss.controller.vm.PermissionsBillingOfficerVM;
import com.systech.mss.controller.vm.PermissionsClaimReviewerVM;
import com.systech.mss.controller.vm.PermissionsClaimsOfficerVM;
import com.systech.mss.domain.PermissionsBillingOfficer;
import com.systech.mss.domain.PermissionsClaimsOfficer;
import com.systech.mss.domain.PermissionsClaimsReviewer;
import com.systech.mss.repository.PermissionsBillingOfficerRepository;
import com.systech.mss.repository.PermissionsClaimOfficerRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class PermissionsClaimOfficerRepositoryImpl extends AbstractRepositoryImpl<PermissionsClaimsOfficer, Long> implements PermissionsClaimOfficerRepository {
    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermissionsClaimOfficerRepositoryImpl() {
        super(PermissionsClaimsOfficer.class);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsClaimsOfficer getPermissions() {
        try {
            String sql = "FROM PermissionsClaimsOfficer p ORDER BY p.id DESC";
            Query query = getEntityManager().createQuery(sql, PermissionsClaimsOfficer.class);
            query.setMaxResults(1);
            return (PermissionsClaimsOfficer) query.getSingleResult();
        } catch (Exception e) {
            return this.setDefault();
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsClaimsOfficer setDefault() {
        try {
            return create(new PermissionsClaimsOfficer());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsClaimsOfficer update(PermissionsClaimsOfficerVM permissionsClaimsOfficerVM) {
        try {
            PermissionsClaimsOfficer permissionsClaimsOfficer = getPermissions();
            if (permissionsClaimsOfficer != null) {
                permissionsClaimsOfficer.setHomeMenuActivated(permissionsClaimsOfficerVM.isHomeMenuActivated());
                permissionsClaimsOfficer.setPersonalInfoMenuActivated(permissionsClaimsOfficerVM.isPersonalInfoMenuActivated());
                permissionsClaimsOfficer.setMembersMenuActivated(permissionsClaimsOfficerVM.isMembersMenuActivated());
                permissionsClaimsOfficer.setStagedContributionsMenuActivated(permissionsClaimsOfficerVM.isStagedContributionsMenuActivated());
                permissionsClaimsOfficer.setClaimsMenuActivated(permissionsClaimsOfficerVM.isClaimsMenuActivated());
                permissionsClaimsOfficer.setBillsMenuActivated(permissionsClaimsOfficerVM.isBillsMenuActivated());
                permissionsClaimsOfficer.setReceiptsMenuActivated(permissionsClaimsOfficerVM.isReceiptsMenuActivated());
                permissionsClaimsOfficer.setDmsMenuActivated(permissionsClaimsOfficerVM.isDmsMenuActivated());
                permissionsClaimsOfficer.setTicketsMenuActivated(permissionsClaimsOfficerVM.isTicketsMenuActivated());
                permissionsClaimsOfficer.setUsersAccountMenuActivated(permissionsClaimsOfficerVM.isUsersAccountMenuActivated());
                permissionsClaimsOfficer.setManageAccountMenuActivated(permissionsClaimsOfficerVM.isManageAccountMenuActivated());
                permissionsClaimsOfficer.setAuditTrailMenuActivated(permissionsClaimsOfficerVM.isAuditTrailMenuActivated());
                permissionsClaimsOfficer.setAllowBookBill(permissionsClaimsOfficerVM.isAllowBookBill());
                permissionsClaimsOfficer.setAllowStageContributions(permissionsClaimsOfficerVM.isAllowStageContributions());
                permissionsClaimsOfficer.setAllowAddSingleUser(permissionsClaimsOfficerVM.isAllowAddSingleUser());
                permissionsClaimsOfficer.setAllowAddBatchUser(permissionsClaimsOfficerVM.isAllowAddBatchUser());
                permissionsClaimsOfficer.setAllowApproveDocuments(permissionsClaimsOfficerVM.isAllowApproveDocuments());
                permissionsClaimsOfficer.setTpfaMenuActivated(permissionsClaimsOfficerVM.isTpfaMenuActivated());
                permissionsClaimsOfficer.setSponsorConfigMenuActivated(permissionsClaimsOfficerVM.isSponsorConfigMenuActivated());
                permissionsClaimsOfficer.setBenefitsMenuActivated(permissionsClaimsOfficerVM.isBenefitsMenuActivated());
                return this.edit(permissionsClaimsOfficer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
