package krzysztof.pecyna.eventsViewer.location.repository.persistence;

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
import krzysztof.pecyna.eventsViewer.location.repository.api.LocationRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class LocationPersistenceRepository implements LocationRepository {

    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Location> find(UUID id) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Location> cq = cb.createQuery(Location.class);
        Root<Location> root = cq.from(Location.class);
        cq.select(root).where(cb.equal(root.get("id"), id));

        try {
            return Optional.of(em.createQuery(cq).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Location> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Location> cq = cb.createQuery(Location.class);
        Root<Location> root = cq.from(Location.class);
        cq.select(root);

        return em.createQuery(cq).getResultList();
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
    @Override
    public void detach(Location entity) {
        em.detach(entity);
    }

}
