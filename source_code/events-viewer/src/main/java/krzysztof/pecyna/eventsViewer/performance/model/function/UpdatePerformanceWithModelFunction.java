package krzysztof.pecyna.eventsViewer.performance.model.function;

import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.performance.model.PerformanceEditModel;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.function.BiFunction;

public class UpdatePerformanceWithModelFunction implements BiFunction<Performance, PerformanceEditModel, Performance>, Serializable {

    @Override
    @SneakyThrows
    public Performance apply(Performance performance, PerformanceEditModel request) {
        return Performance.builder()
                .id(performance.getId())
                .date(request.getDate())
                .performanceType(request.getPerformanceType())
                .artist(performance.getArtist())
                .location(Location.builder()
                        .id(request.getLocation().getId())
                        .streetAddress(request.getLocation().getStreetAddress())
                        .maximumAudienceCapacity(request.getLocation().getMaximumAudienceCapacity())
                        .locationType(request.getLocation().getLocationType())
                        .performances(request.getLocation().getPerformances())
                        .build())
                .build();
    }
}
