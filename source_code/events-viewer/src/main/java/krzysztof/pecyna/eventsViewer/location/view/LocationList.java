package krzysztof.pecyna.eventsViewer.location.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import krzysztof.pecyna.eventsViewer.component.ModelFunctionFactory;
import krzysztof.pecyna.eventsViewer.location.model.LocationsModel;
import krzysztof.pecyna.eventsViewer.location.service.LocationService;

@RequestScoped
@Named
public class LocationList {
    private final LocationService locationService;

    private final ModelFunctionFactory factory;

    private LocationsModel locations;


    @Inject
    public LocationList(LocationService locationService, ModelFunctionFactory factory) {
        this.locationService = locationService;
        this.factory = factory;
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
