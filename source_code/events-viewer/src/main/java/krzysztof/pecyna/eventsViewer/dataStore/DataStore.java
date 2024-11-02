package krzysztof.pecyna.eventsViewer.dataStore;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.serialization.CloningUtility;
import lombok.extern.java.Log;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
@ApplicationScoped
public class DataStore {

    private final Set<Artist> artists = new HashSet<>();

    private final Set<Performance> performances = new HashSet<>();

    private final Set<Location> locations = new HashSet<>();

    private final CloningUtility cloningUtility;

    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    public DataStore() {
        this.cloningUtility = null;
    }

    // Artist entity
    public synchronized List<Artist> findAllArtists() {
        return artists.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createArtist(Artist value) throws IllegalArgumentException {
        if (artists.stream().anyMatch(artist -> artist.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The artist id \"%s\" is not unique".formatted(value.getId()));
        }
        artists.add(cloningUtility.clone(value));
    }

    public synchronized void updateArtist(Artist value) throws IllegalArgumentException {
        if (artists.removeIf(artist -> artist.getId().equals(value.getId()))) {
            artists.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The artist with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteArtist(UUID id) throws IllegalArgumentException {
        if (!artists.removeIf(artist -> artist.getId().equals(id))) {
            throw new IllegalArgumentException("The artist with id \"%s\" does not exist".formatted(id));
        }
    }

    //Performance entity
    public synchronized List<Performance> findAllPerformances() {
        return performances.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createPerformance(Performance value) throws IllegalArgumentException {
        if (performances.stream().anyMatch(performance -> performance.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The performance id \"%s\" is not unique".formatted(value.getId()));
        }
        performances.add(cloningUtility.clone(value));
    }

    public synchronized void updatePerformance(Performance value) throws IllegalArgumentException {
        if (performances.removeIf(performance -> performance.getId().equals(value.getId()))) {
            performances.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The performance with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deletePerformance(UUID id) throws IllegalArgumentException {
        if (!performances.removeIf(performance -> performance.getId().equals(id))) {
            throw new IllegalArgumentException("The performance with id \"%s\" does not exist".formatted(id));
        }
    }

    //Location entity
    public synchronized List<Location> findAllLocations() {
        return locations.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createLocation(Location value) throws IllegalArgumentException {
        if (locations.stream().anyMatch(location -> location.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The location id \"%s\" is not unique".formatted(value.getId()));
        }
        locations.add(cloningUtility.clone(value));
    }

    public synchronized void updateLocation(Location value) throws IllegalArgumentException {
        if (locations.removeIf(location -> location.getId().equals(value.getId()))) {
            locations.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The location with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    public synchronized void deleteLocation(UUID id) throws IllegalArgumentException {
        if (!locations.removeIf(location -> location.getId().equals(id))) {
            throw new IllegalArgumentException("The location with id \"%s\" does not exist".formatted(id));
        }else {
            performances.removeIf(unit -> unit.getLocation().getId().equals(id));
        }
    }
}
