package krzysztof.pecyna.eventsViewer.location.controller.rest;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import krzysztof.pecyna.eventsViewer.artist.entity.UserRoles;
import krzysztof.pecyna.eventsViewer.component.DtoFunctionFactory;
import krzysztof.pecyna.eventsViewer.location.controller.api.LocationController;
import krzysztof.pecyna.eventsViewer.location.dto.GetLocationResponse;
import krzysztof.pecyna.eventsViewer.location.dto.GetLocationsResponse;
import krzysztof.pecyna.eventsViewer.location.dto.PatchLocationRequest;
import krzysztof.pecyna.eventsViewer.location.dto.PutLocationRequest;
import krzysztof.pecyna.eventsViewer.location.service.LocationService;
import lombok.extern.java.Log;

import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
@RolesAllowed({UserRoles.ADMIN, UserRoles.USER})
public class LocationRestController implements LocationController {
    private LocationService locationService;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public LocationRestController(DtoFunctionFactory factory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setLocationService(LocationService locationService) {
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
    @RolesAllowed("admin")
    @Override
    public void putLocation(UUID id, PutLocationRequest request) {
        try {
            locationService.create(factory.requestToLocation().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(LocationController.class, "getLocation")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException("Location already exists, to update location use PATCH method");
            }
            throw ex;
        }
    }

    @Override
    public void patchLocation(UUID id, PatchLocationRequest request) {
        locationService.find(id)
                .ifPresentOrElse(entity -> locationService.update(factory.updateLocation().apply(entity, request)), () -> {
                    throw new NotFoundException("Location not found");
                });
    }
    @RolesAllowed(UserRoles.ADMIN)
    @Override
    public void deleteLocation(UUID id) {
        locationService.find(id).ifPresentOrElse(entity -> locationService.delete(id), () -> {
            throw new NotFoundException("Location not found");
        });
    }
}
