package com.systech.mss.repository;

import com.systech.mss.domain.MemberSubmittedDocs;
import com.systech.mss.domain.StageContribution;
import com.systech.mss.vm.benefitrequest.MakeContributionStkVM;

import java.util.List;

public interface StageContributionRepository extends AbstractRepository<StageContribution,Long>{
    StageContribution save(MakeContributionStkVM makeContributionStkVM);
    List<StageContribution> getStagedContributionsForSubmission();
}
