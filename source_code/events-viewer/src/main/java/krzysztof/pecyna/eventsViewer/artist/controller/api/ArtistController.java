package krzysztof.pecyna.eventsViewer.artist.controller.api;

import krzysztof.pecyna.eventsViewer.artist.dto.GetArtistResponse;
import krzysztof.pecyna.eventsViewer.artist.dto.GetArtistsResponse;
import krzysztof.pecyna.eventsViewer.artist.dto.PatchArtistRequest;
import krzysztof.pecyna.eventsViewer.artist.dto.PutArtistRequest;

import java.io.InputStream;
import java.util.UUID;

public interface ArtistController {
    GetArtistResponse getArtist(UUID id);

    GetArtistsResponse getArtists();

    void putArtist(UUID id, PutArtistRequest request);

    void patchArtist(UUID id, PatchArtistRequest request);

    void deleteArtist(UUID id);

    byte[] getArtistAvatar(UUID id, String pathToAvatars);

    void putArtistAvatar(UUID id, InputStream avatar, String pathToAvatars);

    void deleteArtistAvatar(UUID id, String pathToAvatars);

    void patchArtistAvatar(UUID id, InputStream avatar, String pathToAvatars);

}
