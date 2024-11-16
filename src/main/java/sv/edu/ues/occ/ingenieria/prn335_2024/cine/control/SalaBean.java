package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;
import java.util.List;

@Stateless
public class SalaBean extends AbstractDataPersist<Sala> {

    @PersistenceContext(unitName = "cinePU")
    private EntityManager em;

    public SalaBean() {
        super(Sala.class); // Llamar al constructor de la clase base
    }

    @Override
    public EntityManager getEntityManager() {
        return em;  // Obtener el EntityManager asociado a este bean
    }

    // Crear nueva sala
    public void create(Sala sala) {
        em.persist(sala);
    }

    // Editar sala
    public void edit(Sala sala) {
        em.merge(sala);
    }

    // Eliminar sala
    public void remove(Sala sala) {
        sala = em.merge(sala);  // Asegura que la entidad está gestionada
        em.remove(sala);
    }

    // Buscar sala por nombre
    public List<Sala> findByName(String nombre) {
        return em.createNamedQuery("Sala.findByName", Sala.class)
                .setParameter("nombre", "%" + nombre + "%")
                .getResultList();
    }

    // Buscar todas las salas
    public List<Sala> findAll() {
        return getEntityManager().createNamedQuery("Sala.findAll", Sala.class)
                .getResultList();
    }

    // Contar salas activas
    public Long countActive() {
        return getEntityManager()
                .createNamedQuery("Sala.countActive", Long.class)
                .getSingleResult();
    }

    // Buscar salas por expresión regular
    public List<Sala> findByExpresionRegular(String expresionRegular) {
        return getEntityManager()
                .createNamedQuery("Sala.findByExpresionRegular", Sala.class)
                .setParameter("expresionRegular", expresionRegular)
                .getResultList();
    }

    // Buscar salas por ID de tipo de sala
    public List<Sala> findByIdTipoSala(Integer idTipoSala) {
        return getEntityManager()
                .createNamedQuery("Sala.findByIdTipoSala", Sala.class)
                .setParameter("idTipoSala", idTipoSala)
                .getResultList();
    }
}
