package com.impact.backend.controller;

import com.impact.backend.entity.Count;
import com.impact.backend.entity.Minutes;
import com.impact.backend.entity.SyncInfo;
import com.impact.backend.entity.UnmatchedCalls;
import com.impact.backend.repository.CallDataRepository;
import com.impact.backend.repository.SyncRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    public static final String ANSWERED = "ANSWERED";
    public static final String HANGUP = "HANGUP";
    public static final String ORIGINATE = "ORIGINATE";
    public static final String RING = "RING";

    @Autowired
    CallDataRepository callDataRepository;

    @Autowired
    SyncRepository syncRepository;

//    Getting total calls for a tenant
    @GetMapping("/crd_count/{id}")
    public ResponseEntity<Object> retriveCDRCount(@PathVariable(value = "id") final String id ) {

        long count = callDataRepository.countByTenant(id);
        return ResponseEntity.ok(new Count(id, count));
    }

//    Success and failure mins for a tenant
    @GetMapping("/total_mins/{id}")
    public ResponseEntity<Object> retriveTotalCallMins(@PathVariable(value = "id") final String id ) {

        Integer successfulMins = callDataRepository.getTotalFailureDurationByTenant(id);
        Integer failureMins = callDataRepository.getTotalSuccessDurationByTenant(id);
        return ResponseEntity.ok(new Minutes(successfulMins, failureMins));
    }

//    Unmatched calls for a tenant
    @GetMapping("/unmatched_calls/{id}")
    public ResponseEntity<Object> retriveUnmatchedCalls(@PathVariable(value = "id") final String id ) {

        Integer unmatchedCalls = callDataRepository.getTotalUnmatchedCalls(id, ORIGINATE);
        return ResponseEntity.ok(new UnmatchedCalls(unmatchedCalls));
    }

//    Get last sync time for a tenant
    @GetMapping("/last_sync/{id}")
    public ResponseEntity<Object> lastSyncInfo(@PathVariable(value = "id") final String tenant) {

        SyncInfo syncInfo = syncRepository.findFirstByTenantOrderBySyncTimeDesc(tenant);
        return ResponseEntity.ok(syncInfo);
    }

}

