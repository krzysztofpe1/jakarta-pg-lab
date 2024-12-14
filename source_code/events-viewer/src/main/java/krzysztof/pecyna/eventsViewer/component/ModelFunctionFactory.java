package krzysztof.pecyna.eventsViewer.component;

import jakarta.enterprise.context.ApplicationScoped;
import krzysztof.pecyna.eventsViewer.artist.model.function.ArtistToModelFunction;
import krzysztof.pecyna.eventsViewer.artist.model.function.ArtistsToModelFunction;
import krzysztof.pecyna.eventsViewer.location.model.function.*;
import krzysztof.pecyna.eventsViewer.performance.model.function.*;

@ApplicationScoped
public class ModelFunctionFactory {

    public LocationToModelFunction locationToModel() {
        return new LocationToModelFunction();
    }

    public LocationsToModelFunction locationsToModel() {
        return new LocationsToModelFunction();
    }

    public LocationToEditModelFunction locationToEditModel() {
        return new LocationToEditModelFunction();
    }

    public ModelToLocationFunction modelToLocation() {
        return new ModelToLocationFunction();
    }

    public PerformanceToModelFunction performanceToModel() {
        return new PerformanceToModelFunction();
    }

    public PerformanceToEditModelFunction performanceToEditModel() {
        return new PerformanceToEditModelFunction();
    }

    public ModelToPerformanceFunction modelToPerformance() {
        return new ModelToPerformanceFunction();
    }

    public UpdatePerformanceWithModelFunction updatePerformance() {
        return new UpdatePerformanceWithModelFunction();
    }

    public PerformancesToModelFunction performancesToModel(){
        return new PerformancesToModelFunction();
    }

    public ArtistToModelFunction artistToModel() {
        return new ArtistToModelFunction();
    }
    public ArtistsToModelFunction artistsToModel() {
        return new ArtistsToModelFunction();
    }
}
