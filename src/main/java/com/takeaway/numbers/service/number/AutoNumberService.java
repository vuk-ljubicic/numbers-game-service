package com.takeaway.numbers.service.number;

import com.takeaway.numbers.cache.ApplicationCache;
import com.takeaway.numbers.eventbus.Producer;
import com.takeaway.numbers.service.console.ConsoleService;

public class AutoNumberService extends NumberService {
    public AutoNumberService(ConsoleService consoleService, ApplicationCache applicationCache, Producer producer) {
        super(consoleService, applicationCache, producer);
    }
    @Override
    public void addNextNumber() {
        if (addNextNumber(-1)) {
            return;
        }
        if (addNextNumber(0)) {
            return;
        }
        if (addNextNumber(1)) {
            return;
        }
    }
}

