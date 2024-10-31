package krzysztof.pecyna.eventsViewer.performance.dto;

import jdk.jfr.Timespan;
import krzysztof.pecyna.eventsViewer.performance.entity.PerformanceType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class GetPerformanceResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Artist {
        private UUID id;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Location {
        private UUID id;
    }

    private UUID id;

    private Timespan duration;

    private PerformanceType performanceType;

    private Location location;

    private Artist artist;
}
