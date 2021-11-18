package pl.air_quality.app.domain;

import pl.air_quality.app.domain.index.IndexLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class GiosDetailInfo {

    private final Double value;
    private final IndexLevel level;
    private final LocalDateTime temporal;
}
