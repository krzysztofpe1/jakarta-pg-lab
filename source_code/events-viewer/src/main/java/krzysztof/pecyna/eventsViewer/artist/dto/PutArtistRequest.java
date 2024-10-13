package krzysztof.pecyna.eventsViewer.artist.dto;

import lombok.*;

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

    private String nickName;
}
