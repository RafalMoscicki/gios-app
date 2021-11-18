package pl.air_quality.app.gios.cache;

import pl.air_quality.app.domain.data.DataDto;
import pl.air_quality.app.domain.index.IndexDto;
import pl.air_quality.app.domain.sensor.SensorsDto;
import pl.air_quality.app.domain.station.CityDto;
import pl.air_quality.app.domain.station.StationDto;
import pl.air_quality.app.exception.CityNotFoundException;
import pl.air_quality.app.gios.client.GiosClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class GiosCacheImpl implements GiosCache {

    private final GiosClient client;

    private LocalDateTime cacheLoadDateTime;

    private List<StationDto> stations;

    private Map<Integer, List<SensorsDto>> sensors;

    private Map<Integer, DataDto> data;

    private Map<Integer, IndexDto> indices;

    private Map<Integer, CityDto> cities;

    @Autowired
    public GiosCacheImpl(GiosClient client) {
        this.client = client;
        loadGiosData();
    }

    @Override
    public List<StationDto> getStations() {
        return stations;
    }

    @Override
    public Map<Integer, List<SensorsDto>> getSensors() {
        return sensors;
    }

    @Override
    public Map<Integer, DataDto> getData() {
        return data;
    }

    @Override
    public Map<Integer, IndexDto> getIndices() {
        return indices;
    }

    private List<StationDto> loadStations() {
        return client.getGiosStations();
    }

    private Map<Integer, List<SensorsDto>> loadSensors(List<StationDto> stations) {
        return stations.stream()
                .collect(Collectors.toMap(StationDto::getId, station -> client.getGiosSensors(station.getId())));
    }

    private Map<Integer, DataDto> loadData(Map<Integer, List<SensorsDto>> sensors) {
        return sensors.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(SensorsDto::getId, sensor -> client.getGiosData(sensor.getId())));
    }

    private Map<Integer, IndexDto> loadIndices(List<StationDto> stations) {
        return stations.stream()
                .collect(Collectors.toMap(StationDto::getId, station -> client.getGiosIndex(station.getId())));
    }

    private Map<Integer, CityDto> loadCities(List<StationDto> stations) {
        return stations.stream()
                .map(StationDto::getCity)
                .distinct()
                .collect(Collectors.toMap(CityDto::getId, city -> city));
    }

    @Override
    public Set<CityDto> getAllCities() {
        return new HashSet<>(cities.values());
    }

    @Override
    public LocalDateTime getDateTime() {
        return cacheLoadDateTime;
    }

    @Override
    public String getCityName(int cityId) throws CityNotFoundException {
        return Optional.ofNullable(cities.get(cityId))
                .map(CityDto::getName)
                .orElseThrow(() -> new CityNotFoundException(cityId));
    }

    @Override
    public void loadGiosData() {
        log.info("started loading data to cache");
        List<StationDto> loadedStations = loadStations();
        Map<Integer, List<SensorsDto>> loadedSensors = loadSensors(loadedStations);
        Map<Integer, DataDto> loadedData = loadData(loadedSensors);
        Map<Integer, IndexDto> loadedIndices = loadIndices(loadedStations);
        Map<Integer, CityDto> loadedCitiesNames = loadCities(loadedStations);
        synchronized (this) {
            stations = loadedStations;
            sensors = loadedSensors;
            data = loadedData;
            indices = loadedIndices;
            cities = loadedCitiesNames;
            cacheLoadDateTime = LocalDateTime.now();
        }
        log.info("ended loading data to cache");
    }
}
