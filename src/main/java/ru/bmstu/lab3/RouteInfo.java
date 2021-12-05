package ru.bmstu.lab3;

import org.apache.spark.api.java.JavaPairRDD;
import scala.Tuple2;

import java.io.Serializable;
import java.util.Map;

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

    public RouteInfo addFlight(RouteInfo flight) {
        maxDelay = flight.maxDelay > maxDelay ? maxDelay = flight.maxDelay : maxDelay;
        flightAmount++;
        delayedAmount += flight.maxDelay > 0 ? 1 : 0;
        canceledAmount += flight.canceledAmount;
        return this;
    }

    static public RouteInfo createRoute(String delay) {
        int isCancelled = delay.isEmpty() ? 1 : 0;
        float maxDelay = isCancelled == 1 || delay.charAt(0) == '-' ? 0 : Float.parseFloat(delay);
        int isDelayed = maxDelay > 0 ? 1 : 0;
        return new RouteInfo(maxDelay, 1, isDelayed, isCancelled);
    }

    static public String join(Tuple2<Tuple2<String, String>, RouteInfo> pair, Map<String, String> airports) {
        int delayedPercent = Math.round(((float) pair._2.delayedAmount / pair._2.flightAmount) * 100);
        int cancelledPercent = Math.round(((float) pair._2.canceledAmount / pair._2.flightAmount) * 100);
        String departureAirportID = pair._1._1;
        String destinationAirportID = pair._1._2;
        return "Для маршрута из аэропорта " + airports.get(departureAirportID) + " в аэропорт " + airports.get(destinationAirportID) +
                "\nмаксимальное время задержки: " + pair._2.maxDelay + " (минут/ы)\n" +
                "процент задержек: " + delayedPercent + "%\n" +
                "процент отмененных рейсов: " + cancelledPercent + "%";
    }
}
