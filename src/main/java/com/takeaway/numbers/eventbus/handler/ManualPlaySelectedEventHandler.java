package com.takeaway.numbers.eventbus.handler;

import com.takeaway.numbers.ApplicationConfig;
import com.takeaway.numbers.cache.ApplicationCache;
import com.takeaway.numbers.eventbus.Consumer;
import com.takeaway.numbers.eventbus.Producer;
import com.takeaway.numbers.eventbus.event.ManualPlaySelectedEvent;
import com.takeaway.numbers.eventbus.event.NumberGeneratedEvent;
import com.takeaway.numbers.service.console.ConsoleService;
import com.takeaway.numbers.service.number.ConsoleNumberService;
import com.takeaway.numbers.service.number.NumberService;

public class ManualPlaySelectedEventHandler extends Handler<ManualPlaySelectedEvent> {

    public ManualPlaySelectedEventHandler(ApplicationCache applicationCache, Producer producer,
                                          Consumer consumer, ConsoleService consoleService, NumberService numberService,
                                          ApplicationConfig applicationConfig) {
        super(applicationCache, producer, consumer, consoleService, numberService, applicationConfig);
    }

    @Override
    public void handle(ManualPlaySelectedEvent event) {
        ConsoleNumberService consoleNumberService = new ConsoleNumberService(consoleService, applicationCache, producer);
        consoleService.setNumberService(consoleNumberService);
        consumer.getHandlers().put(NumberGeneratedEvent.class,
                new NumberGeneratedEventHandler(applicationCache, producer, consumer, consoleService,
                        consoleNumberService, applicationConfig));
        if (applicationCache.isStartingGame()) {
            consoleService.enterPositiveWholeNumber();
        } else {
            consoleService.waitingForPlayerToStart();
        }
    }
}
