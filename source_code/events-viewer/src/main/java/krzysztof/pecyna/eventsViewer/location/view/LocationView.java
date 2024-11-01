package krzysztof.pecyna.eventsViewer.location.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import krzysztof.pecyna.eventsViewer.component.ModelFunctionFactory;
import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.location.model.LocationModel;
import krzysztof.pecyna.eventsViewer.location.service.LocationService;
import krzysztof.pecyna.eventsViewer.performance.service.PerformanceService;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class LocationView implements Serializable {
    private final LocationService locationService;

    private final ModelFunctionFactory factory;

    private final PerformanceService performanceService;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private LocationModel location;

    @Inject
    public LocationView(LocationService locationService, ModelFunctionFactory factory, PerformanceService performanceService) {
        this.locationService = locationService;
        this.factory = factory;
        this.performanceService = performanceService;
    }

    public void init() throws IOException {
        Optional<Location> location = locationService.find(id);
        if (location.isPresent()) {
            this.location = factory.locationToModel().apply(location.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Location not found!!!");
        }
    }

    public String deletePerformance(UUID userId) {
        performanceService.delete(userId);
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}
