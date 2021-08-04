package com.systech.mss.repository;

import com.systech.mss.domain.MailConfig;

import java.util.List;

public interface MailConfigRepository extends  AbstractRepository<MailConfig,Long> {
    List<MailConfig> getMailConfigs();
    MailConfig getActiveMailConfigs();
}
