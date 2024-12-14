package krzysztof.pecyna.eventsViewer.performance.dto.function;


import krzysztof.pecyna.eventsViewer.performance.dto.PatchPerformanceRequest;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;

import java.util.function.BiFunction;

public class UpdatePerformanceWithRequestFunction implements BiFunction<Performance, PatchPerformanceRequest, Performance> {
    @Override
    public Performance apply(Performance performance, PatchPerformanceRequest request) {
        return Performance.builder()
                .id(performance.getId())
                .date(request.getDate())
                .performanceType(request.getPerformanceType())
                .artist(performance.getArtist())
                .location(performance.getLocation())
                .version(request.getVersion())
                .creationDateTime(performance.getCreationDateTime())
                .build();
    }
}
