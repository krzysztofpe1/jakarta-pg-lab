package krzysztof.pecyna.eventsViewer.location.dto.function;


import krzysztof.pecyna.eventsViewer.location.dto.GetLocationsResponse;
import krzysztof.pecyna.eventsViewer.location.entity.Location;

import java.util.List;
import java.util.function.Function;

public class LocationsToResponseFunction implements Function<List<Location>, GetLocationsResponse> {
    @Override
    public GetLocationsResponse apply(List<Location> locations) {
        return GetLocationsResponse.builder()
                .locations(locations.stream()
                        .map(location -> GetLocationsResponse.Location.builder()
                                .id(location.getId())
                                .streetAddress(location.getStreetAddress())
                                .build())
                        .toList())
                .build();
    }
}
