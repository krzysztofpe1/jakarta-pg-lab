package krzysztof.pecyna.eventsViewer.artist.dto.function;

import krzysztof.pecyna.eventsViewer.artist.dto.GetArtistResponse;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;

import java.util.function.Function;

public class ArtistToResponseFunction implements Function<Artist, GetArtistResponse> {

    @Override
    public GetArtistResponse apply(Artist artist) {
        return GetArtistResponse.builder()
                .id(artist.getId())
                .firstName(artist.getFirstName())
                .lastName(artist.getLastName())
                .nickName(artist.getNickName())
                .build();
    }
}
