package org.aioceaneye.userservicewithmailverification.audit;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.aioceaneye.userservicewithmailverification.model.User;

public class UserAuditingListener {

    @PrePersist
    public void onPrePersist(User entity) {
        entity.setCreatedBy(null);
        entity.setCreatedAt(null);
    }

    @PreUpdate
    public void onPreUpdate(User entity) {

    }

}
