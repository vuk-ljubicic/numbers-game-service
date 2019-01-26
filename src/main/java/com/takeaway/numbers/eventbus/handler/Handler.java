package com.takeaway.numbers.eventbus.handler;

import com.takeaway.numbers.ApplicationConfig;
import com.takeaway.numbers.cache.ApplicationCache;
import com.takeaway.numbers.eventbus.Consumer;
import com.takeaway.numbers.eventbus.Producer;
import com.takeaway.numbers.eventbus.event.Event;
import com.takeaway.numbers.service.console.ConsoleService;
import com.takeaway.numbers.service.number.NumberService;

public abstract class Handler<E extends Event> {
    protected ApplicationCache applicationCache;
    protected Producer producer;
    protected Consumer consumer;
    protected ConsoleService consoleService;
    protected NumberService numberService;
    protected ApplicationConfig applicationConfig;

    public abstract void handle(E event);

    public Handler(ApplicationCache applicationCache, Producer producer, Consumer consumer,
                   ConsoleService consoleService, NumberService numberService, ApplicationConfig applicationConfig) {
        this.applicationCache = applicationCache;
        this.producer = producer;
        this.consumer = consumer;
        this.consoleService = consoleService;
        this.numberService = numberService;
        this.applicationConfig = applicationConfig;
    }
}
