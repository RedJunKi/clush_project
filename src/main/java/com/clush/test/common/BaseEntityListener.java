package com.clush.test.common;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class BaseEntityListener {
    @PrePersist
    public void prePersist(BaseEntity entity) {
        entity.setCreatedAt(LocalDateTime.now().withNano(2));
        entity.setModifiedAt(LocalDateTime.now().withNano(2));
    }


    @PreUpdate
    public void preUpdate(BaseEntity entity) {
        entity.setModifiedAt(LocalDateTime.now().withNano(2));
    }
}
