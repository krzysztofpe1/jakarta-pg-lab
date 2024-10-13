package krzysztof.pecyna.eventsViewer.artist.repository.api;

import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.repository.api.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArtistRepository extends Repository<Artist, UUID> {

    List<Artist> findByFirstName(String firstName);

    List<Artist> findByLastName(String lastName);

    List<Artist> findByNickName(String nickName);

}
