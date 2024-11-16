package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sucursal;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class SalaBean extends AbstractDataPersist<Sala> implements Serializable {

    @PersistenceContext(unitName = "cinePU")
    private EntityManager em;

    // Constructor predeterminado
    public SalaBean() {
        super(Sala.class); // Se pasa la entidad gestionada al padre
    }

    /**
     * Crear una nueva sala en la base de datos.
     *
     * @param registro Objeto Sala a persistir.
     * @throws IllegalArgumentException si la sala es nula.
     */
   @Override
   public void create(Sala registro) throws IllegalStateException, IllegalArgumentException {
       if (registro == null) {
           throw new IllegalArgumentException("El registro no puede ser nulo");
       }
       super.create(registro);
   }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    /**
     * Obtener un rango de registros de Sala.
     *
     * @param first Índice inicial.
     * @param max   Número máximo de resultados.
     * @return Lista de salas.
     */
    @Override
    public List<Sala> findRange(int first, int max) {
        return em.createQuery("SELECT s FROM Sala s", Sala.class)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    public List<Programacion> getProgramacionByIdSala(Sala sala) {

        return List.of();
    }

    public List<Sala> getSalasBySucursal(Sucursal sucursal) {
        try {
            return em.createNamedQuery("Sala.findSalBySucursal", Sala.class).
                    setParameter("sucursal",sucursal ).getResultList();
        }catch (Exception ex){
            Logger.getLogger(SalaBean.class.getName()).log(Level.SEVERE, "error al buscar sucursales y salas", ex);
        }


        return List.of();
    }
}
