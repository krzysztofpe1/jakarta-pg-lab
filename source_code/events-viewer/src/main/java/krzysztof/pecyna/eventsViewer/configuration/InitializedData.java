package krzysztof.pecyna.eventsViewer.configuration;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.servlet.ServletContextListener;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.artist.entity.UserRoles;
import krzysztof.pecyna.eventsViewer.artist.service.ArtistService;
import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.location.entity.LocationType;
import krzysztof.pecyna.eventsViewer.location.service.LocationService;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.performance.entity.PerformanceType;
import krzysztof.pecyna.eventsViewer.performance.service.PerformanceService;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class InitializedData implements ServletContextListener {

    private ArtistService artistService;

    private PerformanceService performanceService;

    private LocationService locationService;

    @Inject
    private SecurityContext securityContext;

    @EJB
    public void setArtistService(ArtistService artistService) {
        this.artistService = artistService;
    }

    @EJB
    public void setPerformanceService(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @EJB
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }


    @PostConstruct
    @SneakyThrows
    private void init() {
        if (artistService.find("Grabowski").isEmpty()) {
            Performance performance1 = Performance.builder()
                    .id(UUID.fromString("396457c5-3f20-49f1-8a7c-755a8d2f0b20"))
                    .date(LocalDate.of(2024, Month.SEPTEMBER, 14))
                    .performanceType(PerformanceType.CONCERT)
                    .build();

            Performance performance2 = Performance.builder()
                    .id(UUID.fromString("396457c5-3f20-49f1-8a7c-755a8d2f0b21"))
                    .date(LocalDate.of(2022, Month.JANUARY, 12))
                    .performanceType(PerformanceType.COMEDY_SHOW)
                    .build();
            Performance performance3 = Performance.builder()
                    .id(UUID.fromString("396457c5-3f20-49f1-8a7c-755a8d2f0b22"))
                    .date(LocalDate.of(2015, Month.FEBRUARY, 2))
                    .performanceType(PerformanceType.COMEDY_SHOW)
                    .build();

            Performance performance4 = Performance.builder()
                    .id(UUID.fromString("396457c5-3f20-49f1-8a7c-755a8d2f0b23"))
                    .date(LocalDate.of(2011, Month.MAY, 30))
                    .performanceType(PerformanceType.CONCERT)
                    .build();


            Artist artist1 = Artist.builder()
                    .id(UUID.fromString("396457c5-3f20-49f1-8a7c-755a8d2f0b10"))
                    .firstName("Kuba")
                    .lastName("Grabowski")
                    .password("Grabowski")
                    .accountCreation(LocalDate.of(2020, Month.JANUARY, 8))
                    .roles(List.of(UserRoles.ADMIN, UserRoles.USER))
                    .performances(Collections.emptyList())
                    .build();

            Artist artist2 = Artist.builder()
                    .id(UUID.fromString("396457c5-3f20-49f1-8a7c-755a8d2f0b11"))
                    .firstName("Abel")
                    .lastName("Tesfaye")
                    .password("Tesfaye")
                    .accountCreation(LocalDate.of(2021, Month.JULY, 2))
                    .roles(List.of(UserRoles.USER))
                    .performances(Collections.emptyList())
                    .build();

            Artist artist3 = Artist.builder()
                    .id(UUID.fromString("396457c5-3f20-49f1-8a7c-755a8d2f0b12"))
                    .firstName("Benito")
                    .lastName("Ocasio")
                    .password("Ocasio")
                    .accountCreation(LocalDate.of(2022, Month.MAY, 30))
                    .roles(List.of(UserRoles.USER))
                    .performances(Collections.emptyList())
                    .build();

            Artist artist4 = Artist.builder()
                    .id(UUID.fromString("396457c5-3f20-49f1-8a7c-755a8d2f0b13"))
                    .firstName("Arianna")
                    .lastName("Grande")
                    .password("Grande")
                    .accountCreation(LocalDate.of(2024, Month.SEPTEMBER, 14))
                    .roles(List.of(UserRoles.USER))
                    .performances(Collections.emptyList())
                    .build();

            Location location1 = Location.builder()
                    .id(UUID.fromString("396457c5-3f20-49f1-8a7c-755a8d2f0b30"))
                    .maximumAudienceCapacity(45000)
                    .streetAddress("Gdansk")
                    .locationType(LocationType.STADIUM)
                    .performances(Collections.emptyList())
                    .build();

            Location location2 = Location.builder()
                    .id(UUID.fromString("396457c5-3f20-49f1-8a7c-755a8d2f0b31"))
                    .maximumAudienceCapacity(22000)
                    .streetAddress("Gdynia")
                    .locationType(LocationType.FIELD)
                    .performances(Collections.emptyList())
                    .build();

            performance1.setArtist(artist1);
            performance1.setLocation(location1);

            performance2.setArtist(artist1);
            performance2.setLocation(location2);

            performance3.setArtist(artist2);
            performance3.setLocation(location1);

            performance4.setArtist(artist3);
            performance4.setLocation(location2);

            artistService.create(artist1);
            artistService.create(artist2);
            artistService.create(artist3);
            artistService.create(artist4);

            locationService.create(location1);
            locationService.create(location2);

            performanceService.create(performance1, artist1.getId(), location1.getId());
            performanceService.create(performance2, artist1.getId(), location2.getId());
            performanceService.create(performance3, artist2.getId(), location1.getId());
            performanceService.create(performance4, artist3.getId(), location2.getId());
        }
    }
}
