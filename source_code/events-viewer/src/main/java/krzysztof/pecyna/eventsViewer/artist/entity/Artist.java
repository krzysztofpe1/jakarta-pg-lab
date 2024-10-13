package krzysztof.pecyna.eventsViewer.artist.entity;

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

public class Artist implements Serializable {

    private UUID id;

    private String firstName;

    private String lastName;

    private String nickName;

    private List<Performance> performanceList;

}
