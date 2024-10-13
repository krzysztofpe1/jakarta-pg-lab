package krzysztof.pecyna.eventsViewer.dataStone.component;

import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.event.entity.Event;
import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.serialization.CloningUtility;
import lombok.extern.java.Log;

import java.util.*;
import java.util.stream.Collectors;

@Log
public class DataStore {

    private final Set<Artist> artists = new HashSet<>();

    private final Set<Performance> performances = new HashSet<>();

    private final Set<Event> events = new HashSet<>();

    private final Set<Location> locations = new HashSet<>();

    private final CloningUtility cloningUtility;

    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    public synchronized List<Artist> findAllArtists() {
        return artists.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized List<Performance> findAllPerformances() {
        return performances.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized List<Event> findAllEvents() {
        return  events.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized List<Location> findAllLocations() {
        return locations.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createArtist(Artist artist) throws IllegalArgumentException{
        if(artists.stream().anyMatch(x->x.getId().equals(artist.getId())))
            throw new IllegalArgumentException("Artist already exists");

        artists.add(cloningUtility.clone(artist));
    }

    public synchronized void updateArtist(Artist artist) throws IllegalArgumentException{
        if(artists.removeIf(x->x.getId().equals(artist.getId())))
            artists.add(cloningUtility.clone(artist));
        else
            throw new IllegalArgumentException("Artist does not exist");
    }

    public synchronized void deleteArtist(UUID id) throws IllegalArgumentException{
        if(!artists.removeIf(x->x.getId().equals(id)))
            throw new IllegalArgumentException("Artist does not exist");
    }

    //the rest of methods to be implemented on next labs

}
