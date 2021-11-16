package api.gios.gov.pl.domain.sensor;

import lombok.Getter;

@Getter
public class ParamCodeDto {

    private String code;
    private String description;

    public ParamCodeDto(ParamCode paramCode) {
        this.code = paramCode.name();
        this.description = paramCode.getDescription().substring(0, 1).toUpperCase()
                + paramCode.getDescription().substring(1);
    }
}
