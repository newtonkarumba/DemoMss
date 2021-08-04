package com.systech.mss.repository;

import com.systech.mss.domain.Config;

public interface ConfigRepository extends  AbstractRepository<Config,Long> {
    Config getActiveConfig();

    Object getSpecificFieldsOfActiveConfigs();
}
