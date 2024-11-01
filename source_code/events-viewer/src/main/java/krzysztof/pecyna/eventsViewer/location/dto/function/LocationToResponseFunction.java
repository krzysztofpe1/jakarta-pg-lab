package krzysztof.pecyna.eventsViewer.location.dto.function;


import krzysztof.pecyna.eventsViewer.location.dto.GetLocationResponse;
import krzysztof.pecyna.eventsViewer.location.entity.Location;

import java.util.function.Function;

public class LocationToResponseFunction implements Function<Location, GetLocationResponse> {
    @Override
    public GetLocationResponse apply(Location location) {
        return GetLocationResponse.builder()
                .id(location.getId())
                .streetAddress(location.getStreetAddress())
                .locationType(location.getLocationType())
                .maximumAudienceCapacity(location.getMaximumAudienceCapacity())
                .performances(location.getPerformances().stream()
                        .map(performance -> GetLocationResponse.Performance.builder()
                                .id(performance.getId())
                                .date(performance.getDate())
                                .build())
                        .toList())
                .build();
    }
}
