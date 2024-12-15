package krzysztof.pecyna.eventsViewer.performance.view;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.TransactionalException;
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

    private PerformanceService performanceService;

    private final ModelFunctionFactory factory;

    private LocationService locationService;

    private final FacesContext facesContext;

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
    public PerformanceEdit( ModelFunctionFactory factory, FacesContext facesContext) {
        this.factory = factory;
        this.facesContext = facesContext;
    }

    @EJB
    public void setPerformanceService(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @EJB
    public void setLocationService(LocationService locationService) {
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

    public String saveAction() throws IOException {
        if (performance.getLocation() == null) {
            return null;
        }
        try {
            performanceService.update(factory.updatePerformance().apply(performanceService.findForCallerPrincipal(id).orElseThrow(), performance), initialLocation);
            String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
            return viewId + "?faces-redirect=true&includeViewParams=true";
        } catch (TransactionalException ex) {
            if (ex.getCause() instanceof OptimisticLockException) {
                init();
                facesContext.addMessage(null, new FacesMessage("Version collision."));
            }
            return null;
        }
    }
}
