package com.systech.mss.repository.impl;

import com.systech.mss.controller.vm.PermissionsClaimApproverVM;
import com.systech.mss.domain.PermissionsClaimsApprover;
import com.systech.mss.domain.PermissionsClaimsOfficer;
import com.systech.mss.repository.PermissionsClaimApproverRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class PermissionsClaimApproverRepositoryImpl extends AbstractRepositoryImpl<PermissionsClaimsApprover, Long> implements PermissionsClaimApproverRepository {


    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermissionsClaimApproverRepositoryImpl() {
        super(PermissionsClaimsApprover.class);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsClaimsApprover getPermissions() {
        try {
            String sql = "FROM PermissionsClaimsApprover p ORDER BY p.id DESC";
            Query query = getEntityManager().createQuery(sql, PermissionsClaimsApprover.class);
            query.setMaxResults(1);
            return (PermissionsClaimsApprover) query.getSingleResult();
        } catch (Exception e) {
            return this.setDefault();
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsClaimsApprover setDefault() {
        try {
            return create(new PermissionsClaimsApprover());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsClaimsApprover update(PermissionsClaimApproverVM permissionsClaimApproverVM) {
        try {
            PermissionsClaimsApprover permissionsClaimsApprover = getPermissions();
            if (permissionsClaimsApprover != null) {
                permissionsClaimsApprover.setHomeMenuActivated(permissionsClaimApproverVM.isHomeMenuActivated());
                permissionsClaimsApprover.setPersonalInfoMenuActivated(permissionsClaimApproverVM.isPersonalInfoMenuActivated());
                permissionsClaimsApprover.setMembersMenuActivated(permissionsClaimApproverVM.isMembersMenuActivated());
                permissionsClaimsApprover.setStagedContributionsMenuActivated(permissionsClaimApproverVM.isStagedContributionsMenuActivated());
                permissionsClaimsApprover.setClaimsMenuActivated(permissionsClaimApproverVM.isClaimsMenuActivated());
                permissionsClaimsApprover.setBillsMenuActivated(permissionsClaimApproverVM.isBillsMenuActivated());
                permissionsClaimsApprover.setReceiptsMenuActivated(permissionsClaimApproverVM.isReceiptsMenuActivated());
                permissionsClaimsApprover.setDmsMenuActivated(permissionsClaimApproverVM.isDmsMenuActivated());
                permissionsClaimsApprover.setTicketsMenuActivated(permissionsClaimApproverVM.isTicketsMenuActivated());
                permissionsClaimsApprover.setUsersAccountMenuActivated(permissionsClaimApproverVM.isUsersAccountMenuActivated());
                permissionsClaimsApprover.setManageAccountMenuActivated(permissionsClaimApproverVM.isManageAccountMenuActivated());
                permissionsClaimsApprover.setAuditTrailMenuActivated(permissionsClaimApproverVM.isAuditTrailMenuActivated());
                permissionsClaimsApprover.setAllowBookBill(permissionsClaimApproverVM.isAllowBookBill());
                permissionsClaimsApprover.setAllowStageContributions(permissionsClaimApproverVM.isAllowStageContributions());
                permissionsClaimsApprover.setAllowAddSingleUser(permissionsClaimApproverVM.isAllowAddSingleUser());
                permissionsClaimsApprover.setAllowAddBatchUser(permissionsClaimApproverVM.isAllowAddBatchUser());
                permissionsClaimsApprover.setAllowApproveDocuments(permissionsClaimApproverVM.isAllowApproveDocuments());
                permissionsClaimsApprover.setTpfaMenuActivated(permissionsClaimApproverVM.isTpfaMenuActivated());
                permissionsClaimsApprover.setSponsorConfigMenuActivated(permissionsClaimApproverVM.isSponsorConfigMenuActivated());
                permissionsClaimsApprover.setBenefitsMenuActivated(permissionsClaimApproverVM.isBenefitsMenuActivated());
                return this.edit(permissionsClaimsApprover);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
