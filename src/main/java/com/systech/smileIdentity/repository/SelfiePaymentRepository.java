package com.systech.smileIdentity.repository;


import com.systech.mss.repository.AbstractRepository;
import com.systech.smileIdentity.Service.vm.SelfiePaymentVm;
import com.systech.smileIdentity.model.SelfieAction;
import com.systech.smileIdentity.model.SelfiePayment;

public interface SelfiePaymentRepository extends AbstractRepository<SelfiePayment,Long> {

    public SelfiePayment save(SelfiePaymentVm selfiePaymentVm);
    public SelfiePayment getUserPendingOrUnusedPaymentPerAction(long userId, SelfieAction selfieAction);
    public boolean getUserUnusedPayments(long userId);
    public SelfiePayment getPaymentByRequestId(String requestId);
}
