package api.gios.gov.pl.domain.data;

import api.gios.gov.pl.util.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiosValuesDto {

    private LocalDateTime date;

    private Double value;

    @JsonCreator
    public GiosValuesDto(@JsonProperty("date") String date,  @JsonProperty("value") Double value) {
        this.date = DateTimeUtils.parse(date);
        this.value = value;
    }
}
