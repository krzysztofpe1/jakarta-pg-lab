package krzysztof.pecyna.eventsViewer.performance.entity;

import jdk.jfr.Timespan;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.location.entity.Location;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Performance implements Serializable {
    private UUID id;

    private Timespan duration;

    private PerformanceType performanceType;

    private Location location;

    private Artist artist;
}
