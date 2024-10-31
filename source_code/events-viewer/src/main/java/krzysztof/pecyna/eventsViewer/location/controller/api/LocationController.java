package krzysztof.pecyna.eventsViewer.location.controller.api;


import java.util.UUID;

public interface LocationController {
    GetLocationResponse getLocation(UUID id);

    GetLocationsResponse getLocations();

    void putLocation(UUID id, PutLocationRequest request);

    void patchLocation(UUID id, PatchLocationRequest request);

    void deleteLocation(UUID id);
}
 