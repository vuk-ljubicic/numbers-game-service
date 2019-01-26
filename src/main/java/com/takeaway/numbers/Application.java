package com.takeaway.numbers;

import com.takeaway.numbers.eventbus.Consumer;
import com.takeaway.numbers.eventbus.connector.Connector;
import com.takeaway.numbers.service.console.ConsoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class Application {

    private static ExecutorService executor;

    @Autowired
    private ConsoleService consoleService;

    public static void main(String[] args) {
        executor = Executors.newFixedThreadPool(3);
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        ConsoleService consoleService = (ConsoleService) applicationContext.getBean("consoleService");
        consoleService.startGame();
    }

    @Bean
    public Connector getConnector(@Value("${instanceType}") String instanceType) {
        return new Connector(instanceType, executor);
    }

    @Bean
    public Consumer getConsumer() {
        return new Consumer(executor);
    }

    public static ExecutorService getExecutor() {
        return executor;
    }

    public static void setExecutor(ExecutorService executor) {
        Application.executor = executor;
    }

    public ConsoleService getConsoleService() {
        return consoleService;
    }

    public void setConsoleService(ConsoleService consoleService) {
        this.consoleService = consoleService;
    }
}