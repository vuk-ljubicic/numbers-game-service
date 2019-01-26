package com.takeaway.numbers.eventbus;

import com.takeaway.numbers.ApplicationConfig;
import com.takeaway.numbers.cache.ApplicationCache;
import com.takeaway.numbers.eventbus.event.AutoPlaySelectedEvent;
import com.takeaway.numbers.eventbus.event.Event;
import com.takeaway.numbers.eventbus.event.ManualPlaySelectedEvent;
import com.takeaway.numbers.eventbus.handler.AutoPlaySelectedEventHandler;
import com.takeaway.numbers.eventbus.handler.Handler;
import com.takeaway.numbers.eventbus.handler.ManualPlaySelectedEventHandler;
import com.takeaway.numbers.service.console.ConsoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

public class Consumer {
    private Logger log = LoggerFactory.getLogger(Consumer.class);
    private ExecutorService executor;
    private Map<Class, Handler> handlers;
    @Autowired
    private EventStore eventStore;
    @Autowired
    private Producer producer;
    @Autowired
    private ApplicationCache applicationCache;
    @Autowired
    private ConsoleService consoleService;
    @Autowired
    private ApplicationConfig applicationConfig;

    public Consumer(ExecutorService executor) {
        this.executor = executor;
    }

    public Map<Class, Handler> getHandlers() {
        return handlers;
    }

    public void setHandlers(Map<Class, Handler> handlers) {
        this.handlers = handlers;
    }

    @PostConstruct
    public void init() {
        initHandlers();
        initConsumerWorker();
    }

    private void initHandlers() {
        handlers = new ConcurrentHashMap<>();
        handlers.put(AutoPlaySelectedEvent.class, new AutoPlaySelectedEventHandler(applicationCache, producer,
                this, consoleService, null, applicationConfig));
        handlers.put(ManualPlaySelectedEvent.class, new ManualPlaySelectedEventHandler(applicationCache, producer,
                this, consoleService, null, applicationConfig));
    }

    private void initConsumerWorker() {
        executor.execute(() -> {
            while (true) {
                try {
                    Event event = (Event) eventStore.getEvents().peekLast();
                    if (event != null) {
                        Handler handler = handlers.get(event.getClass());
                        if (handler != null) {
                            if (!event.isHandledLocally()) {
                                handler.handle(event.getClass().cast(event));
                                event.setHandledLocally(true);
                            }
                            if (!event.isShouldDistribute()) {
                                eventStore.getEvents().removeLast();
                            }
                        } else {
                            event = (Event)eventStore.getEvents().removeLast();
                            eventStore.getEvents().addFirst(event);
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        });
    }
}
