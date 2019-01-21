package com.takeaway.numbers.eventbus.handler;

import com.takeaway.numbers.cache.ApplicationCache;
import com.takeaway.numbers.eventbus.Consumer;
import com.takeaway.numbers.eventbus.Producer;
import com.takeaway.numbers.eventbus.event.NumberGeneratedEvent;
import com.takeaway.numbers.service.console.ConsoleService;

public class AutoNumberGeneratedEventHandler extends Handler<NumberGeneratedEvent> {
    public AutoNumberGeneratedEventHandler(ApplicationCache applicationCache, Producer producer,
                                           Consumer consumer, ConsoleService consoleService) {
        super(applicationCache, producer, consumer, consoleService);
    }

    @Override
    public void handle(NumberGeneratedEvent event) {
        if(event.getResultingNumber() == 1){
            consoleService.winner(event);
        } else {
            if (event.getResultingNumber() != applicationCache.getCurrentNumber()) {
                consoleService.numberGenerated(event);
                applicationCache.setCurrentNumber(event.getResultingNumber());
                consoleService.addNumber();
            }
        }
    }
}
