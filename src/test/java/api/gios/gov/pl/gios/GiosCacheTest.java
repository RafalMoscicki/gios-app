package api.gios.gov.pl.gios;

import api.gios.gov.pl.domain.data.GiosDataDto;
import api.gios.gov.pl.domain.index.GiosIndexDto;
import api.gios.gov.pl.domain.sensor.GiosSensorsDto;
import api.gios.gov.pl.domain.station.GiosStationDto;
import api.gios.gov.pl.gios.cache.GiosCacheImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@SpringBootTest
class GiosCacheTest {

    @Autowired
    private GiosCacheImpl giosCache;

    @Test
    public void checkCache() {
        Collection<GiosStationDto> stations = giosCache.getStations();
        Map<Integer, List<GiosSensorsDto>> sensors = giosCache.getSensors();
        Map<Integer, GiosDataDto> data = giosCache.getData();
        Map<Integer, GiosIndexDto> indices = giosCache.getIndices();
    }
}