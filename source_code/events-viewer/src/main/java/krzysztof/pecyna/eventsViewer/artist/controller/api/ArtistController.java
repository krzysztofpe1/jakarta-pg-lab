package krzysztof.pecyna.eventsViewer.artist.controller.api;

import krzysztof.pecyna.eventsViewer.artist.dto.GetArtistResponse;
import krzysztof.pecyna.eventsViewer.artist.dto.GetArtistsResponse;
import krzysztof.pecyna.eventsViewer.artist.dto.PatchArtistRequest;
import krzysztof.pecyna.eventsViewer.artist.dto.PutArtistRequest;
import krzysztof.pecyna.eventsViewer.component.exception.AvatarDoesNotExistException;
import krzysztof.pecyna.eventsViewer.component.exception.AvatarExistsException;

import java.io.InputStream;
import java.util.UUID;

public interface ArtistController {

    GetArtistResponse getArtist(UUID id);

    GetArtistsResponse getArtists();

    void putArtist(UUID id, PutArtistRequest data);

    void patchArtist(UUID id, PatchArtistRequest data);

    void deleteArtist(UUID id);

    byte[] getArtistAvatar(UUID id);

    void putArtistAvatar(UUID id, InputStream data) throws AvatarExistsException;

    void deleteArtistAvatar(UUID id) throws AvatarDoesNotExistException;

    void patchArtistAvatar(UUID id, InputStream data) throws AvatarDoesNotExistException;

}
