package pl.air_quality.app.gios.cache;

import pl.air_quality.app.domain.data.DataDto;
import pl.air_quality.app.domain.index.IndexDto;
import pl.air_quality.app.domain.sensor.SensorsDto;
import pl.air_quality.app.domain.station.CityDto;
import pl.air_quality.app.domain.station.StationDto;
import pl.air_quality.app.exception.CityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GiosCache {

    List<StationDto> getStations();

    Map<Integer, List<SensorsDto>> getSensors();

    Map<Integer, DataDto> getData();

    Map<Integer, IndexDto> getIndices();

    void loadGiosData();

    String getCityName(int cityId) throws CityNotFoundException;

    Set<CityDto> getAllCities();

    LocalDateTime getDateTime();
}
