package com.takeaway.numbers.eventbus.event;

public abstract class Event {
    protected boolean isStreamed;

    public boolean isStreamed() {
        return isStreamed;
    }

    public void setStreamed(boolean streamed) {
        isStreamed = streamed;
    }
}