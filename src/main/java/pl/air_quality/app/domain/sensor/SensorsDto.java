package pl.air_quality.app.domain.sensor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SensorsDto {

    @JsonProperty("id")
    private int id;

    @JsonProperty("stationId")
    private int stationId;

    @JsonProperty("param")
    private ParamDto paramDto;
}
