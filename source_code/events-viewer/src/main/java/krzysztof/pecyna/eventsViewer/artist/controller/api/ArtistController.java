package krzysztof.pecyna.eventsViewer.artist.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import krzysztof.pecyna.eventsViewer.artist.dto.GetArtistResponse;
import krzysztof.pecyna.eventsViewer.artist.dto.GetArtistsResponse;
import krzysztof.pecyna.eventsViewer.artist.dto.PatchArtistRequest;
import krzysztof.pecyna.eventsViewer.artist.dto.PutArtistRequest;

import javax.print.attribute.standard.Media;
import java.awt.*;
import java.io.InputStream;
import java.util.UUID;

public interface ArtistController {

    /*@GET
    @Path("/artists/{id}")
    @Produces(MediaType.APPLICATION_JSON)*/
    GetArtistResponse getArtist( @PathParam("id") UUID id);

    /*@GET
    @Path("/artists/")
    @Produces(MediaType.APPLICATION_JSON)*/
    GetArtistsResponse getArtists();

    /*@PUT
    @Path("/artists/{id}")*/
    void putArtist(@PathParam("id") UUID id, PutArtistRequest request);

    /*@PATCH
    @Path("/artists/{id}")*/
    void patchArtist(@PathParam("id") UUID id, PatchArtistRequest request);

    /*@DELETE
    @Path("/artists/{id}")*/
    void deleteArtist(@PathParam("id") UUID id);


    byte[] getArtistAvatar(UUID id, String pathToAvatars);

    void putArtistAvatar(UUID id, InputStream avatar, String pathToAvatars);

    void deleteArtistAvatar(UUID id, String pathToAvatars);

    void patchArtistAvatar(UUID id, InputStream avatar, String pathToAvatars);

}
