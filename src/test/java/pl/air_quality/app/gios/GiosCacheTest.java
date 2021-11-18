package pl.air_quality.app.gios;

import pl.air_quality.app.domain.data.DataDto;
import pl.air_quality.app.domain.index.IndexDto;
import pl.air_quality.app.domain.sensor.SensorsDto;
import pl.air_quality.app.domain.station.StationDto;
import pl.air_quality.app.gios.cache.GiosCacheImpl;
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
        Collection<StationDto> stations = giosCache.getStations();
        Map<Integer, List<SensorsDto>> sensors = giosCache.getSensors();
        Map<Integer, DataDto> data = giosCache.getData();
        Map<Integer, IndexDto> indices = giosCache.getIndices();
    }
}