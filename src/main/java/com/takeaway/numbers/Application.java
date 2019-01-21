package com.takeaway.numbers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    @Autowired
    private ApplicationConfig applicationConfig;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}