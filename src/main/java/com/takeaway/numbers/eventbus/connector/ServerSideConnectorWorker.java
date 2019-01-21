package com.takeaway.numbers.eventbus.connector;

import com.takeaway.numbers.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class ServerSideConnectorWorker extends ConnectorWorker implements Runnable {
    private Logger log = LoggerFactory.getLogger(ServerSideConnectorWorker.class);

    @Autowired
    public ServerSideConnectorWorker(ApplicationConfig applicationConfig,
                                     EventReader eventReader, EventWriter eventWritter) {
        this.applicationConfig = applicationConfig;
        this.eventReader = eventReader;
        this.eventWritter = eventWritter;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(applicationConfig.getServerPort());
            Socket clientSocket = serverSocket.accept();
            startEventReaderAndWriter(clientSocket);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
