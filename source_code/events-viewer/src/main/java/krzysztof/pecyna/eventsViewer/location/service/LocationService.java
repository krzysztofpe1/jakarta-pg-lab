package krzysztof.pecyna.eventsViewer.location.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.location.repository.api.LocationRepository;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.performance.service.PerformanceService;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@ApplicationScoped
public class LocationService {
    private final LocationRepository locationRepository;

    private final PerformanceService performanceService;

    @Inject
    public LocationService(LocationRepository locationRepository, PerformanceService performanceService) {
        this.locationRepository = locationRepository;
        this.performanceService = performanceService;
    }

    public LocationService() {
        this.locationRepository = null;
        this.performanceService = null;
    }

    public Optional<Location> find(UUID id) {
        return locationRepository.find(id);
    }

    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Transactional
    public void create(Location location) {
        locationRepository.create(location);
    }

    @Transactional
    public void update(Location location) {
        locationRepository.update(location);
    }

    @Transactional
    public void delete(UUID id) {
        Location location = locationRepository.find(id).orElseThrow(NotFoundException::new);
        locationRepository.delete(location);
    }
}
