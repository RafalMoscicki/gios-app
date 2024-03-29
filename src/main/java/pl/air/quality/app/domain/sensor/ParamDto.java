package pl.air.quality.app.domain.sensor;

import pl.air.quality.app.util.ParamCodeUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParamDto {

    private String paramName;

    private ParamCode paramCode;

    @JsonCreator
    public ParamDto(@JsonProperty("paramName") String paramName, @JsonProperty("paramCode") String paramCode) {
        this.paramName = paramName;
        this.paramCode = ParamCodeUtil.parse(paramName);
    }
}
