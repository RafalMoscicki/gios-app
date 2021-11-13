package api.gios.gov.pl.domain.index;

import java.util.Arrays;

public enum IndexLevel {

    VERY_GOOD("Bardzo dobry"),
    GOOD("Dobry"),
    MODERATE("Umiarkowany"),
    SUFFICIENT("Dostateczny"),
    BAD("Zły"),
    VERY_BAD("Bardzo zły");

    private final String description;

    IndexLevel(String description) {
        this.description = description;
    }

    public static IndexLevel getByDescription(String description) {
        return Arrays.stream(values())
                .filter(indexLevel -> indexLevel.description.equals(description))
                .findFirst()
                .orElse(null);
    }
}
