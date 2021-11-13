package api.gios.gov.pl.domain;

import api.gios.gov.pl.domain.sensor.ParamCode;

import java.util.HashMap;
import java.util.Map;

public class GiosInfo {

    private final String stationName;
    private final Map<ParamCode, GiosDetailInfo> values = new HashMap<>();

    public GiosInfo(String stationName) {
        this.stationName = stationName;
    }

    public void add(ParamCode paramCode, GiosDetailInfo detailInfo) {
        values.put(paramCode, detailInfo);
    }

    public Map<ParamCode, GiosDetailInfo> getValues() {
        return values;
    }

    public String getStationName() {
        return stationName;
    }
}
