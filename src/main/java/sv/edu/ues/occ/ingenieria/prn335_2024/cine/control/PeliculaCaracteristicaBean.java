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
    private EntityManager em;

    public PeliculaCaracteristicaBean() {
        super(PeliculaCaracteristica.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(PeliculaCaracteristica registro) throws IllegalStateException, IllegalArgumentException {
        try {
            super.create(registro);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al crear la característica", e);
            throw new RuntimeException("Error al guardar la característica de la película", e);
        }
    }

    @Override
    public List<PeliculaCaracteristica> findRange(int first, int max) {
        try {
            return em.createQuery("SELECT p FROM PeliculaCaracteristica p", PeliculaCaracteristica.class)
                    .setFirstResult(first)
                    .setMaxResults(max)
                    .getResultList();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al obtener el rango de características", e);
            return List.of();
        }
    }

    public PeliculaCaracteristica findById(final Long idPeliculaCaracteristica) {
        if (idPeliculaCaracteristica == null || idPeliculaCaracteristica <= 0) {
            throw new IllegalArgumentException("ID de característica de película no válido");
        }
        return em.find(PeliculaCaracteristica.class, idPeliculaCaracteristica);
    }

    public List<PeliculaCaracteristica> findByIdPelicula(final long idPelicula, int first, int last) {
        try {
            TypedQuery<PeliculaCaracteristica> query = em.createNamedQuery("PeliculaCaracteristica.findByIdPelicula", PeliculaCaracteristica.class);
            query.setParameter("idPelicula", idPelicula);
            query.setFirstResult(first);
            query.setMaxResults(last);
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al buscar características de la película", e);
        }
        return List.of();
    }

    public List<TipoPelicula> findAllTiposPelicula() {
        try {
            TypedQuery<TipoPelicula> query = em.createNamedQuery("TipoPelicula.findAll", TipoPelicula.class); // Cambié el nombre de la consulta
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al buscar todos los tipos de películas", e);
        }
        return List.of();
    }

    public int countPeliculaCaracteristica(final long idPelicula) {
        try {
            TypedQuery<Long> query = em.createNamedQuery("PeliculaCaracteristica.countByIdPelicula", Long.class);
            query.setParameter("idPelicula", idPelicula);
            return query.getSingleResult().intValue();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al contar las características de la película", e);
        }
        return 0;
    }

    public List<PeliculaCaracteristica> findByExpresionRegular(String expresionRegular) {
        try {
            return em.createNamedQuery("PeliculaCaracteristica.findByExpresionRegular", PeliculaCaracteristica.class)
                    .setParameter("expresionRegular", expresionRegular)
                    .getResultList();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al buscar características por expresión regular", e);
        }
        return List.of();
    }

    public void remove(PeliculaCaracteristica registro) {
        try {
            if (registro != null) {
                em.remove(em.merge(registro));
            } else {
                throw new IllegalArgumentException("El registro no puede ser nulo");
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error al eliminar la característica", e);
            throw new RuntimeException("Error al eliminar la característica de la película", e);
        }
    }
}
