package com.systech.mss.repository;


import com.systech.mss.domain.Config;
import com.systech.mss.domain.SponsorConfig;

public interface SponsorConfigRepository extends  AbstractRepository<SponsorConfig,Long> {
    SponsorConfig getActiveConfig();

    Object getSponsorActiveConfigs();
}
