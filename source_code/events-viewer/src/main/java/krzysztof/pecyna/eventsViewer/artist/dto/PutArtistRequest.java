package krzysztof.pecyna.eventsViewer.artist.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutArtistRequest {
    private String firstName;

    private String lastName;

    private String password;

    private LocalDateTime accountCreation;
}
