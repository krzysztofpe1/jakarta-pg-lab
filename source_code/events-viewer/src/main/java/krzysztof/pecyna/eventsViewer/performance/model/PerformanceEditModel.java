package krzysztof.pecyna.eventsViewer.performance.model;

import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.location.model.LocationModel;
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
public class PerformanceEditModel {
    private LocalDate date;

    private PerformanceType performanceType;

    private LocationModel location;

    private Artist artist;

    private Long version;
}
