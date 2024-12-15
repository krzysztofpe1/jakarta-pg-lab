package krzysztof.pecyna.eventsViewer.artist.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class GetArtistResponse {
    private UUID id;

    private String firstName;

    private String lastName;
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Performance {
        private UUID id;

        private LocalDateTime date;
    }

    @Singular
    private List<Performance> performances;
}
