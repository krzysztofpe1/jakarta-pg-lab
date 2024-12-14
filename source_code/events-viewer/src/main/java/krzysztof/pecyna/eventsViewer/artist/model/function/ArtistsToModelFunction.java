package krzysztof.pecyna.eventsViewer.artist.model.function;


import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.artist.model.ArtistsModel;

import java.util.List;
import java.util.function.Function;

public class ArtistsToModelFunction implements Function<List<Artist>, ArtistsModel> {
    @Override
    public ArtistsModel apply(List<Artist> entity) {
        return ArtistsModel.builder()
                .artists(entity.stream()
                        .map(Artist -> ArtistsModel.Artist.builder()
                                .id(Artist.getId())
                                .firstName(Artist.getFirstName())
                                .lastName(Artist.getLastName())
                                .build())
                        .toList())
                .build();
    }
}