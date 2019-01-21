package com.takeaway.numbers.service.console.commands;

import com.takeaway.numbers.cache.ApplicationCache;
import com.takeaway.numbers.eventbus.EventStore;
import com.takeaway.numbers.eventbus.Producer;
import com.takeaway.numbers.eventbus.event.NumberGeneratedEvent;
import com.takeaway.numbers.service.console.ConsoleService;
import com.takeaway.numbers.util.NumberAdditionUtil;

import java.util.regex.Matcher;

public class NumberToAddCommand extends Command {
    public static final String ADDED_NUMBER_REGEX = "^\\s*(-?[0,1])\\s*$";

    @Override
    public void execute(Producer producer, Matcher matcher, ConsoleService consoleService,
                        ApplicationCache applicationCache, EventStore eventStore) {
        NumberAdditionUtil numberAdditionUtil = new NumberAdditionUtil(applicationCache);
        Integer numberToAdd = Integer.valueOf(matcher.group(1));
        if (numberAdditionUtil.checkAndAdd(numberToAdd)) {
            consoleService.addNumber();
        } else {
            producer.produce(new NumberGeneratedEvent(numberAdditionUtil.getPreviousNumber(),
                    numberAdditionUtil.getNumberToAdd(), numberAdditionUtil.getCurrentNumber(),
                    applicationCache.getServiceInstance()));
        }
    }

    @Override
    public String getRegex() {
        return ADDED_NUMBER_REGEX;
    }
}
