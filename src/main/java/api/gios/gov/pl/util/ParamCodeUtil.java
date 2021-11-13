package api.gios.gov.pl.util;

import api.gios.gov.pl.domain.sensor.ParamCode;

public class ParamCodeUtil {

    public static ParamCode parse(String name) {
        return ParamCode.getByDescription(name);
    }
}
