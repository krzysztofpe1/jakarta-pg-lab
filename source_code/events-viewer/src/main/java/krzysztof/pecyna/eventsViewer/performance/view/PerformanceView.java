package krzysztof.pecyna.eventsViewer.performance.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import krzysztof.pecyna.eventsViewer.component.ModelFunctionFactory;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.performance.model.PerformanceModel;
import krzysztof.pecyna.eventsViewer.performance.service.PerformanceService;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@ViewScoped
@Named
public class PerformanceView implements Serializable {
    private final PerformanceService performanceService;

    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private PerformanceModel performance;

    @Inject
    public PerformanceView(PerformanceService performanceService, ModelFunctionFactory factory) {
        this.performanceService = performanceService;
        this.factory = factory;
    }

    public void init() throws IOException {
        Optional<Performance> performance = performanceService.find(id);
        if (performance.isPresent()) {
            this.performance = factory.performanceToModel().apply(performance.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Performance not found!!!");
        }
    }


}
