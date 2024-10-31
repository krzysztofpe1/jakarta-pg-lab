package krzysztof.pecyna.eventsViewer.location.dto;

import jdk.jfr.Timespan;
import krzysztof.pecyna.eventsViewer.location.entity.LocationType;
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

public class GetLocationResponse {
    private UUID id;

    private String streetAddress;

    private Integer maximumAudienceCapacity;

    private LocationType locationType;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Performance {
        private UUID id;

        private Timespan duration;
    }

    @Singular
    private List<Performance> performances;

}
