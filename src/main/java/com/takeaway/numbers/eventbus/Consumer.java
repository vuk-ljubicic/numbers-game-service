package com.takeaway.numbers.eventbus;

import com.takeaway.numbers.eventbus.event.Event;
import com.takeaway.numbers.eventbus.handler.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class Consumer {
    private Logger log = LoggerFactory.getLogger(Consumer.class);
    private ExecutorService executor;
    private Map<Class, Handler> handlers;
    @Autowired
    private EventStore eventStore;

    public Consumer(ExecutorService executor) {
        this.executor = executor;
    }

    @PostConstruct
    public void init() {
        initHandlers();
        initConsumerWorker();
    }

    private void initHandlers() {
        handlers = new HashMap<>();
    }

    private void initConsumerWorker() {
        executor.execute(() -> {
            while (true) {
                try {
                    Event event = (Event) eventStore.getEvents().peekLast();
                    if (event.isStreamed()) {
                        event = (Event) eventStore.getEvents().removeLast();
                    }
                    Handler handler = handlers.get(event.getClass());
                    if (handler != null) {
                        handler.handle(event.getClass().cast(event));
                    }
                } catch(Exception e){
                    log.error(e.getMessage(), e);
                }
            }
        });
    }
}
