package pl.filmveeb.model;

import java.util.Arrays;

public enum RatingValue {
    MISTAKE("Nieporozumienie", 1),
    VERY_BAD("Bardzo zły", 2),
    WEAK("Słaby", 3),
    ACCEPTABLE("Ujdzie",4),
    AVERAGE("Średni", 5),
    NOT_BAD("Niezły", 6),
    GOOD("Dobry", 7),
    VERY_GOOD("Bardzo dobry", 8),
    AMAZING("Rewelacyjny",9),
    MASTERPIECE("Arcydzieło!", 10);

    private final String plName;
    private final int value;

    RatingValue(String plName, int value) {
        this.plName = plName;
        this.value = value;
    }

    public String getPlName() {
        return plName;
    }

    public int getValue() {
        return value;
    }

    public static RatingValue fromValue(int value) {
        return Arrays.stream(RatingValue.values())
                .filter(ratingValue -> ratingValue.getValue() == value)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nie znaleziona oceny!"));
    }
}
