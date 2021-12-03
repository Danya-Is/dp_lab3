package ru.bmstu.lab3;

import java.io.Serializable;

public class RouteInfo implements Serializable {
    private float maxDelay;
    private int flightAmount;
    private int delayedAmount;
    private int canceledAmount;

    public RouteInfo(float maxDelay, int flightAmount, int delayedAmount, int canceledAmount) {
        this.maxDelay = maxDelay;
        this.flightAmount = flightAmount;
        this.delayedAmount = delayedAmount;
        this.canceledAmount = canceledAmount;
    }

    public RouteInfo addFlight(float delay, int isCancelled) {
        maxDelay = delay > maxDelay ? maxDelay = delay : maxDelay;
        flightAmount++;
        delayedAmount += delay > 0 ? delay : 0;
        canceledAmount += isCancelled;
        return this;
    }
}

//gitwatch test
