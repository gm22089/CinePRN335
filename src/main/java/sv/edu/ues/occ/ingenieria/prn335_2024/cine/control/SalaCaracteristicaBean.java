package sv.edu.ues.occ.ingenieria.prn335_2024.cine.boundary.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.SalaCaracteristica;

import java.util.List;

@Stateless
public class SalaCaracteristicaBean {

    @PersistenceContext(unitName = "cinePU")
    private EntityManager em;

    // Crear un nuevo registro
    public void create(SalaCaracteristica salaCaracteristica) {
        em.persist(salaCaracteristica);
    }

    // Modificar un registro existente
    public SalaCaracteristica update(SalaCaracteristica salaCaracteristica) {
        return em.merge(salaCaracteristica);
    }

    // Eliminar un registro
    public void delete(SalaCaracteristica salaCaracteristica) {
        salaCaracteristica = em.merge(salaCaracteristica);
        em.remove(salaCaracteristica);
    }

    // Buscar un registro por su ID
    public SalaCaracteristica find(Integer idSalaCaracteristica) {
        return em.find(SalaCaracteristica.class, idSalaCaracteristica);
    }

    // Obtener todos los registros
    public List<SalaCaracteristica> findAll() {
        return em.createQuery("SELECT s FROM SalaCaracteristica s", SalaCaracteristica.class).getResultList();
    }

    // Obtener un rango de registros (para paginación)
    public List<SalaCaracteristica> findRange(int first, int max) {
        return em.createQuery("SELECT s FROM SalaCaracteristica s", SalaCaracteristica.class)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    // Contar el total de registros
    public long count() {
        return em.createQuery("SELECT COUNT(s) FROM SalaCaracteristica s", Long.class)
                .getSingleResult();
    }
}