package pl.air.quality.app.domain.station;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StationDto {

    @JsonProperty("id")
    private int id;

    @JsonProperty("stationName")
    private String stationName;

    @JsonProperty("city")
    private CityDto city;
}
