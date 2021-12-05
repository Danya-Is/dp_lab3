package ru.bmstu.lab3;

import java.io.Serializable;
import java.util.Arrays;

public class TableRow implements Serializable {
    private String[] data;

    private static final String FLIGHTS_DELIMITER = ",";
    private static final String AIRPORTS_DELIMITER = "\",\"";

    public TableRow(String[] data) {
        this.data = data;
    }

    public static TableRow parseAirportTable(String table) {
        String[] data = table.split(AIRPORTS_DELIMITER);
        data[0] = deleteQuotes(data[0]);
        data[data.length - 1] = deleteQuotes(data[data.length - 1]);
        return new TableRow(data);
    }

    private static String deleteQuotes(String str) {
        str = str.replaceAll("\"", "");
        return str;
    }

    public static TableRow parseFlightTable(String table) {
        String[] data = table.split(FLIGHTS_DELIMITER);
        return new TableRow(data);
    }

    public String get(int id) throws Exception {
        if (id > data.length)
            throw new Exception(Arrays.toString(data));
        else
            return data[id];

    }
}
