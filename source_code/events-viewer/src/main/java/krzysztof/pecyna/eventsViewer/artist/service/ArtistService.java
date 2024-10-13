package krzysztof.pecyna.eventsViewer.artist.service;

import jakarta.ws.rs.NotFoundException;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.artist.repository.api.ArtistRepository;
import krzysztof.pecyna.eventsViewer.component.AvatarService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ArtistService {

    private final ArtistRepository artistRepository;
    private final AvatarService avatarService;

    public ArtistService(final ArtistRepository artistRepository, AvatarService avatarService) {
        this.artistRepository = artistRepository;
        this.avatarService = avatarService;
    }

    public Optional<Artist> find(UUID id){
        return artistRepository.find(id);
    }

    public List<Artist> findAll(){
        return artistRepository.findAll();
    }

    public List<Artist> findByFirstName(String name){
        return artistRepository.findByFirstName(name);
    }

    public List<Artist> findByLastName(String name){
        return artistRepository.findByLastName(name);
    }

    public List<Artist> findByNickName(String nickName){
        return artistRepository.findByNickName(nickName);
    }

    public void create(Artist artist){
        artistRepository.create(artist);
    }

    public void update(Artist artist){
        artistRepository.update(artist);
    }

    public void delete(Artist artist){
        artistRepository.delete(artist);
    }

    public byte[] getAvatar(UUID id) {
        if(artistRepository.find(id).isEmpty())
            throw new NotFoundException();

        return avatarService.getAvatar(id);
    }

    public void createAvatar(UUID id, InputStream is) {
        artistRepository.find(id).ifPresentOrElse(
                artist -> {avatarService.createAvatar(id, is);},
                () -> { throw new NotFoundException("Artist not found");
                }
        );
    }

    public void updateAvatar(UUID id, InputStream is) {
        artistRepository.find(id).ifPresentOrElse(
                artist -> {avatarService.updateAvatar(id,is);},
                () -> { throw new NotFoundException("Artist not found");}
        );
    }

    public void deleteAvatar(UUID id){
        artistRepository.find(id).ifPresentOrElse(
                artist -> {avatarService.deleteAvatar(id);},
                () -> { throw new NotFoundException("Artist not found");}
        );
    }

}
