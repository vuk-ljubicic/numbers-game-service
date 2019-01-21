package com.takeaway.numbers.eventbus;

import com.takeaway.numbers.eventbus.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    @Autowired
    private EventStore eventStore;

    public void produce(Event event) {
        eventStore.getEvents().addFirst(event);
    }
}
