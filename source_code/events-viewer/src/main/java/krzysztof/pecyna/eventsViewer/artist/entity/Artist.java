package krzysztof.pecyna.eventsViewer.artist.entity;

import jakarta.persistence.*;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(exclude = "performances")
@Entity
@Table(name = "artists")
public class Artist implements Serializable {
    @Id
    private UUID id;

    private String firstName;

    private String lastName;

    @ToString.Exclude
    private String password;

    @CollectionTable(name = "users__roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;

    @ToString.Exclude
    @OneToMany(mappedBy = "performance", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Performance> performances;

    private LocalDate accountCreation;
}
