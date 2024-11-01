package krzysztof.pecyna.eventsViewer.location.model.function;

import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.location.model.LocationModel;

import java.io.Serializable;
import java.util.function.Function;

public class LocationToModelFunction implements Function<Location, LocationModel>, Serializable {

    @Override
    public LocationModel apply(Location location) {
        return LocationModel.builder()
                .id(location.getId())
                .streetAddress(location.getStreetAddress())
                .maximumAudienceCapacity(location.getMaximumAudienceCapacity())
                .locationType(location.getLocationType())
                .performances(location.getPerformances())
                .build();
    }
}
