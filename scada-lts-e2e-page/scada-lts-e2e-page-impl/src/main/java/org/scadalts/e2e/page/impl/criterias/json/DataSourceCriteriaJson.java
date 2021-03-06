package org.scadalts.e2e.page.impl.criterias.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;

import java.util.Objects;

@Data
@Builder
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataSourceCriteriaJson {

    private IdentifierJson<DataSourceType> identifier;
    private boolean enabled;

    public DataSourceCriteria toDataSourceSecondCriteria() {
        return DataSourceCriteria.criteriaSecond(new DataSourceIdentifier(identifier.getValue(),identifier.getType()),
                enabled);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataSourceCriteriaJson)) return false;
        DataSourceCriteriaJson that = (DataSourceCriteriaJson) o;
        return Objects.equals(getIdentifier(), that.getIdentifier());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getIdentifier());
    }
}
