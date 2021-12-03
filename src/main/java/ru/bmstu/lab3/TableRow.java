package ru.bmstu.lab3;

import java.io.Serializable;

public class TableRow implements Serializable {
    private String[] data;

    private static final String FLIGHTS_DELIMITER = ",";
    private static final String AIRPORTS_DELIMITER = "\",\"";

    public TableRow(String[] data) {
        this.data = data;
    }

    public static TableRow parseAirportTable(String table) {
        String[] data = table.split(AIRPORTS_DELIMITER);
        data[0].replaceAll("\"", "");
        data[data.length - 1].replaceAll("\"", "");
        return new TableRow(data);
    }

    public static TableRow parseFlightTable(String table) {
        String[] data = table.split(FLIGHTS_DELIMITER);
        return new TableRow(data);
    }

    public String get(int id) {
        return data[id];
    }
}
