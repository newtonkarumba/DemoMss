package com.systech.mss.repository;

import com.systech.mss.domain.MemberSubmittedDocs;

import java.util.List;

public interface MemberSubmittedDocsRepository extends AbstractRepository<MemberSubmittedDocs,Long>{

    List<MemberSubmittedDocs> getDocumentsForApproval();

    List<MemberSubmittedDocs> getDocumentsForApprovalByMemberId( long memberId);
}
