package krzysztof.pecyna.eventsViewer.location.entity;

import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Location implements Serializable {
    private UUID id;

    private String streetAddress;

    private Integer maximumAudienceCapacity;

    private List<Performance> performances;
}
