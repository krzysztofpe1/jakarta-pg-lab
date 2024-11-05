package krzysztof.pecyna.eventsViewer.performance.controller.api;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import krzysztof.pecyna.eventsViewer.performance.dto.GetPerformanceResponse;
import krzysztof.pecyna.eventsViewer.performance.dto.GetPerformancesResponse;
import krzysztof.pecyna.eventsViewer.performance.dto.PatchPerformanceRequest;
import krzysztof.pecyna.eventsViewer.performance.dto.PutPerformanceRequest;

import java.util.UUID;

public interface PerformanceController {
    @GET
    @Path("/artists/{id}/performances")
    @Produces(MediaType.APPLICATION_JSON)
    GetPerformancesResponse getArtistPerformances(@PathParam("id") UUID id);

    @GET
    @Path("/locations/{id}/performances")
    @Produces(MediaType.APPLICATION_JSON)
    GetPerformancesResponse getLocationPerformances(@PathParam("id") UUID id);

    @GET
    @Path("/locations/{locationId}/performances/{performanceId}")
    @Produces(MediaType.APPLICATION_JSON)
    GetPerformanceResponse getLocationPerformance(@PathParam("locationId") UUID locationId, @PathParam("performanceId") UUID performanceId);

    @PUT
    @Path("/locations/{locationId}/performances/{performanceId}")
    void putLocationPerformance(@PathParam("locationId") UUID locationId, @PathParam("performanceId") UUID performanceId, PutPerformanceRequest request);

    @PATCH
    @Path("/locations/{locationId}/performances/{performanceId}")
    void patchLocationPerformance(@PathParam("locationId") UUID locationId, @PathParam("performanceId") UUID performanceId, PatchPerformanceRequest request);

    @DELETE
    @Path("/locations/{locationId}/performances/{performanceId}")
    void deleteLocationPerformance(@PathParam("locationId") UUID locationId, @PathParam("performanceId") UUID performanceId);

    // Test only
    @GET
    @Path("/performances")
    @Produces(MediaType.APPLICATION_JSON)
    GetPerformancesResponse getPerformances();

    @GET
    @Path("/performances/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetPerformanceResponse getPerformance(@PathParam("id") UUID id);


}
