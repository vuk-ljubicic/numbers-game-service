package com.takeaway.numbers.eventbus.event;

public class NumberGeneratedEvent extends Event {
    private Integer previousNumber;
    private Integer addedNumber;
    private Integer resultingNumber;

    public NumberGeneratedEvent(Integer previousNumber, Integer addedNumber,
                                Integer resultingNumber, String instanceType) {
        super(instanceType);
        this.previousNumber = previousNumber;
        this.addedNumber = addedNumber;
        this.resultingNumber = resultingNumber;
        shouldDistribute = true;
    }

    public Integer getPreviousNumber() {
        return previousNumber;
    }

    public void setPreviousNumber(Integer previousNumber) {
        this.previousNumber = previousNumber;
    }

    public Integer getAddedNumber() {
        return addedNumber;
    }

    public void setAddedNumber(Integer addedNumber) {
        this.addedNumber = addedNumber;
    }

    public Integer getResultingNumber() {
        return resultingNumber;
    }

    public void setResultingNumber(Integer resultingNumber) {
        this.resultingNumber = resultingNumber;
    }
}

