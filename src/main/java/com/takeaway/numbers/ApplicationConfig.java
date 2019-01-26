package com.takeaway.numbers;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class ApplicationConfig {
    private String serverHost;
    private Integer serverPort;
    private String clientConnectSleep;
    private String startingPlayer;
    private Integer randomPositiveNumberLow;
    private Integer randomPositiveNumberHigh;

    public Integer getRandomPositiveNumberLow() {
        return randomPositiveNumberLow;
    }

    public void setRandomPositiveNumberLow(Integer randomPositiveNumberLow) {
        this.randomPositiveNumberLow = randomPositiveNumberLow;
    }

    public Integer getRandomPositiveNumberHigh() {
        return randomPositiveNumberHigh;
    }

    public void setRandomPositiveNumberHigh(Integer randomPositiveNumberHigh) {
        this.randomPositiveNumberHigh = randomPositiveNumberHigh;
    }

    public String getStartingPlayer() {
        return startingPlayer;
    }

    public void setStartingPlayer(String startingPlayer) {
        this.startingPlayer = startingPlayer;
    }

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public String getClientConnectSleep() {
        return clientConnectSleep;
    }

    public void setClientConnectSleep(String clientConnectSleep) {
        this.clientConnectSleep = clientConnectSleep;
    }
}
