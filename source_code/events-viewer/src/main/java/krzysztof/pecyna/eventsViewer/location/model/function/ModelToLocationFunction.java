package krzysztof.pecyna.eventsViewer.location.model.function;


import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.location.model.LocationCreateModel;

import java.io.Serializable;
import java.util.Collections;
import java.util.function.Function;

public class ModelToLocationFunction implements Function<LocationCreateModel, Location>, Serializable {
    @Override
    public Location apply(LocationCreateModel model) {
        return Location.builder()
                .id(model.getId())
                .streetAddress(model.getStreetAddress())
                .maximumAudienceCapacity(model.getMaximumAudienceCapacity())
                .locationType(model.getLocationType())
                .performances(Collections.emptyList())
                .build();
    }
}
