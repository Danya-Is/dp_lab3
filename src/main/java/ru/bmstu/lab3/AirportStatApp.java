package main.java.ru.bmstu.lab3;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import ru.bmstu.lab3.FlightInfo;
import scala.Tuple2;

public class AirportStatApp {

    public static final int DEPATURE_AIRPORT_POS = 11;
    public static final int DESTINATION_AIRPORT_POS = 14;
    public static final int AIRPORT_DELAY_POS = 18;
    public static final int IS_CANCELED_POS = 19;

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("AirportStatApp");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> flights = sc.textFile("FLIGHTS.scv").filter(row -> !row.contains("ARR_DELAY"));
        flights
                .map(flightRow -> flightRow.split(","))
                .mapToPair(flightRow -> {
                    float delayTime = flightRow[AIRPORT_DELAY_POS] == null ? 0 : Float.parseFloat(flightRow[AIRPORT_DELAY_POS]);
                    int isDelayed = delayTime > 0 ? 1 : 0;
                    int isCancelled = Float.parseFloat(flightRow[IS_CANCELED_POS]) == 0 ? 0: 1;
                    FlightInfo info = new FlightInfo(delayTime, 1, isDelayed, isCancelled);
                    new Tuple2<>(new Tuple2(flightRow[DEPATURE_AIRPORT_POS], flightRow[DESTINATION_AIRPORT_POS]), flightRow[AIRPORT_DELAY_POS])
                });

    }
}
