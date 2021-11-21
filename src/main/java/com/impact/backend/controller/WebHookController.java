package com.impact.backend.controller;

import com.impact.backend.entity.PhoneDetails;
import com.impact.backend.entity.SyncInfo;
import com.impact.backend.repository.CallDataRepository;
import com.impact.backend.repository.SyncRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.impact.backend.entity.CallData;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class WebHookController {

    public static final String TENANT = "tenant-uuid";

    @Autowired
    CallDataRepository callDataRepository;

    @Autowired
    SyncRepository syncRepository;

//    Map to keep track of thread running to sync data for each Tenant
    private Map<String, Thread> syncThreads = new HashMap<>();

//    Runnable to sync one time data in the background.
    private Runnable syncData = () -> {

        try {
//            Until all data is read
//            restTemplate.getForEntity()

            Thread.sleep(10000); // Mocking processing time
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

//    @Autowired
//    RestTemplate restTemplate;

//    A health check endpoint. We can also use actuator service ...
    @GetMapping("/info")
    public String index() {
        return "Greetings from Spring Boot!";
    }

//    CURD call
    @GetMapping("/all")
    public List<CallData> retrieveAllPhoneDetails() {
        return callDataRepository.findAll();
    }

//    CURD call
    @PostMapping
    public ResponseEntity<Object> getData(@RequestBody CallData callData, @RequestHeader Map<String, String> headers){

        callData.setTenant(headers.get(TENANT));
        CallData callDataCreated = callDataRepository.save(callData);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(callData.getPk()).toUri();

        System.out.println(location);

        return ResponseEntity.created(location).build();
    }

//    Call for Adhoc sync
//    This allows sync for multiple tenants ... BUT only once for each tenant.
//    Further checks can be put in place to validate whether trigger is allowed only once a day OR based on some time
    @GetMapping("/adhoc_sync/{id}")
    public ResponseEntity<Object> triggerSync(@PathVariable(value = "id") final String tenant) {

        synchronized (this){

            Thread syncThread = syncThreads.get(tenant);
            if (syncThread != null && syncThread.isAlive()) { return ResponseEntity.ok("Sync Already in Progress for Tenant : " + tenant); }

            syncThread = new Thread(syncData);
            syncThread.start();
            syncThreads.put(tenant, syncThread);

            SyncInfo createdSyncInfo = syncRepository.save(new SyncInfo(new Date(), tenant));
            return ResponseEntity.ok("Sync Triggerred for Tenant : " + tenant);
        }
    }

}