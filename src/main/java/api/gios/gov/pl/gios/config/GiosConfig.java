package api.gios.gov.pl.gios.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class GiosConfig {

    @Value("${gios.station.api.endpoint}")
    private String giosStationsEndpoint;

    @Value("${gios.sensors.api.endpoint}")
    private String giosSensorsEndpoint;

    @Value("${gios.data.sensorId.api.endpoint}")
    private String giosSensorDataEndpoint;

    @Value("${gios.index.api.endpoint}")
    private String giosIndexEndpoint;
}
