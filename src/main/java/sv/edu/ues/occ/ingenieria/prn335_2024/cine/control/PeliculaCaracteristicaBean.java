package sv.edu.ues.occ.ingenieria.prn335_2024.cine.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.PeliculaCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoPelicula;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class PeliculaCaracteristicaBean extends AbstractDataPersist<PeliculaCaracteristica> implements Serializable {
    @PersistenceContext(unitName = "cinePU")
    EntityManager em;

    public PeliculaCaracteristicaBean() {
        super(PeliculaCaracteristica.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public List<PeliculaCaracteristica> findByIdPelicula(final long idPelicula, int first, int last) {
        try {
            TypedQuery<PeliculaCaracteristica> q = em.createNamedQuery("PeliculaCaracteristica.findByIdPelicula", PeliculaCaracteristica.class);
            q.setParameter("idPelicula", idPelicula);
            q.setFirstResult(first);
            q.setMaxResults(last);
            return q.getResultList();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al buscar características de la película", e);
        }
        return List.of();
    }

    public List<TipoPelicula> findAllTiposPelicula() {
        try {
            TypedQuery<TipoPelicula> q = em.createNamedQuery("PeliculaCaracteristica.findAll", TipoPelicula.class);
            return q.getResultList();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al buscar todos los tipos de películas", e);
        }
        return List.of();
    }

    public int countPeliculaCaracteristica(final long idPelicula) {
        try {
            TypedQuery<Long> q = em.createNamedQuery("PeliculaCaracteristica.countByIdPelicula", Long.class);
            q.setParameter("idPelicula", idPelicula);
            return q.getSingleResult().intValue();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al contar las características de la película", e);
        }
        return 0;
    }
}
