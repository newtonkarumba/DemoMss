package com.systech.mss.repository;

import com.systech.mss.domain.ClaimDocuments;

import java.util.List;

public interface ClaimDocumentsRepository extends AbstractRepository<ClaimDocuments, Long> {
    List<ClaimDocuments> getByClaimId(long claimId);

    List<ClaimDocuments> getByClaimIdAndReasonForExit(long claimId, String benefitReason);

    List<ClaimDocuments> getUploadedClaimDocuments(long claimId, String benefitReason);
}
