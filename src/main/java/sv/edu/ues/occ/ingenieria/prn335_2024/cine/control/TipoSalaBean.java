package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class TipoSalaBean extends AbstractDataPersist<TipoSala> implements Serializable {

    @PersistenceContext(unitName = "cinePU")
    private EntityManager em;

    public TipoSalaBean() {
        super(TipoSala.class);
    }

    @Override
    public void create(TipoSala registro) throws IllegalStateException, IllegalArgumentException {
        super.create(registro);
    }

    @Override
    public List<TipoSala> findRange(int first, int max) {
        return em.createQuery("SELECT t FROM TipoSala t", TipoSala.class)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    public TipoSala findById(final Integer idTipoSala) {
        if (idTipoSala == null || idTipoSala <= 0) {
            throw new IllegalArgumentException("ID de tipo de sala no válido");
        }
        return em.find(TipoSala.class, idTipoSala);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<TipoSala> findAll() {
        return getEntityManager().createNamedQuery("TipoSala.findAll", TipoSala.class).getResultList();
    }

    public List<TipoSala> findByName(String nombre) {
        return getEntityManager()
                .createNamedQuery("TipoSala.findByName", TipoSala.class)
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList();
    }

    public Long countActive() {
        return getEntityManager()
                .createNamedQuery("TipoSala.countActive", Long.class)
                .getSingleResult();
    }

    public List<TipoSala> findByExpresionRegular(String expresionRegular) {
        return getEntityManager()
                .createNamedQuery("TipoSala.findByExpresionRegular", TipoSala.class)
                .setParameter("expresionRegular", expresionRegular)
                .getResultList();
    }
}
