package krzysztof.pecyna.eventsViewer.performance.dto;

import jdk.jfr.Timespan;
import krzysztof.pecyna.eventsViewer.performance.entity.PerformanceType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchPerformanceRequest {
    private Timespan duration;

    private PerformanceType performanceType;

}
