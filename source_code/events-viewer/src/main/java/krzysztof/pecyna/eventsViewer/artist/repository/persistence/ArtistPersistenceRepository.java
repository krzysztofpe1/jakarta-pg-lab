package krzysztof.pecyna.eventsViewer.artist.repository.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.artist.repository.api.ArtistRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ArtistPersistenceRepository implements ArtistRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Artist> findByLastName(String lastName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Artist> cq = cb.createQuery(Artist.class);
        Root<Artist> root = cq.from(Artist.class);
        cq.select(root).where(cb.equal(root.get("lastName"), lastName));

        try {
            return Optional.of(em.createQuery(cq).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }


    @Override
    public Optional<Artist> find(UUID id) {
        return Optional.ofNullable(em.find(Artist.class, id));
    }

    @Override
    public List<Artist> findAll() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Artist> cq = cb.createQuery(Artist.class);
        Root<Artist> root = cq.from(Artist.class);
        cq.select(root);

        return em.createQuery(cq).getResultList();
    }

    @Override
    public void create(Artist entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Artist entity) {
        em.remove(em.find(Artist.class, entity.getId()));
    }

    @Override
    public void detach(Artist entity) {
        em.detach(entity);
    }

    @Override
    public void update(Artist entity) {
        em.merge(entity);
    }
}
