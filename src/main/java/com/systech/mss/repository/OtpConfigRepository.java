package com.systech.mss.repository;

import com.systech.mss.domain.OtpConfig;
import com.systech.mss.domain.OtpIdentifier;

import java.util.List;

public interface OtpConfigRepository extends AbstractRepository<OtpConfig,Long>{
    List<OtpConfig> findEnabled();

    OtpConfig findByIdentifier(OtpIdentifier identifier);
}
