package com.takeaway.numbers.eventbus.connector;

import com.takeaway.numbers.eventbus.Event;
import com.takeaway.numbers.eventbus.EventStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

@Component
public class EventWriter implements Runnable {
    private Logger log = LoggerFactory.getLogger(EventWriter.class);
    private ObjectOutputStream outputStream;
    @Autowired
    private EventStore eventStore;

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Event event = (Event) eventStore.getEvents().peekLast();
                if (!event.isStreamed()) {
                    outputStream.writeObject(event);
                    event.setStreamed(true);
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
