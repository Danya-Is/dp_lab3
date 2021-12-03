package ru.bmstu.lab3;

import org.apache.spark.api.java.function.Function2;

public class MergeRouteInfoFunction implements Function2<RouteInfo, RouteInfo, RouteInfo> {
    @Override
    public RouteInfo call(RouteInfo routeInfo, RouteInfo routeInfo2) throws Exception {
        return routeInfo.addFlight(routeInfo2);
    }
}
