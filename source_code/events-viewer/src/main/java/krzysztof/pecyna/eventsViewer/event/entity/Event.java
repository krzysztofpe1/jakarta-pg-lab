package krzysztof.pecyna.eventsViewer.event.entity;

import krzysztof.pecyna.eventsViewer.location.entity.Location;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Event implements Serializable {

    private UUID id;

    private String name;

    private int audienceCapacity;

    private Location location;

}
