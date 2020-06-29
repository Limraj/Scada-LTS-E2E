package org.scadalts.e2e.common.utils;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Builder
@EqualsAndHashCode
public class VariationUnit<T> {

    private final @Singular("sequence") List<T> variation;
    private final T startValue;

    private VariationUnit(List<T> variation, T startValue) {
        this.variation = new ArrayList<>(variation);
        this.startValue = startValue;
    }

    public List<T> getVariationWithStart() {
        List<T> result = new ArrayList<>();
        result.add(startValue);
        result.addAll(variation);
        return result;
    }
}
