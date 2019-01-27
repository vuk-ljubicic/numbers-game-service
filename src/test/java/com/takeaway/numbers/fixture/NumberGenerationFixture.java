package com.takeaway.numbers.fixture;

import java.util.Map;

public class NumberGenerationFixture {
    private Integer startingNumber;
    private String winningInstance;
    private Map<String, String> instancePlayModes;

    public NumberGenerationFixture(Integer startingNumber, String winningInstance, Map<String, String> instancePlayModes) {
        this.startingNumber = startingNumber;
        this.winningInstance = winningInstance;
        this.instancePlayModes = instancePlayModes;
    }

    public Map<String, String> getInstancePlayModes() {
        return instancePlayModes;
    }

    public void setInstancePlayModes(Map<String, String> instancePlayModes) {
        this.instancePlayModes = instancePlayModes;
    }

    public Integer getStartingNumber() {
        return startingNumber;
    }

    public void setStartingNumber(Integer startingNumber) {
        this.startingNumber = startingNumber;
    }

    public String getWinningInstance() {
        return winningInstance;
    }

    public void setWinningInstance(String winningInstance) {
        this.winningInstance = winningInstance;
    }
}
