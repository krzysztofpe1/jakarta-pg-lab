package krzysztof.pecyna.eventsViewer.artist.repository.memory;

import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.artist.repository.api.ArtistRepository;
import krzysztof.pecyna.eventsViewer.dataStone.component.DataStore;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ArtistInMemoryRepository implements ArtistRepository {

    private final DataStore store;

    public ArtistInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public List<Artist> findByFirstName(String firstName) {
        return  store.findAllArtists().stream().filter(artist -> artist.getFirstName().equals(firstName)).toList();
    }

    @Override
    public List<Artist> findByLastName(String lastName) {
        return store.findAllArtists().stream().filter(artist -> artist.getLastName().equals(lastName)).toList();
    }

    @Override
    public List<Artist> findByNickName(String nickName) {
        return store.findAllArtists().stream().filter(artist -> artist.getNickName().equals(nickName)).toList();
    }

    @Override
    public Optional<Artist> find(UUID id) {
        return store.findAllArtists().stream()
                .filter(artist -> artist.getId().equals(id)).findFirst();
    }

    @Override
    public List<Artist> findAll() {
        return store.findAllArtists();
    }

    @Override
    public void create(Artist entity) {
        store.createArtist(entity);
    }

    @Override
    public void update(Artist entity) {
        store.updateArtist(entity);
    }

    @Override
    public void delete(Artist entity) {
        store.deleteArtist(entity.getId());
    }
}
