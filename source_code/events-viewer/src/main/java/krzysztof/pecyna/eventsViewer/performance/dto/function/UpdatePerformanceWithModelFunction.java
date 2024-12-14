package krzysztof.pecyna.eventsViewer.performance.dto.function;

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
                .artist(performance.getArtist())
                .performanceType(performance.getPerformanceType())
                .date(performance.getDate())
                .location(Location.builder()
                        .id(request.getLocation().getId())
                        .streetAddress(request.getLocation().getStreetAddress())
                        .locationType(request.getLocation().getLocationType())
                        .maximumAudienceCapacity(request.getLocation().getMaximumAudienceCapacity())
                        .performances(request.getLocation().getPerformances())
                        .build())
                .build();
    }
}
