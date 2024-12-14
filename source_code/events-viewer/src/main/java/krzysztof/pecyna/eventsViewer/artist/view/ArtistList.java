package krzysztof.pecyna.eventsViewer.artist.view;


import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import krzysztof.pecyna.eventsViewer.artist.model.ArtistsModel;
import krzysztof.pecyna.eventsViewer.artist.service.ArtistService;
import krzysztof.pecyna.eventsViewer.component.ModelFunctionFactory;
import lombok.NoArgsConstructor;

@RequestScoped
@Named
@NoArgsConstructor(force = true)
public class ArtistList {
    private final ArtistService service;

    private ArtistsModel artists;

    private final ModelFunctionFactory factory;

    public ArtistList(ArtistService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }
    public ArtistsModel getArtists() {
        if (artists == null) {
            artists = factory.artistsToModel().apply(service.findAll());
        }
        return artists;
    }
    public String deleteAction(ArtistsModel.Artist artist) {
        service.delete(artist.getId());
        return "artist_list?faces-redirect=true";
    }
}