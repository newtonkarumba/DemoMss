package com.systech.mss.repository.impl;

import com.systech.mss.domain.MemberSubmittedDocs;
import com.systech.mss.repository.MemberSubmittedDocsRepository;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class MemberSubmittedDocsRepositoryImpl extends AbstractRepositoryImpl<MemberSubmittedDocs, Long> implements MemberSubmittedDocsRepository {
    @Inject
    private EntityManager em;

    @Inject
    Logger log;

    public MemberSubmittedDocsRepositoryImpl() {
        super(MemberSubmittedDocs.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    @Override
    public List<MemberSubmittedDocs> getDocumentsForApproval() {
        List<MemberSubmittedDocs> memberSubmittedDocs = new ArrayList<>();
        try {
            Query query = getEntityManager().createNativeQuery(
                    "SELECT  * FROM  MemberSubmittedDocs WHERE (!isApproved) ORDER  BY id DESC", MemberSubmittedDocs.class
            );
            memberSubmittedDocs = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getReducedMemberSubmittedDocs(memberSubmittedDocs);
    }

    @Override
    public List<MemberSubmittedDocs> getDocumentsForApprovalByMemberId(long memberId) {
        List<MemberSubmittedDocs> memberSubmittedDocs = new ArrayList<>();
        try {
            Query query = getEntityManager().createNativeQuery(
                    "SELECT  * FROM  MemberSubmittedDocs WHERE (memberId=:memberId) ORDER  BY id DESC", MemberSubmittedDocs.class
            );
            query.setParameter("memberId", memberId);
            memberSubmittedDocs = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getReducedMemberSubmittedDocs(memberSubmittedDocs);
    }

    List<MemberSubmittedDocs> getReducedMemberSubmittedDocs(List<MemberSubmittedDocs> submittedDocs) {
        List<MemberSubmittedDocs> list = new ArrayList<>();
        for (MemberSubmittedDocs memberSubmittedDocs :
                submittedDocs) {
            list.add(MemberSubmittedDocs.getSimpleMemberSubmittedDocs(memberSubmittedDocs));
        }
        return list;
    }

}
