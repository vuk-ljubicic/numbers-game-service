package com.takeaway.numbers.eventbus.connector;

import com.takeaway.numbers.eventbus.event.Event;
import com.takeaway.numbers.eventbus.EventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

@Component
public class EventReader implements Runnable {
    private Logger log = LoggerFactory.getLogger(EventReader.class);
    private ObjectInputStream inputStream;
    @Autowired
    private EventStore eventStore;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(ObjectInputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Event event = (Event) inputStream.readObject();
                event.setHandledLocally(false);
                synchronized (eventStore.getEvents()) {
                    eventStore.getEvents().addFirst(event);
                }
            } catch (IOException | ClassNotFoundException e) {
                log.error(e.getMessage(), e);
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
