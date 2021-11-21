package com.impact.backend.entity;

public
class Minutes{

    private Integer successfulCallMins;
    private Integer failedCallsMins;

    public Minutes(Integer successfulCallMins, Integer failedCallsMins) {
        this.successfulCallMins = successfulCallMins;
        this.failedCallsMins = failedCallsMins;
    }

    public Integer getSuccessfulCallMins() {
        return successfulCallMins;
    }

    public void setSuccessfulCallMins(Integer successfulCallMins) {
        this.successfulCallMins = successfulCallMins;
    }

    public Integer getFailedCallsMins() {
        return failedCallsMins;
    }

    public void setFailedCallsMins(Integer failedCallsMins) {
        this.failedCallsMins = failedCallsMins;
    }
}
