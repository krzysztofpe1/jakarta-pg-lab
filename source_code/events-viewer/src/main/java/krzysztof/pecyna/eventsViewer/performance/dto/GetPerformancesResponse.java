package krzysztof.pecyna.eventsViewer.performance.dto;

import jdk.jfr.Timespan;
import krzysztof.pecyna.eventsViewer.performance.entity.PerformanceType;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class GetPerformancesResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Performance {
        private UUID id;

        private Timespan duration;

        private PerformanceType performanceType;
    }

    @Singular
    private List<Performance> performances;
}
