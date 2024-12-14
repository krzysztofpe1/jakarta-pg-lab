package krzysztof.pecyna.eventsViewer.configuration;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.artist.entity.UserRoles;
import krzysztof.pecyna.eventsViewer.artist.repository.api.ArtistRepository;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@NoArgsConstructor(force = true)
public class InitializeAdminService {

    private final ArtistRepository artistRepository;

    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public InitializeAdminService(
            ArtistRepository artistRepository,@SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash
    ) {
        this.artistRepository = artistRepository;
        this.passwordHash = passwordHash;
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        if (artistRepository.findByLastName("admin-service").isEmpty()) {

            Artist admin = Artist.builder()
                    .id(UUID.fromString("d9f823f4-f057-4f18-aeb7-b6654bc3d000"))
                    .lastName("admin-service")
                    .password(passwordHash.generate("adminadmin".toCharArray()))
                    .roles(List.of(UserRoles.ADMIN, UserRoles.USER))
                    .build();

            artistRepository.create(admin);
        }
    }

}
