package pl.air.quality.app.gios.client;

import pl.air.quality.app.domain.data.DataDto;
import pl.air.quality.app.domain.index.IndexDto;
import pl.air.quality.app.domain.sensor.SensorsDto;
import pl.air.quality.app.domain.station.StationDto;
import pl.air.quality.app.gios.config.GiosConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component
@RequiredArgsConstructor
public class GiosClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(GiosClient.class);

    private final RestTemplate restTemplate;
    private final GiosConfig giosConfig;

    public List<StationDto> getGiosStations() {
        URI url = UriComponentsBuilder.fromUriString(giosConfig.getGiosStationsEndpoint()).build().toUri();

        try {
            StationDto[] giosStationResponse = restTemplate.getForObject(url, StationDto[].class);
            return new ArrayList<>(Optional.ofNullable(giosStationResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList()));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<SensorsDto> getGiosSensors(int id) {
        Map<String, Integer> urlParam = new HashMap<>();
        urlParam.put("sensorId", id);
        URI url = UriComponentsBuilder.fromUriString(giosConfig.getGiosSensorsEndpoint())
                .buildAndExpand(urlParam)
                .toUri();

        try {
            SensorsDto[] giosSensorsResponse = restTemplate.getForObject(url, SensorsDto[].class);
            return Optional.ofNullable(giosSensorsResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public DataDto getGiosData(int sensorId) {
        Map<String, Integer> urlParam = new HashMap<>();
        urlParam.put("sensorId", sensorId);
        URI url = UriComponentsBuilder.fromUriString(giosConfig.getGiosSensorDataEndpoint())
                .buildAndExpand(urlParam)
                .toUri();

        try {
            return restTemplate.getForObject(url, DataDto.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    public IndexDto getGiosIndex(int id) {
        Map<String, Integer> urlParam = new HashMap<>();
        urlParam.put("stationId", id);
        URI url = UriComponentsBuilder.fromUriString(giosConfig.getGiosIndexEndpoint())
                .buildAndExpand(urlParam)
                .toUri();

        try {
            return restTemplate.getForObject(url, IndexDto.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
}
