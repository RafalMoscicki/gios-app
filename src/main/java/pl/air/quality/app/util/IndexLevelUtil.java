package pl.air.quality.app.util;

import pl.air.quality.app.domain.index.IndexLevel;

public class IndexLevelUtil {

    public static IndexLevel parse(String name) {
        return IndexLevel.getByDescription(name);
    }
}
