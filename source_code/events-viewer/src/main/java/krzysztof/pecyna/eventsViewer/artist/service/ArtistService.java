package krzysztof.pecyna.eventsViewer.artist.service;

import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.artist.repository.api.ArtistRepository;
import krzysztof.pecyna.eventsViewer.controller.servlet.exception.NotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.inject.Inject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import jakarta.enterprise.context.ApplicationScoped;
import krzysztof.pecyna.eventsViewer.controller.servlet.exception.AlreadyExistsException;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.performance.service.PerformanceService;

@ApplicationScoped
public class ArtistService {
    private final ArtistRepository artistRepository;

    private final PerformanceService performanceService;

    @Inject
    public ArtistService(ArtistRepository repository, PerformanceService performanceService) {
        this.artistRepository = repository;
        this.performanceService = performanceService;
    }

    public ArtistService() {
        this.artistRepository = null;
        this.performanceService = null;
    }

    public Optional<Artist> find(UUID id) {
        return artistRepository.find(id);
    }

    public Optional<Artist> find(String name) {
        return artistRepository.findByName(name);
    }

    public List<Artist> findAll() {
        return artistRepository.findAll();
    }

    public void create(Artist artist) {
        artistRepository.create(artist);
    }

    public void update(Artist artist) {
        artistRepository.update(artist);
    }

    public void delete(UUID id) {
        Artist artist = artistRepository.find(id).orElseThrow(NotFoundException::new);
        Optional<List<Performance>> performancesToDelete = performanceService.findAllByArtist(id);
        performancesToDelete.ifPresent(performances -> performances.forEach(performance -> {
            performanceService.delete(performance.getId());
        }));
        artistRepository.delete(artist);

    }

    public void createAvatar(UUID id, InputStream avatar, String pathToAvatars) throws AlreadyExistsException {
        artistRepository.find(id).ifPresent(artist -> {
            try {
                Path destinationPath = Path.of(pathToAvatars, id.toString() + ".png");
                if (Files.exists(destinationPath)) {
                    throw new AlreadyExistsException("Avatar already exists, to update avatar use PATCH method");
                }
                Files.copy(avatar, destinationPath);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });

    }

    public void updateAvatar(UUID id, InputStream avatar, String pathToAvatars) {
        artistRepository.find(id).ifPresent(artist -> {
            try {
                Path existingPath = Path.of(pathToAvatars, id.toString() + ".png");
                if (Files.exists(existingPath)) {
                    Files.copy(avatar, existingPath, StandardCopyOption.REPLACE_EXISTING);
                } else {
                    throw new NotFoundException("Artist avatar not found, to create avatar use PUT method");
                }
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });

    }

}
