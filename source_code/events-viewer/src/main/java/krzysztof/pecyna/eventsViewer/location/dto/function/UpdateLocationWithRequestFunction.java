package krzysztof.pecyna.eventsViewer.location.dto.function;

import krzysztof.pecyna.eventsViewer.location.dto.PatchLocationRequest;
import krzysztof.pecyna.eventsViewer.location.entity.Location;

import java.util.function.BiFunction;

public class UpdateLocationWithRequestFunction implements BiFunction<Location, PatchLocationRequest, Location> {
    @Override
    public Location apply(Location location, PatchLocationRequest request) {
        return Location.builder()
                .id(location.getId())
                .streetAddress(request.getStreetAddress())
                .maximumAudienceCapacity(request.getMaximumAudienceCapacity())
                .locationType(request.getLocationType())
                .performances(location.getPerformances())
                .build();
    }
}
