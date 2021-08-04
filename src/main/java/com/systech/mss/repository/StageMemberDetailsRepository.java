package com.systech.mss.repository;

import com.systech.mss.domain.StageMemberDetails;

import java.util.List;

public interface StageMemberDetailsRepository extends AbstractRepository<StageMemberDetails,Long>{
    StageMemberDetails findByMemberId(long memberId);

    List<StageMemberDetails> findByScheme(long schemeId);

    List<StageMemberDetails> findBySchemeAndSponsor(long schemeId, long sponsorId);
}
