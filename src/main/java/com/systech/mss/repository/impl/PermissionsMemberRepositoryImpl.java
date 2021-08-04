package com.systech.mss.repository.impl;

import com.systech.mss.controller.vm.PermissionsMemberVM;
import com.systech.mss.domain.PermissionsMember;
import com.systech.mss.repository.PermissionsMemberRepository;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class PermissionsMemberRepositoryImpl extends AbstractRepositoryImpl<PermissionsMember, Long> implements PermissionsMemberRepository {

    @Inject
    Logger log;

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PermissionsMemberRepositoryImpl() {
        super(PermissionsMember.class);
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsMember getPermissions() {
        try {
            String sql = "FROM PermissionsMember p ORDER BY p.id DESC";
            Query query = getEntityManager().createQuery(sql, PermissionsMember.class);
            query.setMaxResults(1);
            return (PermissionsMember) query.getSingleResult();
        } catch (Exception e) {
            return this.setDefault();
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsMember setDefault() {
        try {
//            Query query=getEntityManager().createQuery("SELECT p.id FROM PermissionsMember p");
//            List<Object> list=query.getResultList();
//            if (list!=null && !list.isEmpty()){
//                log.error(list.toString());
//                for (Object o : list) {
//                    query=getEntityManager().createQuery("DELETE  FROM PermissionsMember p WHERE p.id=:id")
//                            .setParameter("id",o);
//                    query.executeUpdate();
//                }
//            }
            return create(new PermissionsMember());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public PermissionsMember update(PermissionsMemberVM memberVM) {
        try {
            PermissionsMember permissionsMember = getPermissions();
            if (permissionsMember != null) {
                permissionsMember.setHomeMenuActivated(memberVM.isHomeMenuActivated());
                permissionsMember.setBalancesMenuActivated(memberVM.isBalancesMenuActivated());
                permissionsMember.setClaimsMenuActivated(memberVM.isClaimsMenuActivated());
                permissionsMember.setDocumentsMenuActivated(memberVM.isDocumentsMenuActivated());
                permissionsMember.setTicketsMenuActivated(memberVM.isTicketsMenuActivated());
                permissionsMember.setContributionsMenuActivated(memberVM.isContributionsMenuActivated());
                permissionsMember.setProjectionsMenuActivated(memberVM.isProjectionsMenuActivated());
                permissionsMember.setAuditTrailMenuActivated(memberVM.isAuditTrailMenuActivated());
                permissionsMember.setManageAccountMenuActivated(memberVM.isManageAccountMenuActivated());
                permissionsMember.setPersonalInfoMenuActivated(memberVM.isPersonalInfoMenuActivated());
                permissionsMember.setAllowInitiateClaim(memberVM.isAllowInitiateClaim());
                permissionsMember.setAllowMakeContributions(memberVM.isAllowMakeContributions());
                permissionsMember.setAllowRequestStatement(memberVM.isAllowRequestStatement());
                permissionsMember.setAllowStkPush(memberVM.isAllowStkPush());
                permissionsMember.setAllowBenefitCalculator(memberVM.isAllowBenefitCalculator());
                permissionsMember.setAllowWhatIfAnalysis(memberVM.isAllowWhatIfAnalysis());
                permissionsMember.setBankMenuActivated(memberVM.isBankMenuActivated());
                permissionsMember.setBenefitsFmActivated(memberVM.isBenefitsFmActivated());
                return this.edit(permissionsMember);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
