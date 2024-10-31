package krzysztof.pecyna.eventsViewer.component;

import jakarta.enterprise.context.ApplicationScoped;
import krzysztof.pecyna.eventsViewer.artist.dto.function.ArtistToResponseFunction;
import krzysztof.pecyna.eventsViewer.artist.dto.function.ArtistsToResponseFunction;
import krzysztof.pecyna.eventsViewer.artist.dto.function.RequestToArtistFunction;
import krzysztof.pecyna.eventsViewer.artist.dto.function.UpdateArtistWithRequestFunction;
import krzysztof.pecyna.eventsViewer.location.dto.function.LocationToResponseFunction;
import krzysztof.pecyna.eventsViewer.location.dto.function.LocationsToResponseFunction;
import krzysztof.pecyna.eventsViewer.location.dto.function.RequestToLocationFunction;
import krzysztof.pecyna.eventsViewer.location.dto.function.UpdateLocationWithRequestFunction;
import krzysztof.pecyna.eventsViewer.performance.dto.function.PerformanceToResponseFunction;
import krzysztof.pecyna.eventsViewer.performance.dto.function.PerformancesToResponseFunction;
import krzysztof.pecyna.eventsViewer.performance.dto.function.RequestToPerformanceFunction;
import krzysztof.pecyna.eventsViewer.performance.dto.function.UpdatePerformanceWithRequestFunction;

@ApplicationScoped
public class DtoFunctionFactory {

    // Artist entity
    public ArtistToResponseFunction artistToResponse() {
        return new ArtistToResponseFunction();
    }

    public ArtistsToResponseFunction artistsToResponse() {
        return new ArtistsToResponseFunction();
    }

    public RequestToArtistFunction requestToArtist() {
        return new RequestToArtistFunction();
    }

    public UpdateArtistWithRequestFunction updateArtist() {
        return new UpdateArtistWithRequestFunction();
    }

    // Performance entity
    public PerformancesToResponseFunction performancesToResponse() {
        return new PerformancesToResponseFunction();
    }

    public PerformanceToResponseFunction performanceToResponse() {
        return new PerformanceToResponseFunction();
    }

    public RequestToPerformanceFunction requestToPerformance() {
        return new RequestToPerformanceFunction();
    }

    public UpdatePerformanceWithRequestFunction updatePerformance() {
        return new UpdatePerformanceWithRequestFunction();
    }

    // Location entity
    public LocationsToResponseFunction locationsToResponse() {
        return new LocationsToResponseFunction();
    }

    public LocationToResponseFunction locationToResponse() {
        return new LocationToResponseFunction();
    }

    public RequestToLocationFunction requestToLocation() {
        return new RequestToLocationFunction();
    }

    public UpdateLocationWithRequestFunction updateLocation() {
        return new UpdateLocationWithRequestFunction();
    }
}