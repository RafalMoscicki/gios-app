package api.gios.gov.pl.gios.cache;

import api.gios.gov.pl.domain.data.GiosDataDto;
import api.gios.gov.pl.domain.data.GiosValuesDto;
import api.gios.gov.pl.domain.index.GiosIndexDto;
import api.gios.gov.pl.domain.index.StIndexLevel;
import api.gios.gov.pl.domain.sensor.GiosParamDto;
import api.gios.gov.pl.domain.sensor.GiosSensorsDto;
import api.gios.gov.pl.domain.sensor.ParamCode;
import api.gios.gov.pl.domain.station.GiosCityDto;
import api.gios.gov.pl.domain.station.GiosCommuneDto;
import api.gios.gov.pl.domain.station.GiosStationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
@Primary
public class MockGiosCache implements GiosCache {

    @Override
    public List<GiosStationDto> getStations() {
        return Arrays.asList(new GiosStationDto(52, "Stacja 1", new GiosCityDto(4, "City 4", new GiosCommuneDto())));
    }

    @Override
    public Map<Integer, List<GiosSensorsDto>> getSensors() {
        List<GiosSensorsDto> sensors = Arrays.asList(
                new GiosSensorsDto(281, 52, new GiosParamDto("benzen", ParamCode.C6H6.name())),
                new GiosSensorsDto(282, 52, new GiosParamDto("pył zawieszony PM10", ParamCode.PM10.name()))
        );
        Map<Integer, List<GiosSensorsDto>> map = new HashMap<>();
        map.put(52, sensors);
        return map;
    }

    @Override
    public Map<Integer, GiosDataDto> getData() {
        List<GiosValuesDto> values1 = Arrays.asList(
                new GiosValuesDto("2021-11-10 22:00:00", null),
                new GiosValuesDto("2021-11-10 22:00:00", 100.0));
        GiosDataDto data1 = new GiosDataDto(ParamCode.C6H6.name(), values1);
        List<GiosValuesDto> values2 = Arrays.asList(
                new GiosValuesDto("2021-11-10 22:00:00", 15.5),
                new GiosValuesDto("2021-11-10 22:00:00", 20.0));
        GiosDataDto data2 = new GiosDataDto(ParamCode.PM10.name(), values2);
        Map<Integer, GiosDataDto> map = new HashMap<>();
        map.put(281, data1);
        map.put(282, data2);
        return map;
    }

    @Override
    public Map<Integer, GiosIndexDto> getIndices() {
        GiosIndexDto index = new GiosIndexDto(
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
        Map<Integer, GiosIndexDto> map = new HashMap<>();
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
    public Set<GiosCityDto> getAllCities() {
        Set<GiosCityDto> cities = new HashSet<>();
        cities.add(new GiosCityDto(4, "City 4", new GiosCommuneDto()));
        return cities;
    }
}
