package com.impact.backend.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class SyncInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    @Temporal(TemporalType.TIMESTAMP)
    private Date syncTime;

    private String tenant;

    public SyncInfo() {
    }

    public SyncInfo(Date syncTime, String tenant) {
        this.syncTime = syncTime;
        this.tenant = tenant;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}
