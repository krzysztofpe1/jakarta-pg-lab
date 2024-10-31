package krzysztof.pecyna.eventsViewer.performance.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import krzysztof.pecyna.eventsViewer.performance.controller.api.PerformanceController;

import java.util.UUID;

@RequestScoped
public class PerformanceSimpleController implements PerformanceController {

    private final  PerformanceService performanceService;

    private final DtoFunctionFactory factory;

    @Inject
    public PerformanceSimpleController(PerformanceService performanceService, DtoFunctionFactory factory) {
        this.performanceService = performanceService;
        this.factory = factory;

    }

    @Override
    public GetPerformanceServicesResponse getArtistPerformances(UUID id) {
        return PerformanceService.findAllByUser(id)
                .map(factory.PerformancesToResponse())
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
        return factory.erformancesToResponse().apply(performanceService.findAll());
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
        } catch (IllegalArgumentException ex) {
            throw new AlreadyExistsException("Performance already exists, to update performance use PATCH method");
        } catch (NotFoundException ex) {
            throw new NotFoundException(ex.getMessage());
        }
    }

    @Override
    public void patchPerformance(UUID id, PatchPerformanceRequest request) {
        performanceService.find(id).ifPresentOrElse(entity -> performanceService.update(factory.updatePerformance().apply(entity, request)), () -> {
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
