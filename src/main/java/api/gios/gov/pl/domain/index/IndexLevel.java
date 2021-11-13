package api.gios.gov.pl.domain.index;

import java.util.Arrays;

public enum IndexLevel {

    VERY_GOOD("Bardzo dobry", 5),
    GOOD("Dobry", 4),
    MODERATE("Umiarkowany", 3),
    SUFFICIENT("Dostateczny", 2),
    BAD("Zły", 1),
    VERY_BAD("Bardzo zły", 0),
    UNDEFINED("Brak indeksu", -1);

    private final String description;
    private final int value;

    IndexLevel(String description, int value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    public static IndexLevel getByDescription(String description) {
        if (description == null) return UNDEFINED;
        return Arrays.stream(values())
                .filter(indexLevel -> indexLevel.description.equals(description))
                .findFirst()
                .orElse(UNDEFINED);
    }

    public static IndexLevel getByValue(int value) {
        return Arrays.stream(values())
                .filter(indexLevel -> indexLevel.value == value)
                .findFirst()
                .orElse(UNDEFINED);
    }
}
