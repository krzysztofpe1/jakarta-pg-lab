package krzysztof.pecyna.eventsViewer.artist.dto.function;

import krzysztof.pecyna.eventsViewer.artist.dto.PutArtistRequest;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToArtistFunction implements BiFunction<UUID, PutArtistRequest, Artist> {
    @Override
    public Artist apply(UUID id, PutArtistRequest request) {
        return Artist.builder()
                .id(id)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .nickName(request.getNickName())
                .build();
    }
}
