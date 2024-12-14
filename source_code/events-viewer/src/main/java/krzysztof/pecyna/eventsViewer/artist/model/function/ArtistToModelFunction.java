package krzysztof.pecyna.eventsViewer.artist.model.function;

import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.artist.model.ArtistModel;

import java.io.Serializable;
import java.util.function.Function;

public class ArtistToModelFunction implements Function<Artist, ArtistModel>, Serializable {
    @Override
    public ArtistModel apply(Artist artist) {
        return ArtistModel.builder()
                .id(artist.getId())
                .firstName(artist.getFirstName())
                .lastName(artist.getLastName())
                .build();
    }
}