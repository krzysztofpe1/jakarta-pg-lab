package krzysztof.pecyna.eventsViewer.performance.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.performance.repository.api.PerformanceRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class PerformancePersistenceRepository implements PerformanceRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Performance> findAllByArtist(Artist artist) {
        return em.createQuery("select u from Performance u where u.artist = :artist", Performance.class)
                .setParameter("artist", artist)
                .getResultList();

    }

    @Override
    public List<Performance> findAllByLocation(Location location) {
        return em.createQuery("select u from Performance u where u.location = :location", Performance.class)
                .setParameter("location", location)
                .getResultList();
    }

    @Override
    public Optional<Performance> findByIdAndArtist(UUID id, Artist artist) {
        try {
            return Optional.of(em.createQuery("select c from Performance c where c.id = :id and c.artist = :artist", Performance.class)
                    .setParameter("artist", artist)
                    .setParameter("id", id)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Performance> find(UUID id) {
        return Optional.ofNullable(em.find(Performance.class, id));
    }

    @Override
    public List<Performance> findAll() {
        return em.createQuery("select u from Performance u", Performance.class).getResultList();
    }

    @Override
    public void create(Performance entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Performance entity) {
        em.remove(em.find(Performance.class, entity.getId()));
    }

    @Override
    public void update(Performance entity) {
        em.merge(entity);
    }
}
