package krzysztof.pecyna.eventsViewer.component;

import jakarta.ws.rs.NotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class AvatarService {

    private static final String avatarsDirectoryPath = "avatars";

    public void createAvatar(UUID artistId, InputStream is){
        try{
            Path pathToAvatar = getAvatarURI(artistId);
            if(Files.exists(pathToAvatar))
                throw new IllegalArgumentException("Avatar already exists");

            Files.copy(is, pathToAvatar, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new IllegalStateException("Could not create avatar", ex);
        }
    }

    public void updateAvatar(UUID artistId, InputStream is){
        try{
            Path pathToAvatar = getAvatarURI(artistId);
            if(!Files.exists(pathToAvatar))
                throw new IllegalArgumentException("Avatar does not exist");

            Files.copy(is, pathToAvatar, StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException ex) {
            throw new IllegalStateException("Could not update avatar", ex);
        }
    }

    public void deleteAvatar(UUID artistId){
        try {
            Path pathToAvatar = getAvatarURI(artistId);
            if(!Files.exists(pathToAvatar))
                throw new IllegalArgumentException("Avatar does not exist");

            Files.delete(pathToAvatar);
        }
        catch (IOException ex) {
            throw new IllegalStateException("Could not delete avatar", ex);
        }
    }

    public byte[] getAvatar(UUID artistId){
        try{
            Path pathToAvatar = getAvatarURI(artistId);

            if(!Files.exists(pathToAvatar))
                throw new NotFoundException("Avatar does not exist");

            return Files.readAllBytes(pathToAvatar);
        }
        catch (IOException ex) {
            throw new IllegalStateException("Could not get avatar", ex);
        }
    }

    private Path getAvatarURI(UUID artistId){
        return Path.of(avatarsDirectoryPath, artistId.toString()+".png");
    }

}
