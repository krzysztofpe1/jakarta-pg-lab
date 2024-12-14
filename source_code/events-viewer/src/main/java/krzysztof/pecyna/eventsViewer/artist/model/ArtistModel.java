package krzysztof.pecyna.eventsViewer.artist.model;

import lombok.*;

import java.util.UUID;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ArtistModel {
    private UUID id;

    private String firstName;

    private String lastName;
}