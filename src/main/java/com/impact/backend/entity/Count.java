package com.impact.backend.entity;

public class Count{

    private String uuid;
    private long totalCalls;

    public Count(String uuid, long count) {
        this.uuid = uuid;
        this.totalCalls = count;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getTotalCalls() {
        return totalCalls;
    }

    public void setTotalCalls(long totalCalls) {
        this.totalCalls = totalCalls;
    }
}

