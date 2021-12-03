package ru.bmstu.lab3;

import org.apache.spark.api.java.function.Function;

public class CreateRouteInfoFunction implements Function<String, RouteInfo> {

    @Override
    public RouteInfo call(String delay) throws Exception {
        return RouteInfo.createRoute(delay);
    }
}
