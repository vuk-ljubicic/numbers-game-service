package com.takeaway.numbers.eventbus.connector;

import com.takeaway.numbers.cache.ApplicationCache;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Based on command line argument, determines if this service instance should act as client or server.
 * Adds corresponding ConnectorWorker that will perform connect to executor.
 */
public class Connector {
    public static final String SERVICE_INSTANCE_SERVER = "SERVER";
    public static final String SERVICE_INSTANCE_CLIENT = "CLIENT";
    @Autowired
    private ClientSideConnectorWorker clientSideConnectorWorker;
    @Autowired
    private ServerSideConnectorWorker serverSideConnectorWorker;
    @Autowired
    private ApplicationCache applicationCache;
    private String instanceType;
    private ExecutorService executor;

    public Connector(String instanceType) {
        this.instanceType = instanceType;
        executor = Executors.newFixedThreadPool(2);
    }

    @PostConstruct
    public void init() {
        if (instanceType != null && instanceType.equals(SERVICE_INSTANCE_SERVER)) {
            applicationCache.setServiceInstanceType(SERVICE_INSTANCE_SERVER);
            serverSideConnectorWorker.setExecutor(executor);
            executor.execute(serverSideConnectorWorker);
        } else {
            applicationCache.setServiceInstanceType(SERVICE_INSTANCE_CLIENT);
            clientSideConnectorWorker.setExecutor(executor);
            executor.execute(clientSideConnectorWorker);
        }
    }
}
