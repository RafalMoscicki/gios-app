package api.gios.gov.pl.domain.sensor;

import api.gios.gov.pl.util.ParamCodeUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GiosParamDto {

    private String paramName;

    private ParamCode paramCode;

    @JsonCreator
    public GiosParamDto(@JsonProperty("paramName") String paramName, @JsonProperty("paramCode") String paramCode) {
        this.paramName = paramName;
        this.paramCode = ParamCodeUtil.parse(paramName);
    }
}
