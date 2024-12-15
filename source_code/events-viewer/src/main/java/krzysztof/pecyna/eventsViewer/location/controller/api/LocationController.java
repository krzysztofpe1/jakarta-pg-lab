package krzysztof.pecyna.eventsViewer.location.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import krzysztof.pecyna.eventsViewer.location.dto.GetLocationResponse;
import krzysztof.pecyna.eventsViewer.location.dto.GetLocationsResponse;
import krzysztof.pecyna.eventsViewer.location.dto.PutLocationRequest;
import krzysztof.pecyna.eventsViewer.location.dto.PatchLocationRequest;

import java.util.UUID;
@Path("")
public interface LocationController {
    @GET
    @Path("/locations/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetLocationResponse getLocation(@PathParam("id") UUID id);

    @GET
    @Path("/locations")
    @Produces(MediaType.APPLICATION_JSON)
    GetLocationsResponse getLocations();

    @PUT
    @Path("/locations/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putLocation(@PathParam("id") UUID id, PutLocationRequest request);

    @PATCH
    @Path("/locations/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchLocation(@PathParam("id") UUID id, PatchLocationRequest request);

    @DELETE
    @Path("/locations/{id}")
    void deleteLocation(@PathParam("id") UUID id);

}
 