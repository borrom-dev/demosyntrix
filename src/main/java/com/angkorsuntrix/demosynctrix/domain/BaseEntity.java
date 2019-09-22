package com.angkorsuntrix.demosynctrix.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

abstract class BaseEntity {

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime create_at;
    @UpdateTimestamp
    private LocalDateTime update_at;
}
