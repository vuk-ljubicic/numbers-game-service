package com.takeaway.numbers.service.number;

import com.takeaway.numbers.cache.ApplicationCache;
import com.takeaway.numbers.eventbus.Producer;
import com.takeaway.numbers.eventbus.event.NumberGeneratedEvent;
import com.takeaway.numbers.service.console.ConsoleService;

public abstract class NumberService {
    protected ConsoleService consoleService;
    protected ApplicationCache applicationCache;
    protected Producer producer;

    public NumberService(ConsoleService consoleService, ApplicationCache applicationCache, Producer producer) {
        this.consoleService = consoleService;
        this.applicationCache = applicationCache;
        this.producer = producer;
    }

    protected Integer checkAndAdd(Integer numberToAdd) {
        Integer previousNumber = applicationCache.getCurrentNumber();
        if ((previousNumber + numberToAdd) % 3 != 0) {
            return -1;
        } else {
            applicationCache.setCurrentNumber((previousNumber + numberToAdd) / 3);
            return previousNumber;
        }
    }

    public boolean addNextNumber(Integer number) {
        Integer previousNumber = checkAndAdd(number);
        if (previousNumber != -1) {
            producer.produce(new NumberGeneratedEvent(previousNumber, number, applicationCache.getCurrentNumber(),
                    applicationCache.getServiceInstance()));
            return true;
        } else {
            return false;
        }
    }

    public abstract void addNextNumber();
}
