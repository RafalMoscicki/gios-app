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
import api.gios.gov.pl.exception.CityNotFoundException;
import api.gios.gov.pl.exception.StationNotFoundException;
import api.gios.gov.pl.gios.cache.GiosCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GiosService {

    @Autowired
    private GiosCache giosCache;

    public GiosService(GiosCache giosCache) {
        this.giosCache = giosCache;
    }

    public GiosInfo getInfo(int stationId) {
        GiosInfo response = new GiosInfo(getStationName(stationId));
        Arrays.stream(ParamCode.values())
                .forEach(paramCode -> response.add(paramCode, getDetailInfo(stationId, paramCode)));
        return response;
    }

    private GiosDetailInfo getDetailInfo(int stationId, ParamCode paramCode) {
        return new GiosDetailInfo(getSensorValue(stationId, paramCode), getIndexInfo(stationId, paramCode).orElse(null));
    }

    private Optional<IndexLevel> getIndexInfo(int stationId, ParamCode paramCode) {
        return Optional.ofNullable(giosCache.getIndices().get(stationId).getIndices().get(paramCode))
                .map(StIndexLevel::getIndexLevel);
    }

    private Double getSensorValue(int stationId, ParamCode paramCode) {
        return giosCache.getData().entrySet().stream()
                .filter(x -> x.getKey().equals(findSensorId(stationId, paramCode)))
                .map(Map.Entry::getValue)
                .map(GiosDataDto::getValues)
                .flatMap(Collection::stream)
                .map(GiosValuesDto::getValue)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private String getStationName(int stationId) {
        return giosCache.getStations()
                .stream()
                .filter(x -> x.getId() == stationId)
                .map(GiosStationDto::getStationName)
                .findFirst()
                .orElseThrow(StationNotFoundException::new);
    }

    private Integer findSensorId(int stationId, ParamCode paramCode) {
        return giosCache.getSensors().get(stationId)
                .stream()
                .filter(x -> x.getGiosParamDto().getParamCode() == paramCode)
                .map(GiosSensorsDto::getId)
                .findFirst()
                .orElse(null);
    }

    public Set<CityDto> getAllCities() {
        return giosCache.getStations().stream()
                .map(GiosStationDto::getCity)
                .map(giosCity -> new CityDto(giosCity.getId(), giosCity.getName()))
                .collect(Collectors.toSet());
    }

    public List<Integer> getStationIds(int cityId) {
        return giosCache.getStations().stream()
                .filter(station -> station.getCity().getId() == cityId)
                .map(GiosStationDto::getId)
                .collect(Collectors.toList());
    }

    public Collection<GiosInfo> getReport(int cityId) {
        return getStationIds(cityId).stream()
                .map(this::getInfo)
                .collect(Collectors.toList());
    }

    public GiosInfo getAverageReport(int cityId) {
        Map<ParamCode, Double> averageValues = getStationIds(cityId).stream()
                .map(this::getInfo)
                .map(GiosInfo::getValues)
                .flatMap(x -> x.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey))
                .entrySet()
                .stream()
                .collect(
                    Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream()
                        .map(e -> e.getValue().getValue())
                        .filter(Objects::nonNull)
                        .mapToDouble(x -> x).average()
                        .orElse(0)
                    )
                );
        Map<ParamCode, Double> averageIndexLevels = getStationIds(cityId).stream()
                .map(this::getInfo)
                .map(GiosInfo::getValues)
                .flatMap(x -> x.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey))
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream()
                                .map(Map.Entry::getValue)
                                .filter(Objects::nonNull)
                                .map(GiosDetailInfo::getLevel)
                                .filter(Objects::nonNull)
                                .map(IndexLevel::getValue)
                                .filter(x -> x != -1)
                                .mapToDouble(x -> x).average()
                                .orElse(-1)
                        )
                );
        GiosInfo giosInfo = new GiosInfo(getCityName(cityId));
        averageValues.forEach((key, value) -> giosInfo.add(key, new GiosDetailInfo(value, IndexLevel.getByValue((int) Math.round(averageIndexLevels.get(key))))));
        return giosInfo;

    }

    public List<GiosInfo> getAverageReportsForAllCities() {
        return getAllCities().stream()
                .map(city -> getAverageReport(city.getId()))
                .collect(Collectors.toList());
    }

    private String getCityName(int cityId) {
        return getAllCities().stream()
                .filter(city -> city.getId() == cityId)
                .map(CityDto::getCityName)
                .findFirst()
                .orElseThrow(() -> new CityNotFoundException(cityId));
    }

    public List<ParamCodeDto> getParamCodes() {
        return Arrays.stream(ParamCode.values()).map(ParamCodeDto::new).collect(Collectors.toList());
    }
}
