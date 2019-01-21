package com.takeaway.numbers.eventbus.handler;

import com.takeaway.numbers.cache.ApplicationCache;
import com.takeaway.numbers.eventbus.Consumer;
import com.takeaway.numbers.eventbus.Producer;
import com.takeaway.numbers.eventbus.event.Event;
import com.takeaway.numbers.service.console.ConsoleService;

public abstract class Handler<E extends Event> {
    protected ApplicationCache applicationCache;
    protected Producer producer;
    protected Consumer consumer;
    protected ConsoleService consoleService;

    public abstract void handle(E event);

    public Handler(ApplicationCache applicationCache, Producer producer,
                   Consumer consumer, ConsoleService consoleService) {
        this.applicationCache = applicationCache;
        this.producer = producer;
        this.consumer = consumer;
        this.consoleService = consoleService;
    }
}
