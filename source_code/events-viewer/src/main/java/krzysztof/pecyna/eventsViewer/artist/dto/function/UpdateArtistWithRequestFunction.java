package krzysztof.pecyna.eventsViewer.artist.dto.function;

import krzysztof.pecyna.eventsViewer.artist.dto.PatchArtistRequest;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;

import java.util.function.BiFunction;

public class UpdateArtistWithRequestFunction implements BiFunction<Artist, PatchArtistRequest, Artist> {
    @Override
    public Artist apply(Artist artist, PatchArtistRequest request) {
        return Artist.builder()
                .id(artist.getId())
                .firstName((request.getFirstName() != null)? request.getFirstName() : artist.getFirstName())
                .lastName((request.getLastName() != null)? request.getLastName() : artist.getLastName())
                .nickName((request.getNickName() != null)? request.getNickName() : artist.getNickName())
                .build();
    }
}
