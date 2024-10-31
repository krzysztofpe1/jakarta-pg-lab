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
public class PutPerformanceRequest {
    private Timespan duration;

    private PerformanceType performanceType;

    private UUID location;

    private UUID artist;
}
