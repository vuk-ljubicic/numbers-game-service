package com.takeaway.numbers.util;

import com.takeaway.numbers.cache.ApplicationCache;

public class NumberAdditionUtil {
    private ApplicationCache applicationCache;
    private Integer previousNumber;
    private Integer currentNumber;
    private Integer numberToAdd;

    public NumberAdditionUtil(ApplicationCache applicationCache) {
        this.applicationCache = applicationCache;
    }

    public ApplicationCache getApplicationCache() {
        return applicationCache;
    }

    public void setApplicationCache(ApplicationCache applicationCache) {
        this.applicationCache = applicationCache;
    }

    public Integer getPreviousNumber() {
        return previousNumber;
    }

    public void setPreviousNumber(Integer previousNumber) {
        this.previousNumber = previousNumber;
    }

    public Integer getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(Integer currentNumber) {
        this.currentNumber = currentNumber;
    }

    public Integer getNumberToAdd() {
        return numberToAdd;
    }

    public void setNumberToAdd(Integer numberToAdd) {
        this.numberToAdd = numberToAdd;
    }

    public boolean checkAndAdd(Integer numberToAdd) {
        this.numberToAdd = numberToAdd;
        previousNumber = applicationCache.getCurrentNumber();
        if (previousNumber + this.numberToAdd % 3 != 0) {
            return false;
        } else {
            currentNumber = (previousNumber + numberToAdd) / 3;
            applicationCache.setCurrentNumber(currentNumber);
            return true;
        }
    }
}
