package krzysztof.pecyna.eventsViewer.performance.model.function;

import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.performance.model.PerformanceCreateModel;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToPerformanceFunction implements Function<PerformanceCreateModel, Performance>, Serializable {
    @Override
    public Performance apply(PerformanceCreateModel model) {
        return Performance.builder()
                .id(model.getId())
                .performanceType(model.getPerformanceType())
                .date(model.getDate())
                .artist(model.getArtist())
                .location(Location.builder()
                        .id(model.getLocation().getId())
                        .streetAddress(model.getLocation().getStreetAddress())
                        .maximumAudienceCapacity(model.getLocation().getMaximumAudienceCapacity())
                        .locationType(model.getLocation().getLocationType())
                        .performances(model.getLocation().getPerformances())
                        .build())
                .build();
    }
}
