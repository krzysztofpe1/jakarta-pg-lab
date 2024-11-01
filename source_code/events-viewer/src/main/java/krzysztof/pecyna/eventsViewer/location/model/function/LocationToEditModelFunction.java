package krzysztof.pecyna.eventsViewer.location.model.function;


import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.location.model.LocationEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class LocationToEditModelFunction implements Function<Location, LocationEditModel>, Serializable {

    @Override
    public LocationEditModel apply(Location location) {
        return LocationEditModel.builder()
                .streetAddress(location.getStreetAddress())
                .maximumAudienceCapacity(location.getMaximumAudienceCapacity())
                .locationType(location.getLocationType())
                .build();
    }
}
