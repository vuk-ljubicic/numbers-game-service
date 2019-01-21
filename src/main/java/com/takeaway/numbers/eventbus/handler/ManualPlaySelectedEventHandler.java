package com.takeaway.numbers.eventbus.handler;

import com.takeaway.numbers.cache.ApplicationCache;
import com.takeaway.numbers.eventbus.Consumer;
import com.takeaway.numbers.eventbus.Producer;
import com.takeaway.numbers.eventbus.event.ManualPlaySelectedEvent;
import com.takeaway.numbers.eventbus.event.NumberGeneratedEvent;
import com.takeaway.numbers.service.console.ConsoleService;

public class ManualPlaySelectedEventHandler extends Handler<ManualPlaySelectedEvent> {

    public ManualPlaySelectedEventHandler(ApplicationCache applicationCache, Producer producer,
                                          Consumer consumer, ConsoleService consoleService) {
        super(applicationCache, producer, consumer, consoleService);
    }

    @Override
    public void handle(ManualPlaySelectedEvent event) {
        consumer.getHandlers().put(NumberGeneratedEvent.class,
                new ManualNumberGeneratedEventHandler(applicationCache, producer, consumer, consoleService));
        if(applicationCache.isStartingGame()){
            consoleService.enterPositiveWholeNumber();
        } else {
            consoleService.waitingForPlayerToStart();
        }
    }
}
