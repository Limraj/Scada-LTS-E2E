package org.scadalts.e2e.service.impl.dicts;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import org.scadalts.e2e.common.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
public enum DataPointRestType implements DictionaryObject {

    BINARY_VALUE("BinaryValue", "binaryValue"),
    NUMERIC_VALUE("NumericValue", "numericValue");

    private final String name;
    private final String id;

    DataPointRestType(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @JsonCreator
    public static DataPointRestType fromString(String string) {
        return Stream.of(DataPointRestType.values())
                .filter(a -> a.name.equals(string))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(string));
    }
}
