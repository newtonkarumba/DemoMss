package com.systech.mss.repository.impl;

import com.systech.mss.controller.vm.PermissionsClaimsAuthorizerVM;
import com.systech.mss.controller.vm.PermissionsClaimsOfficerVM;
import com.systech.mss.domain.PermissionsClaimsAuthorizer;
import com.systech.mss.domain.PermissionsClaimsOfficer;
import com.systech.mss.domain.PermissionsClaimsReviewer;
import com.systech.mss.repository.PermissionsClaimsAuthorizerRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class PermissionsClaimsAuthorizerRepositoryImpl extends AbstractRepositoryImpl<PermissionsClaimsAuthorizer, Long> implements PermissionsClaimsAuthorizerRepository {
    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermissionsClaimsAuthorizerRepositoryImpl() {
        super(PermissionsClaimsAuthorizer.class);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsClaimsAuthorizer getPermissions() {
        try {
            String sql = "FROM PermissionsClaimsAuthorizer p ORDER BY p.id DESC";
            Query query = getEntityManager().createQuery(sql, PermissionsClaimsAuthorizer.class);
            query.setMaxResults(1);
            return (PermissionsClaimsAuthorizer) query.getSingleResult();
        } catch (Exception e) {
            return this.setDefault();
        }
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public PermissionsClaimsAuthorizer setDefault() {
        try {
            return create(new PermissionsClaimsAuthorizer());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public PermissionsClaimsAuthorizer update(PermissionsClaimsAuthorizerVM permissionsClaimsAuthorizerVM) {
        try {
            PermissionsClaimsAuthorizer permissionsClaimsAuthorizer = getPermissions();
            if (permissionsClaimsAuthorizer != null) {
                permissionsClaimsAuthorizer.setHomeMenuActivated(permissionsClaimsAuthorizer.isHomeMenuActivated());
                permissionsClaimsAuthorizer.setPersonalInfoMenuActivated(permissionsClaimsAuthorizerVM.isPersonalInfoMenuActivated());
                permissionsClaimsAuthorizer.setMembersMenuActivated(permissionsClaimsAuthorizerVM.isMembersMenuActivated());
                permissionsClaimsAuthorizer.setStagedContributionsMenuActivated(permissionsClaimsAuthorizerVM.isStagedContributionsMenuActivated());
                permissionsClaimsAuthorizer.setClaimsMenuActivated(permissionsClaimsAuthorizerVM.isClaimsMenuActivated());
                permissionsClaimsAuthorizer.setBillsMenuActivated(permissionsClaimsAuthorizerVM.isBillsMenuActivated());
                permissionsClaimsAuthorizer.setReceiptsMenuActivated(permissionsClaimsAuthorizerVM.isReceiptsMenuActivated());
                permissionsClaimsAuthorizer.setDmsMenuActivated(permissionsClaimsAuthorizerVM.isDmsMenuActivated());
                permissionsClaimsAuthorizer.setTicketsMenuActivated(permissionsClaimsAuthorizerVM.isTicketsMenuActivated());
                permissionsClaimsAuthorizer.setUsersAccountMenuActivated(permissionsClaimsAuthorizerVM.isUsersAccountMenuActivated());
                permissionsClaimsAuthorizer.setManageAccountMenuActivated(permissionsClaimsAuthorizerVM.isManageAccountMenuActivated());
                permissionsClaimsAuthorizer.setAuditTrailMenuActivated(permissionsClaimsAuthorizerVM.isAuditTrailMenuActivated());
                permissionsClaimsAuthorizer.setAllowBookBill(permissionsClaimsAuthorizerVM.isAllowBookBill());
                permissionsClaimsAuthorizer.setAllowStageContributions(permissionsClaimsAuthorizerVM.isAllowStageContributions());
                permissionsClaimsAuthorizer.setAllowAddSingleUser(permissionsClaimsAuthorizerVM.isAllowAddSingleUser());
                permissionsClaimsAuthorizer.setAllowAddBatchUser(permissionsClaimsAuthorizerVM.isAllowAddBatchUser());
                permissionsClaimsAuthorizer.setAllowApproveDocuments(permissionsClaimsAuthorizerVM.isAllowApproveDocuments());
                permissionsClaimsAuthorizer.setTpfaMenuActivated(permissionsClaimsAuthorizerVM.isTpfaMenuActivated());
                permissionsClaimsAuthorizer.setSponsorConfigMenuActivated(permissionsClaimsAuthorizerVM.isSponsorConfigMenuActivated());
                permissionsClaimsAuthorizer.setBenefitsMenuActivated(permissionsClaimsAuthorizerVM.isBenefitsMenuActivated());
                return this.edit(permissionsClaimsAuthorizer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
