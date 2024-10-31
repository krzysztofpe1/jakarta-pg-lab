package krzysztof.pecyna.eventsViewer.location.dto.function;

import krzysztof.pecyna.eventsViewer.location.dto.PutLocationRequest;
import krzysztof.pecyna.eventsViewer.location.entity.Location;

import java.util.Collections;
import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToLocationFunction implements BiFunction<UUID, PutLocationRequest, Location> {
    @Override
    public Location apply(UUID uuid, PutLocationRequest request) {
        return Location.builder()
                .id(uuid)
                .streetAddress(request.getStreetAddress())
                .locationType(request.getLocationType())
                .maximumAudienceCapacity(request.getMaximumAudienceCapacity())
                .performances(Collections.emptyList())
                .build();
    }
}
