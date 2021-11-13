package api.gios.gov.pl.domain.sensor;

import java.util.Arrays;

public enum ParamCode {

    C6H6("benzen", "c6h6IndexLevel"),
    CO("tlenek węgla", "coIndexLevel"),
    NO2("dwutlenek azotu", "no2IndexLevel"),
    O3("ozon", "o3IndexLevel"),
    SO2("dwutlenek siarki", "so2IndexLevel"),
    PM10("pył zawieszony PM10", "pm10IndexLevel"),
    PM2_5("pył zawieszony PM2.5", "pm2.5IndexLevel"),
    PM25("pył zawieszony PM25", "pm25IndexLevel");

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
}
