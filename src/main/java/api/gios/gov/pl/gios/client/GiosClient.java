package api.gios.gov.pl.gios.client;

import api.gios.gov.pl.domain.data.GiosDataDto;
import api.gios.gov.pl.domain.index.GiosIndexDto;
import api.gios.gov.pl.domain.sensor.GiosSensorsDto;
import api.gios.gov.pl.domain.station.GiosStationDto;
import api.gios.gov.pl.gios.config.GiosConfig;
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

    public List<GiosStationDto> getGiosStations() {
        URI url = UriComponentsBuilder.fromUriString(giosConfig.getGiosStationsEndpoint()).build().toUri();

        try {
            GiosStationDto[] giosStationResponse = restTemplate.getForObject(url, GiosStationDto[].class);
            return new ArrayList<>(Optional.ofNullable(giosStationResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList()));
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<GiosSensorsDto> getGiosSensors(int id) {
        Map<String, Integer> urlParam = new HashMap<>();
        urlParam.put("sensorId", id);
        URI url = UriComponentsBuilder.fromUriString(giosConfig.getGiosSensorsEndpoint())
                .buildAndExpand(urlParam)
                .toUri();

        try {
            GiosSensorsDto[] giosSensorsResponse = restTemplate.getForObject(url, GiosSensorsDto[].class);
            return Optional.ofNullable(giosSensorsResponse)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public GiosDataDto getGiosData(int sensorId) {
        Map<String, Integer> urlParam = new HashMap<>();
        urlParam.put("sensorId", sensorId);
        URI url = UriComponentsBuilder.fromUriString(giosConfig.getGiosSensorDataEndpoint())
                .buildAndExpand(urlParam)
                .toUri();

        try {
            return restTemplate.getForObject(url, GiosDataDto.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    public GiosIndexDto getGiosIndex(int id) {
        Map<String, Integer> urlParam = new HashMap<>();
        urlParam.put("stationId", id);
        URI url = UriComponentsBuilder.fromUriString(giosConfig.getGiosIndexEndpoint())
                .buildAndExpand(urlParam)
                .toUri();

        try {
            return restTemplate.getForObject(url, GiosIndexDto.class);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }
}
