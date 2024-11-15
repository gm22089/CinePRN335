package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoReserva;

import java.util.List;

@Stateless
@LocalBean
public class TipoReservaBean extends AbstractDataPersist<TipoReserva> {

    @PersistenceContext(unitName = "cinePU")
    private EntityManager em;

    public TipoReservaBean() {
        super(TipoReserva.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    // Crea un nuevo registro TipoReserva
    @Override
    public void create(TipoReserva registro) throws IllegalStateException, IllegalArgumentException {
        super.create(registro);
    }

    // Encuentra un registro por ID
    @Override
    public TipoReserva findById(Object idTipoReserva) throws IllegalArgumentException, IllegalStateException {
        return super.findById(idTipoReserva);
    }

    // Encuentra todos los registros de TipoReserva
    public List<TipoReserva> findAll() {
        return em.createQuery("SELECT t FROM TipoReserva t WHERE t.activo = TRUE AND t.activo IS NOT NULL", TipoReserva.class).getResultList();
    }


        // Encuentra registros de TipoReserva dentro de un rango (para paginación)
    public List<TipoReserva> findRange(int first, int max) {
        return em.createQuery("SELECT t FROM TipoReserva t WHERE t.activo = TRUE", TipoReserva.class)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    // Contar el número total de registros activos de TipoReserva
    public Long countActive() {
        return getEntityManager()
                .createNamedQuery("TipoReserva.countActive", Long.class)
                .getSingleResult();
    }

    // Método adicional: Buscar registros de TipoReserva por nombre
    public List<TipoReserva> findByName(String nombre) {
        return getEntityManager()
                .createNamedQuery("TipoReserva.findByName", TipoReserva.class)
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList();
    }
}
