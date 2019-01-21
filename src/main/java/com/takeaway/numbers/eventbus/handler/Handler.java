package com.takeaway.numbers.eventbus.handler;

import com.takeaway.numbers.cache.ApplicationCache;
import com.takeaway.numbers.eventbus.event.Event;

public abstract class Handler<E extends Event> {
    private ApplicationCache applicationCache;

    public abstract void handle(E event);
}
