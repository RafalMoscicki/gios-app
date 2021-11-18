package pl.air.quality.app.util;

import pl.air.quality.app.domain.sensor.ParamCode;

public class ParamCodeUtil {

    public static ParamCode parse(String name) {
        return ParamCode.getByDescription(name);
    }
}
