package krzysztof.pecyna.eventsViewer.configuration.listner;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import krzysztof.pecyna.eventsViewer.dataStone.component.DataStore;
import krzysztof.pecyna.eventsViewer.serialization.CloningUtility;

public class CreateDataSource implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute("dataSource", new DataStore(new CloningUtility()));
    }

}
