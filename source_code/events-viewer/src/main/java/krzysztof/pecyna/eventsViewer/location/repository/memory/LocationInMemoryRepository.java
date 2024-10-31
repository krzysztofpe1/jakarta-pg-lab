package krzysztof.pecyna.eventsViewer.location.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import krzysztof.pecyna.eventsViewer.dataStore.DataStore;
import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.location.repository.api.LocationRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class LocationInMemoryRepository implements LocationRepository {

    private final DataStore store;

    @Inject
    public LocationInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Location> find(UUID id) {
        return store.findAllLocations().stream().filter(location -> location.getId().equals(id)).findFirst();
    }

    @Override
    public List<Location> findAll() {
        return store.findAllLocations();
    }

    @Override
    public void create(Location entity) {
        store.createLocation(entity);
    }

    @Override
    public void delete(Location entity) {
        store.deleteLocation(entity.getId());
    }

    @Override
    public void update(Location entity) {
        store.updateLocation(entity);
    }
}
