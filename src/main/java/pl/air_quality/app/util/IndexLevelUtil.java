package pl.air_quality.app.util;

import pl.air_quality.app.domain.index.IndexLevel;

public class IndexLevelUtil {

    public static IndexLevel parse(String name) {
        return IndexLevel.getByDescription(name);
    }
}
