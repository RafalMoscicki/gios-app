package pl.air_quality.app.util;

import pl.air_quality.app.domain.sensor.ParamCode;

public class ParamCodeUtil {

    public static ParamCode parse(String name) {
        return ParamCode.getByDescription(name);
    }
}
