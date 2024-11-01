package krzysztof.pecyna.eventsViewer.location.model;

import krzysztof.pecyna.eventsViewer.location.entity.LocationType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class LocationEditModel {
    private String streetAddress;

    private Integer maximumAudienceCapacity;

    private LocationType locationType;
}
