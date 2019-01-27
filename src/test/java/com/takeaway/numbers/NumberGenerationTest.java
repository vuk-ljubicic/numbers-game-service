package com.takeaway.numbers;

import com.takeaway.numbers.eventbus.connector.Connector;
import com.takeaway.numbers.eventbus.event.NumberGeneratedEvent;
import com.takeaway.numbers.fixture.NumberGenerationFixture;
import com.takeaway.numbers.mocks.ConsumerMockFactory;
import com.takeaway.numbers.mocks.ConsumerRunnableMock;
import com.takeaway.numbers.mocks.EventBusMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class NumberGenerationTest {
    public static final Integer MAX_EVENT_COUNT = 10;
    private NumberGenerationFixture numberGenerationFixture;
    private ConsumerMockFactory consumerMockFactory;
    private ConsumerRunnableMock serverConsumerMock;
    private ConsumerRunnableMock clientConsumerMock;
    private EventBusMock eventBusMock;

    public NumberGenerationTest(NumberGenerationFixture numberGenerationFixture) {
        this.numberGenerationFixture = numberGenerationFixture;
        consumerMockFactory = new ConsumerMockFactory();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Map<String, String> serverAutoClientManual = new HashMap<>();
        serverAutoClientManual.put(Connector.SERVICE_INSTANCE_SERVER, "A");
        serverAutoClientManual.put(Connector.SERVICE_INSTANCE_CLIENT, "M");
        Map<String, String> serverManualClientManual = new HashMap<>();
        serverManualClientManual.put(Connector.SERVICE_INSTANCE_SERVER, "M");
        serverManualClientManual.put(Connector.SERVICE_INSTANCE_CLIENT, "M");
        Map<String, String> serverManualClientAuto = new HashMap<>();
        serverManualClientAuto.put(Connector.SERVICE_INSTANCE_SERVER, "M");
        serverManualClientAuto.put(Connector.SERVICE_INSTANCE_CLIENT, "A");
        Map<String, String> serverAutoClientAuto = new HashMap<>();
        serverAutoClientAuto.put(Connector.SERVICE_INSTANCE_SERVER, "A");
        serverAutoClientAuto.put(Connector.SERVICE_INSTANCE_CLIENT, "A");
        return Arrays.asList(new Object[][]{
                {new NumberGenerationFixture(5, Connector.SERVICE_INSTANCE_SERVER, serverAutoClientManual)},
                {new NumberGenerationFixture(21, Connector.SERVICE_INSTANCE_CLIENT, serverManualClientManual)},
                {new NumberGenerationFixture(51, Connector.SERVICE_INSTANCE_SERVER, serverManualClientAuto)},
                {new NumberGenerationFixture(101, Connector.SERVICE_INSTANCE_SERVER, serverAutoClientAuto)},
                {new NumberGenerationFixture(10, Connector.SERVICE_INSTANCE_SERVER, serverAutoClientManual)}
        });
    }

    @Before
    public void setup() throws Exception {
        serverConsumerMock = consumerMockFactory.getConsumerMock(Connector.SERVICE_INSTANCE_SERVER,
                numberGenerationFixture, MAX_EVENT_COUNT);
        clientConsumerMock = consumerMockFactory.getConsumerMock(Connector.SERVICE_INSTANCE_CLIENT,
                numberGenerationFixture, MAX_EVENT_COUNT);
        eventBusMock = new EventBusMock(serverConsumerMock.getEventStore(), clientConsumerMock.getEventStore(),
                MAX_EVENT_COUNT);
        Thread serverConsumerMockThread = new Thread(serverConsumerMock);
        Thread clientConsumerMockThread = new Thread(clientConsumerMock);
        Thread eventBusMockThread = new Thread(eventBusMock);
        eventBusMockThread.start();
        serverConsumerMockThread.start();
        clientConsumerMockThread.start();
        serverConsumerMockThread.join();
        clientConsumerMockThread.join();
        eventBusMockThread.join();
    }

    @Test
    public void numberGenerationTest() {
        NumberGeneratedEvent result = serverConsumerMock.getWinnerEvent() != null ?
                serverConsumerMock.getWinnerEvent() : clientConsumerMock.getWinnerEvent();
        assertTrue(result.getInstanceType().equals(numberGenerationFixture.getWinningInstance()));
    }
}
