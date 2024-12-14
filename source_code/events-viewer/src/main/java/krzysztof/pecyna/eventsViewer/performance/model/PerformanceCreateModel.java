package krzysztof.pecyna.eventsViewer.performance.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.location.model.LocationModel;
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
    @NotNull
    private LocalDate date;
    @NotNull
    private PerformanceType performanceType;
    @NotNull
    private LocationModel location;
    @NotNull
    private Artist artist;
}
