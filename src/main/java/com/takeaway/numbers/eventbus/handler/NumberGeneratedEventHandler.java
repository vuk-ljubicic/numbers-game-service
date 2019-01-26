package com.takeaway.numbers.eventbus.handler;

import com.takeaway.numbers.ApplicationConfig;
import com.takeaway.numbers.cache.ApplicationCache;
import com.takeaway.numbers.eventbus.Consumer;
import com.takeaway.numbers.eventbus.Producer;
import com.takeaway.numbers.eventbus.event.NumberGeneratedEvent;
import com.takeaway.numbers.service.console.ConsoleService;
import com.takeaway.numbers.service.number.NumberService;

public class NumberGeneratedEventHandler extends Handler<NumberGeneratedEvent> {
    public NumberGeneratedEventHandler(ApplicationCache applicationCache, Producer producer,
                                       Consumer consumer, ConsoleService consoleService, NumberService numberService,
                                       ApplicationConfig applicationConfig) {
        super(applicationCache, producer, consumer, consoleService, numberService, applicationConfig);
    }

    @Override
    public void handle(NumberGeneratedEvent event) {
        if (event.getResultingNumber() == 1) {
            consoleService.winner(event);
        } else {
            if (event.getResultingNumber() != applicationCache.getCurrentNumber()) {
                consoleService.numberGenerated(event);
                applicationCache.setCurrentNumber(event.getResultingNumber());
                numberService.addNextNumber();
            }
        }
    }
}
