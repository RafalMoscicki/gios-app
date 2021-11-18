package pl.air_quality.app.domain.sensor;

import lombok.Getter;

@Getter
public class ParamCodeDto {

    private final String code;
    private final String description;

    public ParamCodeDto(ParamCode paramCode) {
        this.code = paramCode.name();
        this.description = paramCode.getDescription().substring(0, 1).toUpperCase()
                + paramCode.getDescription().substring(1);
    }
}
