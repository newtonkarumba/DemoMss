package com.systech.smileIdentity.repository;


import com.systech.mss.repository.AbstractRepository;
import com.systech.smileIdentity.model.SmileIdentityConfig;

public interface SmileIdentityConfigRepository extends AbstractRepository<SmileIdentityConfig,Long> {

    SmileIdentityConfig getActiveConfig();
}
