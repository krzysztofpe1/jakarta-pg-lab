package krzysztof.pecyna.eventsViewer.performance.controller.api;


import krzysztof.pecyna.eventsViewer.performance.dto.GetPerformanceResponse;
import krzysztof.pecyna.eventsViewer.performance.dto.GetPerformancesResponse;
import krzysztof.pecyna.eventsViewer.performance.dto.PatchPerformanceRequest;
import krzysztof.pecyna.eventsViewer.performance.dto.PutPerformanceRequest;

import java.util.UUID;

public interface PerformanceController {
    GetPerformancesResponse getArtistPerformances(UUID id);

    GetPerformancesResponse getLocationPerformances(UUID id);

    GetPerformancesResponse getPerformances();

    GetPerformanceResponse getPerformance(UUID id);

    void putPerformance(UUID id, PutPerformanceRequest request);

    void patchPerformance(UUID id, PatchPerformanceRequest request);

    void deletePerformance(UUID id);
}
