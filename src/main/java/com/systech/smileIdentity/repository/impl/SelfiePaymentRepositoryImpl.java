package com.systech.smileIdentity.repository.impl;


import com.systech.mss.repository.impl.AbstractRepositoryImpl;
import com.systech.smileIdentity.Service.vm.SelfiePaymentVm;
import com.systech.smileIdentity.model.PaymentStatus;
import com.systech.smileIdentity.model.SelfieAction;
import com.systech.smileIdentity.model.SelfiePayment;
import com.systech.smileIdentity.repository.SelfiePaymentRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

public class SelfiePaymentRepositoryImpl extends AbstractRepositoryImpl<SelfiePayment,Long> implements SelfiePaymentRepository {

    @Inject
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SelfiePaymentRepositoryImpl() {
        super(SelfiePayment.class);
    }


    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public SelfiePayment save(SelfiePaymentVm selfiePaymentVm) {
        SelfiePayment selfiePayment=new SelfiePayment();
        try{
            selfiePayment.setUserId(selfiePaymentVm.getUserId());
            selfiePayment.setAction(selfiePaymentVm.getAction());
            selfiePayment.setPaybill(selfiePaymentVm.getPaybill());
            selfiePayment.setRequestId(selfiePaymentVm.getRequestId());
            selfiePayment.setAmount(BigDecimal.valueOf(selfiePaymentVm.getAmount()));
            selfiePayment.setPhoneNumber(selfiePaymentVm.getPhone());
            selfiePayment.setMpesaPhoneNumber(selfiePaymentVm.getFinalPhone());
            selfiePayment.setMerchantRequestID(selfiePaymentVm.getMerchantRequestID());
            selfiePayment.setTimestamp(selfiePaymentVm.getTimestamp());
            selfiePayment.setPassword(selfiePaymentVm.getPassword());
            return create(selfiePayment);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SelfiePayment getUserPendingOrUnusedPaymentPerAction(long userId, SelfieAction selfieAction) {
        try {
            String sql = "FROM SelfiePayment  s WHERE s.userId=:userId  AND s.action=:selfieAction AND  (s.paymentStatus=:pending or s.paymentStatus=:notUsed)  ORDER BY  s.id DESC";
            Query query = getEntityManager().createQuery(sql, SelfiePayment.class);
            query.setParameter("userId", userId);
            query.setParameter("selfieAction", selfieAction);
            query.setParameter("pending", PaymentStatus.PENDING);
            query.setParameter("notUsed", PaymentStatus.PAID_SUCCESSFUL_NOT_USED);
            query.setMaxResults(1);
            return (SelfiePayment) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean getUserUnusedPayments(long userId) {
        try {
            String sql = "FROM SelfiePayment  s WHERE s.userId=:userId and (s.paymentStatus=:pending or s.paymentStatus=:notUsed) ORDER BY  s.id DESC";
            Query query = getEntityManager().createQuery(sql, SelfiePayment.class);
            query.setParameter("userId", userId);
            query.setParameter("pending", PaymentStatus.PENDING);
            query.setParameter("notUsed", PaymentStatus.PAID_SUCCESSFUL_NOT_USED);
            List<SelfiePayment> selfiePayments = query.getResultList();
            if(!selfiePayments.isEmpty()){
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public SelfiePayment getPaymentByRequestId(String requestId) {
        try {
            String sql = "FROM SelfiePayment  s WHERE s.requestId=:requestId";
            Query query = getEntityManager().createQuery(sql, SelfiePayment.class);
            query.setParameter("requestId", requestId);
            return (SelfiePayment) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
