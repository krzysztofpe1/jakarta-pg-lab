package krzysztof.pecyna.eventsViewer.performance.repository.api;

import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.repository.api.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PerformanceRepository extends Repository<Performance, UUID> {

    List<Performance> findAllByArtist(Artist artist);

    List<Performance> findAllByLocation(UUID location);

    Optional<Performance> findByIdAndArtist(UUID id, Artist artist);
}
