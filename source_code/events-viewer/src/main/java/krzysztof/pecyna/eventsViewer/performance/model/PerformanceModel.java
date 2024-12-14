package krzysztof.pecyna.eventsViewer.performance.model;

import krzysztof.pecyna.eventsViewer.performance.entity.PerformanceType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PerformanceModel {
    private UUID id;

    private LocalDate date;

    private PerformanceType performanceType;

    private Long version;

    private LocalDateTime creationDateTime;
}
