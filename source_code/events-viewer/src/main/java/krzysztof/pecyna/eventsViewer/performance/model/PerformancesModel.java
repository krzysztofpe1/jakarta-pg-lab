package krzysztof.pecyna.eventsViewer.performance.model;

import krzysztof.pecyna.eventsViewer.performance.entity.PerformanceType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PerformancesModel {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Performance {

        private UUID id;

        private LocalDate date;

        private PerformanceType performanceType;

        private Long version;

        private LocalDateTime creationDateTime;

        private LocalDateTime modifiedDateTime;
    }

    @Singular
    private List<Performance> performances;

}