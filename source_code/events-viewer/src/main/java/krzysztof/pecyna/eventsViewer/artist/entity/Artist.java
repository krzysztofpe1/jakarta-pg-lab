package krzysztof.pecyna.eventsViewer.artist.entity;

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
@Table(name = "artists")

public class Artist implements Serializable {

    @Id
    private UUID id;

    private String firstName;

    private String lastName;

    @OneToMany
    private List<Performance> performances;
}
