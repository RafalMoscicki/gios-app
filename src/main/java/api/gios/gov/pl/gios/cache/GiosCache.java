package api.gios.gov.pl.gios.cache;

import api.gios.gov.pl.domain.data.GiosDataDto;
import api.gios.gov.pl.domain.index.GiosIndexDto;
import api.gios.gov.pl.domain.sensor.GiosSensorsDto;
import api.gios.gov.pl.domain.station.GiosStationDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GiosCache {

    List<GiosStationDto> getStations();

    Map<Integer, List<GiosSensorsDto>> getSensors();

    Map<Integer, GiosDataDto> getData();

    Map<Integer, GiosIndexDto> getIndices();

    void loadStations();

    void loadSensors();

    void loadData();

    void loadIndices();
}
