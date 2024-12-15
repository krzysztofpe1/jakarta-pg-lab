package krzysztof.pecyna.eventsViewer.performance.repository.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.location.entity.Location;
import krzysztof.pecyna.eventsViewer.performance.entity.Performance;
import krzysztof.pecyna.eventsViewer.performance.repository.api.PerformanceRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PerformancePersistenceRepository implements PerformanceRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Performance> findAllByArtist(Artist artist) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Performance> cq = cb.createQuery(Performance.class);
        Root<Performance> root = cq.from(Performance.class);
        cq.select(root).where(cb.equal(root.get("artist"), artist));

        return em.createQuery(cq).getResultList();

    }

    @Override
    public List<Performance> findAllByLocation(UUID location) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Performance> cq = cb.createQuery(Performance.class);
        Root<Performance> root = cq.from(Performance.class);
        cq.select(root).where(cb.equal(root.get("location").get("id"), location));

        return em.createQuery(cq).getResultList();
    }

    @Override
    public Optional<Performance> findByIdAndArtist(UUID id, Artist artist) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Performance> cq = cb.createQuery(Performance.class);
        Root<Performance> root = cq.from(Performance.class);
        cq.select(root)
                .where(cb.equal(root.get("id"), id), cb.equal(root.get("artist"), artist));

        try {
            return Optional.of(em.createQuery(cq).getSingleResult());
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
    public void detach(Performance entity) {
        em.detach(entity);
    }

    @Override
    public void update(Performance entity) {
        em.merge(entity);
    }
}
