package pl.air.quality.app.gios.cache;

import pl.air.quality.app.domain.data.DataDto;
import pl.air.quality.app.domain.data.ValuesDto;
import pl.air.quality.app.domain.index.IndexDto;
import pl.air.quality.app.domain.index.StIndexLevel;
import pl.air.quality.app.domain.sensor.ParamCode;
import pl.air.quality.app.domain.sensor.ParamDto;
import pl.air.quality.app.domain.sensor.SensorsDto;
import pl.air.quality.app.domain.station.CityDto;
import pl.air.quality.app.domain.station.StationDto;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class MockGiosCache implements GiosCache {

    @Override
    public List<StationDto> getStations() {
        return Arrays.asList(new StationDto(52, "Stacja 1", new CityDto(4, "City 4")));
    }

    @Override
    public Map<Integer, List<SensorsDto>> getSensors() {
        List<SensorsDto> sensors = Arrays.asList(
                new SensorsDto(281, 52, new ParamDto("benzen", ParamCode.C6H6.name())),
                new SensorsDto(282, 52, new ParamDto("pył zawieszony PM10", ParamCode.PM10.name()))
        );
        Map<Integer, List<SensorsDto>> map = new HashMap<>();
        map.put(52, sensors);
        return map;
    }

    @Override
    public Map<Integer, DataDto> getData() {
        List<ValuesDto> values1 = Arrays.asList(
                new ValuesDto("2021-11-10 22:00:00", null),
                new ValuesDto("2021-11-10 22:00:00", 100.0));
        DataDto data1 = new DataDto(ParamCode.C6H6.name(), values1);
        List<ValuesDto> values2 = Arrays.asList(
                new ValuesDto("2021-11-10 22:00:00", 15.5),
                new ValuesDto("2021-11-10 22:00:00", 20.0));
        DataDto data2 = new DataDto(ParamCode.PM10.name(), values2);
        Map<Integer, DataDto> map = new HashMap<>();
        map.put(281, data1);
        map.put(282, data2);
        return map;
    }

    @Override
    public Map<Integer, IndexDto> getIndices() {
        IndexDto index = new IndexDto(
                52,
                "2021-11-10 22:00:00",
                new StIndexLevel("Dobry"),
                new StIndexLevel("Bardzo dobry"),
                new StIndexLevel("Zły"),
                new StIndexLevel("Bardzo zły"),
                new StIndexLevel("Dostateczny"),
                new StIndexLevel("Umiarkowany"),
                new StIndexLevel(null),
                new StIndexLevel("Dostateczny")
        );
        Map<Integer, IndexDto> map = new HashMap<>();
        map.put(52, index);
        return map;
    }

    @Override
    public void loadGiosData() {
        // do nothing
    }

    @Override
    public String getCityName(int cityId) {
        return "City";
    }

    @Override
    public Set<CityDto> getAllCities() {
        Set<CityDto> cities = new HashSet<>();
        cities.add(new CityDto(4, "City 4"));
        return cities;
    }

    @Override
    public LocalDateTime getDateTime() {
        return LocalDateTime.now();
    }
}
