package com.takeaway.numbers.service.number;

import com.takeaway.numbers.cache.ApplicationCache;
import com.takeaway.numbers.eventbus.Producer;
import com.takeaway.numbers.service.console.ConsoleService;

public class ConsoleNumberService extends NumberService {
    public ConsoleNumberService(ConsoleService consoleService, ApplicationCache applicationCache, Producer producer) {
        super(consoleService, applicationCache, producer);
    }

    @Override
    public void addNextNumber() {
        consoleService.addNumber();
    }
}
