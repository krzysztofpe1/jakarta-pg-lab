package krzysztof.pecyna.eventsViewer.performance.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import krzysztof.pecyna.eventsViewer.component.ModelFunctionFactory;
import krzysztof.pecyna.eventsViewer.location.model.LocationModel;
import krzysztof.pecyna.eventsViewer.location.service.LocationService;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.performance.model.PerformanceEditModel;
import krzysztof.pecyna.eventsViewer.performance.service.PerformanceService;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class PerformanceEdit implements Serializable {

    private final PerformanceService performanceService;

    private final ModelFunctionFactory factory;

    private final LocationService locationService;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private PerformanceEditModel performance;

    @Setter
    @Getter
    private UUID initialLocation;

    @Setter
    @Getter
    private List<LocationModel> locations;

    @Inject
    public PerformanceEdit(PerformanceService performanceService, ModelFunctionFactory factory, LocationService locationService) {
        this.performanceService = performanceService;
        this.factory = factory;
        this.locationService = locationService;
    }

    public void init() throws IOException {
        Optional<Performance> performance = performanceService.find(id);
        if (performance.isPresent()) {
            this.performance = factory.performanceToEditModel().apply(performance.get());
            this.initialLocation = this.performance.getLocation().getId();
            this.locations = locationService.findAll().stream().map(factory.locationToModel()).toList();
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Performance not found!!!");
        }
    }

    public String saveAction() {
        if (performance.getPerformanceType() == null) {
            return null;
        }
        performanceService.update(factory.updatePerformance().apply(performanceService.find(id).orElseThrow(), performance), initialLocation);
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}
