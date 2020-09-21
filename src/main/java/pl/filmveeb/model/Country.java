package pl.filmveeb.model;

import java.util.Arrays;

public enum Country {
    POLAND("Polska", "PL"),
    GERMANY("Niemcy", "DE"),
    GREAT_BRITAIN("Wielka Brytania", "GB"),
    FRANCE("Francja", "FR"),
    UKRAINE("Ukraina", "UK"),
    ITALY("Włochy", "IT");

    private final String plName;
    private final String symbol;


    Country(String plName, String symbol) {
        this.plName = plName;
        this.symbol = symbol;
    }

    public String getPlName() {
        return plName;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Country fromSymbol(String symbol) {
        return Arrays.stream(Country.values())
                .filter(country -> country.getSymbol().equals(symbol))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nie znaleziona kraju!"));

    }
}
