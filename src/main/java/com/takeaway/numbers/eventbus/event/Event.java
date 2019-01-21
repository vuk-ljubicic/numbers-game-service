package com.takeaway.numbers.eventbus.event;

import java.time.LocalDateTime;

public abstract class Event {
    protected boolean shouldDistribute;
    private String instanceType;
    private LocalDateTime timeStamp;

    public Event(String instanceType) {
        shouldDistribute = false;
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
}
