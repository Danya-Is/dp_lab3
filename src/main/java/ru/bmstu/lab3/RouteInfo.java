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

    public static RouteInfo addFlight(float delay, int isCancelled) {
        return new RouteInfo(delay > )
    }
}
