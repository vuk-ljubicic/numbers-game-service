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
    protected Map<Class, Handler> handlers;
    @Autowired
    protected EventStore eventStore;
    @Autowired
    protected Producer producer;
    @Autowired
    protected ApplicationCache applicationCache;
    @Autowired
    protected ConsoleService consoleService;
    @Autowired
    protected ApplicationConfig applicationConfig;

    public Consumer(ExecutorService executor) {
        this.executor = executor;
    }

    public Consumer(){

    }

    public Map<Class, Handler> getHandlers() {
        return handlers;
    }

    public void setHandlers(Map<Class, Handler> handlers) {
        this.handlers = handlers;
    }

    public EventStore getEventStore() {
        return eventStore;
    }

    public void setEventStore(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public ApplicationCache getApplicationCache() {
        return applicationCache;
    }

    public void setApplicationCache(ApplicationCache applicationCache) {
        this.applicationCache = applicationCache;
    }

    public ConsoleService getConsoleService() {
        return consoleService;
    }

    public void setConsoleService(ConsoleService consoleService) {
        this.consoleService = consoleService;
    }

    public ApplicationConfig getApplicationConfig() {
        return applicationConfig;
    }

    public void setApplicationConfig(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    @PostConstruct
    public void init() {
        initHandlers();
        initConsumerWorker();
    }

    public void initHandlers() {
        handlers = new ConcurrentHashMap<>();
        handlers.put(AutoPlaySelectedEvent.class, new AutoPlaySelectedEventHandler(applicationCache, producer,
                this, consoleService, null, applicationConfig));
        handlers.put(ManualPlaySelectedEvent.class, new ManualPlaySelectedEventHandler(applicationCache, producer,
                this, consoleService, null, applicationConfig));
    }

    private void initConsumerWorker() {
        executor.execute(() -> {
            while (true) {
               invokeHandler();
            }
        });
    }

    public void invokeHandler(){
        try {
            synchronized (eventStore.getEvents()) {
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
                        event = (Event) eventStore.getEvents().removeLast();
                        eventStore.getEvents().addFirst(event);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
