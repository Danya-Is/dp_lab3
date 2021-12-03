package main.java.ru.bmstu.lab3;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import ru.bmstu.lab3.AppendRouteInfoFunction;
import ru.bmstu.lab3.CreateRouteInfoFunction;
import ru.bmstu.lab3.MergeRouteInfoFunction;
import ru.bmstu.lab3.RouteInfo;
import scala.Tuple2;

import java.util.Map;

public class AirportStatApp {

    public static final int DEPATURE_AIRPORT_POS = 11;
    public static final int DESTINATION_AIRPORT_POS = 14;
    public static final int AIRPORT_DELAY_POS = 18;
    public static final int IS_CANCELED_POS = 19;

    public static final String FLIGHTS_DELIMITER = ",";
    public static final String AIRPORTS_DELIMITER = "\",\"";

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("AirportStatApp");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> flights = sc.textFile("FLIGHTS.scv").filter(row -> !row.contains("ARR_DELAY"));
        Map<String, String> airports = sc.textFile("AIRPORTS.scv")
                .filter(row -> !row.contains("Code"))
                .map(airportRow -> airportRow.split(AIRPORTS_DELIMITER))
                .mapToPair(airportRows -> new Tuple2<>(airportRows[0], airportRows[1]))
                .collectAsMap();
        JavaPairRDD<Tuple2<String, String>, RouteInfo> routes = flights
                .map(flightRow -> flightRow.split(FLIGHTS_DELIMITER))
                .mapToPair(flightRows -> new Tuple2<>(new Tuple2<>(flightRows[DEPATURE_AIRPORT_POS], flightRows[DESTINATION_AIRPORT_POS]), flightRows[AIRPORT_DELAY_POS]))
                .combineByKey(new CreateRouteInfoFunction(), new AppendRouteInfoFunction(), new MergeRouteInfoFunction());


        final Broadcast<Map<String, String>> airportsBroadcasted = sc.broadcast(airports);

        routes.map(pair -> RouteInfo.join(pair, airportsBroadcasted.value()));

    }
}
