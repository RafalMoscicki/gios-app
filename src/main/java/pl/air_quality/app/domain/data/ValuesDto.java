package pl.air_quality.app.domain.data;

import pl.air_quality.app.util.DateTimeUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValuesDto {

    private LocalDateTime date;

    private Double value;

    @JsonCreator
    public ValuesDto(@JsonProperty("date") String date, @JsonProperty("value") Double value) {
        this.date = DateTimeUtils.parse(date);
        this.value = value;
    }
}
