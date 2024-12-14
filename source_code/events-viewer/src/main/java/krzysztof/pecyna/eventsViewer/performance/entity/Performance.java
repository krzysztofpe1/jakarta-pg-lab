package krzysztof.pecyna.eventsViewer.performance.entity;

import jakarta.persistence.*;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.entity.VersionAndCreationDateAuditable;
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
@ToString()
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "performances")
public class Performance extends VersionAndCreationDateAuditable implements Serializable {
    @Id
    private UUID id;

    private LocalDate date;

    private PerformanceType performanceType;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Location location;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Artist artist;

    @Override
    public void updateCreationDateTime() {
        super.updateCreationDateTime();
    }

    @Override
    public void updateEditDateTime() {
        super.updateEditDateTime();
    }
}
