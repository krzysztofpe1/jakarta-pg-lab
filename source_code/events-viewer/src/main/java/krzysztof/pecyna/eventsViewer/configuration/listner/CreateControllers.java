package krzysztof.pecyna.eventsViewer.configuration.listner;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import krzysztof.pecyna.eventsViewer.artist.controller.api.ArtistController;
import krzysztof.pecyna.eventsViewer.artist.controller.simple.ArtistSimpleController;
import krzysztof.pecyna.eventsViewer.artist.service.ArtistService;
import krzysztof.pecyna.eventsViewer.component.AvatarService;
import krzysztof.pecyna.eventsViewer.component.DtoFunctionsFactory;

public class CreateControllers  implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ArtistService artistService = (ArtistService) event.getServletContext().getAttribute("artistService");

        event.getServletContext().setAttribute("artistController", new ArtistSimpleController(artistService, new DtoFunctionsFactory()));

    }

}
