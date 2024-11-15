package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPago;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class TipoPagoBean extends AbstractDataPersist<TipoPago> implements Serializable {

    @PersistenceContext(unitName = "cinePU")
    private EntityManager em;

    public TipoPagoBean() {
        super(TipoPago.class);
    }

    @Override
    public void create(TipoPago registro) throws IllegalStateException, IllegalArgumentException {
        // Asegurarse de que el ID no se establezca manualmente
        if (registro.getIdTipoPago() != null) {
            throw new IllegalArgumentException("No se debe establecer el ID manualmente");
        }
        super.create(registro);
    }

    @Override
    public List<TipoPago> findRange(int first, int max) {
        return em.createQuery("SELECT t FROM TipoPago t", TipoPago.class)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    public TipoPago findById(final Integer idTipoPago) {
        if (idTipoPago == null || idTipoPago <= 0) {
            throw new IllegalArgumentException("ID de tipo de pago no válido");
        }
        return em.find(TipoPago.class, idTipoPago);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<TipoPago> findAll() {
        return getEntityManager().createNamedQuery("TipoPago.findAll", TipoPago.class).getResultList();
    }

    public List<TipoPago> findByName(String nombre) {
        return getEntityManager()
                .createNamedQuery("TipoPago.findByNombre", TipoPago.class)
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList();
    }

    public Long countActive() {
        return getEntityManager()
                .createNamedQuery("TipoPago.findByActivo", Long.class)
                .getSingleResult();
    }


}
