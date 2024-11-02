package krzysztof.pecyna.eventsViewer.performance.controller.rest;

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
import krzysztof.pecyna.eventsViewer.performance.controller.api.PerformanceController;
import krzysztof.pecyna.eventsViewer.performance.dto.GetPerformanceResponse;
import krzysztof.pecyna.eventsViewer.performance.dto.GetPerformancesResponse;
import krzysztof.pecyna.eventsViewer.performance.dto.PatchPerformanceRequest;
import krzysztof.pecyna.eventsViewer.performance.dto.PutPerformanceRequest;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.performance.service.PerformanceService;

import java.util.UUID;

@Path("")
public class PerformanceRestController implements PerformanceController {
    private final PerformanceService performanceService;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public PerformanceRestController(PerformanceService performanceService, DtoFunctionFactory factory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.performanceService = performanceService;
        this.factory = factory;
        this.uriInfo = uriInfo;

    }

    @Override
    public GetPerformancesResponse getArtistPerformances(UUID id) {
        return performanceService.findAllByArtist(id)
                .map(factory.performancesToResponse())
                .orElseThrow(() -> new NotFoundException("Artist not found"));
    }

    @Override
    public GetPerformancesResponse getLocationPerformances(UUID id) {
        return performanceService.findAllByLocation(id)
                .map(factory.performancesToResponse())
                .orElseThrow(() -> new NotFoundException("Location not found"));
    }

    @Override
    public GetPerformancesResponse getPerformances() {
        return factory.performancesToResponse().apply(performanceService.findAll());
    }

    @Override
    public GetPerformanceResponse getPerformance(UUID id) {
        return performanceService.find(id)
                .map(factory.performanceToResponse())
                .orElseThrow(() -> new NotFoundException("Performance not found"));
    }

    @Override
    public void putPerformance(UUID id, PutPerformanceRequest request) {
        try {
            Performance performance = factory.requestToPerformance().apply(id, request);
            performanceService.create(performance, request.getArtist(), request.getLocation());

            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(PerformanceController.class, "getPerformance")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (IllegalArgumentException ex) {
            throw new NotAllowedException("Performance already exists, to update performance use PATCH method");
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @Override
    public void patchPerformance(UUID id, PatchPerformanceRequest request) {
        performanceService.find(id).ifPresentOrElse(entity -> performanceService.update(factory.updatePerformance().apply(entity, request), entity.getLocation().getId()), () -> {
            throw new NotFoundException("Performance not found");
        });
    }

    @Override
    public void deletePerformance(UUID id) {
        performanceService.find(id).ifPresentOrElse(entity -> performanceService.delete(id), () -> {
            throw new NotFoundException("Performance not found");
        });
    }
}
