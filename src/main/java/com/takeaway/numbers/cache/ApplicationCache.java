package com.takeaway.numbers.cache;

import org.springframework.stereotype.Component;

@Component
public class ApplicationCache {
    private String serviceInstance;
    private String peerInstance;
    private boolean startingGame;
    private Integer currentNumber;

    public ApplicationCache() {
        currentNumber = 0;
    }

    public boolean isStartingGame() {
        return startingGame;
    }

    public void setStartingGame(boolean startingGame) {
        this.startingGame = startingGame;
    }

    public String getPeerInstance() {
        return peerInstance;
    }

    public void setPeerInstance(String peerInstance) {
        this.peerInstance = peerInstance;
    }

    public Integer getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(Integer currentNumber) {
        this.currentNumber = currentNumber;
    }

    public String getServiceInstance() {
        return serviceInstance;
    }

    public void setServiceInstance(String serviceInstance) {
        this.serviceInstance = serviceInstance;
    }
}
