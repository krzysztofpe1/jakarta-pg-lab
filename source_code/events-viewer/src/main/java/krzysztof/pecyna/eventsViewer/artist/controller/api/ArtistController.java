package krzysztof.pecyna.eventsViewer.artist.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import krzysztof.pecyna.eventsViewer.artist.dto.GetArtistResponse;
import krzysztof.pecyna.eventsViewer.artist.dto.GetArtistsResponse;
import krzysztof.pecyna.eventsViewer.artist.dto.PatchArtistRequest;
import krzysztof.pecyna.eventsViewer.artist.dto.PutArtistRequest;

import java.io.InputStream;
import java.util.UUID;
@Path("")
public interface ArtistController {
    @GET
    @Path("/artists/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetArtistResponse getArtist(@PathParam("id") UUID id);

    @GET
    @Path("/artists")
    @Produces(MediaType.APPLICATION_JSON)
    GetArtistsResponse getArtists();

    @PUT
    @Path("/artists/{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    void putArtist(@PathParam("id") UUID id, PutArtistRequest request);

    @PATCH
    @Path("/artists/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchArtist(@PathParam("id") UUID id, PatchArtistRequest request);

    @DELETE
    @Path("/artists/{id}")
    void deleteArtist(@PathParam("id") UUID id);

    @GET
    @Path("/artists/{id}/avatar")
    @Produces("image/png")
    byte[] getArtistAvatar(UUID id, String pathToAvatars);

    @PUT
    @Path("/artists/{id}/avatar")
    @Consumes({MediaType.APPLICATION_JSON})
    void putArtistAvatar(UUID id, InputStream avatar, String pathToAvatars);

    @DELETE
    @Path("/artists/{id}/avatar")
    void deleteArtistAvatar(UUID id, String pathToAvatars);

    @PATCH
    @Path("/artists/{id}/avatar")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchArtistAvatar(UUID id, InputStream avatar, String pathToAvatars);
}
