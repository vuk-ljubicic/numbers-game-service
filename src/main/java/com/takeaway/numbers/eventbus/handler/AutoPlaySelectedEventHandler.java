package com.takeaway.numbers.eventbus.handler;

import com.takeaway.numbers.ApplicationConfig;
import com.takeaway.numbers.cache.ApplicationCache;
import com.takeaway.numbers.eventbus.Consumer;
import com.takeaway.numbers.eventbus.Producer;
import com.takeaway.numbers.eventbus.event.AutoPlaySelectedEvent;
import com.takeaway.numbers.eventbus.event.NumberGeneratedEvent;
import com.takeaway.numbers.service.console.ConsoleService;
import com.takeaway.numbers.service.number.NumberService;
import com.takeaway.numbers.service.number.AutoNumberService;

import java.util.Random;

public class AutoPlaySelectedEventHandler extends Handler<AutoPlaySelectedEvent> {

    public AutoPlaySelectedEventHandler(ApplicationCache applicationCache, Producer producer,
                                        Consumer consumer, ConsoleService consoleService, NumberService numberService,
                                        ApplicationConfig applicationConfig) {
        super(applicationCache, producer, consumer, consoleService, numberService, applicationConfig);
    }

    @Override
    public void handle(AutoPlaySelectedEvent event) {
        consumer.getHandlers().put(NumberGeneratedEvent.class,
                new NumberGeneratedEventHandler(applicationCache, producer, consumer, consoleService,
                        new AutoNumberService(consoleService, applicationCache, producer), applicationConfig));
        if (applicationCache.isStartingGame()) {
            Random r = new Random();
            int result = r.nextInt(applicationConfig.getRandomPositiveNumberHigh() -
                    applicationConfig.getRandomPositiveNumberLow()) + applicationConfig.getRandomPositiveNumberLow();
            producer.produce(new NumberGeneratedEvent(applicationCache.getCurrentNumber(), 0,
                    result, applicationCache.getServiceInstance()));
            applicationCache.setCurrentNumber(result);
        } else {
            consoleService.waitingForPlayerToStart();
        }
    }
}
