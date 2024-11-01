package krzysztof.pecyna.eventsViewer.performance.model.function;

import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.performance.model.PerformanceEditModel;

import java.io.Serializable;
import java.util.function.Function;

public class PerformanceToEditModelFunction implements Function<Performance, PerformanceEditModel>, Serializable {

    @Override
    public PerformanceEditModel apply(Performance performance) {
        return PerformanceEditModel.builder()
                .date(performance.getDate())
                .performanceType(performance.getPerformanceType())
                .artist(performance.getArtist())
                .location(LocationModel.builder()
                        .id(performance.getLocation().getId())
                        .streetAddress(performance.getLocation().getStreetAddress())
                        .maximumAudienceCapacity(performance.getLocation().getMaximumAudienceCapacity())
                        .locationType(performance.getLocation().getLocationType())
                        .performances(performance.getLocation().getPerformances())
                        .build())
                .build();
    }
}
