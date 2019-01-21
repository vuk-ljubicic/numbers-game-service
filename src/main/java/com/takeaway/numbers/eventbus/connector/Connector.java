package com.takeaway.numbers.eventbus.connector;

import com.takeaway.numbers.ApplicationConfig;
import com.takeaway.numbers.cache.ApplicationCache;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;

/**
 * Based on command line argument, determines if this service instance should act as client or server.
 * Adds corresponding ConnectorWorker that will perform connect to executor.
 */
public class Connector {
    public static final String SERVICE_INSTANCE_SERVER = "PLAYER1";
    public static final String SERVICE_INSTANCE_CLIENT = "PLAYER2";
    @Autowired
    private ClientSideConnectorWorker clientSideConnectorWorker;
    @Autowired
    private ServerSideConnectorWorker serverSideConnectorWorker;
    @Autowired
    private ApplicationCache applicationCache;
    @Autowired
    private ApplicationConfig applicationConfig;

    private ExecutorService executor;
    private String instanceType;

    public Connector(String instanceType, ExecutorService executor) {
        this.instanceType = instanceType;
    }

    @PostConstruct
    public void init() {
        if (instanceType != null && instanceType.equals(SERVICE_INSTANCE_CLIENT)) {
            applicationCache.setServiceInstance(SERVICE_INSTANCE_CLIENT);
            applicationCache.setPeerInstance(SERVICE_INSTANCE_SERVER);
            applicationCache.setStartingGame(applicationConfig.getStartingPlayer().equals(SERVICE_INSTANCE_CLIENT));
            clientSideConnectorWorker.setExecutor(executor);
            executor.execute(clientSideConnectorWorker);
        } else {
            applicationCache.setServiceInstance(SERVICE_INSTANCE_SERVER);
            applicationCache.setPeerInstance(SERVICE_INSTANCE_CLIENT);
            applicationCache.setStartingGame(applicationConfig.getStartingPlayer().equals(SERVICE_INSTANCE_SERVER));
            serverSideConnectorWorker.setExecutor(executor);
            executor.execute(serverSideConnectorWorker);

        }
    }
}
