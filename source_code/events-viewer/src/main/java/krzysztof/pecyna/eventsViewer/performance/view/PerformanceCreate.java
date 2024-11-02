package krzysztof.pecyna.eventsViewer.performance.view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.artist.service.ArtistService;
import krzysztof.pecyna.eventsViewer.component.ModelFunctionFactory;
import krzysztof.pecyna.eventsViewer.location.model.LocationModel;
import krzysztof.pecyna.eventsViewer.location.service.LocationService;
import krzysztof.pecyna.eventsViewer.performance.model.PerformanceCreateModel;
import krzysztof.pecyna.eventsViewer.performance.service.PerformanceService;
import lombok.Getter;
import lombok.Setter;


import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@ViewScoped
@Named
public class PerformanceCreate implements Serializable {

    private final PerformanceService performanceService;

    private final ModelFunctionFactory factory;

    private final ArtistService artistService;

    private final LocationService locationService;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private PerformanceCreateModel performance;

    @Setter
    @Getter
    private List<LocationModel> locations;

    private static final UUID TEMP_ARTIST_ID = UUID.fromString("396457c5-3f20-49f1-8a7c-755a8d2f0b10");

    @Inject
    public PerformanceCreate(PerformanceService performanceService, ModelFunctionFactory factory, ArtistService artistService, LocationService locationService) {
        this.performanceService = performanceService;
        this.factory = factory;
        this.artistService = artistService;
        this.locationService = locationService;
    }

    public void init() throws IOException {
        Artist tempArtist = this.artistService.find(TEMP_ARTIST_ID).get();
        this.performance = PerformanceCreateModel.builder().id(UUID.randomUUID()).artist(tempArtist).build();
        this.locations = locationService.findAll().stream().map(factory.locationToModel()).toList();
    }

    public String saveAction() {
        if (performance.getLocation() == null || performance.getDate() == null) {
            return null;
        }
        performanceService.create(factory.modelToPerformance().apply(performance), TEMP_ARTIST_ID, performance.getLocation().getId());
        return "/location/location_view.xhtml?faces-redirect=true&id=" + performance.getLocation().getId();

    }

}
