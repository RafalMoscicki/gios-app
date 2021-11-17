package api.gios.gov.pl.service;

import api.gios.gov.pl.domain.CityDto;
import api.gios.gov.pl.domain.GiosDetailInfo;
import api.gios.gov.pl.domain.GiosInfo;
import api.gios.gov.pl.domain.data.GiosDataDto;
import api.gios.gov.pl.domain.data.GiosValuesDto;
import api.gios.gov.pl.domain.index.IndexLevel;
import api.gios.gov.pl.domain.index.StIndexLevel;
import api.gios.gov.pl.domain.sensor.GiosSensorsDto;
import api.gios.gov.pl.domain.sensor.ParamCode;
import api.gios.gov.pl.domain.sensor.ParamCodeDto;
import api.gios.gov.pl.domain.station.GiosStationDto;
import api.gios.gov.pl.exception.StationNotFoundException;
import api.gios.gov.pl.gios.cache.GiosCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

public interface GiosService {

    GiosInfo getInfo(int stationId);

    Set<CityDto> getAllCities();

    List<Integer> getStationIds(int cityId);

    List<GiosInfo> getReport(int cityId);

    GiosInfo getAverageReport(int cityId);

    List<GiosInfo> getAverageReportsForAllCities();

    List<ParamCodeDto> getParamCodes();

    IndexLevel getIndexLevel(Double indexVal);
}
