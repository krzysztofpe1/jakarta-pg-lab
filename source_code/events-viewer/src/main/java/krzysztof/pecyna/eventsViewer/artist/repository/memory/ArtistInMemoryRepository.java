package krzysztof.pecyna.eventsViewer.artist.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.artist.repository.api.ArtistRepository;
import krzysztof.pecyna.eventsViewer.dataStore.DataStore;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class ArtistInMemoryRepository implements ArtistRepository {

    private final DataStore store;

    @Inject
    public ArtistInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Artist> find(UUID id) {
        return store.findAllArtists().stream()
                .filter(artist -> artist.getId().equals(id))
                .findFirst();
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
    public void delete(Artist entity) {
        store.deleteArtist(entity.getId());
    }

    @Override
    public void update(Artist entity) {
        store.updateArtist(entity);
    }

    @Override
    public Optional<Artist> findByName(String name) {
        return store.findAllArtists().stream()
                .filter(artist -> artist.getLastName().equals(name))
                .findFirst();
    }
}