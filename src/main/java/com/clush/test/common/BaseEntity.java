package com.clush.test.common;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
@Getter
@Setter
public class BaseEntity {

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
