package ru.bmstu.lab3;

import org.apache.spark.api.java.function.Function2;

public class AppendRouteInfoFunction implements Function2<String, RouteInfo, String> {
    @Override
    public RouteInfo call(RouteInfo routeInfo, String delay) throws Exception {
        return routeInfo.addFlight(RouteInfo.createRoute(delay));
    }

    @Override
    public String call(String s, RouteInfo routeInfo) throws Exception {
        return null;
    }
}
