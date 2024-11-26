package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Pelicula;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.PeliculaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Programacion;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.Sala;

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

    public List<Pelicula> getAllPeliculas(int init , int max , String campo,String orden) {
        try {
            List<Pelicula> ps= em.createNamedQuery("Pelicula.findAll", Pelicula.class).setFirstResult(init).setMaxResults(max).getResultList();
            return ps;
        }catch (Exception ex){
            Logger.getLogger(SalaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return List.of();
    }

    public int countAllPeliculas() {
        try {
            Number result = (Number) em.createNamedQuery("Pelicula.countAll").getSingleResult();

            if (result == null) {
                return 0;
            }

            return result.intValue();
        } catch (Exception ex) {
            Logger.getLogger(SalaBean.class.getName()).log(Level.SEVERE, "Error al contar las peliculas", ex);
            return 0;
        }
    }

    public List<PeliculaCaracteristica> getCaracteristicasByPelicula(Pelicula pelicula) {
        try {
            return em.createNamedQuery("Caracteristica.findByPelicula", PeliculaCaracteristica.class)
                    .setParameter("peliculaId", pelicula.getIdPelicula())
                    .getResultList();
        } catch (Exception ex) {
            Logger.getLogger(PeliculaBean.class.getName()).log(Level.SEVERE, "Error al obtener características", ex);
            return List.of();
        }
    }

}