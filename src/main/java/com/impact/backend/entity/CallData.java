package com.impact.backend.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.Map;

@Entity
public class CallData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long pk;

    private String uuid;

    private String domain_name;
    private String caller_name;
    private Long caller_number;
    private Long destination_number;
    private String direction;
    private String call_start;
    private String ring_start;
    private String answer_start;
    private String call_end;
    private Integer duration;
    private String recording;
    private Boolean click_to_call;
    private String click_to_call_data;
    private String action;
    private String tenant;

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDomain_name() {
        return domain_name;
    }

    public void setDomain_name(String domain_name) {
        this.domain_name = domain_name;
    }

    public String getCaller_name() {
        return caller_name;
    }

    public void setCaller_name(String caller_name) {
        this.caller_name = caller_name;
    }

    public Long getCaller_number() {
        return caller_number;
    }

    public void setCaller_number(Long caller_number) {
        this.caller_number = caller_number;
    }

    public Long getDestination_number() {
        return destination_number;
    }

    public void setDestination_number(Long destination_number) {
        this.destination_number = destination_number;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getCall_start() {
        return call_start;
    }

    public void setCall_start(String call_start) {
        this.call_start = call_start;
    }

    public String getRing_start() {
        return ring_start;
    }

    public void setRing_start(String ring_start) {
        this.ring_start = ring_start;
    }

    public String getAnswer_start() {
        return answer_start;
    }

    public void setAnswer_start(String answer_start) {
        this.answer_start = answer_start;
    }

    public String getCall_end() {
        return call_end;
    }

    public void setCall_end(String call_end) {
        this.call_end = call_end;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getRecording() {
        return recording;
    }

    public void setRecording(String recording) {
        this.recording = recording;
    }

    public Boolean getClick_to_call() {
        return click_to_call;
    }

    public void setClick_to_call(Boolean click_to_call) {
        this.click_to_call = click_to_call;
    }

    public String getClick_to_call_data() {
        return click_to_call_data;
    }

    public void setClick_to_call_data(String click_to_call_data) {
        this.click_to_call_data = click_to_call_data;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }
}