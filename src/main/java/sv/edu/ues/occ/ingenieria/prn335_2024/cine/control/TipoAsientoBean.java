package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoAsiento;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class TipoAsientoBean extends AbstractDataPersist<TipoAsiento> implements Serializable {

    @PersistenceContext(unitName = "cinePU")
    private EntityManager em;

    public TipoAsientoBean() {
        super(TipoAsiento.class);
    }

    @Override
    public void create(TipoAsiento registro) throws IllegalStateException, IllegalArgumentException {
        super.create(registro);
    }

    @Override
    public List<TipoAsiento> findRange(int first, int max) {
        return em.createQuery("SELECT t FROM TipoAsiento t", TipoAsiento.class)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    public TipoAsiento findById(final Integer idTipoAsiento) {
        if (idTipoAsiento == null || idTipoAsiento <= 0) {
            throw new IllegalArgumentException("ID de tipo de asiento no válido");
        }
        return em.find(TipoAsiento.class, idTipoAsiento);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<TipoAsiento> findAll() {
        return getEntityManager().createNamedQuery("TipoAsiento.findAll", TipoAsiento.class).getResultList();
    }

    public List<TipoAsiento> findByName(String nombre) {
        return getEntityManager()
                .createNamedQuery("TipoAsiento.findByName", TipoAsiento.class)
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList();
    }

    public Long countActive() {
        return getEntityManager()
                .createNamedQuery("TipoAsiento.countActive", Long.class)
                .getSingleResult();
    }

    public List<TipoAsiento> findByExpresionRegular(String expresionRegular) {
        return getEntityManager()
                .createNamedQuery("TipoAsiento.findByExpresionRegular", TipoAsiento.class)
                .setParameter("expresionRegular", expresionRegular)
                .getResultList();
    }
}
