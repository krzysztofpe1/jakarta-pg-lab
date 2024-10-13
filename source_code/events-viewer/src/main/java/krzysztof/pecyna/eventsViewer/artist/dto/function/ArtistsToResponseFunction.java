package krzysztof.pecyna.eventsViewer.artist.dto.function;

import krzysztof.pecyna.eventsViewer.artist.dto.GetArtistResponse;
import krzysztof.pecyna.eventsViewer.artist.dto.GetArtistsResponse;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;

import java.util.List;
import java.util.function.Function;

public class ArtistsToResponseFunction implements Function<List<Artist>, GetArtistsResponse> {
    @Override
    public GetArtistsResponse apply(List<Artist> artists) {
        return GetArtistsResponse.builder()
                .artists(artists.stream()
                        .map(artist -> GetArtistsResponse.Artist.builder()
                                .id(artist.getId())
                                .firstName(artist.getFirstName())
                                .lastName(artist.getLastName())
                                .nickName(artist.getNickName())
                                .build()
                        )
                        .toList()
                )
                .build();
    }
}
