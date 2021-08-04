package com.systech.mss.repository;

import com.systech.mss.domain.Member;

import java.util.List;

public interface MemberRepository extends AbstractRepository<Member, Long>{
    List<Member> getUnPushedToXe(long schemeId);

    List<Member> getUnPushedToXe(long schemeId, long sponsorId);
}
