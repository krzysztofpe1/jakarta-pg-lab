package krzysztof.pecyna.eventsViewer.location.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "locations")

public class Location implements Serializable {

    @Id
    private UUID id;

    private String streetAddress;

    private Integer maximumAudienceCapacity;

    private LocationType locationType;

    @OneToMany
    private List<Performance> performances;
}
