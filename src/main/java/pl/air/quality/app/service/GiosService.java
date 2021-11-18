package pl.air.quality.app.service;

import pl.air.quality.app.domain.CityDto;
import pl.air.quality.app.domain.report.ReportInfo;
import pl.air.quality.app.domain.index.IndexLevel;
import pl.air.quality.app.domain.sensor.ParamCodeDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface GiosService {

    ReportInfo getInfo(int stationId);

    Set<CityDto> getAllCities();

    List<Integer> getStationIds(int cityId);

    List<ReportInfo> getReport(int cityId);

    ReportInfo getAverageReport(int cityId);

    List<ReportInfo> getAverageReportsForAllCities();

    List<ParamCodeDto> getParamCodes();

    IndexLevel getIndexLevel(Double indexVal);

    LocalDateTime getDateTime();
}
