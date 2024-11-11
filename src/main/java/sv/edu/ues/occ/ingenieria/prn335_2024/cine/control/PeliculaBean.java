package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class PeliculaBean extends AbstractDataPersist<Pelicula> implements Serializable {
    @PersistenceContext(unitName = "cinePU")
    EntityManager em;

    public PeliculaBean() {
        super(Pelicula.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<Pelicula> getAllPeliculas(int init, int max, String campo, String orden) {
        try {
            return em.createNamedQuery("Pelicula.findAll", Pelicula.class)
                    .setFirstResult(init)
                    .setMaxResults(max)
                    .getResultList();
        } catch (Exception ex) {
            Logger.getLogger(PeliculaBean.class.getName()).log(Level.SEVERE, null, ex);
            return List.of();
        }
    }

    public int countAllPeliculas() {
        try {
            Number result = (Number) em.createNamedQuery("Pelicula.countAll").getSingleResult();
            return result != null ? result.intValue() : 0;
        } catch (Exception ex) {
            Logger.getLogger(PeliculaBean.class.getName()).log(Level.SEVERE, "Error al contar las peliculas", ex);
            return 0;
        }
    }
}
