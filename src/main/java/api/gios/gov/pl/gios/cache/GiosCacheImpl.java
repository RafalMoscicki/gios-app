package api.gios.gov.pl.gios.cache;

import api.gios.gov.pl.domain.data.GiosDataDto;
import api.gios.gov.pl.domain.index.GiosIndexDto;
import api.gios.gov.pl.domain.sensor.GiosSensorsDto;
import api.gios.gov.pl.domain.station.GiosStationDto;
import api.gios.gov.pl.gios.cache.GiosCache;
import api.gios.gov.pl.gios.client.GiosClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@RequiredArgsConstructor
@Component
public class GiosCacheImpl implements GiosCache {

    @Autowired
    private final GiosClient client;

    private List<GiosStationDto> stations;

    private Map<Integer, List<GiosSensorsDto>> sensors;

    private Map<Integer, GiosDataDto> data;

    private Map<Integer, GiosIndexDto> indices;

    @Override
    public List<GiosStationDto> getStations() {
        if (stations == null) {
            loadStations();
        }
        return stations;
    }

    @Override
    public Map<Integer, List<GiosSensorsDto>> getSensors() {
        if (sensors == null) {
            sensors = new HashMap<>();
            loadSensors();
        }
        return sensors;
    }

    @Override
    public Map<Integer, GiosDataDto> getData() {
        if (data == null) {
            data = new HashMap<>();
            loadData();
        }
        return data;
    }

    @Override
    public Map<Integer, GiosIndexDto> getIndices() {
        if (indices == null) {
            indices = new HashMap<>();
            loadIndices();
        }
        return indices;
    }

    @Override
    public void loadStations() {
        stations = client.getGiosStations();
    }

    @Override
    public void loadSensors() {
        getStations().forEach(station -> sensors.put(station.getId(), client.getGiosSensors(station.getId())));
    }

    @Override
    public void loadData() {
        getSensors().values().stream()
                .flatMap(Collection::stream)
                .forEach(sensor -> data.put(sensor.getId(), client.getGiosData(sensor.getId())));
//        getSensors().values().forEach(sensorId -> data.put(sensorId, client.getGiosData(sensorId)));
    }

    @Override
    public void loadIndices() {
        getStations().forEach(station -> indices.put(station.getId(), client.getGiosIndex(station.getId())));
    }
}
