package krzysztof.pecyna.eventsViewer.location.model;

import krzysztof.pecyna.eventsViewer.location.entity.LocationType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class LocationCreateModel {
    private UUID id;

    private String streetAddress;

    private Integer maximumAudienceCapacity;

    private LocationType locationType;
}
