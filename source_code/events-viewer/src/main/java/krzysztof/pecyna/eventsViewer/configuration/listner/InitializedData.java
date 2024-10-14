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

        this.artistService = (ArtistService) event.getServletContext().getAttribute("artistService");

        init();
    }

    @SneakyThrows
    private void init() {
        Artist artist1 = Artist.builder()
                .id(UUID.fromString("396457c5-3f20-49f1-8a7c-755a8d2f0b8d"))
                .firstName("Kuba")
                .firstName("Grabowski")
                .nickName("Quebonafide")
                .build();

        Artist artist2 = Artist.builder()
                .id(UUID.fromString("809a330f-132b-4af2-a756-1ef619f99416"))
                .firstName("Abel")
                .lastName("Tesfaye")
                .nickName("The Weeknd")
                .build();

        Artist artist3 = Artist.builder()
                .id(UUID.fromString("1c2b08f8-8e3a-44f2-b7a6-bdf119c1a111"))
                .firstName("Benito")
                .lastName("Ocasio")
                .nickName("Bad Bunny")
                .build();

        Artist artist4 = Artist.builder()
                .id(UUID.fromString("18a7acce-a056-44c5-b4cb-e20e65bb84cb"))
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
