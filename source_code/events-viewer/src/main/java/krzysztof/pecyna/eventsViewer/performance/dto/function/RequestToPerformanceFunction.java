package krzysztof.pecyna.eventsViewer.performance.dto.function;


import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.performance.dto.PutPerformanceRequest;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;

import java.util.UUID;
import java.util.function.BiFunction;

public class RequestToPerformanceFunction implements BiFunction<UUID, PutPerformanceRequest, Performance> {
    @Override
    public Performance apply(UUID uuid, PutPerformanceRequest request) {
        return Performance.builder()
                .id(uuid)
                .duration(request.getDuration())
                .performanceType(request.getPerformanceType())
                .artist(Artist.builder().id(request.getArtist()).build())
                .location(Location.builder().id(request.getLocation()).build())
                .build();
    }
}
