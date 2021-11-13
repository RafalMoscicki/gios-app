package api.gios.gov.pl.domain.index;

import api.gios.gov.pl.util.IndexLevelUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StIndexLevel {

    private IndexLevel indexLevel;

    @JsonCreator
    public StIndexLevel(@JsonProperty("indexLevelName") String indexLevel) {
        this.indexLevel = IndexLevelUtil.parse(indexLevel);
    }
}
