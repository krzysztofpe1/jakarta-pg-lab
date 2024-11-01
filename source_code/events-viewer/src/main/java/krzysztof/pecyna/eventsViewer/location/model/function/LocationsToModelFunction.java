package krzysztof.pecyna.eventsViewer.location.model.function;

import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.location.model.LocationsModel;

import java.util.List;
import java.util.function.Function;

public class LocationsToModelFunction implements Function<List<Location>, LocationsModel> {
    @Override
    public LocationsModel apply(List<Location> locations) {
        return LocationsModel.builder()
                .locations(locations.stream()
                        .map(location -> LocationsModel.Location.builder()
                                .id(location.getId())
                                .streetAddress(location.getStreetAddress())
                                .build())
                        .toList())
                .build();
    }
}
