package krzysztof.pecyna.eventsViewer.component;

import jakarta.enterprise.context.ApplicationScoped;
import krzysztof.pecyna.eventsViewer.location.model.function.*;
import krzysztof.pecyna.eventsViewer.performance.model.function.UpdatePerformanceWithModelFunction;
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
}
