package com.takeaway.numbers.service.console.commands;

import com.takeaway.numbers.cache.ApplicationCache;
import com.takeaway.numbers.eventbus.EventStore;
import com.takeaway.numbers.eventbus.Producer;
import com.takeaway.numbers.service.console.ConsoleService;
import com.takeaway.numbers.service.number.NumberService;

import java.util.regex.Matcher;

public class RestartCommand extends Command{
    public static final String R_REGEX = "^\\s*R\\s*$";

    @Override
    public void execute(Producer producer, Matcher matcher, ConsoleService consoleService,
                        ApplicationCache applicationCache, EventStore eventStore, NumberService numberService) {
        applicationCache.setCurrentNumber(-1);
        consoleService.startGame();
    }

    @Override
    public String getRegex() {
        return R_REGEX;
    }
}
