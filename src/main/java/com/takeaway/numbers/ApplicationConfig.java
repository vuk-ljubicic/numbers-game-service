package com.takeaway.numbers;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class ApplicationConfig {
    private Map<String, Map<String, String>> serviceInstances;
    private String defaultServerInstance;
    private String clientConnectSleep;

    public String getClientConnectSleep() {
        return clientConnectSleep;
    }

    public void setClientConnectSleep(String clientConnectSleep) {
        this.clientConnectSleep = clientConnectSleep;
    }

    public Map<String, Map<String, String>> getServiceInstances() {
        return serviceInstances;
    }

    public void setServiceInstances(Map<String, Map<String, String>> serviceInstances) {
        this.serviceInstances = serviceInstances;
    }

    public String getDefaultServerInstance() {
        return defaultServerInstance;
    }

    public void setDefaultServerInstance(String defaultServerInstance) {
        this.defaultServerInstance = defaultServerInstance;
    }
}
