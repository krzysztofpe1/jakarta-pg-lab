package krzysztof.pecyna.eventsViewer.performance.model;

import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.location.entity.Location;
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
public class PerformanceCreateModel {
    private UUID id;

    private LocalDate date;

    private PerformanceType performanceType;

    private Location location;

    private Artist artist;
}
