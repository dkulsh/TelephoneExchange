package com.impact.backend.repository;

import com.impact.backend.entity.PhoneDetails;
import com.impact.backend.entity.SyncInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SyncRepository extends JpaRepository<SyncInfo, Long> {

    SyncInfo findFirstByTenantOrderBySyncTimeDesc(String tenant);
}
