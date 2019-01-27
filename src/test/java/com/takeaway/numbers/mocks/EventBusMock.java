package com.takeaway.numbers.mocks;

import com.takeaway.numbers.eventbus.EventStore;
import com.takeaway.numbers.eventbus.event.NumberGeneratedEvent;

public class EventBusMock implements Runnable {
    private EventStore serverEventStore;
    private EventStore clientEventStore;
    private Integer eventCount;
    private Integer maxEventCount;

    public EventBusMock(EventStore serverEventStore, EventStore clientEventStore, Integer maxEventCount) {
        this.serverEventStore = serverEventStore;
        this.clientEventStore = clientEventStore;
        this.maxEventCount = maxEventCount;
        this.eventCount = 0;
    }

    @Override
    public void run() {
        while (eventCount <= maxEventCount) {
            publishEvent(serverEventStore, clientEventStore);
            publishEvent(clientEventStore, serverEventStore);
            eventCount++;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void publishEvent(EventStore sourceEventStore, EventStore destinationEventStore) {
        synchronized (sourceEventStore.getEvents()) {
            synchronized (destinationEventStore.getEvents()) {
                if (sourceEventStore.getEvents().peekLast() != null) {
                    NumberGeneratedEvent sourceEvent =
                            (NumberGeneratedEvent) sourceEventStore.getEvents().peekLast();
                    if (sourceEvent.isShouldDistribute()) {
                        sourceEvent.setShouldDistribute(false);
                        NumberGeneratedEvent destinationEvent = new NumberGeneratedEvent(sourceEvent.getPreviousNumber(),
                                sourceEvent.getAddedNumber(), sourceEvent.getResultingNumber(), sourceEvent.getInstanceType());
                        destinationEvent.setShouldDistribute(false);
                        destinationEvent.setHandledLocally(false);
                        destinationEventStore.getEvents().addFirst(destinationEvent);
                    }
                }
            }
        }
    }
}
