package com.takeaway.numbers.service.console.commands;

import com.takeaway.numbers.cache.ApplicationCache;
import com.takeaway.numbers.eventbus.EventStore;
import com.takeaway.numbers.eventbus.Producer;
import com.takeaway.numbers.eventbus.event.AutoPlaySelectedEvent;
import com.takeaway.numbers.service.console.ConsoleService;
import com.takeaway.numbers.service.number.NumberService;

import java.util.regex.Matcher;

public class AutoModeCommand extends Command{
    public static final String A_REGEX = "^\\s*A\\s*$";

    @Override
    public void execute(Producer producer, Matcher matcher, ConsoleService consoleService,
                        ApplicationCache applicationCache, EventStore eventStore, NumberService numberService) {
        producer.produce(new AutoPlaySelectedEvent(applicationCache.getServiceInstance()));
    }

    @Override
    public String getRegex() {
        return A_REGEX;
    }
}
