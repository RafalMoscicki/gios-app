package pl.air_quality.app.gios.client;

import pl.air_quality.app.domain.sensor.SensorsDto;
import pl.air_quality.app.domain.station.StationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

@SpringBootTest
class GiosClientTest {

    @Autowired
    private GiosClient giosClient;

    @Test
    public void shouldFindAllStations() throws URISyntaxException {
        //Given
        URI uri = new URI("https://api.gios.gov.pl/pjp-api/rest/station/findAll");

        //When & //Then
        List<StationDto> stations = giosClient.getGiosStations();
    }

    @Test
    public void shouldFindSensorsByStationId() throws URISyntaxException {
        //Given
        URI uri = new URI("https://api.gios.gov.pl/pjp-api/rest/station/sensors/14");

        //When & //Then
        Collection<SensorsDto> giosSensors = giosClient.getGiosSensors(14);
    }

    @Test
    public void shouldFindDataFromSensor() throws URISyntaxException {
        //Given
        URI uri = new URI("ttp://api.gios.gov.pl/pjp-api/rest/data/getData/92");

        //When & //Then
        giosClient.getGiosData(92);
    }

    @Test
    public void shouldFindIndexValueFromStation() throws URISyntaxException {
        //Given
        URI uri = new URI("http://api.gios.gov.pl/pjp-api/rest/aqindex/getIndex/14");

        //When & //Then
        giosClient.getGiosIndex(14);
    }
}