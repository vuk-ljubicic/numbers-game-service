package com.takeaway.numbers.mocks;

import com.takeaway.numbers.eventbus.event.NumberGeneratedEvent;
import com.takeaway.numbers.service.console.ConsoleService;

public class ConsoleServiceMock extends ConsoleService {
    private NumberGeneratedEvent winnerEvent;

    public NumberGeneratedEvent getWinnerEvent() {
        return winnerEvent;
    }

    public void setWinnerEvent(NumberGeneratedEvent winnerEvent) {
        this.winnerEvent = winnerEvent;
    }

    @Override
    public String consoleInput() {
        Integer previousNumber = applicationCache.getCurrentNumber();
        if ((previousNumber - 1) % 3 == 0) {
            return "-1";
        }
        if ((previousNumber) % 3 == 0) {
            return "0";
        }
        if ((previousNumber + 1) % 3 == 0) {
            return "1";
        }
        return "";
    }

    @Override
    public void winner(NumberGeneratedEvent numberGeneratedEvent) {
        winnerEvent = numberGeneratedEvent;
    }
}
