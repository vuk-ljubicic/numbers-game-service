package com.takeaway.numbers;

import com.takeaway.numbers.eventbus.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    @Autowired
    private ApplicationConfig applicationConfig;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Connector getConnector(@Value("${instanceType}") String instanceType) {
        return new Connector(instanceType);
    }
}