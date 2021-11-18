package pl.air_quality.app.service;

import pl.air_quality.app.domain.CityDto;
import pl.air_quality.app.domain.GiosInfo;
import pl.air_quality.app.domain.index.IndexLevel;
import pl.air_quality.app.domain.sensor.ParamCodeDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface GiosService {

    GiosInfo getInfo(int stationId);

    Set<CityDto> getAllCities();

    List<Integer> getStationIds(int cityId);

    List<GiosInfo> getReport(int cityId);

    GiosInfo getAverageReport(int cityId);

    List<GiosInfo> getAverageReportsForAllCities();

    List<ParamCodeDto> getParamCodes();

    IndexLevel getIndexLevel(Double indexVal);

    LocalDateTime getDateTime();
}
