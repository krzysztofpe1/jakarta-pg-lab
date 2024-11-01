package krzysztof.pecyna.eventsViewer.performance.dto;

import krzysztof.pecyna.eventsViewer.performance.entity.PerformanceType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchPerformanceRequest {
    private LocalDate date;

    private PerformanceType performanceType;

}
