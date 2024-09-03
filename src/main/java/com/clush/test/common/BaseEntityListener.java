package com.clush.test.common;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class BaseEntityListener {
    @PrePersist
    public void prePersist(BaseEntity entity) {
        entity.setCreatedAt(LocalDateTime.now());
        entity.setModifiedAt(LocalDateTime.now());
    }


    @PreUpdate
    public void preUpdate(BaseEntity entity) {
        entity.setModifiedAt(LocalDateTime.now());
    }
}
