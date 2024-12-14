package krzysztof.pecyna.eventsViewer.artist.model.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.artist.model.ArtistModel;
import krzysztof.pecyna.eventsViewer.artist.service.ArtistService;
import krzysztof.pecyna.eventsViewer.component.ModelFunctionFactory;

import java.util.Optional;
import java.util.UUID;
@FacesConverter(value = "artistConverter", forClass = ArtistModel.class, managed = true)
public class ArtistModelConverter implements Converter<ArtistModel> {

    private final ArtistService service;

    private final ModelFunctionFactory factory;

    @Inject
    public ArtistModelConverter(ArtistService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public ArtistModel getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("ArtistModelConverter.getAsObject");
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<Artist> artist = service.find(UUID.fromString(value));
        System.out.println("ArtistModelConverter.getAsObject");
        return artist.map(factory.artistToModel()).orElse(null);
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, ArtistModel value) {
        System.out.println("ArtistModelConverter.getAsString");
        return value == null ? "" : value.getId().toString();
    }
}