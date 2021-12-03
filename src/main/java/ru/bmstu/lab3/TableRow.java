package ru.bmstu.lab3;

import java.io.Serializable;

public class TableRow implements Serializable {
    private String[] data;

    private static final String FLIGHTS_DELIMITER = ",";
    private static final String AIRPORTS_DELIMITER = "\",\"";

    public void parseAirportTable(String table) {
        data = table.split(AIRPORTS_DELIMITER);
        data[0].replaceAll("\"", "");
        data[data.length - 1].replaceAll("\"", "");
    }
}
