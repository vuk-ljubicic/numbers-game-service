package com.takeaway.numbers.eventbus;

import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingDeque;

@Component
public class EventStore {
    private LinkedBlockingDeque<Object> events;

    public EventStore() {
        events = new LinkedBlockingDeque<>();
    }

    public LinkedBlockingDeque<Object> getEvents() {
        return events;
    }

    public void setEvents(LinkedBlockingDeque<Object> events) {
        this.events = events;
    }

    public void removeAll(){
        events.clear();
    }
}
