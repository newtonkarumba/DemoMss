package com.systech.mss.repository.impl;

import com.systech.mss.controller.vm.PermissionsSponsorVM;
import com.systech.mss.domain.PermissionsSponsor;
import com.systech.mss.repository.PermissionsSponsorRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class PermissionsSponsorRepositoryImp extends AbstractRepositoryImpl<PermissionsSponsor, Long> implements PermissionsSponsorRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermissionsSponsorRepositoryImp() {
        super(PermissionsSponsor.class);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsSponsor getPermissions() {
        try {
            String sql = "FROM PermissionsSponsor p ORDER BY p.id DESC";
            Query query = getEntityManager().createQuery(sql, PermissionsSponsor.class);
            query.setMaxResults(1);
            return (PermissionsSponsor) query.getSingleResult();
        } catch (Exception e) {
            return this.setDefault();
        }
    }


    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsSponsor setDefault() {
        try {
//            List<PermissionsSponsor> list = findAll();
//            if (list != null) {
//                for (PermissionsSponsor p : list) {
//                    remove(p);
//                }
//            }
            return create(new PermissionsSponsor());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsSponsor update(PermissionsSponsorVM sponsorVM) {
        try {
            PermissionsSponsor PermissionsSponsor = getPermissions();
            if (PermissionsSponsor != null) {
                PermissionsSponsor.setHomeMenuActivated(sponsorVM.isHomeMenuActivated());
                PermissionsSponsor.setPersonalInfoMenuActivated(sponsorVM.isPersonalInfoMenuActivated());
                PermissionsSponsor.setMembersMenuActivated(sponsorVM.isMembersMenuActivated());
                PermissionsSponsor.setStagedContributionsMenuActivated(sponsorVM.isStagedContributionsMenuActivated());
                PermissionsSponsor.setClaimsMenuActivated(sponsorVM.isClaimsMenuActivated());
                PermissionsSponsor.setBillsMenuActivated(sponsorVM.isBillsMenuActivated());
                PermissionsSponsor.setReceiptsMenuActivated(sponsorVM.isReceiptsMenuActivated());
                PermissionsSponsor.setDmsMenuActivated(sponsorVM.isDmsMenuActivated());
                PermissionsSponsor.setTicketsMenuActivated(sponsorVM.isTicketsMenuActivated());
                PermissionsSponsor.setUsersAccountMenuActivated(sponsorVM.isUsersAccountMenuActivated());
                PermissionsSponsor.setManageAccountMenuActivated(sponsorVM.isManageAccountMenuActivated());
                PermissionsSponsor.setAuditTrailMenuActivated(sponsorVM.isAuditTrailMenuActivated());
                PermissionsSponsor.setAllowBookBill(sponsorVM.isAllowBookBill());
                PermissionsSponsor.setAllowStageContributions(sponsorVM.isAllowStageContributions());
                PermissionsSponsor.setAllowAddSingleUser(sponsorVM.isAllowAddSingleUser());
                PermissionsSponsor.setAllowAddBatchUser(sponsorVM.isAllowAddBatchUser());
                PermissionsSponsor.setAllowApproveDocuments(sponsorVM.isAllowApproveDocuments());
                PermissionsSponsor.setTpfaMenuActivated(sponsorVM.isTpfaMenuActivated());
                PermissionsSponsor.setSponsorConfigMenuActivated(sponsorVM.isSponsorConfigMenuActivated());
                PermissionsSponsor.setBenefitsMenuActivated(sponsorVM.isBenefitsMenuActivated());
                return this.edit(PermissionsSponsor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
