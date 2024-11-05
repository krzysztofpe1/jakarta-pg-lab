package krzysztof.pecyna.eventsViewer.performance.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.artist.service.ArtistService;
import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.location.service.LocationService;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.performance.repository.api.PerformanceRepository;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PerformanceService {
    private final PerformanceRepository performanceRepository;

    private final ArtistService artistService;

    private final LocationService locationService;

    @Inject
    public PerformanceService(PerformanceRepository performanceRepository, ArtistService artistService, LocationService locationService) {
        this.performanceRepository = performanceRepository;
        this.artistService = artistService;
        this.locationService = locationService;
    }

    public PerformanceService() {
        this.performanceRepository = null;
        this.artistService = null;
        this.locationService = null;
    }

    public Optional<Performance> find(UUID id) {
        return performanceRepository.find(id);
    }

    public List<Performance> findAll() {
        return performanceRepository.findAll();
    }

    public Optional<Performance> findByLocationAndPerformance(UUID locationId, UUID performanceId) {
        Location location = locationService.find(locationId)
                .orElseThrow(() -> new NotFoundException("Location not found: " + locationId));

        return performanceRepository.find(performanceId)
                .filter(performance -> performance.getLocation().getId().equals(location.getId()));
    }

    public void create(Performance performance, UUID artistId, UUID locationId) {

        Artist artist = artistService.find(artistId).orElseThrow(() -> new NotFoundException("Artist not found: " + artistId));

        Location location = locationService.find(locationId).orElseThrow(() -> new NotFoundException("Location not found: " + locationId));

        performanceRepository.create(performance);

        List<Performance> artistPerformances = new ArrayList<>(artist.getPerformances());
        artistPerformances.add(performance);
        artist.setPerformances(artistPerformances);
        artistService.update(artist);

        List<Performance> locationPerformances = new ArrayList<>(location.getPerformances());
        locationPerformances.add(performance);
        location.setPerformances(locationPerformances);
        locationService.update(location);
    }

    public void update(Performance performance, UUID initialLocation) {
        Artist artist = artistService.find(performance.getArtist().getId())
                .orElseThrow(() -> new NotFoundException("Artist not found: " + performance.getArtist().getId()));

        Location newLocation = locationService.find(performance.getLocation().getId())
                .orElseThrow(() -> new NotFoundException("Location not found: " + performance.getLocation().getId()));

        if (!initialLocation.equals(newLocation.getId())) {
            Location oldLocation = locationService.find(initialLocation)
                    .orElseThrow(() -> new NotFoundException("Initial location not found: " + initialLocation));

            oldLocation.getPerformances().removeIf(oldLocationPerformance -> oldLocationPerformance.getId().equals(performance.getId()));
            locationService.update(oldLocation);
        }

        boolean artistPerformanceUpdated = artist.getPerformances().removeIf(artistPerformance -> artistPerformance.getId().equals(performance.getId()));
        if (artistPerformanceUpdated) {
            artist.getPerformances().add(performance);
        } else {
            throw new NotFoundException("Performance not found in artist's performances: " + performance.getId());
        }

        newLocation.getPerformances().removeIf(locationPerformance -> locationPerformance.getId().equals(performance.getId()));
        newLocation.getPerformances().add(performance);

        artistService.update(artist);
        locationService.update(newLocation);
        performanceRepository.update(performance);
    }

    public void delete(UUID id) {
        Performance performance = performanceRepository.find(id)
                .orElseThrow(NotFoundException::new);

        Artist artist = artistService.find(performance.getArtist().getId())
                .orElseThrow(() -> new NotFoundException("Artist not found: " + performance.getArtist().getId()));

        Location location = locationService.find(performance.getLocation().getId())
                .orElseThrow(() -> new NotFoundException("Location not found: " + performance.getLocation().getId()));

        artist.getPerformances().removeIf(u -> u.getId().equals(performance.getId()));
        location.getPerformances().removeIf(f -> f.getId().equals(performance.getId()));

        artistService.update(artist);
        locationService.update(location);
        performanceRepository.delete(performance);
    }

    public Optional<List<Performance>> findAllByArtist(UUID id) {
        return artistService.find(id)
                .map(performanceRepository::findAllByArtist);
    }

    public Optional<List<Performance>> findAllByLocation(UUID id) {
        return locationService.find(id)
                .map(performanceRepository::findAllByLocation);
    }
}