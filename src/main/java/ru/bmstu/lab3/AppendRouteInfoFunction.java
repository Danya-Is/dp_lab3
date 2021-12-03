package ru.bmstu.lab3;

import org.apache.spark.api.java.function.Function2;

public class AppendRouteInfoFunction implements Function2<RouteInfo, String, RouteInfo> {
    @Override
    public RouteInfo call(RouteInfo routeInfo, String delay) throws Exception {
        return routeInfo.addFlight(RouteInfo.createRoute(delay));
    }
}
