package org.aioceaneye.userservicewithmailverification.audit;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.aioceaneye.userservicewithmailverification.model.Status;
import org.aioceaneye.userservicewithmailverification.model.User;

import java.time.LocalDateTime;

public class UserAuditingListener {

    @PreUpdate
    public void onAdminApproval(User entity) {
        if(entity.getStatus().equals(Status.ACTIVE)) {
            entity.setCreatedAt(LocalDateTime.now());
        }
    }

}
