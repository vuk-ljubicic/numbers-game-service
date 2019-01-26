package com.takeaway.numbers.service.console.commands;

import com.takeaway.numbers.cache.ApplicationCache;

import com.takeaway.numbers.eventbus.EventStore;
import com.takeaway.numbers.eventbus.Producer;
import com.takeaway.numbers.service.console.ConsoleService;
import com.takeaway.numbers.service.number.NumberService;

import java.util.regex.Matcher;

public abstract class Command {
    public abstract void execute(Producer producer, Matcher matcher, ConsoleService consoleService,
                                 ApplicationCache applicationCache, EventStore eventStore, NumberService numberService);
    public abstract String getRegex();
}
