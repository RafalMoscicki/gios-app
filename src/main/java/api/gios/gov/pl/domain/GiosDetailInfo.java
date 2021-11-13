package api.gios.gov.pl.domain;

import api.gios.gov.pl.domain.index.IndexLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GiosDetailInfo {

    private final Double value;
    private final IndexLevel level;
}
