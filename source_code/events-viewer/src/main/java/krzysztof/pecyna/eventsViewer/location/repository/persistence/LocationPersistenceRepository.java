package krzysztof.pecyna.eventsViewer.location.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.location.repository.api.LocationRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class LocationPersistenceRepository implements LocationRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Location> find(UUID id) {
        return Optional.ofNullable(em.find(Location.class, id));
    }

    @Override
    public List<Location> findAll() {
        return em.createQuery("select f from Location f", Location.class).getResultList();
    }

    @Override
    public void create(Location entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Location entity) {
        em.remove(em.find(Location.class, entity.getId()));
    }

    @Override
    public void update(Location entity) {
        em.merge(entity);
    }
}
