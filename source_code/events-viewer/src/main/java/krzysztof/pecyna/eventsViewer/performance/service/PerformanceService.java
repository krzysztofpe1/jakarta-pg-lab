package krzysztof.pecyna.eventsViewer.performance.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.artist.entity.UserRoles;
import krzysztof.pecyna.eventsViewer.artist.repository.api.ArtistRepository;
import krzysztof.pecyna.eventsViewer.artist.service.ArtistService;
import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.location.service.LocationService;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.performance.repository.api.PerformanceRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class PerformanceService {
    private final PerformanceRepository performanceRepository;

    private final ArtistRepository artistRepository;

    private final ArtistService artistService;

    private final LocationService locationService;

    private final SecurityContext securityContext;

    @Inject
    public PerformanceService(PerformanceRepository performanceRepository, ArtistRepository artistRepository, ArtistService artistService, LocationService locationService,
                       @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.performanceRepository = performanceRepository;
        this.artistRepository = artistRepository;
        this.artistService = artistService;
        this.locationService = locationService;
        this.securityContext = securityContext;

    }

    @RolesAllowed(UserRoles.USER)
    public Optional<Performance> find(UUID id) {
        return performanceRepository.find(id);
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<Performance> find(Artist artist, UUID id) {
        return performanceRepository.findByIdAndArtist(id, artist);
    }


    @RolesAllowed(UserRoles.USER)
    public Optional<Performance> findForCallerPrincipal(UUID locationId, UUID performanceId) {
        checkAdminRoleOrOwner(performanceRepository.find(performanceId));
        return findByLocationAndPerformance(locationId, performanceId);
    }

    @RolesAllowed(UserRoles.USER)
    public Optional<Performance> findForCallerPrincipal(UUID performanceId) {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return find(performanceId);
        }
        Artist artist = artistRepository.findByLastName(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return find(artist, performanceId);

    }

    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public List<Performance> findAllForCallerPrincipal() {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return performanceRepository.findAll();
        }
        Artist artist = artistRepository.findByLastName(securityContext.getCallerPrincipal().getName()).orElseThrow(IllegalStateException::new);
        return findAll(artist);
    }

    @RolesAllowed(UserRoles.USER)
    public List<Performance> findAll(Artist artist) {
        return performanceRepository.findAllByArtist(artist);
    }


    public Optional<Performance> findByLocationAndPerformance(UUID locationId, UUID performanceId) {
        Location location = locationService.find(locationId)
                .orElseThrow(() -> new NotFoundException("Location not found: " + locationId));

        return performanceRepository.find(performanceId)
                .filter(performance -> performance.getLocation().getId().equals(location.getId()));
    }

    @RolesAllowed(UserRoles.USER)
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

    @RolesAllowed(UserRoles.USER)
    @TransactionAttribute
    public void update(Performance performance, UUID initialLocation) {
        Performance original = performanceRepository.find(performance.getId()).orElseThrow(IllegalStateException::new);

        performanceRepository.detach(original);
        Performance existingPerformance = performanceRepository.find(performance.getId())
                .orElseThrow(() -> new NotFoundException("Performance not found: " + performance.getId()));

        checkAdminRoleOrOwner(Optional.of(existingPerformance));

        Location newLocation = locationService.find(performance.getLocation().getId())
                .orElseThrow(() -> new NotFoundException("Location not found: " + performance.getLocation().getId()));

        if (!initialLocation.equals(newLocation.getId())) {
            Location oldLocation = locationService.find(initialLocation)
                    .orElseThrow(() -> new NotFoundException("Initial location not found: " + initialLocation));

            oldLocation.getPerformances().removeIf(f -> f.getId().equals(existingPerformance.getId()));
            locationService.update(oldLocation);
        }

        existingPerformance.setDate(performance.getDate());
        existingPerformance.setPerformanceType(performance.getPerformanceType());
        existingPerformance.setLocation(newLocation);

        artistService.update(existingPerformance.getArtist());
        locationService.update(newLocation);

        performanceRepository.update(existingPerformance);
    }


    @RolesAllowed(UserRoles.USER)
    @TransactionAttribute
    public void update(Performance performance) {
        Performance original = performanceRepository.find(performance.getId()).orElseThrow(IllegalStateException::new);
        performanceRepository.detach(original);
        performanceRepository.update(performance);
    }



    @RolesAllowed(UserRoles.USER)
    public void createForCallerPrincipal(Performance performance) {
        Artist artist = artistRepository.findByLastName(securityContext.getCallerPrincipal().getName()).orElseThrow(IllegalStateException::new);
        performance.setArtist(artist);
        performanceRepository.create(performance);
    }

    @RolesAllowed(UserRoles.USER)
    public void delete(UUID id) {
        checkAdminRoleOrOwner(performanceRepository.find(id));

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

    @RolesAllowed(UserRoles.USER)
    public Optional<List<Performance>> findAllByArtist(UUID id) {
        return artistService.find(id)
                .map(performanceRepository::findAllByArtist);
    }

    public Optional<List<Performance>> findAllByLocation(UUID locationId) {
        return Optional.ofNullable(performanceRepository.findAllByLocation(locationId));
    }


    private void checkAdminRoleOrOwner(Optional<Performance> performance) throws EJBAccessException {
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return;
        }
        if (securityContext.isCallerInRole(UserRoles.USER)
                && performance.isPresent()
                && performance.get().getArtist().getLastName().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new EJBAccessException("Caller not authorized.");
    }
}