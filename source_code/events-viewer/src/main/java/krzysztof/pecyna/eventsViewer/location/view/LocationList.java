package krzysztof.pecyna.eventsViewer.location.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import krzysztof.pecyna.eventsViewer.component.ModelFunctionFactory;
import krzysztof.pecyna.eventsViewer.location.model.LocationsModel;
import krzysztof.pecyna.eventsViewer.location.service.LocationService;

@RequestScoped
@Named
public class LocationList {
    private LocationService locationService;

    private final ModelFunctionFactory factory;

    private LocationsModel locations;


    @Inject
    public LocationList(ModelFunctionFactory factory) {
        this.factory = factory;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
    }


    @EJB
    public void setLocationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public LocationsModel getLocations() {
        if (locations == null) {
            locations = factory.locationsToModel().apply(locationService.findAll());
        }
        return locations;
    }

    public String deleteAction(LocationsModel.Location location) {
        locationService.delete(location.getId());
        return "location_list?faces-redirect=true";
    }
}
