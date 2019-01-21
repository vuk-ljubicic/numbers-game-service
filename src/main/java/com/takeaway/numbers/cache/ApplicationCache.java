package com.takeaway.numbers.cache;

import org.springframework.stereotype.Component;

@Component
public class ApplicationCache {
    private String serviceInstanceType;
    public String getServiceInstanceType() {
        return serviceInstanceType;
    }

    public void setServiceInstanceType(String serviceInstanceType) {
        this.serviceInstanceType = serviceInstanceType;
    }
}
