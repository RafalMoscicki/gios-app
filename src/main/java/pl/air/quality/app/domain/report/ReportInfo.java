package pl.air.quality.app.domain.report;

import pl.air.quality.app.domain.report.DetailInfo;
import pl.air.quality.app.domain.sensor.ParamCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ReportInfo {

    private final String stationName;
    private final Map<ParamCode, DetailInfo> values = new HashMap<>();

    public ReportInfo(String stationName) {
        this.stationName = stationName;
    }

    public void add(ParamCode paramCode, DetailInfo detailInfo) {
        values.put(paramCode, detailInfo);
    }

    public Map<ParamCode, DetailInfo> getValues() {
        return values;
    }
}
