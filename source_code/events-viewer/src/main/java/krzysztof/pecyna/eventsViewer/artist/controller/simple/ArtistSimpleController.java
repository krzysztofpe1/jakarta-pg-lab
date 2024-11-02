package krzysztof.pecyna.eventsViewer.artist.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import krzysztof.pecyna.eventsViewer.artist.controller.api.ArtistController;
import krzysztof.pecyna.eventsViewer.artist.dto.GetArtistResponse;
import krzysztof.pecyna.eventsViewer.artist.dto.GetArtistsResponse;
import krzysztof.pecyna.eventsViewer.artist.dto.PatchArtistRequest;
import krzysztof.pecyna.eventsViewer.artist.dto.PutArtistRequest;
import krzysztof.pecyna.eventsViewer.artist.service.ArtistService;
import krzysztof.pecyna.eventsViewer.component.DtoFunctionFactory;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.NotAllowedException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequestScoped
public class ArtistSimpleController implements ArtistController {

    private final ArtistService artistService;

    private final DtoFunctionFactory factory;

    @Inject
    public ArtistSimpleController(DtoFunctionFactory factory, ArtistService artistService) {
        this.factory = factory;
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

    @Override
    public void putArtist(UUID id, PutArtistRequest request) {
        try {
            artistService.create(factory.requestToArtist().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new NotAllowedException("Artist already exists, to update artist use PATCH method");
        }
    }

    @Override
    public void patchArtist(UUID id, PatchArtistRequest request) {
        artistService.find(id).ifPresentOrElse(entity -> artistService.update(factory.updateArtist().apply(entity, request)), () -> {
            throw new NotFoundException("Artist not found, to create artist use PUT method");
        });

    }

    @Override
    public void deleteArtist(UUID id) {
        artistService.find(id).ifPresentOrElse(
                entity -> artistService.delete(id),
                () -> {
                    throw new NotFoundException("Artist not found");
                }
        );
    }

    @Override
    public byte[] getArtistAvatar(UUID id, String pathToAvatars) {
        Path pathToAvatar = Paths.get(
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
                        Path avatarPath = Paths.get(pathToAvatars, artist.getId().toString() + ".png");
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
