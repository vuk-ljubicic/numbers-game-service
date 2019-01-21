package com.takeaway.numbers.eventbus.handler;

import com.takeaway.numbers.cache.ApplicationCache;
import com.takeaway.numbers.eventbus.Consumer;
import com.takeaway.numbers.eventbus.Producer;
import com.takeaway.numbers.eventbus.event.AutoPlaySelectedEvent;
import com.takeaway.numbers.eventbus.event.NumberGeneratedEvent;
import com.takeaway.numbers.service.console.ConsoleService;

import java.util.Random;

public class AutoPlaySelectedEventHandler extends Handler<AutoPlaySelectedEvent> {

    public AutoPlaySelectedEventHandler(ApplicationCache applicationCache, Producer producer,
                                        Consumer consumer, ConsoleService consoleService) {
        super(applicationCache, producer, consumer, consoleService);
    }

    @Override
    public void handle(AutoPlaySelectedEvent event) {
        consumer.getHandlers().put(NumberGeneratedEvent.class,
                new AutoNumberGeneratedEventHandler(applicationCache, producer, consumer, consoleService));
        if(applicationCache.isStartingGame()){
            producer.produce(new NumberGeneratedEvent(applicationCache.getCurrentNumber(), 0,
                    getRandomPositiveNumber(), applicationCache.getServiceInstance()));
        } else {
            consoleService.waitingForPlayerToStart();
        }
    }

    private Integer getRandomPositiveNumber(){
        Random r = new Random();
        int low = 10;
        int high = 100;
        int result = r.nextInt(high-low) + low;
        return result;
    }
}
