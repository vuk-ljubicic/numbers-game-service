package com.takeaway.numbers.eventbus.connector;

import com.takeaway.numbers.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;

@Component
public class ClientSideConnectorWorker extends ConnectorWorker implements Runnable {
    private Logger log = LoggerFactory.getLogger(Connector.class);

    @Autowired
    public ClientSideConnectorWorker(ApplicationConfig applicationConfig,
                                     EventReader eventReader, EventWriter eventWritter) {
        this.applicationConfig = applicationConfig;
        this.eventReader = eventReader;
        this.eventWritter = eventWritter;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = new Socket(applicationConfig.getServerHost(), applicationConfig.getServerPort());
                startEventReaderAndWriter(socket);
                break;
            } catch (IOException e) {
                log.info("Unable to connect to server");
            }
            try {
                Thread.sleep(new Integer(applicationConfig.getClientConnectSleep()));
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
