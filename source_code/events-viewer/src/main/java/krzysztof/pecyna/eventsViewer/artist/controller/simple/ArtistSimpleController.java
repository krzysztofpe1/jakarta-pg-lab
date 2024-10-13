package krzysztof.pecyna.eventsViewer.artist.controller.simple;

import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import krzysztof.pecyna.eventsViewer.artist.controller.api.ArtistController;
import krzysztof.pecyna.eventsViewer.artist.dto.GetArtistResponse;
import krzysztof.pecyna.eventsViewer.artist.dto.GetArtistsResponse;
import krzysztof.pecyna.eventsViewer.artist.dto.PatchArtistRequest;
import krzysztof.pecyna.eventsViewer.artist.dto.PutArtistRequest;
import krzysztof.pecyna.eventsViewer.artist.service.ArtistService;
import krzysztof.pecyna.eventsViewer.component.DtoFunctionsFactory;

import java.io.InputStream;
import java.util.UUID;

public class ArtistSimpleController implements ArtistController {

    private final ArtistService artistService;
    private final DtoFunctionsFactory factory;

    public ArtistSimpleController(ArtistService artistService, DtoFunctionsFactory factory) {
        this.artistService = artistService;
        this.factory = factory;
    }

    @Override
    public GetArtistResponse getArtist(UUID id) {
        return artistService.find(id)
                .map(factory.artistToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetArtistsResponse getArtists() {
        return factory.artistsToResponse().apply(artistService.findAll());
    }

    @Override
    public void putArtist(UUID id, PutArtistRequest data) {
        try{
            artistService.create(factory.requestToArtist().apply(id,data));
        }
        catch (IllegalArgumentException ex){
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchArtist(UUID id, PatchArtistRequest data) {
        artistService.find(id).ifPresentOrElse(
                entity -> artistService.update(factory.updateArtist().apply(entity, data)),
                () -> {throw  new NotFoundException();}
        );
    }

    @Override
    public void deleteArtist(UUID id) {
        artistService.find(id).ifPresentOrElse(
                entity-> {artistService.delete(entity);},
                () -> {throw  new NotFoundException();}
        );
    }

    @Override
    public byte[] getArtistAvatar(UUID id) {
        return artistService.getAvatar(id);
    }

    @Override
    public void putArtistAvatar(UUID id, InputStream avatarData) {
        artistService.createAvatar(id, avatarData);
    }

    @Override
    public void deleteArtistAvatar(UUID id) {
        artistService.deleteAvatar(id);
    }

    @Override
    public void patchArtistAvatar(UUID id, InputStream avatarData) {
        artistService.updateAvatar(id, avatarData);
    }
}
