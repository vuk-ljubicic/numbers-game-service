package com.takeaway.numbers.service.console.commands;

import com.takeaway.numbers.cache.ApplicationCache;
import com.takeaway.numbers.eventbus.EventStore;
import com.takeaway.numbers.eventbus.Producer;
import com.takeaway.numbers.eventbus.event.NumberGeneratedEvent;
import com.takeaway.numbers.service.console.ConsoleService;

import java.util.regex.Matcher;

public class PositiveWholeNumberCommand extends Command {
    public static final String RANDOM_NUMBER_REGEX = "^\\s*(\\d+)\\s*$";

    @Override
    public void execute(Producer producer, Matcher matcher, ConsoleService consoleService,
                        ApplicationCache applicationCache, EventStore eventStore) {
        Integer positiveWholeNumber = Integer.valueOf(matcher.group(1));
        producer.produce(new NumberGeneratedEvent(applicationCache.getCurrentNumber(), 0,
                positiveWholeNumber, applicationCache.getServiceInstance()));
        applicationCache.setCurrentNumber(positiveWholeNumber);
    }

    @Override
    public String getRegex() {
        return RANDOM_NUMBER_REGEX;
    }
}
