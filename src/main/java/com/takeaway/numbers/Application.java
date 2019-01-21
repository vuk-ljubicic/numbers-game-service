package com.takeaway.numbers;

import com.takeaway.numbers.eventbus.Consumer;
import com.takeaway.numbers.eventbus.connector.Connector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class Application {

    private static ExecutorService executor;

    public static void main(String[] args) {
        executor = Executors.newFixedThreadPool(3);
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Connector getConnector(@Value("${instanceType}") String instanceType) {
        return new Connector(instanceType, executor);
    }

    @Bean
    public Consumer getConsumer() {
        return new Consumer(executor);
    }
}