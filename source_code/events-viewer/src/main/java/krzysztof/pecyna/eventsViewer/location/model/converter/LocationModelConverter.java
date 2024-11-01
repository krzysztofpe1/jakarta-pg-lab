package krzysztof.pecyna.eventsViewer.location.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import krzysztof.pecyna.eventsViewer.component.ModelFunctionFactory;
import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.location.model.LocationModel;
import krzysztof.pecyna.eventsViewer.location.service.LocationService;

import java.util.Optional;
import java.util.UUID;

@FacesConverter(forClass = LocationModel.class, managed = true)
public class LocationModelConverter implements Converter<LocationModel> {
    private final LocationService locationService;

    private final ModelFunctionFactory factory;

    @Inject
    public LocationModelConverter(LocationService locationService, ModelFunctionFactory factory) {
        this.locationService = locationService;
        this.factory = factory;
    }

    @Override
    public LocationModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Location> location = locationService.find(UUID.fromString(value));
        return location.map(factory.locationToModel()).orElse(null);

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, LocationModel value) {
        return value == null ? "" : value.getId().toString();


    }
}
