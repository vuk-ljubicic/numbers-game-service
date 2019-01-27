package com.takeaway.numbers.eventbus;

import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public class EventStore {
    private LinkedList<Object> events;

    public EventStore() {
        events = new LinkedList<>();
    }

    public LinkedList<Object> getEvents() {
        return events;
    }

    public void setEvents(LinkedList<Object> events) {
        this.events = events;
    }

    public void removeAll(){
        synchronized (events) {
            events.clear();
        }
    }
}
