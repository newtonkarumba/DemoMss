package com.systech.mss.repository.impl;

import com.systech.mss.domain.BenefitRequest;
import com.systech.mss.domain.YesNo;
import com.systech.mss.repository.BenefitRequestRepository;
import com.systech.mss.vm.benefitrequest.*;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BenefitRequestRepositoryImpl extends AbstractRepositoryImpl<BenefitRequest, Long> implements BenefitRequestRepository {
    @Inject
    private EntityManager em;

    @Inject
    Logger log;

    public BenefitRequestRepositoryImpl() {
        super(BenefitRequest.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public BenefitRequest savePersonalDetails(long memberId,long mssUserId,PersonalDetailsVM personalDetailsVM) {
        BenefitRequest benefitRequest = new BenefitRequest();
        benefitRequest.setMemberId(memberId);
        benefitRequest.setMemberName(personalDetailsVM.getName());
        benefitRequest.setMemberNo(personalDetailsVM.getMemberNo());
        benefitRequest.setDob(personalDetailsVM.getDob());
        benefitRequest.setEmail(personalDetailsVM.getEmail());
        benefitRequest.setSchemeId(personalDetailsVM.getSchemeId());
        benefitRequest.setSponsorId(personalDetailsVM.getSponsorId());
        benefitRequest.setMssUserId(mssUserId);
        try {
            return this.create(benefitRequest);
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public BenefitRequest saveGroundOfBenefits(GroundOfBenefits groundOfBenefits) {
        BenefitRequest benefitRequest=this.find(groundOfBenefits.getId());
        if (benefitRequest!=null){
            benefitRequest.setRequiresDocuments(groundOfBenefits.isRequiresDocuments());
            benefitRequest.setBenefitReason(groundOfBenefits.getReason());
            benefitRequest.setBenefitReasonId(groundOfBenefits.getReasonId());
            return  this.edit(benefitRequest);
        }
        return null;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public List<BenefitRequest> getMemberBenefitRequests(long memberId) {
        try {
            String sql = "FROM BenefitRequest  br WHERE br.memberId=:memberId AND br.submitted=:submitted ORDER BY br.id DESC";
            Query query = getEntityManager().createQuery(sql, BenefitRequest.class);
            query.setParameter("memberId", memberId);
            query.setParameter("submitted", YesNo.YES);
            query.setMaxResults(200);
            List<BenefitRequest> benefitRequests= query.getResultList();
//            List<BenefitRequest> benefitRequests1=new ArrayList<>();
            for (BenefitRequest b:
                benefitRequests ) {
                b.setShortCreatedDate(b.getDateCreated().format(DateTimeFormatter.ofPattern("MMM dd, uuuu")));
//                benefitRequests1.add(b);
            }
            return benefitRequests;
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public BenefitRequest savePaymentOptions(PaymentOptionsVM paymentOptionsVM) {
        BenefitRequest benefitRequest=this.find(paymentOptionsVM.getId());
        if (benefitRequest!=null){
            benefitRequest.setPaymentOption(paymentOptionsVM.getPaymentOption());
            benefitRequest.setIsPercentageOrAmount(paymentOptionsVM.getIsPercentageOrAmount());
            benefitRequest.setTotalAmount(paymentOptionsVM.getTotalAmount());
            benefitRequest.setAmountPercentage(paymentOptionsVM.getAmountPercentage());
            return  this.edit(benefitRequest);
        }
        return null;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public BenefitRequest saveBankDetails(BankDetailsVM bankDetailsVM) {
        BenefitRequest benefitRequest=this.find(bankDetailsVM.getId());
        if (benefitRequest!=null){
            benefitRequest.setBankName(bankDetailsVM.getBankName());
            benefitRequest.setBankBranchName(bankDetailsVM.getBankBranch());
            benefitRequest.setAccountName(bankDetailsVM.getAccountName());
            benefitRequest.setAccountNumber(bankDetailsVM.getAccountNumber());
            benefitRequest.setSubmitted(YesNo.YES);
            return  this.edit(benefitRequest);
        }
        return null;
    }

    @Override
    public List<BenefitRequest> searchABenefitRequest(String name) {
        try {
            if(name!=null) {



                String sql = "From BenefitRequest br where br.memberNo=:name ORDER BY br.id DESC ";
                Query query = em.createQuery(sql, BenefitRequest.class);
                query.setParameter("name", name);
                query.setMaxResults(10);
                List<BenefitRequest> benefitRequests = query.getResultList();
                if (benefitRequests != null) {
                    for (BenefitRequest benefitRequest :
                            benefitRequests) {
                        benefitRequest.setShortCreatedDate(benefitRequest.getDateCreated().format(DateTimeFormatter.ofPattern("MMM dd, uuuu")));

                    }
                    return benefitRequests;
                }
            }

        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public BenefitRequest saveMedicalReasons(MedicalReasons medicalReasons) {
        BenefitRequest benefitRequest=this.find(medicalReasons.getId());
        if (benefitRequest!=null){
            benefitRequest.setMedicalIssue(medicalReasons.getMedicalIssue());
            benefitRequest.setMedicalExplanation(medicalReasons.getMedicalExplanation());
            return  this.edit(benefitRequest);
        }
        return null;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public void updateSetDocumentsUploaded(long recordId) {
        BenefitRequest benefitRequest=this.find(recordId);
        if (benefitRequest!=null){
            benefitRequest.setDocumentsUploaded(true);
             this.edit(benefitRequest);
        }
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public BenefitRequest finishSavingBenefitRequest(long reqId) {
        BenefitRequest benefitRequest=this.find(reqId);
        if (benefitRequest!=null){
            benefitRequest.setSubmitted(YesNo.YES);
            return  this.edit(benefitRequest);
        }
        return null;
    }

    @Override
    public List<BenefitRequest> getMemberEditedClaims(long memberId) {
        try{

            String sql = "From BenefitRequest br where br.memberId=:memberId and br.isEdited=:isEdited and br.isPostedToFm=:isPostedToFm ORDER BY br.id DESC ";
            Query query = em.createQuery(sql, BenefitRequest.class);
            query.setParameter("memberId", memberId);
            query.setParameter("isEdited",YesNo.YES);
            query.setParameter("isPostedToFm",YesNo.NO);
            return query.getResultList();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}