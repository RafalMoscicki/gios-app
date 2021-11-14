package api.gios.gov.pl.gios.cache;

import api.gios.gov.pl.domain.data.GiosDataDto;
import api.gios.gov.pl.domain.index.GiosIndexDto;
import api.gios.gov.pl.domain.sensor.GiosSensorsDto;
import api.gios.gov.pl.domain.station.GiosStationDto;
import api.gios.gov.pl.gios.client.GiosClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GiosCacheImpl implements GiosCache {

    private final GiosClient client;

    private List<GiosStationDto> stations;

    private Map<Integer, List<GiosSensorsDto>> sensors;

    private Map<Integer, GiosDataDto> data;

    private Map<Integer, GiosIndexDto> indices;

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

    @Override
    public void loadGiosData() {
        log.info("started loading data to cache");
        List<GiosStationDto> loadedStations = loadStations();
        Map<Integer, List<GiosSensorsDto>> loadedSensors = loadSensors(loadedStations);
        Map<Integer, GiosDataDto> loadedData = loadData(loadedSensors);
        Map<Integer, GiosIndexDto> loadedIndices = loadIndices(loadedStations);
        synchronized (this) {
            stations = loadedStations;
            sensors = loadedSensors;
            data = loadedData;
            indices = loadedIndices;
        }
        log.info("ended loading data to cache");
    }
}
