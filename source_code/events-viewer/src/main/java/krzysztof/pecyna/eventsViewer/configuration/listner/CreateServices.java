package krzysztof.pecyna.eventsViewer.configuration.listner;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import krzysztof.pecyna.eventsViewer.artist.repository.api.ArtistRepository;
import krzysztof.pecyna.eventsViewer.artist.repository.memory.ArtistInMemoryRepository;
import krzysztof.pecyna.eventsViewer.artist.service.ArtistService;
import krzysztof.pecyna.eventsViewer.component.AvatarService;
import krzysztof.pecyna.eventsViewer.dataStone.component.DataStore;
import lombok.SneakyThrows;

public class CreateServices implements ServletContextListener {

    @SneakyThrows
    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataStore = (DataStore) event.getServletContext().getAttribute("dataSource");

        ArtistRepository artistRepository = new ArtistInMemoryRepository(dataStore);
        String avatarsDirPath = event.getServletContext().getInitParameter("avatarsDirectoryPath");
        AvatarService avatarService = new AvatarService(avatarsDirPath);

        event.getServletContext().setAttribute("artistService", new ArtistService(artistRepository, avatarService));
    }

}
