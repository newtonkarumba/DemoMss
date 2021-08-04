package com.systech.mss.repository;


import com.systech.mss.domain.BenefitRequest;
import com.systech.mss.vm.benefitrequest.*;

import java.util.List;

public interface BenefitRequestRepository extends AbstractRepository<BenefitRequest, Long> {
    BenefitRequest savePersonalDetails(long memberId,long mssUserId,PersonalDetailsVM personalDetailsVM);

    BenefitRequest saveGroundOfBenefits(GroundOfBenefits groundOfBenefits);

    List<BenefitRequest> getMemberBenefitRequests(long memberId);

    BenefitRequest savePaymentOptions(PaymentOptionsVM paymentOptionsVM);

    BenefitRequest saveBankDetails(BankDetailsVM bankDetailsVM);

    List<BenefitRequest> searchABenefitRequest(String name);

    BenefitRequest saveMedicalReasons(MedicalReasons medicalReasons);

    void updateSetDocumentsUploaded(long recordId);

    BenefitRequest finishSavingBenefitRequest(long reqId);

    List<BenefitRequest> getMemberEditedClaims(long memberId);
}
