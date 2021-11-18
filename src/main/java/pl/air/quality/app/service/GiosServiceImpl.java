package pl.air.quality.app.service;

import lombok.extern.slf4j.Slf4j;
import pl.air.quality.app.domain.sensor.ParamCodeDto;
import pl.air.quality.app.exception.CityNotFoundException;
import pl.air.quality.app.domain.CityDto;
import pl.air.quality.app.domain.report.DetailInfo;
import pl.air.quality.app.domain.report.ReportInfo;
import pl.air.quality.app.domain.data.DataDto;
import pl.air.quality.app.domain.data.ValuesDto;
import pl.air.quality.app.domain.index.IndexLevel;
import pl.air.quality.app.domain.index.StIndexLevel;
import pl.air.quality.app.domain.sensor.SensorsDto;
import pl.air.quality.app.domain.sensor.ParamCode;
import pl.air.quality.app.domain.station.StationDto;
import pl.air.quality.app.exception.StationNotFoundException;
import pl.air.quality.app.gios.cache.GiosCache;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GiosServiceImpl implements GiosService {

    @Autowired
    private final GiosCache giosCache;

    public ReportInfo getInfo(int stationId) {
        String stationName = null;
        try {
            stationName = getStationName(stationId);
        } catch (StationNotFoundException e) {
            log.error("Getting info", e);
        }
        ReportInfo response = new ReportInfo(stationName);
        Arrays.stream(ParamCode.values())
                .forEach(paramCode -> response.add(paramCode, getDetailInfo(stationId, paramCode)));
        return response;
    }

    private DetailInfo getDetailInfo(int stationId, ParamCode paramCode) {
        return new DetailInfo(getSensorValue(stationId, paramCode), getIndexInfo(stationId, paramCode).orElse(null), getSensorTemporal(stationId, paramCode));
    }

    private Optional<IndexLevel> getIndexInfo(int stationId, ParamCode paramCode) {
        return Optional.ofNullable(giosCache.getIndices().get(stationId).getIndices().get(paramCode))
                .map(StIndexLevel::getIndexLevel);
    }

    private Double getSensorValue(int stationId, ParamCode paramCode) {
        return giosCache.getData().entrySet().stream()
                .filter(x -> x.getKey().equals(findSensorId(stationId, paramCode)))
                .map(Map.Entry::getValue)
                .map(DataDto::getValues)
                .flatMap(Collection::stream)
                .map(ValuesDto::getValue)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(Double.NaN);
    }

    private LocalDateTime getSensorTemporal(int stationId, ParamCode paramCode) {
        return giosCache.getData().entrySet().stream()
                .filter(x -> x.getKey().equals(findSensorId(stationId, paramCode)))
                .map(Map.Entry::getValue)
                .map(DataDto::getValues)
                .flatMap(Collection::stream)
                .map(ValuesDto::getDate)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private String getStationName(int stationId) throws StationNotFoundException {
        return giosCache.getStations()
                .stream()
                .filter(x -> x.getId() == stationId)
                .map(StationDto::getStationName)
                .findFirst()
                .orElseThrow(() -> new StationNotFoundException(stationId));
    }

    private Integer findSensorId(int stationId, ParamCode paramCode) {
        return giosCache.getSensors().get(stationId)
                .stream()
                .filter(x -> x.getParamDto().getParamCode() == paramCode)
                .map(SensorsDto::getId)
                .findFirst()
                .orElse(null);
    }

    public Set<CityDto> getAllCities() {
        return giosCache.getAllCities().stream()
                .map(city -> new CityDto(city.getId(), city.getName()))
                .collect(Collectors.toSet());
    }

    public List<Integer> getStationIds(int cityId) {
        return giosCache.getStations().stream()
                .filter(station -> station.getCity().getId() == cityId)
                .map(StationDto::getId)
                .collect(Collectors.toList());
    }

    public List<ReportInfo> getReport(int cityId) {
        return getStationIds(cityId).stream()
                .map(this::getInfo)
                .collect(Collectors.toList());
    }

    public ReportInfo getAverageReport(int cityId) {
        Map<ParamCode, Double> averageValues = getStationIds(cityId).stream()
                .map(this::getInfo)
                .map(ReportInfo::getValues)
                .flatMap(x -> x.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey))
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream()
                                .map(e -> e.getValue().getValue())
                                .filter(Objects::nonNull)
                                .filter(value -> !value.isNaN())
                                .mapToDouble(x -> x).average()
                                .orElse(Double.NaN)
                        )
                );
        Map<ParamCode, Double> averageIndexLevels = getStationIds(cityId).stream()
                .map(this::getInfo)
                .map(ReportInfo::getValues)
                .flatMap(x -> x.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey))
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream()
                                .map(Map.Entry::getValue)
                                .filter(Objects::nonNull)
                                .map(DetailInfo::getLevel)
                                .filter(Objects::nonNull)
                                .map(IndexLevel::getValue)
                                .filter(x -> x != -1)
                                .mapToDouble(x -> x).average()
                                .orElse(Double.NaN)
                        )
                );
        String cityName = null;
        try {
            cityName = giosCache.getCityName(cityId);
        } catch (CityNotFoundException e) {
            log.error("Creating average report", e);
        }
        ReportInfo reportInfo = new ReportInfo(cityName);
        averageValues.forEach((key, value) -> reportInfo.add(key, new DetailInfo(value, getIndexLevel(averageIndexLevels.get(key)), giosCache.getDateTime())));
        return reportInfo;

    }

    public List<ReportInfo> getAverageReportsForAllCities() {
        return getAllCities().stream()
                .map(city -> getAverageReport(city.getId()))
                .collect(Collectors.toList());
    }

    public List<ParamCodeDto> getParamCodes() {
        return Arrays.stream(ParamCode.values()).map(ParamCodeDto::new).collect(Collectors.toList());
    }

    @Override
    public IndexLevel getIndexLevel(Double indexVal) {
        if (indexVal.equals(Double.NaN)) {
            return IndexLevel.UNDEFINED;
        }
        return IndexLevel.getByValue((int) Math.round(indexVal));
    }

    @Override
    public LocalDateTime getDateTime() {
        return giosCache.getDateTime();
    }
}
