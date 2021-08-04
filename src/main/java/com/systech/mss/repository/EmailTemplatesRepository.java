package com.systech.mss.repository;

import com.systech.mss.domain.EmailTemplates;
import com.systech.mss.domain.EmailTemplatesEnum;

public interface EmailTemplatesRepository extends AbstractRepository<EmailTemplates, Long>{
    EmailTemplates findByEmailTemplatesEnum(EmailTemplatesEnum emailTemplatesEnum);
}
