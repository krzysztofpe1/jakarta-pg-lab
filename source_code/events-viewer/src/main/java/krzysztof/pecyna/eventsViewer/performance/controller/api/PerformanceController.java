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
    @Path("/performances")
    @Produces(MediaType.APPLICATION_JSON)
    GetPerformancesResponse getPerformances();

    @GET
    @Path("/performances/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetPerformanceResponse getPerformance(@PathParam("id") UUID id);

    @PUT
    @Path("/performances/{id}")
    void putPerformance(@PathParam("id") UUID id, PutPerformanceRequest request);

    @PATCH
    @Path("/performances/{id}")
    void patchPerformance(@PathParam("id") UUID id, PatchPerformanceRequest request);

    @DELETE
    @Path("/performances/{id}")
    void deletePerformance(@PathParam("id") UUID id);
}
