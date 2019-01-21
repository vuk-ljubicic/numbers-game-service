package com.takeaway.numbers.eventbus.connector;

import com.takeaway.numbers.ApplicationConfig;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public abstract class ConnectorWorker {
    protected ExecutorService executor;
    protected ApplicationConfig applicationConfig;
    protected EventWriter eventWritter;
    protected EventReader eventReader;

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    protected void startEventReaderAndWriter(Socket socket) throws IOException {
        eventWritter.setOutputStream(new ObjectOutputStream(socket.getOutputStream()));
        eventReader.setInputStream(new ObjectInputStream(socket.getInputStream()));
        executor.execute(eventReader);
        executor.execute(eventWritter);
    }
}
