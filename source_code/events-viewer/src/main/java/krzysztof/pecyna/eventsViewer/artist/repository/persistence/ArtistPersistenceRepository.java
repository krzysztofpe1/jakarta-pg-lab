package krzysztof.pecyna.eventsViewer.artist.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import krzysztof.pecyna.eventsViewer.artist.entity.Artist;
import krzysztof.pecyna.eventsViewer.artist.repository.api.ArtistRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class ArtistPersistenceRepository implements ArtistRepository {
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Artist> findByName(String lastName) {
        try {
            return Optional.of(em.createQuery("select u from Artist u where u.lastName = :lastName", Artist.class)
                    .setParameter("lastName", lastName)
                    .getSingleResult());
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
        return em.createQuery("select u from Artist u", Artist.class).getResultList();
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
    public void update(Artist entity) {
        em.merge(entity);
    }
}
