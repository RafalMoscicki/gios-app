package api.gios.gov.pl.gios.cache;

import api.gios.gov.pl.domain.CityDto;
import api.gios.gov.pl.domain.data.GiosDataDto;
import api.gios.gov.pl.domain.index.GiosIndexDto;
import api.gios.gov.pl.domain.sensor.GiosSensorsDto;
import api.gios.gov.pl.domain.station.GiosCityDto;
import api.gios.gov.pl.domain.station.GiosStationDto;
import api.gios.gov.pl.exception.CityNotFoundException;
import api.gios.gov.pl.gios.client.GiosClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class GiosCacheImpl implements GiosCache {

    private final GiosClient client;

    private List<GiosStationDto> stations;

    private Map<Integer, List<GiosSensorsDto>> sensors;

    private Map<Integer, GiosDataDto> data;

    private Map<Integer, GiosIndexDto> indices;

    private Map<Integer, GiosCityDto> cities;

    @Autowired
    public GiosCacheImpl(GiosClient client) {
        this.client = client;
        loadGiosData();
    }

    @Override
    public List<GiosStationDto> getStations() {
        return stations;
    }

    @Override
    public Map<Integer, List<GiosSensorsDto>> getSensors() {
        return sensors;
    }

    @Override
    public Map<Integer, GiosDataDto> getData() {
        return data;
    }

    @Override
    public Map<Integer, GiosIndexDto> getIndices() {
        return indices;
    }

    private List<GiosStationDto> loadStations() {
        return client.getGiosStations();
    }

    private Map<Integer, List<GiosSensorsDto>> loadSensors(List<GiosStationDto> stations) {
        return stations.stream()
                .collect(Collectors.toMap(GiosStationDto::getId, station -> client.getGiosSensors(station.getId())));
    }

    private Map<Integer, GiosDataDto> loadData(Map<Integer, List<GiosSensorsDto>> sensors) {
        return sensors.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toMap(GiosSensorsDto::getId, sensor -> client.getGiosData(sensor.getId())));
    }

    private Map<Integer, GiosIndexDto> loadIndices(List<GiosStationDto> stations) {
        return stations.stream()
                .collect(Collectors.toMap(GiosStationDto::getId, station -> client.getGiosIndex(station.getId())));
    }

    private Map<Integer, GiosCityDto> loadCities(List<GiosStationDto> stations) {
        return stations.stream()
                .map(GiosStationDto::getCity)
                .distinct()
                .collect(Collectors.toMap(GiosCityDto::getId, city -> city));
    }

    @Override
    public Set<GiosCityDto> getAllCities() {
        return new HashSet<>(cities.values());
    }

    @Override
    public String getCityName(int cityId) {
        return Optional.ofNullable(cities.get(cityId))
                .map(GiosCityDto::getName)
                .orElseThrow(() -> new CityNotFoundException(cityId));
    }

    @Override
    public void loadGiosData() {
        log.info("started loading data to cache");
        List<GiosStationDto> loadedStations = loadStations();
        Map<Integer, List<GiosSensorsDto>> loadedSensors = loadSensors(loadedStations);
        Map<Integer, GiosDataDto> loadedData = loadData(loadedSensors);
        Map<Integer, GiosIndexDto> loadedIndices = loadIndices(loadedStations);
        Map<Integer, GiosCityDto> loadedCitiesNames = loadCities(loadedStations);
        synchronized (this) {
            stations = loadedStations;
            sensors = loadedSensors;
            data = loadedData;
            indices = loadedIndices;
            cities = loadedCitiesNames;
        }
        log.info("ended loading data to cache");
    }
}
