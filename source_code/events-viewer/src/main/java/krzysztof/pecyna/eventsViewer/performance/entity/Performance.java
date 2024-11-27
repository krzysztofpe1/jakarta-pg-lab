package krzysztof.pecyna.eventsViewer.performance.entity;

import jakarta.persistence.*;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.location.entity.Location;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "performances")

public class Performance implements Serializable {

    @Id
    private UUID id;

    private LocalDate date;

    private PerformanceType performanceType;

    @ManyToOne
    @JoinColumn(name = "location")
    @ToString.Exclude // Exclude to avoid cyclic references in toString
    @EqualsAndHashCode.Exclude
    private Location location;

    @ManyToOne
    @JoinColumn(name = "artist")
    @ToString.Exclude // Exclude to avoid cyclic references in toString
    @EqualsAndHashCode.Exclude
    private Artist artist;
}

