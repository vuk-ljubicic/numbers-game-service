package com.takeaway.numbers.service.console.commands;

import com.takeaway.numbers.cache.ApplicationCache;
import com.takeaway.numbers.eventbus.EventStore;
import com.takeaway.numbers.eventbus.Producer;
import com.takeaway.numbers.eventbus.event.ManualPlaySelectedEvent;
import com.takeaway.numbers.service.console.ConsoleService;

import java.util.regex.Matcher;

public class ManualModeCommand extends Command{
    public static final String M_REGEX = "^\\s*M\\s*$";

    @Override
    public void execute(Producer producer, Matcher matcher, ConsoleService consoleService,
                        ApplicationCache applicationCache, EventStore eventStore) {
        producer.produce(new ManualPlaySelectedEvent(applicationCache.getServiceInstance()));
    }

    @Override
    public String getRegex() {
        return M_REGEX;
    }
}
