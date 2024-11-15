package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sucursal;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class SucursalBean extends AbstractDataPersist<Sucursal> implements Serializable {

    @PersistenceContext(unitName = "cinePU")
    private EntityManager em;

    public SucursalBean() {
        super(Sucursal.class);
    }

    @Override
    public Sucursal update(Sucursal registro) throws IllegalArgumentException {
        if (registro == null) {
            throw new IllegalArgumentException("El registro no puede ser nulo");
        }
        if (registro.getIdSucursal() == null || registro.getIdSucursal() <= 0) {
            throw new IllegalArgumentException("El ID del registro no es válido");
        }
        return em.merge(registro);
    }


    @Override
    public void create(Sucursal registro) throws IllegalStateException, IllegalArgumentException {
        if (registro == null) {
            throw new IllegalArgumentException("El registro no puede ser nulo");
        }
        super.create(registro);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Sucursal> findRange(int first, int max) {
        return em.createQuery("SELECT s FROM Sucursal s", Sucursal.class)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    public Sucursal findById(final Integer idSucursal) {
        if (idSucursal == null || idSucursal <= 0) {
            throw new IllegalArgumentException("ID de sucursal no válido");
        }
        return em.find(Sucursal.class, idSucursal);
    }

    public List<Sucursal> findAll() {
        return getEntityManager().createNamedQuery("Sucursal.findAll", Sucursal.class).getResultList();
    }

    public List<Sucursal> findByName(String nombre) {
        return getEntityManager()
                .createNamedQuery("Sucursal.findByNombre", Sucursal.class)
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList();
    }

    public Long countActive() {
        return getEntityManager()
                .createNamedQuery("Sucursal.findByActivo", Long.class)
                .setParameter("activo", true)
                .getSingleResult();
    }

    public List<Sucursal> findByCity(String ciudad) {
        return getEntityManager()
                .createNamedQuery("Sucursal.findByCity", Sucursal.class)
                .setParameter("ciudad", "%" + ciudad + "%")
                .getResultList();
    }



    public void delete(Sucursal registro) {
        if (registro == null) {
            throw new IllegalArgumentException("El registro no puede ser nulo");
        }
        em.remove(em.contains(registro) ? registro : em.merge(registro));
    }
}
