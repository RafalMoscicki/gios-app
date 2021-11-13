package api.gios.gov.pl.gios;

import api.gios.gov.pl.domain.data.GiosDataDto;
import api.gios.gov.pl.domain.data.GiosValuesDto;
import api.gios.gov.pl.domain.index.GiosIndexDto;
import api.gios.gov.pl.domain.index.StIndexLevel;
import api.gios.gov.pl.domain.sensor.GiosParamDto;
import api.gios.gov.pl.domain.sensor.GiosSensorsDto;
import api.gios.gov.pl.domain.sensor.ParamCode;
import api.gios.gov.pl.domain.station.GiosCityDto;
import api.gios.gov.pl.domain.station.GiosStationDto;
import api.gios.gov.pl.gios.cache.GiosCache;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockGiosCache implements GiosCache {

    @Override
    public List<GiosStationDto> getStations() {
        return Arrays.asList(new GiosStationDto(52, "Stacja 1", new GiosCityDto()));
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
    public void loadStations() {

    }

    @Override
    public void loadSensors() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void loadIndices() {

    }
}
