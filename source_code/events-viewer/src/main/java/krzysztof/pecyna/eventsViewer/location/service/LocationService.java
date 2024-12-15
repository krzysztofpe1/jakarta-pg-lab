package krzysztof.pecyna.eventsViewer.location.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import krzysztof.pecyna.eventsViewer.artist.entity.UserRoles;
import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.location.repository.api.LocationRepository;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.performance.service.PerformanceService;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class LocationService {
    private final LocationRepository locationRepository;

    private final PerformanceService performanceService;

    private final SecurityContext securityContext;

    @Inject
    public LocationService(LocationRepository locationRepository, PerformanceService performanceService, @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext) {
        this.locationRepository = locationRepository;
        this.performanceService = performanceService;
        this.securityContext = securityContext;

    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public Optional<Location> find(UUID id) {
        return locationRepository.find(id).map(location -> {
            List<Performance> performances = performanceService.findAllByLocation(location.getId()).orElse(Collections.emptyList());

            String currentUserName = securityContext.getCallerPrincipal().getName();
            boolean isAdmin = securityContext.isCallerInRole(UserRoles.ADMIN);

            performances.removeIf(performance ->
                    !isAdmin && !performance.getArtist().getLastName().equals(currentUserName)
            );

            location.setPerformances(performances);
            return location;
        });
    }

    @RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void create(Location location) {
        locationRepository.create(location);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void update(Location location) {
        locationRepository.update(location);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void delete(UUID id) {
        Location location = locationRepository.find(id).orElseThrow(NotFoundException::new);
        Optional<List<Performance>> performancesToDelete = performanceService.findAllByLocation(id);
        performancesToDelete.ifPresent(performances -> performances.forEach(performance -> {
            performanceService.delete(performance.getId());
        }));
        locationRepository.delete(location);
    }
}
