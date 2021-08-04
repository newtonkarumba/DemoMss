package com.systech.mss.repository.impl;

import com.systech.mss.controller.vm.PermissionsPoVM;
import com.systech.mss.domain.PermissionsPrincipalOfficer;
import com.systech.mss.repository.PermissionsPORepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class PermissionsPoRepositoryImp extends AbstractRepositoryImpl<PermissionsPrincipalOfficer, Long> implements PermissionsPORepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermissionsPoRepositoryImp() {
        super(PermissionsPrincipalOfficer.class);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsPrincipalOfficer getPermissions() {
        try {
            String sql = "FROM PermissionsPrincipalOfficer p ORDER BY p.id DESC";
            Query query = getEntityManager().createQuery(sql, PermissionsPrincipalOfficer.class);
            query.setMaxResults(1);
            return (PermissionsPrincipalOfficer) query.getSingleResult();
        } catch (Exception e) {
            return this.setDefault();
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsPrincipalOfficer setDefault() {
        try {
//            List<PermissionsPrincipalOfficer> list
//                    =findAll();
//            if (list!=null){
//                for (PermissionsPrincipalOfficer permissionsPrincipalOfficer : list) {
//                    remove(permissionsPrincipalOfficer);
//                }
//            }
            return create(new PermissionsPrincipalOfficer());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsPrincipalOfficer update(PermissionsPoVM poVM) {
        try {
            PermissionsPrincipalOfficer permissionsPrincipalOfficer = getPermissions();
            if (permissionsPrincipalOfficer != null) {
                permissionsPrincipalOfficer.setHomeMenuActivated(poVM.isHomeMenuActivated());
                permissionsPrincipalOfficer.setPersonalInfoMenuActivated(poVM.isPersonalInfoMenuActivated());
                permissionsPrincipalOfficer.setMembersMenuActivated(poVM.isMembersMenuActivated());
                permissionsPrincipalOfficer.setSchemesMenuActivated(poVM.isSchemesMenuActivated());
                permissionsPrincipalOfficer.setClaimsMenuActivated(poVM.isClaimsMenuActivated());
                permissionsPrincipalOfficer.setStagedContributionsMenuActivated(poVM.isStagedContributionsMenuActivated());
                permissionsPrincipalOfficer.setBillsMenuActivated(poVM.isBillsMenuActivated());
                permissionsPrincipalOfficer.setReceiptsMenuActivated(poVM.isReceiptsMenuActivated());
                permissionsPrincipalOfficer.setDmsMenuActivated(poVM.isDmsMenuActivated());
                permissionsPrincipalOfficer.setTicketsMenuActivated(poVM.isTicketsMenuActivated());
                permissionsPrincipalOfficer.setUsersAccountMenuActivated(poVM.isUsersAccountMenuActivated());
                permissionsPrincipalOfficer.setManageAccountMenuActivated(poVM.isManageAccountMenuActivated());
                permissionsPrincipalOfficer.setAuditTrailMenuActivated(poVM.isAuditTrailMenuActivated());
                permissionsPrincipalOfficer.setAllowBookBill(poVM.isAllowBookBill());
                permissionsPrincipalOfficer.setAllowStageContributions(poVM.isAllowStageContributions());
                permissionsPrincipalOfficer.setAllowAddSingleUser(poVM.isAllowAddSingleUser());
                permissionsPrincipalOfficer.setAllowAddBatchUser(poVM.isAllowAddBatchUser());
                permissionsPrincipalOfficer.setAllowApproveDocuments(poVM.isAllowApproveDocuments());
                permissionsPrincipalOfficer.setAllowInitiateClaims(poVM.isAllowInitiateClaims());
                permissionsPrincipalOfficer.setAllowBenefitCalculator(poVM.isAllowBenefitCalculator());
                permissionsPrincipalOfficer.setAllowViewMemberContributions(poVM.isAllowViewMemberContributions());
                return this.edit(permissionsPrincipalOfficer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
