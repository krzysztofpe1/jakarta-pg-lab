package krzysztof.pecyna.eventsViewer.configuration.listner;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import krzysztof.pecyna.eventsViewer.artist.repository.api.ArtistRepository;
import krzysztof.pecyna.eventsViewer.artist.repository.memory.ArtistInMemoryRepository;
import krzysztof.pecyna.eventsViewer.artist.service.ArtistService;
import krzysztof.pecyna.eventsViewer.component.AvatarService;
import krzysztof.pecyna.eventsViewer.dataStone.component.DataStore;

public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataStore = (DataStore) event.getServletContext().getAttribute("dataStore");

        ArtistRepository artistRepository = new ArtistInMemoryRepository(dataStore);
        AvatarService avatarService = new AvatarService();

        event.getServletContext().setAttribute("artistService", new ArtistService(artistRepository, avatarService));
    }

}
