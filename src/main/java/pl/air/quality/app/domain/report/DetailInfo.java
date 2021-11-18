package pl.air.quality.app.domain.report;

import pl.air.quality.app.domain.index.IndexLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class DetailInfo {

    private final Double value;
    private final IndexLevel level;
    private final LocalDateTime temporal;
}
