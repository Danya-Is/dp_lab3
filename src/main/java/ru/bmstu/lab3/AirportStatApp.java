package main.java.ru.bmstu.lab3;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class AirportStatApp {

    public static final int DEPATURE_AIRPORT_POS = 11;
    public static final int DESTINATION_AIRPORT_POS = 14;
    public static final int AIRPORT_DELAY_POS = 18;
    public static final int IS_CANCELED_POS = 19;

    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("AirportStatApp");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> flights = sc.textFile("FLIGHTS.scv").filter(row -> !row.contains("ARR_DELAY"));

    }
}
