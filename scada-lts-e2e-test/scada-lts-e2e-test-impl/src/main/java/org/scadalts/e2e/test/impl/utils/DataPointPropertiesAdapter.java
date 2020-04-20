package org.scadalts.e2e.test.impl.utils;

import lombok.EqualsAndHashCode;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventDetectorIdentifier;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointChartRenderProperties;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointProperties;
import org.scadalts.e2e.page.impl.dicts.EngineeringUnit;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;
import org.scadalts.e2e.service.impl.services.datapoint.DataPointPropertiesJson;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
public class DataPointPropertiesAdapter extends DataPointProperties {

    public DataPointPropertiesAdapter(DataPointPropertiesJson dataPointPropertiesJson) {
        super(EngineeringUnit.getType("", dataPointPropertiesJson.getEngineeringUnits()), dataPointPropertiesJson.getChartColour(),
                new DataPointLoggingPropertiesAdapter(dataPointPropertiesJson),
                dataPointPropertiesJson.getChartRenderer() == null ? DataPointChartRenderProperties.empty() : new DataPointChartRenderPropertiesAdapter(dataPointPropertiesJson.getChartRenderer()),
                new DataPointTextRendererPropertiesAdapter(dataPointPropertiesJson.getTextRenderer()),
                _eventDetectors(dataPointPropertiesJson.getEventDetectors()));
    }

    private static List<EventDetectorCriteria> _eventDetectors(List<String> eventDetectors) {
        return eventDetectors.stream().map(a -> EventDetectorCriteria.builder()
                    .identifier(new EventDetectorIdentifier(a, EventDetectorType.CHANGE))
                    .build())
                .collect(Collectors.toList());
    }

}
