package api.gios.gov.pl.domain.station;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiosCommuneDto {

    @JsonProperty("communeName")
    private String communeName;

    @JsonProperty("districtName")
    private String districtName;

    @JsonProperty("provinceName")
    private String provinceName;
}