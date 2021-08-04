package com.systech.mss.repository;

import com.systech.mss.domain.MobileMoneyConfig;

public interface MobileMoneyRepository extends AbstractRepository<MobileMoneyConfig,Long>{
    MobileMoneyConfig getActiveConfig();
}
