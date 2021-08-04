package com.systech.mss.repository.impl;

import com.systech.mss.domain.Member;
import com.systech.mss.repository.MemberRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class MemberRepositoryImpl extends AbstractRepositoryImpl<Member, Long> implements MemberRepository {
    @Inject
    private EntityManager em;

    public MemberRepositoryImpl() {
        super(Member.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public List<Member> getUnPushedToXe(long schemeId) {
        try {
            Query query = getEntityManager().createQuery(
                    "FROM Member m WHERE  m.posted=:posted and m.schemeId=:schemeId ORDER BY m.id DESC", Member.class
            );
            query.setParameter("posted",false);
            query.setParameter("schemeId",schemeId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Member> getUnPushedToXe(long schemeId, long sponsorId) {
        try {
            Query query = getEntityManager().createQuery(
                    "FROM Member m WHERE  (m.posted=:posted) and (m.schemeId=:schemeId) and (m.sponsorId=:sponsorId) ORDER BY " +
                            "m.id DESC",
                    Member.class
            );
            query.setParameter("posted",false);
            query.setParameter("schemeId",schemeId);
            query.setParameter("sponsorId",sponsorId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
