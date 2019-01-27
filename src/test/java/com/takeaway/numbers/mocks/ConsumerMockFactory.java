package com.takeaway.numbers.mocks;

import com.takeaway.numbers.ApplicationConfig;
import com.takeaway.numbers.cache.ApplicationCache;
import com.takeaway.numbers.eventbus.Consumer;
import com.takeaway.numbers.eventbus.EventStore;
import com.takeaway.numbers.eventbus.Producer;
import com.takeaway.numbers.eventbus.connector.Connector;
import com.takeaway.numbers.eventbus.event.NumberGeneratedEvent;
import com.takeaway.numbers.eventbus.handler.NumberGeneratedEventHandler;
import com.takeaway.numbers.fixture.NumberGenerationFixture;
import com.takeaway.numbers.service.number.AutoNumberService;
import com.takeaway.numbers.service.number.ConsoleNumberService;

public class ConsumerMockFactory {
    public ConsumerRunnableMock getConsumerMock(String player, NumberGenerationFixture fixture, Integer maxEventCount) {
        EventStore eventStore = new EventStore();
        ApplicationCache applicationCache = new ApplicationCache();
        applicationCache.setCurrentNumber(0);
        applicationCache.setServiceInstance(player);
        applicationCache.setPeerInstance(player.equals(Connector.SERVICE_INSTANCE_SERVER) ?
                Connector.SERVICE_INSTANCE_CLIENT : Connector.SERVICE_INSTANCE_SERVER);
        applicationCache.setStartingGame(player.equals(Connector.SERVICE_INSTANCE_SERVER));
        Producer producer = new Producer(eventStore);
        ConsumerRunnableMock consumer = new ConsumerRunnableMock(maxEventCount);
        consumer.setApplicationCache(applicationCache);
        consumer.setEventStore(eventStore);
        consumer.setProducer(producer);
        consumer.setApplicationConfig(new ApplicationConfig());
        consumer.setConsoleService(getConsoleServiceMock(producer, applicationCache, eventStore));
        consumer.initHandlers();
        initNumberGeneratedEventHandler(fixture, applicationCache, consumer);
        initStartingEvent(eventStore, fixture, applicationCache, consumer);
        return consumer;
    }

    private void initStartingEvent(EventStore eventStore, NumberGenerationFixture fixture,
                                   ApplicationCache applicationCache, Consumer consumer) {
        if (applicationCache.isStartingGame()) {
            applicationCache.setCurrentNumber(fixture.getStartingNumber());
            eventStore.getEvents().addFirst(
                    new NumberGeneratedEvent(0, 0,
                            fixture.getStartingNumber(), applicationCache.getServiceInstance()));
        }
    }

    private void initNumberGeneratedEventHandler(NumberGenerationFixture fixture, ApplicationCache applicationCache,
                                                 Consumer consumer) {
        if (fixture.getInstancePlayModes().get(applicationCache.getServiceInstance()).equals("A")) {
            AutoNumberService autoNumberService =
                    new AutoNumberService(consumer.getConsoleService(), applicationCache, consumer.getProducer());
            consumer.getHandlers().put(NumberGeneratedEvent.class,
                    new NumberGeneratedEventHandler(applicationCache, consumer.getProducer(), consumer,
                            consumer.getConsoleService(), autoNumberService, consumer.getApplicationConfig()));
        } else {
            ConsoleNumberService consoleNumberService =
                    new ConsoleNumberService(consumer.getConsoleService(), applicationCache, consumer.getProducer());
            consumer.getConsoleService().setNumberService(consoleNumberService);
            consumer.getHandlers().put(NumberGeneratedEvent.class,
                    new NumberGeneratedEventHandler(applicationCache, consumer.getProducer(), consumer,
                            consumer.getConsoleService(), consoleNumberService, consumer.getApplicationConfig()));
        }
    }

    private ConsoleServiceMock getConsoleServiceMock(Producer producer, ApplicationCache applicationCache,
                                                     EventStore eventStore) {
        ConsoleServiceMock consoleService = new ConsoleServiceMock();
        consoleService.setProducer(producer);
        consoleService.setApplicationCache(applicationCache);
        consoleService.setEventStore(eventStore);
        return consoleService;
    }
}
