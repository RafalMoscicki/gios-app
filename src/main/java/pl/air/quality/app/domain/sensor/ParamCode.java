package pl.air.quality.app.domain.sensor;

import java.util.Arrays;

public enum ParamCode {
    PM10("pył zawieszony PM10", "pm10IndexLevel"),
    PM2_5("pył zawieszony PM2.5", "pm25IndexLevel"),
    NO2("dwutlenek azotu", "no2IndexLevel"),
    SO2("dwutlenek siarki", "so2IndexLevel"),
    CO("tlenek węgla", "coIndexLevel"),
    C6H6("benzen", "c6h6IndexLevel"),
    O3("ozon", "o3IndexLevel");

    private final String description;

    private final String fieldName;

    ParamCode(String description, String fieldName) {
        this.description = description;
        this.fieldName = fieldName;
    }

    public static ParamCode getByDescription(String description) {
        return Arrays.stream(values())
                .filter(paramLevel -> paramLevel.description.equals(description))
                .findFirst()
                .orElse(null);
    }

    public static ParamCode getByFieldName(String fieldName) {
        return Arrays.stream(values())
                .filter(paramLevel -> paramLevel.fieldName.equals(fieldName))
                .findFirst()
                .orElse(null);
    }

    public String getDescription() {
        return description;
    }
}
