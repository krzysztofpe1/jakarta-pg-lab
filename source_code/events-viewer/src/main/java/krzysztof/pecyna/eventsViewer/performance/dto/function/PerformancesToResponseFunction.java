package krzysztof.pecyna.eventsViewer.performance.dto.function;


import krzysztof.pecyna.eventsViewer.performance.dto.GetPerformancesResponse;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;

import java.util.List;
import java.util.function.Function;

public class PerformancesToResponseFunction implements Function<List<Performance>, GetPerformancesResponse> {
    @Override
    public GetPerformancesResponse apply(List<Performance> performances) {
        return GetPerformancesResponse.builder()
                .performances(performances.stream()
                        .map(performance -> GetPerformancesResponse.Performance.builder()
                                .id(performance.getId())
                                .date(performance.getDate())
                                .performanceType(performance.getPerformanceType())
                                .build())
                        .toList())
                .build();
    }
}
