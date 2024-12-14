package krzysztof.pecyna.eventsViewer.performance.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import krzysztof.pecyna.eventsViewer.component.ModelFunctionFactory;
import krzysztof.pecyna.eventsViewer.performance.model.PerformancesModel;
import krzysztof.pecyna.eventsViewer.performance.service.PerformanceService;

@RequestScoped
@Named
public class PerformanceList {
    private PerformanceService performanceService;

    private PerformancesModel performances;

    private final ModelFunctionFactory factory;

    @Inject
    public PerformanceList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    public PerformancesModel getPerformances() {
        if(performances == null) {
            performances = factory.performancesToModel().apply(performanceService.findAllForCallerPrincipal());
        }
        return performances;
    }

    @EJB
    public void setPerformanceService(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    public String deleteAction(PerformancesModel.Performance performance){
        performanceService.delete(performance.getId());
        return "performance_list?faces-redirect=true";
    }
}