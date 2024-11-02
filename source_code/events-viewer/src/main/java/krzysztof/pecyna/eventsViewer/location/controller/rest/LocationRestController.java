package krzysztof.pecyna.eventsViewer.location.controller.rest;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.NotAllowedException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import krzysztof.pecyna.eventsViewer.component.DtoFunctionFactory;
import krzysztof.pecyna.eventsViewer.location.controller.api.LocationController;
import krzysztof.pecyna.eventsViewer.location.dto.GetLocationResponse;
import krzysztof.pecyna.eventsViewer.location.dto.GetLocationsResponse;
import krzysztof.pecyna.eventsViewer.location.dto.PatchLocationRequest;
import krzysztof.pecyna.eventsViewer.location.dto.PutLocationRequest;
import krzysztof.pecyna.eventsViewer.location.service.LocationService;

import java.util.UUID;

@Path("")
public class LocationRestController implements LocationController {
    private final LocationService locationService;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public LocationRestController(final LocationService locationService, final DtoFunctionFactory factory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.locationService = locationService;
        this.uriInfo = uriInfo;
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
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(LocationController.class, "getLocation")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new NotAllowedException("Location already exists, to update location use PATCH method");
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
