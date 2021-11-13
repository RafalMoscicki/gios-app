package api.gios.gov.pl.util;

import api.gios.gov.pl.domain.index.IndexLevel;

public class IndexLevelUtil {

    public static IndexLevel parse(String name) {
        return IndexLevel.getByDescription(name);
    }
}
