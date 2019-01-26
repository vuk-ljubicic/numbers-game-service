package com.takeaway.numbers.eventbus.event;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Event implements Serializable {
    protected boolean shouldDistribute;
    protected boolean handledLocally;
    private String instanceType;
    private LocalDateTime timeStamp;

    public Event(String instanceType) {
        shouldDistribute = false;
        handledLocally = false;
        timeStamp = LocalDateTime.now();
        this.instanceType = instanceType;
    }

    public boolean isShouldDistribute() {
        return shouldDistribute;
    }

    public void setShouldDistribute(boolean shouldDistribute) {
        this.shouldDistribute = shouldDistribute;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public boolean isHandledLocally() {
        return handledLocally;
    }

    public void setHandledLocally(boolean handledLocally) {
        this.handledLocally = handledLocally;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
