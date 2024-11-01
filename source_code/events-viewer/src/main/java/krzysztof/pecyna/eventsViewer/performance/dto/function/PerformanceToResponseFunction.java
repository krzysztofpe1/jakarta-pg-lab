package krzysztof.pecyna.eventsViewer.performance.dto.function;


import krzysztof.pecyna.eventsViewer.performance.dto.GetPerformanceResponse;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;

import java.util.function.Function;

public class PerformanceToResponseFunction implements Function<Performance, GetPerformanceResponse> {
    @Override
    public GetPerformanceResponse apply(Performance performance) {
        return GetPerformanceResponse.builder()
                .id(performance.getId())
                .date(performance.getDate())
                .performanceType(performance.getPerformanceType())
                .artist(GetPerformanceResponse.Artist.builder()
                        .id(performance.getArtist().getId())
                        .build())
                .location(GetPerformanceResponse.Location.builder()
                        .id(performance.getLocation().getId())
                        .build())
                .build();
    }
}
