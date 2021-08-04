package com.systech.mss.repository.impl;

import com.systech.mss.controller.vm.PermissionsBillingOfficerVM;
import com.systech.mss.controller.vm.PermissionsClaimReviewerVM;
import com.systech.mss.controller.vm.PermissionsClaimsOfficerVM;
import com.systech.mss.domain.PermissionsBillingOfficer;
import com.systech.mss.domain.PermissionsClaimsOfficer;
import com.systech.mss.domain.PermissionsClaimsReviewer;
import com.systech.mss.repository.PermissionsBillingOfficerRepository;
import com.systech.mss.repository.PermissionsClaimOfficerRepository;
import com.systech.mss.repository.PermissionsClaimsReviewerRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class PermissionsClaimsReviewerRepositoryImpl extends AbstractRepositoryImpl<PermissionsClaimsReviewer, Long> implements PermissionsClaimsReviewerRepository {
    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermissionsClaimsReviewerRepositoryImpl() {
        super(PermissionsClaimsReviewer.class);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsClaimsReviewer getPermissions() {
        try {
            String sql = "FROM PermissionsClaimsReviewer p ORDER BY p.id DESC";
            Query query = getEntityManager().createQuery(sql, PermissionsClaimsReviewer.class);
            query.setMaxResults(1);
            return (PermissionsClaimsReviewer) query.getSingleResult();
        } catch (Exception e) {
            return this.setDefault();
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsClaimsReviewer setDefault() {
        try {
            return create(new PermissionsClaimsReviewer());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsClaimsReviewer update(PermissionsClaimReviewerVM permissionsClaimReviewerVM) {
        try {
            PermissionsClaimsReviewer permissionsClaimsReviewer = getPermissions();
            if (permissionsClaimsReviewer != null) {
                permissionsClaimsReviewer.setHomeMenuActivated(permissionsClaimReviewerVM.isHomeMenuActivated());
                permissionsClaimsReviewer.setPersonalInfoMenuActivated(permissionsClaimReviewerVM.isPersonalInfoMenuActivated());
                permissionsClaimsReviewer.setMembersMenuActivated(permissionsClaimReviewerVM.isMembersMenuActivated());
                permissionsClaimsReviewer.setStagedContributionsMenuActivated(permissionsClaimReviewerVM.isStagedContributionsMenuActivated());
                permissionsClaimsReviewer.setClaimsMenuActivated(permissionsClaimReviewerVM.isClaimsMenuActivated());
                permissionsClaimsReviewer.setBillsMenuActivated(permissionsClaimReviewerVM.isBillsMenuActivated());
                permissionsClaimsReviewer.setReceiptsMenuActivated(permissionsClaimReviewerVM.isReceiptsMenuActivated());
                permissionsClaimsReviewer.setDmsMenuActivated(permissionsClaimReviewerVM.isDmsMenuActivated());
                permissionsClaimsReviewer.setTicketsMenuActivated(permissionsClaimReviewerVM.isTicketsMenuActivated());
                permissionsClaimsReviewer.setUsersAccountMenuActivated(permissionsClaimReviewerVM.isUsersAccountMenuActivated());
                permissionsClaimsReviewer.setManageAccountMenuActivated(permissionsClaimReviewerVM.isManageAccountMenuActivated());
                permissionsClaimsReviewer.setAuditTrailMenuActivated(permissionsClaimReviewerVM.isAuditTrailMenuActivated());
                permissionsClaimsReviewer.setAllowBookBill(permissionsClaimReviewerVM.isAllowBookBill());
                permissionsClaimsReviewer.setAllowStageContributions(permissionsClaimReviewerVM.isAllowStageContributions());
                permissionsClaimsReviewer.setAllowAddSingleUser(permissionsClaimReviewerVM.isAllowAddSingleUser());
                permissionsClaimsReviewer.setAllowAddBatchUser(permissionsClaimReviewerVM.isAllowAddBatchUser());
                permissionsClaimsReviewer.setAllowApproveDocuments(permissionsClaimReviewerVM.isAllowApproveDocuments());
                permissionsClaimsReviewer.setTpfaMenuActivated(permissionsClaimReviewerVM.isTpfaMenuActivated());
                permissionsClaimsReviewer.setSponsorConfigMenuActivated(permissionsClaimReviewerVM.isSponsorConfigMenuActivated());
                permissionsClaimsReviewer.setBenefitsMenuActivated(permissionsClaimReviewerVM.isBenefitsMenuActivated());


                return this.edit(permissionsClaimsReviewer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
