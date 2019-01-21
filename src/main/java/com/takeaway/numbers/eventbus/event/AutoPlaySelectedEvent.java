package com.takeaway.numbers.eventbus.event;

public class AutoPlaySelectedEvent extends Event {

    public AutoPlaySelectedEvent(String instanceType) {
        super(instanceType);
        shouldDistribute = false;
    }
}
