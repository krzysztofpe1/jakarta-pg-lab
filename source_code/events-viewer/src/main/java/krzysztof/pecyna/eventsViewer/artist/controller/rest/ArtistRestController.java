package krzysztof.pecyna.eventsViewer.artist.controller.rest;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.EJBException;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import krzysztof.pecyna.eventsViewer.artist.controller.api.ArtistController;
import krzysztof.pecyna.eventsViewer.artist.dto.GetArtistResponse;
import krzysztof.pecyna.eventsViewer.artist.dto.GetArtistsResponse;
import krzysztof.pecyna.eventsViewer.artist.dto.PatchArtistRequest;
import krzysztof.pecyna.eventsViewer.artist.dto.PutArtistRequest;
import krzysztof.pecyna.eventsViewer.artist.entity.UserRoles;
import krzysztof.pecyna.eventsViewer.artist.service.ArtistService;
import krzysztof.pecyna.eventsViewer.component.DtoFunctionFactory;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.java.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Level;

@Path("")
@Log
@RolesAllowed(UserRoles.USER)
public class ArtistRestController implements ArtistController {

    private ArtistService artistService;

    private final DtoFunctionFactory factory;

    private final UriInfo uriInfo;

    private HttpServletResponse response;

    @Context
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Inject
    public ArtistRestController(DtoFunctionFactory factory, @SuppressWarnings("CdiInjectionPointsInspection") UriInfo uriInfo) {
        this.factory = factory;
        this.uriInfo = uriInfo;
    }

    @EJB
    public void setArtistService(ArtistService artistService) {
        this.artistService = artistService;
    }


    @Override
    public GetArtistResponse getArtist(UUID id) {
        return artistService.find(id)
                .map(factory.artistToResponse())
                .orElseThrow(() -> new NotFoundException("Artist not found"));
    }

    @Override
    public GetArtistsResponse getArtists() {
        return factory.artistsToResponse().apply(artistService.findAll());
    }

    @PermitAll
    @Override
    public void putArtist(UUID id, PutArtistRequest request) {
        try {
            artistService.create(factory.requestToArtist().apply(id, request));
            response.setHeader("Location", uriInfo.getBaseUriBuilder()
                    .path(ArtistController.class, "getArtist")
                    .build(id)
                    .toString());
            throw new WebApplicationException(Response.Status.CREATED);
        } catch (EJBException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                log.log(Level.WARNING, ex.getMessage(), ex);
                throw new BadRequestException("Artist already exists, to update artist use PATCH method");
            }
            throw ex;
        }
    }

    @Override
    public void patchArtist(UUID id, PatchArtistRequest request) {
        artistService.find(id)
                .ifPresentOrElse(entity -> artistService.update(factory.updateArtist().apply(entity, request)), () -> {
                    throw new NotFoundException("Artist not found");
                });

    }

    @Override
    public void deleteArtist(UUID id) {
        artistService.find(id).ifPresentOrElse(entity -> artistService.delete(id), () -> {
            throw new NotFoundException("Artist not found");
        });
    }

    @Override
    public byte[] getArtistAvatar(UUID id, String pathToAvatars) {

        java.nio.file.Path pathToAvatar = Paths.get(
                pathToAvatars,
                artistService.find(id)
                        .map(artist -> artist.getId().toString())
                        .orElseThrow(() -> new NotFoundException("Artist does not exist"))
                        + ".png"
        );
        try {
            if (!Files.exists(pathToAvatar)) {
                throw new NotFoundException("Artist avatar does not exist");
            }
            return Files.readAllBytes(pathToAvatar);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void putArtistAvatar(UUID id, InputStream avatar, String pathToAvatars) {
        artistService.find(id).ifPresentOrElse(
                artist -> {
                    artistService.createAvatar(id, avatar, pathToAvatars);
                },
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteArtistAvatar(UUID id, String pathToAvatars) {
        artistService.find(id).ifPresentOrElse(
                artist -> {
                    try {
                        java.nio.file.Path avatarPath = Paths.get(pathToAvatars, artist.getId().toString() + ".png");
                        if (!Files.exists(avatarPath)) {
                            throw new NotFoundException("Artist avatar does not exist");
                        }
                        Files.delete(avatarPath);
                    } catch (IOException e) {
                        throw new NotFoundException(e);
                    }
                },
                () -> {
                    throw new NotFoundException("Artist does not exist");
                }
        );
    }

    @Override
    public void patchArtistAvatar(UUID id, InputStream avatar, String pathToAvatars) {
        artistService.find(id).ifPresentOrElse(
                artist -> artistService.updateAvatar(id, avatar, pathToAvatars),
                () -> {
                    throw new NotFoundException("Artist does not exist");
                }
        );
    }
}
