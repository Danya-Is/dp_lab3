package main.java.ru.bmstu.lab3;


import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class AirportStatApp {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("AirportStatApp");
        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.textFile("FLIGHTS.scv");
    }
}
