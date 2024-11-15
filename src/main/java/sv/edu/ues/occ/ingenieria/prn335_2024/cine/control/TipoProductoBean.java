package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoProducto;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class TipoProductoBean extends AbstractDataPersist<TipoProducto> implements Serializable {

    @PersistenceContext(unitName = "cinePU")
    private EntityManager em;

    public TipoProductoBean() {
        super(TipoProducto.class);
    }

    @Override
    public void create(TipoProducto registro) throws IllegalStateException, IllegalArgumentException {
        super.create(registro);
    }

    @Override
    public List<TipoProducto> findRange(int first, int max) {
        return em.createQuery("SELECT t FROM TipoProducto t", TipoProducto.class)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    public TipoProducto findById(final Integer idTipoProducto) {
        if (idTipoProducto == null || idTipoProducto <= 0) {
            throw new IllegalArgumentException("ID de tipo de producto no válido");
        }
        return em.find(TipoProducto.class, idTipoProducto);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<TipoProducto> findAll() {
        return getEntityManager().createQuery("SELECT t FROM TipoProducto t", TipoProducto.class).getResultList();
    }

    public List<TipoProducto> findByName(String nombre) {
        return getEntityManager()
                .createQuery("SELECT t FROM TipoProducto t WHERE t.nombre LIKE :nombre", TipoProducto.class)
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList();
    }

    public Long countActive() {
        return getEntityManager()
                .createQuery("SELECT COUNT(t) FROM TipoProducto t WHERE t.activo = true", Long.class)
                .getSingleResult();
    }
}
