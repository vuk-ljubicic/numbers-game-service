package com.takeaway.numbers.mocks;

import com.takeaway.numbers.eventbus.Consumer;
import com.takeaway.numbers.eventbus.event.NumberGeneratedEvent;

import java.util.concurrent.ExecutorService;

public class ConsumerRunnableMock extends Consumer implements Runnable {
    private Integer eventCount;
    private Integer maxEventCount;

    public ConsumerRunnableMock(ExecutorService executor) {
        super(executor);
    }

    public ConsumerRunnableMock(Integer maxEventCount) {
        this.maxEventCount = maxEventCount;
        eventCount = 0;
    }

    @Override
    public void run() {
        while (eventCount <= maxEventCount) {
            eventCount++;
            invokeHandler();
        }
    }

    public NumberGeneratedEvent getWinnerEvent() {
        return ((ConsoleServiceMock) this.getConsoleService()).getWinnerEvent();
    }
}
