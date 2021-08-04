package com.systech.mss.domain;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AuditListener {
    @PrePersist
    void onCreate(AbstractAuditingEntity entity) {
        entity.setCreatedDate(LocalDateTime.now());
    }

    @PreUpdate
    void onUpdate(AbstractAuditingEntity entity) {
        entity.setLastModifiedDate(LocalDateTime.now());
    }
}
