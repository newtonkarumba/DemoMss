package com.systech.mss.repository.impl;

import com.systech.mss.domain.Documents;
import com.systech.mss.domain.MemberSubmittedDocs;
import com.systech.mss.domain.StageContribution;
import com.systech.mss.repository.StageContributionRepository;
import com.systech.mss.util.StringUtil;
import com.systech.mss.vm.benefitrequest.MakeContributionStkVM;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class StageContributionRepositoryImpl extends AbstractRepositoryImpl<StageContribution, Long>
        implements StageContributionRepository {

    @Inject
    private EntityManager em;

    @Inject
    Logger log;

    public StageContributionRepositoryImpl() {
        super(StageContribution.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public StageContribution save(MakeContributionStkVM makeContributionStkVM) {
        try {
            StageContribution stageContribution = new StageContribution();
            stageContribution.setAmount(new BigDecimal(StringUtil.toString(makeContributionStkVM.getAmount())));
            stageContribution.setMemberId(makeContributionStkVM.getMemberId());
            stageContribution.setMpesaPhoneNumber(makeContributionStkVM.getPhone());
            stageContribution.setPhoneNumber(makeContributionStkVM.getPhone());
            stageContribution.setRequestId(makeContributionStkVM.getRequestId());
            stageContribution.setMerchantRequestID(makeContributionStkVM.getMerchantRequestID());
            stageContribution.setPaybill(makeContributionStkVM.getPaybill());
            stageContribution.setSendToXi(false);
            stageContribution.setSchemeId(makeContributionStkVM.getSchemeId());
            stageContribution.setTimestamp(makeContributionStkVM.getTimestamp());
            stageContribution.setPassword(makeContributionStkVM.getPassword());
            return create(stageContribution);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<StageContribution> getStagedContributionsForSubmission() {
        String sql="SELECT  * FROM  StageContribution WHERE (!sendToXi) ORDER  BY id DESC";
        try{
            Query query=getEntityManager().createNativeQuery(sql, StageContribution.class);
            query.setMaxResults(200);
            return query.getResultList();
        }catch (Exception e){}
        return new ArrayList<>();
    }


}
