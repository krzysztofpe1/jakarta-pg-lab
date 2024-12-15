package krzysztof.pecyna.eventsViewer.artist.view;


import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import krzysztof.pecyna.eventsViewer.artist.model.ArtistsModel;
import krzysztof.pecyna.eventsViewer.artist.service.ArtistService;
import krzysztof.pecyna.eventsViewer.component.ModelFunctionFactory;
import lombok.NoArgsConstructor;

@RequestScoped
@Named
@NoArgsConstructor(force = true)
public class ArtistList {
    private ArtistService service;

    private ArtistsModel artists;

    private final ModelFunctionFactory factory;

    @Inject
    public ArtistList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setFractionService(ArtistService service) {
        this.service = service;
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