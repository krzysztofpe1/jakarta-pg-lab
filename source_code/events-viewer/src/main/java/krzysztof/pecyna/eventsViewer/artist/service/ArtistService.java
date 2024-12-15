package krzysztof.pecyna.eventsViewer.artist.service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.artist.entity.UserRoles;
import krzysztof.pecyna.eventsViewer.artist.repository.api.ArtistRepository;
import jakarta.ws.rs.NotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.inject.Inject;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import jakarta.ws.rs.NotAllowedException;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.performance.service.PerformanceService;
import lombok.NoArgsConstructor;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class ArtistService {
    private final ArtistRepository artistRepository;

    private final PerformanceService performanceService;

    private final Pbkdf2PasswordHash passwordHash;

    private final SecurityContext securityContext;

    @Inject
    public ArtistService(ArtistRepository repository, PerformanceService performanceService, @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash, SecurityContext securityContext) {
        this.artistRepository = repository;
        this.performanceService = performanceService;
        this.passwordHash = passwordHash;
        this.securityContext = securityContext;
    }
    @PermitAll
    public Optional<Artist> find(UUID id) {
        return artistRepository.find(id);
    }
    @PermitAll
    public Optional<Artist> find(String name) {
        return artistRepository.findByLastName(name);
    }
    @PermitAll
    public List<Artist> findAll() {
        return artistRepository.findAll();
    }

    @PermitAll
    public void create(Artist artist) {
        artist.setPassword(passwordHash.generate(artist.getPassword().toCharArray()));
        artistRepository.create(artist);
    }

    @PermitAll
    public void update(Artist artist) {
        artistRepository.update(artist);
    }

    @RolesAllowed(UserRoles.ADMIN)
    public void delete(UUID id) {
        Artist artist = artistRepository.find(id).orElseThrow(NotFoundException::new);
        Optional<List<Performance>> performancesToDelete = performanceService.findAllByArtist(id);
        performancesToDelete.ifPresent(performances -> performances.forEach(performance -> {
            performanceService.delete(performance.getId());
        }));
        artistRepository.delete(artist);

    }
    @PermitAll
    public void createAvatar(UUID id, InputStream avatar, String pathToAvatars) throws NotAllowedException {
        artistRepository.find(id).ifPresent(artist -> {
            try {
                Path destinationPath = Path.of(pathToAvatars, id.toString() + ".png");
                if (Files.exists(destinationPath)) {
                    throw new NotAllowedException("Avatar already exists, to update avatar use PATCH method");
                }
                Files.copy(avatar, destinationPath);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });

    }

    @RolesAllowed(UserRoles.ADMIN)
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

    public Optional<Artist> findCallerPrincipal() {
        if (securityContext.getCallerPrincipal() != null) {
            if (securityContext.isCallerInRole("admin")) {
                System.out.println("Użytkownik jest administratorem.");
            } else {
                System.out.println("Użytkownik nie jest administratorem.");
            }
            return find(securityContext.getCallerPrincipal().getName());
        } else {
            return Optional.empty();
        }
    }

}
