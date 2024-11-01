package krzysztof.pecyna.eventsViewer.location.model;

import krzysztof.pecyna.eventsViewer.location.entity.LocationType;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class LocationModel {
    private UUID id;

    private String streetAddress;

    private Integer maximumAudienceCapacity;

    private LocationType locationType;

    @Singular
    private List<Performance> performances;
}
