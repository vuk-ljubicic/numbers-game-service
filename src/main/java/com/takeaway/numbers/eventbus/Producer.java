package com.takeaway.numbers.eventbus;

import com.takeaway.numbers.eventbus.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    @Autowired
    private EventStore eventStore;

    public Producer() {

    }

    public Producer(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public EventStore getEventStore() {
        return eventStore;
    }

    public void setEventStore(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public void produce(Event event) {
        synchronized (eventStore.getEvents()) {
            eventStore.getEvents().addFirst(event);
        }
    }
}
