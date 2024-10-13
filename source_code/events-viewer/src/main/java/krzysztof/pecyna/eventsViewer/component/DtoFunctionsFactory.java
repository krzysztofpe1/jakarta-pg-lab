package krzysztof.pecyna.eventsViewer.component;

import krzysztof.pecyna.eventsViewer.artist.dto.function.ArtistToResponseFunction;
import krzysztof.pecyna.eventsViewer.artist.dto.function.ArtistsToResponseFunction;
import krzysztof.pecyna.eventsViewer.artist.dto.function.RequestToArtistFunction;
import krzysztof.pecyna.eventsViewer.artist.dto.function.UpdateArtistWithRequestFunction;

public class DtoFunctionsFactory {

    public ArtistToResponseFunction artistToResponse(){
        return new ArtistToResponseFunction();
    }

    public ArtistsToResponseFunction artistsToResponse(){
        return new ArtistsToResponseFunction();
    }

    public RequestToArtistFunction requestToArtist(){
        return new RequestToArtistFunction();
    }

    public UpdateArtistWithRequestFunction updateArtist(){
        return new UpdateArtistWithRequestFunction();
    }

}
