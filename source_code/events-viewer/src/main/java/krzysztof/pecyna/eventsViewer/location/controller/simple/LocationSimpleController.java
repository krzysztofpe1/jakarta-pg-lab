package krzysztof.pecyna.eventsViewer.location.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import krzysztof.pecyna.eventsViewer.component.DtoFunctionFactory;
import krzysztof.pecyna.eventsViewer.controller.servlet.exception.AlreadyExistsException;
import krzysztof.pecyna.eventsViewer.location.controller.api.LocationController;
import krzysztof.pecyna.eventsViewer.controller.servlet.exception.NotFoundException;
import krzysztof.pecyna.eventsViewer.location.dto.GetLocationResponse;
import krzysztof.pecyna.eventsViewer.location.dto.GetLocationsResponse;
import krzysztof.pecyna.eventsViewer.location.service.LocationService;
import krzysztof.pecyna.eventsViewer.location.dto.PutLocationRequest;
import krzysztof.pecyna.eventsViewer.location.dto.PatchLocationRequest;

import java.util.UUID;

@RequestScoped
public class LocationSimpleController implements LocationController {
    private final LocationService locationService;

    private final DtoFunctionFactory factory;

    @Inject
    public LocationSimpleController(final LocationService locationService, final DtoFunctionFactory factory) {
        this.factory = factory;
        this.locationService = locationService;
    }

    @Override
    public GetLocationResponse getLocation(UUID id) {
        return locationService.find(id)
                .map(factory.locationToResponse())
                .orElseThrow(() -> new NotFoundException("Location not found"));
    }

    @Override
    public GetLocationsResponse getLocations() {
        return factory.locationsToResponse().apply(locationService.findAll());
    }

    @Override
    public void putLocation(UUID id, PutLocationRequest request) {
        try {
            locationService.create(factory.requestToLocation().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new AlreadyExistsException("Location already exists, to update location use PATCH method");
        }
    }

    @Override
    public void patchLocation(UUID id, PatchLocationRequest request) {
        locationService.find(id)
                .ifPresentOrElse(entity -> locationService.update(factory.updateLocation().apply(entity, request)), () -> {
                    throw new NotFoundException("Location not found");
                });
    }

    @Override
    public void deleteLocation(UUID id) {
        locationService.find(id).ifPresentOrElse(entity -> locationService.delete(id), () -> {
            throw new NotFoundException("Location not found");
        });
    }
}
