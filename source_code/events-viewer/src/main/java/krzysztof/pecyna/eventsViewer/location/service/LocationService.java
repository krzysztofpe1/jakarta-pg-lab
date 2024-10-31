package krzysztof.pecyna.eventsViewer.location.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.performance.service.PerformanceService;
import krzysztof.pecyna.eventsViewer.controller.servlet.exception.NotFoundException;

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

    public void create(Location location) {
        locationRepository.create(location);
    }

    public void update(Location location) {
        locationRepository.update(location);
    }

    public void delete(UUID id) {
        Location location = locationRepository.find(id).orElseThrow(NotFoundException::new);
        Optional<List<Performance>> performancesToDelete = performanceService.findAllByLocation(id);
        performancesToDelete.ifPresent(performances -> performances.forEach(performance -> {
            performanceService.delete(performance.getId());
        }));

        performancesToDelete.ifPresent(performances -> {

        });

        locationRepository.delete(location);
    }
}
