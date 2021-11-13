package api.gios.gov.pl.domain.index;

import api.gios.gov.pl.domain.sensor.ParamCode;
import api.gios.gov.pl.util.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiosIndexDto {

    private int id;
//
//    private LocalDateTime stCalcDate;

    private StIndexLevel stIndexLevel;

    private Map<ParamCode, StIndexLevel> indices;

    @JsonCreator
    public GiosIndexDto(
            @JsonProperty("id") int id,
            @JsonProperty("stCalcDate") String stCalcDate,
            @JsonProperty("stIndexLevel") StIndexLevel stIndexLevel,
            @JsonProperty("so2IndexLevel") StIndexLevel so2IndexLevel,
            @JsonProperty("no2IndexLevel") StIndexLevel no2IndexLevel,
            @JsonProperty("pm10IndexLevel") StIndexLevel pm10IndexLevel,
            @JsonProperty("o3IndexLevel") StIndexLevel o3IndexLevel,
            @JsonProperty("pm25IndexLevel") StIndexLevel pm25IndexLevel,
            @JsonProperty("pm2.5IndexLevel") StIndexLevel pm2_5IndexLevel,
            @JsonProperty("c6h6IndexLevel") StIndexLevel c6h6IndexLevel) {
        this.id = id;
//        this.stCalcDate = DateTimeUtils.parse(stCalcDate);
        indices = new HashMap<>();
        indices.put(ParamCode.getByFieldName("so2IndexLevel"), so2IndexLevel);
        indices.put(ParamCode.getByFieldName("no2IndexLevel"), no2IndexLevel);
        indices.put(ParamCode.getByFieldName("pm10IndexLevel"), pm10IndexLevel);
        indices.put(ParamCode.getByFieldName("o3IndexLevel"), o3IndexLevel);
        indices.put(ParamCode.getByFieldName("pm25IndexLevel"), pm25IndexLevel);
        indices.put(ParamCode.getByFieldName("pm2_5IndexLevel"), pm2_5IndexLevel);
        indices.put(ParamCode.getByFieldName("c6h6IndexLevel"), c6h6IndexLevel);
        this.stIndexLevel = stIndexLevel;
    }
}
