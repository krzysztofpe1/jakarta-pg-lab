package krzysztof.pecyna.eventsViewer.performance.dto;

import krzysztof.pecyna.eventsViewer.performance.entity.PerformanceType;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutPerformanceRequest {
    private LocalDate date;

    private PerformanceType performanceType;

    private UUID location;

    private UUID artist;
}
