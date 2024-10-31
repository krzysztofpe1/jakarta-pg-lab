package krzysztof.pecyna.eventsViewer.location.dto;

import krzysztof.pecyna.eventsViewer.location.entity.LocationType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class PatchLocationRequest {
    private String streetAddress;

    private Integer maximumAudienceCapacity;

    private LocationType locationType;

}
