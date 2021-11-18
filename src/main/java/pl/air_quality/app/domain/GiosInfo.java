package pl.air_quality.app.domain;

import pl.air_quality.app.domain.sensor.ParamCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
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
}
