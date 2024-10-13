package krzysztof.pecyna.eventsViewer.artist.dto;

import lombok.*;

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

    private String nickName;

}
