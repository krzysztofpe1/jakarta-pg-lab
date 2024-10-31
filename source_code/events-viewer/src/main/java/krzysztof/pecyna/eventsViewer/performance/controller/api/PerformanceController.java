package krzysztof.pecyna.eventsViewer.performance.controller.api;


import java.util.UUID;

public interface PerformanceController {
    GetPerformancesResponse getArtistPerformances(UUID id);

    GetPerformancesResponse getLocationPerformances(UUID id);

    GetPerformancesResponse getPerformances();

    GetPerformanceResponse getPerformance(UUID id);

    void putPerformance(UUID id, PutUnitRequest request);

    void patchPerformance(UUID id, PatchUnitRequest request);

    void deletePerformance(UUID id);
}
