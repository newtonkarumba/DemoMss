package com.systech.mss.repository;

import com.systech.mss.domain.StagedBeneficiaries;

import java.util.List;

public interface StagedBeneficiariesRepository extends AbstractRepository<StagedBeneficiaries,Long>{
    List<StagedBeneficiaries> findByScheme(long schemeId);

    List<StagedBeneficiaries> findBySchemeAndSponsor(long schemeId, long sponsorId);
}
