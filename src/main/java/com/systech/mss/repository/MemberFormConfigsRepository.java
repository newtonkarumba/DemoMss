package com.systech.mss.repository;

import com.systech.mss.domain.MemberFormConfigs;

public interface MemberFormConfigsRepository extends AbstractRepository<MemberFormConfigs,Long>{
    MemberFormConfigs getConfigs();

    MemberFormConfigs setDefault();

    MemberFormConfigs update(MemberFormConfigs memberFormConfigs);
}
