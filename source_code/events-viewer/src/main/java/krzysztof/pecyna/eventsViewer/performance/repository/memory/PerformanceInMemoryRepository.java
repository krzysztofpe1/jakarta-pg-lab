package krzysztof.pecyna.eventsViewer.performance.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import krzysztof.pecyna.eventsViewer.dataStore.DataStore;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.performance.repository.api.PerformanceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestScoped
public class PerformanceInMemoryRepository implements PerformanceRepository {

    private final DataStore store;

    @Inject
    public PerformanceInMemoryRepository(DataStore store) {
        this.store = store;
    }


    @Override
    public Optional<Performance> find(UUID id) {
        return store.findAllPerformances().stream().filter(performance -> id.equals(performance.getId())).findFirst();
    }

    @Override
    public List<Performance> findAll() {
        return store.findAllPerformances();
    }

    @Override
    public void create(Performance entity) {
        store.createPerformance(entity);
    }

    @Override
    public void delete(Performance entity) {
        store.deletePerformance(entity.getId());
    }

    @Override
    public void update(Performance entity) {
        store.updatePerformance(entity);
    }

    @Override
    public List<Performance> findAllByArtist(Artist artist) {
        return store.findAllPerformances().stream()
                .filter(performance ->
                        artist.getId().equals(performance.getArtist().getId())
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<Performance> findAllByLocation(Location location) {
        return store.findAllPerformances().stream()
                .filter(performance ->
                        location.getId().equals(performance.getLocation().getId())
                )
                .collect(Collectors.toList());
    }
}
