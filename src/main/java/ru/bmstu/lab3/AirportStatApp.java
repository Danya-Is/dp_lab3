package ru.bmstu.lab3;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import scala.Tuple2;

import java.util.Map;

public class AirportStatApp {

    public static final int DEPARTURE_AIRPORT_POS = 11;
    public static final int DESTINATION_AIRPORT_POS = 14;
    public static final int AIRPORT_DELAY_POS = 17;
    public static final int IS_CANCELED_POS = 19;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Input format : <output path>");
            System.exit(-1);
        }

        SparkConf conf = new SparkConf().setAppName("AirportStatApp");
        JavaSparkContext sc = new JavaSparkContext(conf);
        Map<String, String> airports = sc.textFile("AIRPORTS.csv")
                .filter(row -> !row.contains("Code"))
                .map(TableRow::parseAirportTable)
                .mapToPair(airportRows -> new Tuple2<>(airportRows.get(0), airportRows.get(1)))
                .collectAsMap();
        JavaPairRDD<Tuple2<String, String>, RouteInfo> routes = sc.textFile("FLIGHTS.csv")
                .filter(row -> !row.contains("ARR_DELAY"))
                .map(TableRow::parseFlightTable)
                .mapToPair(flightRows -> new Tuple2<>(new Tuple2<>(flightRows.get(DEPARTURE_AIRPORT_POS), flightRows.get(DESTINATION_AIRPORT_POS)), flightRows.get(AIRPORT_DELAY_POS)))
                .combineByKey(new CreateRouteInfoFunction(), new AppendRouteInfoFunction(), new MergeRouteInfoFunction());


        final Broadcast<Map<String, String>> airportsBroadcasted = sc.broadcast(airports);

        routes.map(pair -> RouteInfo.join(pair, airportsBroadcasted.value()));

    }
}
