package krzysztof.pecyna.eventsViewer.configuration.listner;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import krzysztof.pecyna.eventsViewer.artist.dto.GetArtistsResponse;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.artist.service.ArtistService;
import lombok.SneakyThrows;

import java.util.UUID;

public class InitializedData implements ServletContextListener {

    private ArtistService artistService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ArtistService artistService = (ArtistService) event.getServletContext().getAttribute("artistService");

        this.artistService = artistService;

        init();
    }

    @SneakyThrows
    private void init() {
        Artist artist1 = Artist.builder()
                .id(UUID.randomUUID())
                .firstName("Kuba")
                .firstName("Grabowski")
                .nickName("Quebonafide")
                .build();

        Artist artist2 = Artist.builder()
                .id(UUID.randomUUID())
                .firstName("Abel")
                .lastName("Tesfaye")
                .nickName("The Weeknd")
                .build();

        Artist artist3 = Artist.builder()
                .id(UUID.randomUUID())
                .firstName("Benito")
                .lastName("Ocasio")
                .nickName("Bad Bunny")
                .build();

        Artist artist4 = Artist.builder()
                .id(UUID.randomUUID())
                .firstName("Arianna")
                .lastName("Grande")
                .nickName("Arianna Grande")
                .build();

        artistService.create(artist1);
        artistService.create(artist2);
        artistService.create(artist3);
        artistService.create(artist4);
    }

}
