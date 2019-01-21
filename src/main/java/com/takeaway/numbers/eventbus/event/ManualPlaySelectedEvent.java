package com.takeaway.numbers.eventbus.event;

public class ManualPlaySelectedEvent extends Event {

    public ManualPlaySelectedEvent(String instanceType) {
        super(instanceType);
        shouldDistribute = false;
    }
}
